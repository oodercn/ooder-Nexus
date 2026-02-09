package net.ooder.nexus.domain.end.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 设备列表结果
 * 用于DeviceController中getDevices方法的返回类�?
 */
public class DeviceListResult implements Serializable {
    private List<Map<String, Object>> devices;
    private Map<String, Object> stats;

    public DeviceListResult() {
    }

    public DeviceListResult(List<Map<String, Object>> devices, Map<String, Object> stats) {
        this.devices = devices;
        this.stats = stats;
    }

    public List<Map<String, Object>> getDevices() {
        return devices;
    }

    public void setDevices(List<Map<String, Object>> devices) {
        this.devices = devices;
    }

    public Map<String, Object> getStats() {
        return stats;
    }

    public void setStats(Map<String, Object> stats) {
        this.stats = stats;
    }
}