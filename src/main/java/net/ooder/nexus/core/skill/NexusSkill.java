package net.ooder.nexus.core.skill;

import net.ooder.sdk.AgentSDK;
import net.ooder.sdk.network.packet.CommandPacket;

/**
 * Nexus�?能接�?
 */
public interface NexusSkill {
    
    /**
     * 初始化技�?
     * @param sdk AgentSDK实例
     */
    void initialize(AgentSDK sdk);
    
    /**
     * 处理MCP注册命令
     * @param packet 命令�?
     */
    void handleMcpRegisterCommand(CommandPacket packet);
    
    /**
     * 处理MCP注销命令
     * @param packet 命令�?
     */
    void handleMcpDeregisterCommand(CommandPacket packet);
    
    /**
     * 处理MCP心跳命令
     * @param packet 命令�?
     */
    void handleMcpHeartbeatCommand(CommandPacket packet);
    
    /**
     * 处理MCP状�?�查询命�?
     * @param packet 命令�?
     */
    void handleMcpStatusCommand(CommandPacket packet);
    
    /**
     * 处理MCP发现命令
     * @param packet 命令�?
     */
    void handleMcpDiscoverCommand(CommandPacket packet);
    
    /**
     * 处理路由查询命令
     * @param packet 命令�?
     */
    void handleRouteQueryCommand(CommandPacket packet);
    
    /**
     * 处理路由更新命令
     * @param packet 命令�?
     */
    void handleRouteUpdateCommand(CommandPacket packet);
    
    /**
     * 处理终端发现命令
     * @param packet 命令�?
     */
    void handleEndagentDiscoverCommand(CommandPacket packet);
    
    /**
     * 处理终端状�?�查询命�?
     * @param packet 命令�?
     */
    void handleEndagentStatusCommand(CommandPacket packet);
    
    /**
     * 处理终端添加命令
     * @param packet 命令�?
     */
    void handleEndagentAddCommand(CommandPacket packet);
    
    /**
     * 处理终端移除命令
     * @param packet 命令�?
     */
    void handleEndagentRemoveCommand(CommandPacket packet);
    
    /**
     * 处理任务请求命令
     * @param packet 命令�?
     */
    void handleTaskRequestCommand(CommandPacket packet);
    
    /**
     * 处理任务响应命令
     * @param packet 命令�?
     */
    void handleTaskResponseCommand(CommandPacket packet);
    
    /**
     * 处理认证命令
     * @param packet 命令�?
     */
    void handleAuthenticateCommand(CommandPacket packet);
    
    /**
     * 处理认证响应命令
     * @param packet 命令�?
     */
    void handleAuthResponseCommand(CommandPacket packet);
    
    /**
     * 启动�?�?
     */
    void start();
    
    /**
     * 停止�?�?
     */
    void stop();
}