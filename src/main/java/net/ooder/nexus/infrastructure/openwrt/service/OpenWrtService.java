package net.ooder.nexus.infrastructure.openwrt.service;

import net.ooder.nexus.infrastructure.openwrt.bridge.OpenWrtBridge;
import net.ooder.nexus.infrastructure.openwrt.bridge.OpenWrtBridgeFactory;
import net.ooder.nexus.infrastructure.openwrt.config.OpenWrtProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;

/**
 * OpenWrt 服务类 - 提供 OpenWrt 路由器管理功能
 * 支持 Mock 模式和真实模式切换
 */
@Service
public class OpenWrtService {

    private static final Logger log = LoggerFactory.getLogger(OpenWrtService.class);

    private final OpenWrtProperties properties;
    private final OpenWrtMockService mockService;
    private OpenWrtBridge bridge;
    private boolean useMock = false;

    @Autowired
    public OpenWrtService(OpenWrtProperties properties, OpenWrtMockService mockService) {
        this.properties = properties;
        this.mockService = mockService;
    }

    @PostConstruct
    public void init() {
        log.info("Initializing OpenWrt Service");
        this.useMock = properties.isMockEnabled();

        if (useMock) {
            log.info("OpenWrt Service running in MOCK mode");
            this.bridge = mockService;
        } else if (properties.isEnabled()) {
            this.bridge = OpenWrtBridgeFactory.getInstance();
            log.info("OpenWrt Service initialized with real bridge");
        } else {
            log.info("OpenWrt Service is disabled");
        }
    }

    @PreDestroy
    public void destroy() {
        log.info("Destroying OpenWrt Service");
        if (bridge != null && bridge.isConnected() && !useMock) {
            bridge.disconnect();
        }
        log.info("OpenWrt Service destroyed");
    }

    /**
     * 切换 Mock 模式
     */
    public void setMockMode(boolean enabled) {
        this.useMock = enabled;
        if (enabled) {
            log.info("Switching to MOCK mode");
            if (bridge != null && bridge.isConnected() && !useMock) {
                bridge.disconnect();
            }
            this.bridge = mockService;
        } else {
            log.info("Switching to REAL mode");
            this.bridge = OpenWrtBridgeFactory.getInstance();
        }
    }

    /**
     * 检查是否使用 Mock 模式
     */
    public boolean isMockMode() {
        return useMock;
    }

    // ==================== 连接管理 ====================

    public boolean connect(String host, String username, String password) {
        log.info("Connecting to OpenWrt router: {}@{} (mock={})", username, host, useMock);
        return bridge.connect(host, username, password);
    }

    public void disconnect() {
        log.info("Disconnecting from OpenWrt router (mock={})", useMock);
        bridge.disconnect();
    }

    public boolean isConnected() {
        return bridge != null && bridge.isConnected();
    }

    // ==================== 网络设置管理 ====================

    public Map<String, Object> getNetworkSetting(String settingType) {
        return bridge.getNetworkSetting(settingType);
    }

    public Map<String, Object> getAllNetworkSettings() {
        return bridge.getAllNetworkSettings();
    }

    public Map<String, Object> updateNetworkSetting(String settingType, Map<String, Object> settingData) {
        return bridge.updateNetworkSetting(settingType, settingData);
    }

    public Map<String, Object> batchUpdateNetworkSettings(Map<String, Map<String, Object>> settingsData) {
        return bridge.batchUpdateNetworkSettings(settingsData);
    }

    // ==================== IP 地址管理 ====================

    public Map<String, Object> getIPAddresses(String type, String status) {
        return bridge.getIPAddresses(type, status);
    }

    public Map<String, Object> addStaticIPAddress(Map<String, Object> ipData) {
        return bridge.addStaticIPAddress(ipData);
    }

    public Map<String, Object> deleteIPAddress(String ipId) {
        return bridge.deleteIPAddress(ipId);
    }

    public Map<String, Object> batchAddStaticIPAddresses(List<Map<String, Object>> ipDataList) {
        return bridge.batchAddStaticIPAddresses(ipDataList);
    }

    public Map<String, Object> batchDeleteIPAddresses(List<String> ipIds) {
        return bridge.batchDeleteIPAddresses(ipIds);
    }

    // ==================== IP 黑名单管理 ====================

    public Map<String, Object> getIPBlacklist() {
        return bridge.getIPBlacklist();
    }

    public Map<String, Object> addIPToBlacklist(Map<String, Object> blacklistData) {
        return bridge.addIPToBlacklist(blacklistData);
    }

    public Map<String, Object> removeIPFromBlacklist(String blacklistId) {
        return bridge.removeIPFromBlacklist(blacklistId);
    }

    public Map<String, Object> batchAddIPToBlacklist(List<Map<String, Object>> blacklistDataList) {
        return bridge.batchAddIPToBlacklist(blacklistDataList);
    }

    public Map<String, Object> batchRemoveIPFromBlacklist(List<String> blacklistIds) {
        return bridge.batchRemoveIPFromBlacklist(blacklistIds);
    }

    // ==================== 配置文件管理 ====================

    public Map<String, Object> getConfigFile(String configFile) {
        return bridge.getConfigFile(configFile);
    }

    public Map<String, Object> updateConfigFile(String configFile, Map<String, Object> configData) {
        return bridge.updateConfigFile(configFile, configData);
    }

    // ==================== 系统操作 ====================

    public Map<String, Object> executeCommand(String command) {
        return bridge.executeCommand(command);
    }

    public Map<String, Object> reboot() {
        return bridge.reboot();
    }

    public Map<String, Object> getSystemStatus() {
        return bridge.getSystemStatus();
    }

    // ==================== 版本信息 ====================

    public Map<String, Object> getVersionInfo() {
        return bridge.getVersionInfo();
    }

    public boolean isVersionSupported(String version) {
        return bridge.isVersionSupported(version);
    }

    public Map<String, Object> getSupportedVersions() {
        return bridge.getSupportedVersions();
    }
}
