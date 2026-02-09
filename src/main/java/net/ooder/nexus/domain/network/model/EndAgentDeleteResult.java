package net.ooder.nexus.domain.network.model;

/**
 * 终端代理删除结果实体Bean
 * 用于EndAgentController中deleteEndAgent方法的返回类�?
 */
public class EndAgentDeleteResult {
    
    private String agentId;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
