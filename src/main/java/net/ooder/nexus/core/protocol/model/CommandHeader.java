package net.ooder.nexus.core.protocol.model;

import java.io.Serializable;

/**
 * 命令头部信息
 */
public class CommandHeader implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 协议版本
     */
    private String version = "2.0";

    /**
     * 协议类型（MCP, ROUTE, END�?
     */
    private String protocolType;

    /**
     * 命令类型
     */
    private String commandType;

    /**
     * 命令唯一标识
     */
    private String commandId;

    /**
     * 时间�?
     */
    private long timestamp;

    /**
     * 源节点ID
     */
    private String sourceId;

    /**
     * 目标节点ID
     */
    private String targetId;

    /**
     * 优先级（HIGH, NORMAL, LOW�?
     */
    private String priority = "NORMAL";

    /**
     * 生存时间（秒�?
     */
    private int ttl = 30;

    public CommandHeader() {
        this.timestamp = System.currentTimeMillis();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    @Override
    public String toString() {
        return "CommandHeader{" +
                "version='" + version + '\'' +
                ", protocolType='" + protocolType + '\'' +
                ", commandType='" + commandType + '\'' +
                ", commandId='" + commandId + '\'' +
                ", timestamp=" + timestamp +
                ", sourceId='" + sourceId + '\'' +
                ", targetId='" + targetId + '\'' +
                ", priority='" + priority + '\'' +
                ", ttl=" + ttl +
                '}';
    }
}
