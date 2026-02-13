package net.ooder.nexus.adapter.inbound.controller.admin;

import net.ooder.config.ResultModel;
import net.ooder.config.ListResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/dashboard")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
public class AdminDashboardController {

    private static final Logger log = LoggerFactory.getLogger(AdminDashboardController.class);

    @PostMapping("/stats")
    @ResponseBody
    public ResultModel<Map<String, Object>> getDashboardStats() {
        ResultModel<Map<String, Object>> result = new ResultModel<>();
        try {
            Map<String, Object> stats = new LinkedHashMap<>();
            
            stats.put("totalUsers", 156);
            stats.put("activeUsers", 42);
            stats.put("totalSkills", 89);
            stats.put("pendingSkills", 5);
            stats.put("totalGroups", 23);
            stats.put("totalExecutions", 1250);
            stats.put("successRate", 98.5);
            stats.put("avgResponseTime", 125);
            
            List<Map<String, Object>> recentActivities = new ArrayList<>();
            recentActivities.add(createActivity("user", "新用户注册", "张三", "5分钟前"));
            recentActivities.add(createActivity("skill", "技能发布", "数据分析工具", "10分钟前"));
            recentActivities.add(createActivity("group", "群组创建", "开发团队", "30分钟前"));
            recentActivities.add(createActivity("execution", "技能执行", "批量处理", "1小时前"));
            stats.put("recentActivities", recentActivities);
            
            List<Map<String, Object>> chartData = new ArrayList<>();
            for (int i = 6; i >= 0; i--) {
                Map<String, Object> day = new LinkedHashMap<>();
                day.put("date", i + "天前");
                day.put("executions", (int)(Math.random() * 100) + 50);
                day.put("users", (int)(Math.random() * 20) + 5);
                chartData.add(day);
            }
            stats.put("chartData", chartData);
            
            result.setData(stats);
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取管理仪表盘统计失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取统计失败: " + e.getMessage());
        }
        return result;
    }

    private Map<String, Object> createActivity(String type, String action, String target, String time) {
        Map<String, Object> activity = new LinkedHashMap<>();
        activity.put("type", type);
        activity.put("action", action);
        activity.put("target", target);
        activity.put("time", time);
        return activity;
    }
}
