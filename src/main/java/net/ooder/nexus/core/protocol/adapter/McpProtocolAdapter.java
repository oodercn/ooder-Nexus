package net.ooder.nexus.core.protocol.adapter;

import net.ooder.nexus.core.protocol.model.CommandPacket;
import net.ooder.nexus.core.protocol.model.CommandResult;
import net.ooder.nexus.infrastructure.management.NexusManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * MCP协议适配�?
 * 处理MCP（Master Control Protocol）相关命�?
 */
@Component
public class McpProtocolAdapter extends AbstractProtocolAdapter {

    public static final String PROTOCOL_TYPE = "MCP";

    // MCP节点注册�?
    private final Map<String, McpNodeInfo> mcpNodes = new ConcurrentHashMap<>();

    @Autowired
    private NexusManager nexusManager;

    public McpProtocolAdapter() {
        super(PROTOCOL_TYPE);
        setDescription("Master Control Protocol Adapter - Handles MCP node registration, heartbeat, and status");
    }

    @PostConstruct
    public void postConstruct() {
        // 可以在这里进行额外的初始�?
        logger.info("McpProtocolAdapter constructed, NexusManager: {}", nexusManager != null ? "available" : "null");
    }

    @Override
    protected void initializeSupportedCommands() {
        addSupportedCommand("MCP_REGISTER");
        addSupportedCommand("MCP_DEREGISTER");
        addSupportedCommand("MCP_HEARTBEAT");
        addSupportedCommand("MCP_STATUS");
        addSupportedCommand("MCP_DISCOVER");
        addSupportedCommand("MCP_CONFIG");
    }

    @Override
    protected void doInitialize() {
        logger.info("MCP adapter initializing with {} supported commands", getSupportedCommands().size());
    }

    @Override
    protected void doDestroy() {
        // 清理MCP节点注册�?
        mcpNodes.clear();
        logger.info("MCP nodes registry cleared");
    }

    @Override
    protected boolean doValidateCommand(CommandPacket packet) {
        String commandType = packet.getCommandType();
        Map<String, Object> payload = packet.getPayload();

        switch (commandType) {
            case "MCP_REGISTER":
                return validateRegisterCommand(payload);
            case "MCP_HEARTBEAT":
                return validateHeartbeatCommand(payload);
            case "MCP_STATUS":
                return validateStatusCommand(payload);
            default:
                return true;
        }
    }

    private boolean validateRegisterCommand(Map<String, Object> payload) {
        if (payload == null) {
            logger.warn("MCP_REGISTER payload is null");
            return false;
        }
        Object nodeId = payload.get("nodeId");
        if (nodeId == null || nodeId.toString().isEmpty()) {
            logger.warn("MCP_REGISTER missing nodeId");
            return false;
        }
        return true;
    }

    private boolean validateHeartbeatCommand(Map<String, Object> payload) {
        if (payload == null) {
            logger.warn("MCP_HEARTBEAT payload is null");
            return false;
        }
        Object nodeId = payload.get("nodeId");
        if (nodeId == null || nodeId.toString().isEmpty()) {
            logger.warn("MCP_HEARTBEAT missing nodeId");
            return false;
        }
        return true;
    }

    private boolean validateStatusCommand(Map<String, Object> payload) {
        // 状�?�上报可以是空payload，表示查询所有状�?
        return true;
    }

    @Override
    protected CommandResult doHandleCommand(CommandPacket packet) {
        String commandType = packet.getCommandType();
        String commandId = packet.getHeader().getCommandId();
        Map<String, Object> payload = packet.getPayload();

        switch (commandType) {
            case "MCP_REGISTER":
                return handleMcpRegister(commandId, payload);
            case "MCP_DEREGISTER":
                return handleMcpDeregister(commandId, payload);
            case "MCP_HEARTBEAT":
                return handleMcpHeartbeat(commandId, payload);
            case "MCP_STATUS":
                return handleMcpStatus(commandId, payload);
            case "MCP_DISCOVER":
                return handleMcpDiscover(commandId, payload);
            case "MCP_CONFIG":
                return handleMcpConfig(commandId, payload);
            default:
                return CommandResult.error(commandId, 400, "Unknown command type: " + commandType);
        }
    }

    private CommandResult handleMcpRegister(String commandId, Map<String, Object> payload) {
        String nodeId = payload.get("nodeId").toString();
        String nodeName = payload.get("nodeName") != null ? payload.get("nodeName").toString() : nodeId;
        String nodeType = payload.get("nodeType") != null ? payload.get("nodeType").toString() : "MCP";

        McpNodeInfo nodeInfo = new McpNodeInfo();
        nodeInfo.setNodeId(nodeId);
        nodeInfo.setNodeName(nodeName);
        nodeInfo.setNodeType(nodeType);
        nodeInfo.setRegisterTime(System.currentTimeMillis());
        nodeInfo.setLastHeartbeatTime(System.currentTimeMillis());
        nodeInfo.setStatus("ONLINE");
        nodeInfo.setCapabilities((Map<String, Object>) payload.get("capabilities"));

        mcpNodes.put(nodeId, nodeInfo);

        logger.info("MCP node registered: {} ({})", nodeId, nodeName);

        // 注册到NexusManager
        if (nexusManager != null) {
            nexusManager.registerNetworkNode(nodeId, nodeInfo.toMap());
        }

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("nodeId", nodeId);
        resultData.put("status", "REGISTERED");
        resultData.put("timestamp", System.currentTimeMillis());
        return CommandResult.success(commandId, resultData);
    }

    private CommandResult handleMcpDeregister(String commandId, Map<String, Object> payload) {
        String nodeId = payload.get("nodeId").toString();

        McpNodeInfo removed = mcpNodes.remove(nodeId);
        if (removed != null) {
            logger.info("MCP node deregistered: {}", nodeId);

            // 从NexusManager移除
            if (nexusManager != null) {
                nexusManager.removeNetworkNode(nodeId);
            }

            Map<String, Object> resultData = new HashMap<>();
            resultData.put("nodeId", nodeId);
            resultData.put("status", "DEREGISTERED");
            return CommandResult.success(commandId, resultData);
        } else {
            return CommandResult.error(commandId, 404, "Node not found: " + nodeId);
        }
    }

    private CommandResult handleMcpHeartbeat(String commandId, Map<String, Object> payload) {
        String nodeId = payload.get("nodeId").toString();

        McpNodeInfo nodeInfo = mcpNodes.get(nodeId);
        if (nodeInfo == null) {
            return CommandResult.error(commandId, 404, "Node not registered: " + nodeId);
        }

        nodeInfo.setLastHeartbeatTime(System.currentTimeMillis());
        nodeInfo.setStatus("ONLINE");

        // 更新节点状�??
        if (nexusManager != null) {
            // 使用registerNetworkNode来更新节点状�?
            nexusManager.registerNetworkNode(nodeId, nodeInfo.toMap());
        }

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("nodeId", nodeId);
        resultData.put("status", "HEARTBEAT_OK");
        resultData.put("timestamp", System.currentTimeMillis());
        return CommandResult.success(commandId, resultData);
    }

    private CommandResult handleMcpStatus(String commandId, Map<String, Object> payload) {
        if (payload != null && payload.get("nodeId") != null) {
            // 查询特定节点状�??
            String nodeId = payload.get("nodeId").toString();
            McpNodeInfo nodeInfo = mcpNodes.get(nodeId);
            if (nodeInfo == null) {
                return CommandResult.error(commandId, 404, "Node not found: " + nodeId);
            }
            Map<String, Object> resultData = new HashMap<>();
            resultData.put("nodeId", nodeId);
            resultData.put("status", nodeInfo.getStatus());
            resultData.put("lastHeartbeat", nodeInfo.getLastHeartbeatTime());
            return CommandResult.success(commandId, resultData);
        } else {
            // 返回�?有节点状�?
            long onlineCount = mcpNodes.values().stream()
                    .filter(n -> "ONLINE".equals(n.getStatus()))
                    .count();

            List<Map<String, Object>> nodesList = mcpNodes.values().stream()
                    .map(McpNodeInfo::toMap)
                    .collect(Collectors.toList());

            Map<String, Object> resultData = new HashMap<>();
            resultData.put("totalNodes", mcpNodes.size());
            resultData.put("onlineNodes", onlineCount);
            resultData.put("nodes", nodesList);
            return CommandResult.success(commandId, resultData);
        }
    }

    private CommandResult handleMcpDiscover(String commandId, Map<String, Object> payload) {
        // 设备发现逻辑
        logger.info("MCP discover command received");

        // 这里可以实现扫描网络中的MCP节点
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("discoveredNodes", mcpNodes.size());
        resultData.put("nodes", new ArrayList<>(mcpNodes.keySet()));
        return CommandResult.success(commandId, resultData);
    }

    private CommandResult handleMcpConfig(String commandId, Map<String, Object> payload) {
        // 配置下发逻辑
        String nodeId = payload != null && payload.get("nodeId") != null ?
                payload.get("nodeId").toString() : null;

        if (nodeId != null && !mcpNodes.containsKey(nodeId)) {
            return CommandResult.error(commandId, 404, "Node not found: " + nodeId);
        }

        logger.info("MCP config command received for node: {}", nodeId);

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("nodeId", nodeId);
        resultData.put("configApplied", true);
        resultData.put("timestamp", System.currentTimeMillis());
        return CommandResult.success(commandId, resultData);
    }

    /**
     * 获取MCP节点信息
     */
    public McpNodeInfo getMcpNode(String nodeId) {
        return mcpNodes.get(nodeId);
    }

    /**
     * 获取�?有MCP节点
     */
    public Map<String, McpNodeInfo> getAllMcpNodes() {
        return new ConcurrentHashMap<>(mcpNodes);
    }

    /**
     * MCP节点信息内部�?
     */
    public static class McpNodeInfo {
        private String nodeId;
        private String nodeName;
        private String nodeType;
        private long registerTime;
        private long lastHeartbeatTime;
        private String status;
        private Map<String, Object> capabilities;

        public Map<String, Object> toMap() {
            Map<String, Object> map = new ConcurrentHashMap<>();
            map.put("nodeId", nodeId);
            map.put("nodeName", nodeName);
            map.put("nodeType", nodeType);
            map.put("registerTime", registerTime);
            map.put("lastHeartbeatTime", lastHeartbeatTime);
            map.put("status", status);
            map.put("capabilities", capabilities);
            return map;
        }

        // Getters and Setters
        public String getNodeId() { return nodeId; }
        public void setNodeId(String nodeId) { this.nodeId = nodeId; }
        public String getNodeName() { return nodeName; }
        public void setNodeName(String nodeName) { this.nodeName = nodeName; }
        public String getNodeType() { return nodeType; }
        public void setNodeType(String nodeType) { this.nodeType = nodeType; }
        public long getRegisterTime() { return registerTime; }
        public void setRegisterTime(long registerTime) { this.registerTime = registerTime; }
        public long getLastHeartbeatTime() { return lastHeartbeatTime; }
        public void setLastHeartbeatTime(long lastHeartbeatTime) { this.lastHeartbeatTime = lastHeartbeatTime; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public Map<String, Object> getCapabilities() { return capabilities; }
        public void setCapabilities(Map<String, Object> capabilities) { this.capabilities = capabilities; }
    }
}
