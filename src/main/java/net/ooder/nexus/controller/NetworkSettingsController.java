package net.ooder.nexus.controller;

import net.ooder.nexus.common.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络设置控制器
 */
@RestController
@RequestMapping("/api/network")
public class NetworkSettingsController {

    private static final Logger log = LoggerFactory.getLogger(NetworkSettingsController.class);

    // 模拟数据存储
    private Map<String, Object> basicSettings = new HashMap<>();
    private Map<String, Object> dnsSettings = new HashMap<>();
    private Map<String, Object> dhcpSettings = new HashMap<>();
    private Map<String, Object> wifiSettings = new HashMap<>();

    public NetworkSettingsController() {
        // 初始化默认设置
        initDefaultSettings();
    }

    private void initDefaultSettings() {
        // 基本设置
        basicSettings.put("networkName", "MyNetwork");
        basicSettings.put("domainName", "local");
        basicSettings.put("timezone", "Asia/Shanghai");
        basicSettings.put("ntpServer", "ntp.aliyun.com");

        // DNS设置
        dnsSettings.put("primaryDns", "223.5.5.5");
        dnsSettings.put("secondaryDns", "223.6.6.6");
        dnsSettings.put("dnsSuffix", "local");
        dnsSettings.put("dnsCacheEnabled", true);

        // DHCP设置
        dhcpSettings.put("dhcpEnabled", true);
        dhcpSettings.put("startIp", "192.168.1.100");
        dhcpSettings.put("endIp", "192.168.1.200");
        dhcpSettings.put("leaseTime", 86400);
        dhcpSettings.put("gateway", "192.168.1.1");
        dhcpSettings.put("subnetMask", "255.255.255.0");

        // WiFi设置
        wifiSettings.put("ssid", "Nexus_WiFi");
        wifiSettings.put("password", "");
        wifiSettings.put("channel", "auto");
        wifiSettings.put("mode", "802.11ac");
        wifiSettings.put("security", "WPA2-PSK");
        wifiSettings.put("hidden", false);
    }

    /**
     * 获取基本设置
     */
    @GetMapping("/settings/basic")
    public ApiResponse<Map<String, Object>> getBasicSettings() {
        try {
            return ApiResponse.success(basicSettings);
        } catch (Exception e) {
            log.error("获取基本设置失败", e);
            return ApiResponse.error("获取基本设置失败: " + e.getMessage());
        }
    }

    /**
     * 更新基本设置
     */
    @PutMapping("/settings/basic")
    public ApiResponse<Map<String, Object>> updateBasicSettings(@RequestBody Map<String, Object> settings) {
        try {
            basicSettings.putAll(settings);
            return ApiResponse.success("200", "基本设置更新成功", basicSettings);
        } catch (Exception e) {
            log.error("更新基本设置失败", e);
            return ApiResponse.error("更新基本设置失败: " + e.getMessage());
        }
    }

    /**
     * 获取DNS设置
     */
    @GetMapping("/settings/dns")
    public ApiResponse<Map<String, Object>> getDnsSettings() {
        try {
            return ApiResponse.success(dnsSettings);
        } catch (Exception e) {
            log.error("获取DNS设置失败", e);
            return ApiResponse.error("获取DNS设置失败: " + e.getMessage());
        }
    }

    /**
     * 更新DNS设置
     */
    @PutMapping("/settings/dns")
    public ApiResponse<Map<String, Object>> updateDnsSettings(@RequestBody Map<String, Object> settings) {
        try {
            dnsSettings.putAll(settings);
            return ApiResponse.success("200", "DNS设置更新成功", dnsSettings);
        } catch (Exception e) {
            log.error("更新DNS设置失败", e);
            return ApiResponse.error("更新DNS设置失败: " + e.getMessage());
        }
    }

    /**
     * 获取DHCP设置
     */
    @GetMapping("/settings/dhcp")
    public ApiResponse<Map<String, Object>> getDhcpSettings() {
        try {
            return ApiResponse.success(dhcpSettings);
        } catch (Exception e) {
            log.error("获取DHCP设置失败", e);
            return ApiResponse.error("获取DHCP设置失败: " + e.getMessage());
        }
    }

    /**
     * 更新DHCP设置
     */
    @PutMapping("/settings/dhcp")
    public ApiResponse<Map<String, Object>> updateDhcpSettings(@RequestBody Map<String, Object> settings) {
        try {
            dhcpSettings.putAll(settings);
            return ApiResponse.success("200", "DHCP设置更新成功", dhcpSettings);
        } catch (Exception e) {
            log.error("更新DHCP设置失败", e);
            return ApiResponse.error("更新DHCP设置失败: " + e.getMessage());
        }
    }

    /**
     * 获取WiFi设置
     */
    @GetMapping("/settings/wifi")
    public ApiResponse<Map<String, Object>> getWifiSettings() {
        try {
            return ApiResponse.success(wifiSettings);
        } catch (Exception e) {
            log.error("获取WiFi设置失败", e);
            return ApiResponse.error("获取WiFi设置失败: " + e.getMessage());
        }
    }

    /**
     * 更新WiFi设置
     */
    @PutMapping("/settings/wifi")
    public ApiResponse<Map<String, Object>> updateWifiSettings(@RequestBody Map<String, Object> settings) {
        try {
            wifiSettings.putAll(settings);
            return ApiResponse.success("200", "WiFi设置更新成功", wifiSettings);
        } catch (Exception e) {
            log.error("更新WiFi设置失败", e);
            return ApiResponse.error("更新WiFi设置失败: " + e.getMessage());
        }
    }
}
