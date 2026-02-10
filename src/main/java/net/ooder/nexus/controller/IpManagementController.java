package net.ooder.nexus.controller;

import net.ooder.nexus.common.model.ApiResponse;
import net.ooder.nexus.domain.network.model.IpAllocation;
import net.ooder.nexus.domain.network.model.IpPool;
import net.ooder.nexus.service.IpManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * IP管理控制器
 */
@RestController
@RequestMapping("/api/ip")
public class IpManagementController {

    private static final Logger log = LoggerFactory.getLogger(IpManagementController.class);

    @Autowired
    private IpManagementService ipManagementService;

    /**
     * 获取所有IP分配
     */
    @GetMapping("/allocations")
    public ApiResponse<List<IpAllocation>> getAllAllocations() {
        try {
            List<IpAllocation> allocations = ipManagementService.getAllAllocations();
            return ApiResponse.success(allocations);
        } catch (Exception e) {
            log.error("获取IP分配列表失败", e);
            return ApiResponse.error("获取IP分配列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据类型获取IP分配
     */
    @GetMapping("/allocations/type/{type}")
    public ApiResponse<List<IpAllocation>> getAllocationsByType(@PathVariable String type) {
        try {
            List<IpAllocation> allocations = ipManagementService.getAllocationsByType(type);
            return ApiResponse.success(allocations);
        } catch (Exception e) {
            log.error("获取IP分配列表失败", e);
            return ApiResponse.error("获取IP分配列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据状态获取IP分配
     */
    @GetMapping("/allocations/status/{status}")
    public ApiResponse<List<IpAllocation>> getAllocationsByStatus(@PathVariable String status) {
        try {
            List<IpAllocation> allocations = ipManagementService.getAllocationsByStatus(status);
            return ApiResponse.success(allocations);
        } catch (Exception e) {
            log.error("获取IP分配列表失败", e);
            return ApiResponse.error("获取IP分配列表失败: " + e.getMessage());
        }
    }

    /**
     * 创建IP分配
     */
    @PostMapping("/allocations")
    public ApiResponse<IpAllocation> createAllocation(@RequestBody IpAllocation allocation) {
        try {
            IpAllocation created = ipManagementService.createAllocation(allocation);
            return ApiResponse.success("200", "IP分配创建成功", created);
        } catch (Exception e) {
            log.error("创建IP分配失败", e);
            return ApiResponse.error("创建IP分配失败: " + e.getMessage());
        }
    }

    /**
     * 更新IP分配
     */
    @PutMapping("/allocations/{id}")
    public ApiResponse<IpAllocation> updateAllocation(@PathVariable String id, @RequestBody IpAllocation allocation) {
        try {
            IpAllocation updated = ipManagementService.updateAllocation(id, allocation);
            if (updated != null) {
                return ApiResponse.success("200", "IP分配更新成功", updated);
            } else {
                return ApiResponse.error("IP分配不存在");
            }
        } catch (Exception e) {
            log.error("更新IP分配失败", e);
            return ApiResponse.error("更新IP分配失败: " + e.getMessage());
        }
    }

    /**
     * 删除IP分配
     */
    @DeleteMapping("/allocations/{id}")
    public ApiResponse<String> deleteAllocation(@PathVariable String id) {
        try {
            boolean deleted = ipManagementService.deleteAllocation(id);
            if (deleted) {
                return ApiResponse.success("200", "IP分配删除成功");
            } else {
                return ApiResponse.error("IP分配不存在");
            }
        } catch (Exception e) {
            log.error("删除IP分配失败", e);
            return ApiResponse.error("删除IP分配失败: " + e.getMessage());
        }
    }

    /**
     * 获取IP池配置
     */
    @GetMapping("/pool")
    public ApiResponse<IpPool> getIpPool() {
        try {
            IpPool pool = ipManagementService.getIpPool();
            return ApiResponse.success(pool);
        } catch (Exception e) {
            log.error("获取IP池配置失败", e);
            return ApiResponse.error("获取IP池配置失败: " + e.getMessage());
        }
    }

    /**
     * 更新IP池配置
     */
    @PutMapping("/pool")
    public ApiResponse<IpPool> updateIpPool(@RequestBody IpPool pool) {
        try {
            IpPool updated = ipManagementService.updateIpPool(pool);
            return ApiResponse.success("200", "IP池配置更新成功", updated);
        } catch (Exception e) {
            log.error("更新IP池配置失败", e);
            return ApiResponse.error("更新IP池配置失败: " + e.getMessage());
        }
    }

    /**
     * 获取IP统计信息
     */
    @GetMapping("/statistics")
    public ApiResponse<IpManagementService.IpStatistics> getStatistics() {
        try {
            IpManagementService.IpStatistics stats = ipManagementService.getStatistics();
            return ApiResponse.success(stats);
        } catch (Exception e) {
            log.error("获取IP统计信息失败", e);
            return ApiResponse.error("获取IP统计信息失败: " + e.getMessage());
        }
    }

    /**
     * 初始化默认数据
     */
    @PostMapping("/init-default")
    public ApiResponse<String> initDefaultData() {
        try {
            ipManagementService.initDefaultData();
            return ApiResponse.success("200", "默认数据初始化成功");
        } catch (Exception e) {
            log.error("初始化默认数据失败", e);
            return ApiResponse.error("初始化默认数据失败: " + e.getMessage());
        }
    }
}
