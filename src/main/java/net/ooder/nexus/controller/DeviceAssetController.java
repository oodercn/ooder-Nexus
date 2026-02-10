package net.ooder.nexus.controller;

import net.ooder.nexus.common.model.ApiResponse;
import net.ooder.nexus.domain.device.model.DeviceAsset;
import net.ooder.nexus.service.DeviceAssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备资产控制器
 */
@RestController
@RequestMapping("/api/devices")
public class DeviceAssetController {

    private static final Logger log = LoggerFactory.getLogger(DeviceAssetController.class);

    @Autowired
    private DeviceAssetService deviceService;

    /**
     * 获取所有设备
     */
    @GetMapping
    public ApiResponse<List<DeviceAsset>> getAllDevices() {
        try {
            List<DeviceAsset> devices = deviceService.getAllDevices();
            return ApiResponse.success(devices);
        } catch (Exception e) {
            log.error("获取设备列表失败", e);
            return ApiResponse.error("获取设备列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取设备
     */
    @GetMapping("/{id}")
    public ApiResponse<DeviceAsset> getDeviceById(@PathVariable String id) {
        try {
            DeviceAsset device = deviceService.getDeviceById(id);
            if (device == null) {
                return ApiResponse.error("设备不存在");
            }
            return ApiResponse.success(device);
        } catch (Exception e) {
            log.error("获取设备详情失败", e);
            return ApiResponse.error("获取设备详情失败: " + e.getMessage());
        }
    }

    /**
     * 创建设备
     */
    @PostMapping
    public ApiResponse<DeviceAsset> createDevice(@RequestBody DeviceAsset device) {
        try {
            DeviceAsset created = deviceService.createDevice(device);
            return ApiResponse.success("200", "设备创建成功", created);
        } catch (Exception e) {
            log.error("创建设备失败", e);
            return ApiResponse.error("创建设备失败: " + e.getMessage());
        }
    }

    /**
     * 更新设备
     */
    @PutMapping("/{id}")
    public ApiResponse<DeviceAsset> updateDevice(@PathVariable String id, @RequestBody DeviceAsset device) {
        try {
            DeviceAsset updated = deviceService.updateDevice(id, device);
            if (updated == null) {
                return ApiResponse.error("设备不存在");
            }
            return ApiResponse.success("200", "设备更新成功", updated);
        } catch (Exception e) {
            log.error("更新设备失败", e);
            return ApiResponse.error("更新设备失败: " + e.getMessage());
        }
    }

    /**
     * 删除设备
     */
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteDevice(@PathVariable String id) {
        try {
            boolean deleted = deviceService.deleteDevice(id);
            if (deleted) {
                return ApiResponse.success("200", "设备删除成功");
            } else {
                return ApiResponse.error("设备不存在");
            }
        } catch (Exception e) {
            log.error("删除设备失败", e);
            return ApiResponse.error("删除设备失败: " + e.getMessage());
        }
    }

    /**
     * 根据类型获取设备
     */
    @GetMapping("/type/{type}")
    public ApiResponse<List<DeviceAsset>> getDevicesByType(@PathVariable String type) {
        try {
            List<DeviceAsset> devices = deviceService.getDevicesByType(type);
            return ApiResponse.success(devices);
        } catch (Exception e) {
            log.error("获取设备列表失败", e);
            return ApiResponse.error("获取设备列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据状态获取设备
     */
    @GetMapping("/status/{status}")
    public ApiResponse<List<DeviceAsset>> getDevicesByStatus(@PathVariable String status) {
        try {
            List<DeviceAsset> devices = deviceService.getDevicesByStatus(status);
            return ApiResponse.success(devices);
        } catch (Exception e) {
            log.error("获取设备列表失败", e);
            return ApiResponse.error("获取设备列表失败: " + e.getMessage());
        }
    }

    /**
     * 获取设备统计信息
     */
    @GetMapping("/stats")
    public ApiResponse<DeviceAssetService.DeviceStats> getDeviceStats() {
        try {
            DeviceAssetService.DeviceStats stats = deviceService.getDeviceStats();
            return ApiResponse.success(stats);
        } catch (Exception e) {
            log.error("获取设备统计信息失败", e);
            return ApiResponse.error("获取设备统计信息失败: " + e.getMessage());
        }
    }

    /**
     * 初始化默认数据
     */
    @PostMapping("/init-default")
    public ApiResponse<String> initDefaultData() {
        try {
            deviceService.initDefaultData();
            return ApiResponse.success("200", "默认数据初始化成功");
        } catch (Exception e) {
            log.error("初始化默认数据失败", e);
            return ApiResponse.error("初始化默认数据失败: " + e.getMessage());
        }
    }
}
