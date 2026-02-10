package net.ooder.nexus.controller;

import net.ooder.nexus.common.model.ApiResponse;
import net.ooder.nexus.domain.network.model.TrafficStats;
import net.ooder.nexus.service.TrafficStatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流量统计控制器
 */
@RestController
@RequestMapping("/api/traffic")
public class TrafficStatsController {

    private static final Logger log = LoggerFactory.getLogger(TrafficStatsController.class);

    @Autowired
    private TrafficStatsService trafficStatsService;

    /**
     * 获取所有设备流量统计
     */
    @GetMapping("/stats")
    public ApiResponse<List<TrafficStats>> getAllTrafficStats() {
        try {
            List<TrafficStats> stats = trafficStatsService.getAllTrafficStats();
            return ApiResponse.success(stats);
        } catch (Exception e) {
            log.error("获取流量统计失败", e);
            return ApiResponse.error("获取流量统计失败: " + e.getMessage());
        }
    }

    /**
     * 获取带宽汇总信息
     */
    @GetMapping("/summary")
    public ApiResponse<TrafficStatsService.BandwidthSummary> getBandwidthSummary() {
        try {
            TrafficStatsService.BandwidthSummary summary = trafficStatsService.getBandwidthSummary();
            return ApiResponse.success(summary);
        } catch (Exception e) {
            log.error("获取带宽汇总失败", e);
            return ApiResponse.error("获取带宽汇总失败: " + e.getMessage());
        }
    }

    /**
     * 获取带宽趋势数据
     */
    @GetMapping("/trend")
    public ApiResponse<List<TrafficStatsService.BandwidthTrend>> getBandwidthTrend(
            @RequestParam(defaultValue = "24h") String period) {
        try {
            List<TrafficStatsService.BandwidthTrend> trends = trafficStatsService.getBandwidthTrend(period);
            return ApiResponse.success(trends);
        } catch (Exception e) {
            log.error("获取带宽趋势失败", e);
            return ApiResponse.error("获取带宽趋势失败: " + e.getMessage());
        }
    }

    /**
     * 更新流量统计
     */
    @PostMapping("/stats")
    public ApiResponse<TrafficStats> updateTrafficStats(@RequestBody TrafficStats stats) {
        try {
            TrafficStats updated = trafficStatsService.updateTrafficStats(stats);
            return ApiResponse.success("200", "流量统计更新成功", updated);
        } catch (Exception e) {
            log.error("更新流量统计失败", e);
            return ApiResponse.error("更新流量统计失败: " + e.getMessage());
        }
    }

    /**
     * 初始化默认数据
     */
    @PostMapping("/init-default")
    public ApiResponse<String> initDefaultData() {
        try {
            trafficStatsService.initDefaultData();
            return ApiResponse.success("200", "默认数据初始化成功");
        } catch (Exception e) {
            log.error("初始化默认数据失败", e);
            return ApiResponse.error("初始化默认数据失败: " + e.getMessage());
        }
    }
}
