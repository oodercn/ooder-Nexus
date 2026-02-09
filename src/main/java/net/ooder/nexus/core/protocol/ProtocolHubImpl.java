package net.ooder.nexus.core.protocol;

import net.ooder.nexus.core.protocol.model.CommandPacket;
import net.ooder.nexus.core.protocol.model.CommandResult;
import net.ooder.nexus.core.protocol.model.ProtocolStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 协议中枢实现�?
 */
@Service
public class ProtocolHubImpl implements ProtocolHub {

    private static final Logger logger = LoggerFactory.getLogger(ProtocolHubImpl.class);

    /**
     * 协议处理器注册表
     */
    private final Map<String, ProtocolHandler> handlers = new ConcurrentHashMap<>();

    /**
     * 协议统计信息
     */
    private final Map<String, ProtocolStats> statsMap = new ConcurrentHashMap<>();

    /**
     * 异步执行�?
     */
    private ExecutorService executorService;

    @PostConstruct
    public void initialize() {
        logger.info("Initializing ProtocolHub...");
        this.executorService = Executors.newFixedThreadPool(10);
        logger.info("ProtocolHub initialized successfully");
    }

    @PreDestroy
    public void destroy() {
        logger.info("Destroying ProtocolHub...");
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
        // �?毁所有协议处理器
        handlers.values().forEach(ProtocolHandler::destroy);
        handlers.clear();
        logger.info("ProtocolHub destroyed");
    }

    @Override
    public void registerProtocolHandler(String protocolType, ProtocolHandler handler) {
        if (protocolType == null || handler == null) {
            throw new IllegalArgumentException("Protocol type and handler cannot be null");
        }

        if (handlers.containsKey(protocolType)) {
            logger.warn("Protocol handler already registered for type: {}, will be replaced", protocolType);
            ProtocolHandler oldHandler = handlers.get(protocolType);
            oldHandler.destroy();
        }

        handlers.put(protocolType, handler);
        statsMap.put(protocolType, new ProtocolStats(protocolType));

        // 初始化处理器
        try {
            handler.initialize();
            logger.info("Registered and initialized protocol handler: {}", protocolType);
        } catch (Exception e) {
            logger.error("Failed to initialize protocol handler: {}", protocolType, e);
            handlers.remove(protocolType);
            statsMap.remove(protocolType);
            throw new RuntimeException("Failed to initialize protocol handler: " + protocolType, e);
        }
    }

    @Override
    public void unregisterProtocolHandler(String protocolType) {
        ProtocolHandler handler = handlers.remove(protocolType);
        if (handler != null) {
            try {
                handler.destroy();
                logger.info("Unregistered protocol handler: {}", protocolType);
            } catch (Exception e) {
                logger.error("Error destroying protocol handler: {}", protocolType, e);
            }
        }
        statsMap.remove(protocolType);
    }

    @Override
    public CommandResult handleCommand(CommandPacket packet) {
        if (packet == null || packet.getHeader() == null) {
            return CommandResult.error(null, 400, "Invalid command packet");
        }

        String protocolType = packet.getProtocolType();
        String commandId = packet.getHeader().getCommandId();

        if (protocolType == null) {
            return CommandResult.error(commandId, 400, "Protocol type is required");
        }

        ProtocolHandler handler = handlers.get(protocolType);
        if (handler == null) {
            logger.warn("No handler found for protocol type: {}", protocolType);
            return CommandResult.error(commandId, 404, "Protocol handler not found: " + protocolType);
        }

        // 更新统计信息
        ProtocolStats stats = statsMap.get(protocolType);
        if (stats != null) {
            stats.incrementTotalCommands();
        }

        long startTime = System.currentTimeMillis();
        CommandResult result;

        try {
            // 验证命令
            if (!handler.validateCommand(packet)) {
                logger.warn("Command validation failed: {}", commandId);
                result = CommandResult.error(commandId, 400, "Command validation failed");
                if (stats != null) {
                    stats.incrementFailedCommands();
                }
                return result;
            }

            // 处理命令
            result = handler.handleCommand(packet);
            long executionTime = System.currentTimeMillis() - startTime;
            result.setExecutionTime(executionTime);

            // 更新统计信息
            if (stats != null) {
                if (result.isSuccess()) {
                    stats.incrementSuccessCommands();
                } else {
                    stats.incrementFailedCommands();
                }
                // 更新平均响应时间
                updateAvgResponseTime(stats, executionTime);
            }

            logger.debug("Command processed: {}, time: {}ms, result: {}",
                    commandId, executionTime, result.isSuccess());

        } catch (Exception e) {
            logger.error("Error handling command: {}", commandId, e);
            result = CommandResult.error(commandId, 500, "Internal error: " + e.getMessage());
            if (stats != null) {
                stats.incrementFailedCommands();
            }
        }

        return result;
    }

    @Override
    public List<String> getSupportedProtocols() {
        return new ArrayList<>(handlers.keySet());
    }

    @Override
    public ProtocolStats getProtocolStats(String protocolType) {
        return statsMap.get(protocolType);
    }

    @Override
    public List<ProtocolStats> getAllProtocolStats() {
        return new ArrayList<>(statsMap.values());
    }

    @Override
    public boolean isProtocolRegistered(String protocolType) {
        return handlers.containsKey(protocolType);
    }

    @Override
    public ProtocolHandler getProtocolHandler(String protocolType) {
        return handlers.get(protocolType);
    }

    /**
     * 异步处理命令
     */
    public void handleCommandAsync(CommandPacket packet, CommandCallback callback) {
        executorService.submit(() -> {
            CommandResult result = handleCommand(packet);
            if (callback != null) {
                callback.onComplete(result);
            }
        });
    }

    /**
     * 更新平均响应时间
     */
    private void updateAvgResponseTime(ProtocolStats stats, long newTime) {
        double currentAvg = stats.getAvgResponseTime();
        long total = stats.getTotalCommands();
        double newAvg = (currentAvg * (total - 1) + newTime) / total;
        stats.setAvgResponseTime(newAvg);
    }

    /**
     * 命令回调接口
     */
    public interface CommandCallback {
        void onComplete(CommandResult result);
    }
}
