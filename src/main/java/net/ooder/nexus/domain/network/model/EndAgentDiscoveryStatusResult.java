package net.ooder.nexus.domain.network.model;

/**
 * 终端代理发现状�?�结果实体Bean
 * 用于EndAgentController中getDiscoveryStatus方法的返回类�?
 */
public class EndAgentDiscoveryStatusResult {
    
    private boolean inProgress;
    private long lastDiscoveryTime;
    private String lastDiscoveryFormatted;
    private int agentCount;

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public long getLastDiscoveryTime() {
        return lastDiscoveryTime;
    }

    public void setLastDiscoveryTime(long lastDiscoveryTime) {
        this.lastDiscoveryTime = lastDiscoveryTime;
    }

    public String getLastDiscoveryFormatted() {
        return lastDiscoveryFormatted;
    }

    public void setLastDiscoveryFormatted(String lastDiscoveryFormatted) {
        this.lastDiscoveryFormatted = lastDiscoveryFormatted;
    }

    public int getAgentCount() {
        return agentCount;
    }

    public void setAgentCount(int agentCount) {
        this.agentCount = agentCount;
    }
}
