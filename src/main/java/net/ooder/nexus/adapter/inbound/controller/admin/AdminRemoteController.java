package net.ooder.nexus.adapter.inbound.controller.admin;

import net.ooder.config.ResultModel;
import net.ooder.config.ListResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/remote")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
public class AdminRemoteController {

    private static final Logger log = LoggerFactory.getLogger(AdminRemoteController.class);

    @PostMapping("/skills/list")
    @ResponseBody
    public ListResultModel<List<Map<String, Object>>> getRemoteSkills() {
        ListResultModel<List<Map<String, Object>>> result = new ListResultModel<>();
        try {
            List<Map<String, Object>> skills = new ArrayList<>();
            skills.add(createRemoteSkill("rs-001", "云端数据分析", "cloud", "synced", "2026-02-12"));
            skills.add(createRemoteSkill("rs-002", "远程监控工具", "edge", "pending", "2026-02-11"));
            skills.add(createRemoteSkill("rs-003", "分布式处理", "hybrid", "synced", "2026-02-10"));
            
            result.setData(skills);
            result.setSize(skills.size());
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取远程技能列表失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取远程技能列表失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/hosting/list")
    @ResponseBody
    public ListResultModel<List<Map<String, Object>>> getHostingList() {
        ListResultModel<List<Map<String, Object>>> result = new ListResultModel<>();
        try {
            List<Map<String, Object>> hosting = new ArrayList<>();
            hosting.add(createHosting("h-001", "AWS 托管", "aws", "active", "us-east-1"));
            hosting.add(createHosting("h-002", "阿里云托管", "aliyun", "active", "cn-shanghai"));
            hosting.add(createHosting("h-003", "本地边缘节点", "edge", "inactive", "local"));
            
            result.setData(hosting);
            result.setSize(hosting.size());
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取托管列表失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取托管列表失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/monitoring/list")
    @ResponseBody
    public ListResultModel<List<Map<String, Object>>> getMonitoringList() {
        ListResultModel<List<Map<String, Object>>> result = new ListResultModel<>();
        try {
            List<Map<String, Object>> monitoring = new ArrayList<>();
            monitoring.add(createMonitoring("m-001", "主节点监控", "health", "normal", 99.9));
            monitoring.add(createMonitoring("m-002", "性能监控", "performance", "warning", 85.5));
            monitoring.add(createMonitoring("m-003", "安全监控", "security", "normal", 100.0));
            
            result.setData(monitoring);
            result.setSize(monitoring.size());
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取监控列表失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取监控列表失败: " + e.getMessage());
        }
        return result;
    }

    private Map<String, Object> createRemoteSkill(String id, String name, String type, String status, String date) {
        Map<String, Object> skill = new LinkedHashMap<>();
        skill.put("id", id);
        skill.put("name", name);
        skill.put("type", type);
        skill.put("status", status);
        skill.put("lastSync", date);
        return skill;
    }

    private Map<String, Object> createHosting(String id, String name, String provider, String status, String region) {
        Map<String, Object> hosting = new LinkedHashMap<>();
        hosting.put("id", id);
        hosting.put("name", name);
        hosting.put("provider", provider);
        hosting.put("status", status);
        hosting.put("region", region);
        return hosting;
    }

    private Map<String, Object> createMonitoring(String id, String name, String type, String status, double score) {
        Map<String, Object> monitoring = new LinkedHashMap<>();
        monitoring.put("id", id);
        monitoring.put("name", name);
        monitoring.put("type", type);
        monitoring.put("status", status);
        monitoring.put("score", score);
        return monitoring;
    }
}
