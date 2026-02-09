package net.ooder.nexus.domain.config.model;

import java.io.Serializable;

/**
 * �?有配置结�?
 * 用于ConfigController中getAllConfigs方法的返回类�?
 */
public class AllConfigsResult implements Serializable {
    private NexusConfigResult nexus;
    private RouteConfigResult route;
    private EndConfigResult end;

    public NexusConfigResult getNexus() {
        return nexus;
    }

    public void setNexus(NexusConfigResult nexus) {
        this.nexus = nexus;
    }

    public RouteConfigResult getRoute() {
        return route;
    }

    public void setRoute(RouteConfigResult route) {
        this.route = route;
    }

    public EndConfigResult getEnd() {
        return end;
    }

    public void setEnd(EndConfigResult end) {
        this.end = end;
    }
}