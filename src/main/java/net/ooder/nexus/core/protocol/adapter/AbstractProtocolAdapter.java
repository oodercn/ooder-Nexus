package net.ooder.nexus.core.protocol.adapter;

import net.ooder.nexus.core.protocol.model.CommandPacket;
import net.ooder.nexus.core.protocol.model.CommandResult;
import net.ooder.nexus.core.protocol.model.ProtocolStatus;
import net.ooder.nexus.core.protocol.ProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * 协议适配器抽象基�?
 */
public abstract class AbstractProtocolAdapter implements ProtocolHandler {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 协议类型
     */
    protected final String protocolType;

    /**
     * 协议状�??
     */
    protected final ProtocolStatus status;

    /**
     * 支持的命令类�?
     */
    protected final Set<String> supportedCommands = new HashSet<>();

    /**
     * 处理器描�?
     */
    protected String description;

    public AbstractProtocolAdapter(String protocolType) {
        this.protocolType = protocolType;
        this.status = new ProtocolStatus(protocolType);
        this.description = protocolType + " Protocol Adapter";
        initializeSupportedCommands();
    }

    /**
     * 初始化支持的命令类型
     */
    protected abstract void initializeSupportedCommands();

    @Override
    public String getProtocolType() {
        return protocolType;
    }

    @Override
    public ProtocolStatus getStatus() {
        return status;
    }

    @Override
    public boolean supportsCommand(String commandType) {
        return supportedCommands.contains(commandType);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void initialize() {
        logger.info("Initializing {} adapter...", protocolType);
        try {
            doInitialize();
            status.markRunning();
            logger.info("{} adapter initialized successfully", protocolType);
        } catch (Exception e) {
            status.markError(e.getMessage());
            logger.error("Failed to initialize {} adapter", protocolType, e);
            throw new RuntimeException("Failed to initialize adapter: " + protocolType, e);
        }
    }

    /**
     * 子类实现具体的初始化逻辑
     */
    protected abstract void doInitialize();

    @Override
    public void destroy() {
        logger.info("Destroying {} adapter...", protocolType);
        try {
            doDestroy();
            status.markStopped();
            logger.info("{} adapter destroyed", protocolType);
        } catch (Exception e) {
            logger.error("Error destroying {} adapter", protocolType, e);
        }
    }

    /**
     * 子类实现具体的销毁�?�辑
     */
    protected abstract void doDestroy();

    @Override
    public boolean validateCommand(CommandPacket packet) {
        if (packet == null || packet.getHeader() == null) {
            logger.warn("Invalid command packet: null or missing header");
            return false;
        }

        String cmdProtocolType = packet.getProtocolType();
        if (!protocolType.equals(cmdProtocolType)) {
            logger.warn("Protocol type mismatch: expected {}, got {}", protocolType, cmdProtocolType);
            return false;
        }

        String commandType = packet.getCommandType();
        if (commandType == null || commandType.isEmpty()) {
            logger.warn("Command type is empty");
            return false;
        }

        if (!supportsCommand(commandType)) {
            logger.warn("Unsupported command type: {}", commandType);
            return false;
        }

        return doValidateCommand(packet);
    }

    /**
     * 子类实现具体的命令验证�?�辑
     */
    protected abstract boolean doValidateCommand(CommandPacket packet);

    @Override
    public CommandResult handleCommand(CommandPacket packet) {
        String commandType = packet.getCommandType();
        String commandId = packet.getHeader().getCommandId();

        logger.debug("Handling {} command: {}", protocolType, commandId);

        try {
            CommandResult result = doHandleCommand(packet);
            logger.debug("Command {} processed: {}", commandId, result.isSuccess());
            return result;
        } catch (Exception e) {
            logger.error("Error handling command: {}", commandId, e);
            return CommandResult.error(commandId, 500, "Handler error: " + e.getMessage());
        }
    }

    /**
     * 子类实现具体的命令处理�?�辑
     */
    protected abstract CommandResult doHandleCommand(CommandPacket packet);

    /**
     * 获取支持的命令类型集�?
     */
    protected Set<String> getSupportedCommands() {
        return supportedCommands;
    }

    /**
     * 添加支持的命令类�?
     */
    protected void addSupportedCommand(String commandType) {
        supportedCommands.add(commandType);
    }

    /**
     * 设置描述
     */
    protected void setDescription(String description) {
        this.description = description;
    }
}
