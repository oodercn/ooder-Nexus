package net.ooder.nexus.model.protocol;

import java.io.Serializable;

/**
 * 协议统计信息
 */
public class ProtocolStats implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 协议类型
     */
    private String protocolType;

    /**
     * 总命令数
     */
    private long totalCommands;

    /**
     * 成功命令数
     */
    private long successCommands;

    /**
     * 失败命令数
     */
    private long failedCommands;

    /**
     * 平均响应时间（毫秒）
     */
    private double avgResponseTime;

    /**
     * 当前连接数
     */
    private int activeConnections;

    /**
     * 每秒命令数
     */
    private double commandsPerSecond;

    /**
     * 最后更新时间
     */
    private long lastUpdateTime;

    public ProtocolStats() {
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public ProtocolStats(String protocolType) {
        this();
        this.protocolType = protocolType;
    }

    /**
     * 计算成功率
     */
    public double getSuccessRate() {
        if (totalCommands == 0) {
            return 100.0;
        }
        return (double) successCommands / totalCommands * 100;
    }

    public void incrementTotalCommands() {
        this.totalCommands++;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public void incrementSuccessCommands() {
        this.successCommands++;
    }

    public void incrementFailedCommands() {
        this.failedCommands++;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public long getTotalCommands() {
        return totalCommands;
    }

    public void setTotalCommands(long totalCommands) {
        this.totalCommands = totalCommands;
    }

    public long getSuccessCommands() {
        return successCommands;
    }

    public void setSuccessCommands(long successCommands) {
        this.successCommands = successCommands;
    }

    public long getFailedCommands() {
        return failedCommands;
    }

    public void setFailedCommands(long failedCommands) {
        this.failedCommands = failedCommands;
    }

    public double getAvgResponseTime() {
        return avgResponseTime;
    }

    public void setAvgResponseTime(double avgResponseTime) {
        this.avgResponseTime = avgResponseTime;
    }

    public int getActiveConnections() {
        return activeConnections;
    }

    public void setActiveConnections(int activeConnections) {
        this.activeConnections = activeConnections;
    }

    public double getCommandsPerSecond() {
        return commandsPerSecond;
    }

    public void setCommandsPerSecond(double commandsPerSecond) {
        this.commandsPerSecond = commandsPerSecond;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "ProtocolStats{" +
                "protocolType='" + protocolType + '\'' +
                ", totalCommands=" + totalCommands +
                ", successCommands=" + successCommands +
                ", failedCommands=" + failedCommands +
                ", avgResponseTime=" + avgResponseTime +
                ", activeConnections=" + activeConnections +
                ", commandsPerSecond=" + commandsPerSecond +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}
