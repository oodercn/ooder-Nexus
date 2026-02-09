package net.ooder.nexus.domain.end.model;

import java.io.Serializable;

/**
 * EndAgent删除结果
 * 用于EndAgentController中deleteEndAgent方法的返回类�?
 */
public class EndAgentDeleteResult implements Serializable {
    private String agentId;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}