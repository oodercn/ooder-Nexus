package net.ooder.nexus.core.protocol;

import net.ooder.nexus.core.protocol.model.CommandPacket;
import net.ooder.nexus.core.protocol.model.CommandResult;
import net.ooder.nexus.core.protocol.model.ProtocolStatus;

/**
 * 协议处理器接�?
 * 处理特定南向协议的核心接�?
 */
public interface ProtocolHandler {

    /**
     * 获取协议类型
     *
     * @return 协议类型标识（如：MCP, ROUTE, END�?
     */
    String getProtocolType();

    /**
     * 处理命令
     *
     * @param packet 命令数据�?
     * @return 命令执行结果
     */
    CommandResult handleCommand(CommandPacket packet);

    /**
     * 验证命令合法�?
     *
     * @param packet 命令数据�?
     * @return 是否合法
     */
    boolean validateCommand(CommandPacket packet);

    /**
     * 获取协议状�??
     *
     * @return 协议状�??
     */
    ProtocolStatus getStatus();

    /**
     * 初始化协议处理器
     */
    void initialize();

    /**
     * �?毁协议处理器
     */
    void destroy();

    /**
     * 是否支持该命令类�?
     *
     * @param commandType 命令类型
     * @return 是否支持
     */
    boolean supportsCommand(String commandType);

    /**
     * 获取处理器描�?
     *
     * @return 描述信息
     */
    String getDescription();
}
