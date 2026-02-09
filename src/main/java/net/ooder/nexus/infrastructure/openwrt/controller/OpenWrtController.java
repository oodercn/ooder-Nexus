package net.ooder.nexus.infrastructure.openwrt.controller;

import net.ooder.nexus.infrastructure.openwrt.service.OpenWrtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OpenWrt REST API Controller - 提供 OpenWrt 路由器管理接口
 */
@RestController
@RequestMapping("/api/openwrt")
public class OpenWrtController {

    private static final Logger log = LoggerFactory.getLogger(OpenWrtController.class);

    private final OpenWrtService openWrtService;

    @Autowired
    public OpenWrtController(OpenWrtService openWrtService) {
        this.openWrtService = openWrtService;
    }

    // ==================== 连接管理 ====================

    @PostMapping("/connect")
    public ResponseEntity<Map<String, Object>> connect(@RequestBody Map<String, String> credentials) {
        log.info("Connecting to OpenWrt router");
        String host = credentials.getOrDefault("host", "192.168.1.1");
        String username = credentials.getOrDefault("username", "root");
        String password = credentials.getOrDefault("password", "");

        boolean connected = openWrtService.connect(host, username, password);

        Map<String, Object> response = new HashMap<>();
        response.put("status", connected ? "success" : "error");
        response.put("connected", connected);
        response.put("message", connected ? "Connected successfully" : "Failed to connect");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/disconnect")
    public ResponseEntity<Map<String, Object>> disconnect() {
        log.info("Disconnecting from OpenWrt router");
        openWrtService.disconnect();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Disconnected successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getConnectionStatus() {
        boolean connected = openWrtService.isConnected();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("connected", connected);

        return ResponseEntity.ok(response);
    }

    // ==================== 网络设置管理 ====================

    @GetMapping("/settings/{settingType}")
    public ResponseEntity<Map<String, Object>> getNetworkSetting(@PathVariable String settingType) {
        log.info("Getting network setting: {}", settingType);
        Map<String, Object> result = openWrtService.getNetworkSetting(settingType);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/settings")
    public ResponseEntity<Map<String, Object>> getAllNetworkSettings() {
        log.info("Getting all network settings");
        Map<String, Object> result = openWrtService.getAllNetworkSettings();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/settings/{settingType}")
    public ResponseEntity<Map<String, Object>> updateNetworkSetting(
            @PathVariable String settingType,
            @RequestBody Map<String, Object> settingData) {
        log.info("Updating network setting: {}", settingType);
        Map<String, Object> result = openWrtService.updateNetworkSetting(settingType, settingData);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/settings/batch")
    public ResponseEntity<Map<String, Object>> batchUpdateNetworkSettings(
            @RequestBody Map<String, Map<String, Object>> settingsData) {
        log.info("Batch updating network settings");
        Map<String, Object> result = openWrtService.batchUpdateNetworkSettings(settingsData);
        return ResponseEntity.ok(result);
    }

    // ==================== IP 地址管理 ====================

    @GetMapping("/ip-addresses")
    public ResponseEntity<Map<String, Object>> getIPAddresses(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status) {
        log.info("Getting IP addresses - type: {}, status: {}", type, status);
        Map<String, Object> result = openWrtService.getIPAddresses(type, status);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/ip-addresses")
    public ResponseEntity<Map<String, Object>> addStaticIPAddress(@RequestBody Map<String, Object> ipData) {
        log.info("Adding static IP address");
        Map<String, Object> result = openWrtService.addStaticIPAddress(ipData);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/ip-addresses/{ipId}")
    public ResponseEntity<Map<String, Object>> deleteIPAddress(@PathVariable String ipId) {
        log.info("Deleting IP address: {}", ipId);
        Map<String, Object> result = openWrtService.deleteIPAddress(ipId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/ip-addresses/batch")
    public ResponseEntity<Map<String, Object>> batchAddStaticIPAddresses(
            @RequestBody List<Map<String, Object>> ipDataList) {
        log.info("Batch adding static IP addresses");
        Map<String, Object> result = openWrtService.batchAddStaticIPAddresses(ipDataList);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/ip-addresses/batch")
    public ResponseEntity<Map<String, Object>> batchDeleteIPAddresses(@RequestBody List<String> ipIds) {
        log.info("Batch deleting IP addresses");
        Map<String, Object> result = openWrtService.batchDeleteIPAddresses(ipIds);
        return ResponseEntity.ok(result);
    }

    // ==================== IP 黑名单管理 ====================

    @GetMapping("/blacklist")
    public ResponseEntity<Map<String, Object>> getIPBlacklist() {
        log.info("Getting IP blacklist");
        Map<String, Object> result = openWrtService.getIPBlacklist();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/blacklist")
    public ResponseEntity<Map<String, Object>> addIPToBlacklist(@RequestBody Map<String, Object> blacklistData) {
        log.info("Adding IP to blacklist");
        Map<String, Object> result = openWrtService.addIPToBlacklist(blacklistData);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/blacklist/{blacklistId}")
    public ResponseEntity<Map<String, Object>> removeIPFromBlacklist(@PathVariable String blacklistId) {
        log.info("Removing IP from blacklist: {}", blacklistId);
        Map<String, Object> result = openWrtService.removeIPFromBlacklist(blacklistId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/blacklist/batch")
    public ResponseEntity<Map<String, Object>> batchAddIPToBlacklist(
            @RequestBody List<Map<String, Object>> blacklistDataList) {
        log.info("Batch adding IPs to blacklist");
        Map<String, Object> result = openWrtService.batchAddIPToBlacklist(blacklistDataList);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/blacklist/batch")
    public ResponseEntity<Map<String, Object>> batchRemoveIPFromBlacklist(@RequestBody List<String> blacklistIds) {
        log.info("Batch removing IPs from blacklist");
        Map<String, Object> result = openWrtService.batchRemoveIPFromBlacklist(blacklistIds);
        return ResponseEntity.ok(result);
    }

    // ==================== 配置文件管理 ====================

    @GetMapping("/config/{configFile}")
    public ResponseEntity<Map<String, Object>> getConfigFile(@PathVariable String configFile) {
        log.info("Getting config file: {}", configFile);
        Map<String, Object> result = openWrtService.getConfigFile(configFile);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/config/{configFile}")
    public ResponseEntity<Map<String, Object>> updateConfigFile(
            @PathVariable String configFile,
            @RequestBody Map<String, Object> configData) {
        log.info("Updating config file: {}", configFile);
        Map<String, Object> result = openWrtService.updateConfigFile(configFile, configData);
        return ResponseEntity.ok(result);
    }

    // ==================== 系统操作 ====================

    @PostMapping("/execute")
    public ResponseEntity<Map<String, Object>> executeCommand(@RequestBody Map<String, String> request) {
        String command = request.get("command");
        log.info("Executing command: {}", command);
        Map<String, Object> result = openWrtService.executeCommand(command);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/reboot")
    public ResponseEntity<Map<String, Object>> reboot() {
        log.info("Rebooting OpenWrt router");
        Map<String, Object> result = openWrtService.reboot();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/system-status")
    public ResponseEntity<Map<String, Object>> getSystemStatus() {
        log.info("Getting system status");
        Map<String, Object> result = openWrtService.getSystemStatus();
        return ResponseEntity.ok(result);
    }

    // ==================== 版本信息 ====================

    @GetMapping("/version")
    public ResponseEntity<Map<String, Object>> getVersionInfo() {
        log.info("Getting version info");
        Map<String, Object> result = openWrtService.getVersionInfo();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/version/supported")
    public ResponseEntity<Map<String, Object>> isVersionSupported(@RequestParam String version) {
        boolean supported = openWrtService.isVersionSupported(version);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("version", version);
        response.put("supported", supported);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/version/supported-list")
    public ResponseEntity<Map<String, Object>> getSupportedVersions() {
        Map<String, Object> result = openWrtService.getSupportedVersions();
        return ResponseEntity.ok(result);
    }

    // ==================== Mock 模式管理 ====================

    @GetMapping("/mock/status")
    public ResponseEntity<Map<String, Object>> getMockStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("mockEnabled", openWrtService.isMockMode());
        response.put("message", openWrtService.isMockMode() ? "Running in MOCK mode" : "Running in REAL mode");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/mock/enable")
    public ResponseEntity<Map<String, Object>> enableMockMode() {
        log.info("Enabling mock mode");
        openWrtService.setMockMode(true);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("mockEnabled", true);
        response.put("message", "Mock mode enabled");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/mock/disable")
    public ResponseEntity<Map<String, Object>> disableMockMode() {
        log.info("Disabling mock mode");
        openWrtService.setMockMode(false);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("mockEnabled", false);
        response.put("message", "Mock mode disabled, switched to real mode");
        return ResponseEntity.ok(response);
    }
}
