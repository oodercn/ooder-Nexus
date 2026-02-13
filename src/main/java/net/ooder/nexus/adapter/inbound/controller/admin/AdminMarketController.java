package net.ooder.nexus.adapter.inbound.controller.admin;

import net.ooder.config.ResultModel;
import net.ooder.config.ListResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/market")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
public class AdminMarketController {

    private static final Logger log = LoggerFactory.getLogger(AdminMarketController.class);

    private final Map<String, Map<String, Object>> enterpriseAPIs = new LinkedHashMap<>();

    public AdminMarketController() {
        enterpriseAPIs.put("ea-001", createEnterpriseAPI("ea-001", "数据同步API", "sync", "active", 5000));
        enterpriseAPIs.put("ea-002", createEnterpriseAPI("ea-002", "批量处理API", "batch", "active", 3000));
        enterpriseAPIs.put("ea-003", createEnterpriseAPI("ea-003", "实时监控API", "monitor", "inactive", 1500));
    }

    @PostMapping("/skills/list")
    @ResponseBody
    public ListResultModel<List<Map<String, Object>>> getMarketSkills() {
        ListResultModel<List<Map<String, Object>>> result = new ListResultModel<>();
        try {
            List<Map<String, Object>> skills = new ArrayList<>();
            skills.add(createMarketSkill("ms-001", "数据分析套件", "data", "published", 156, 4.8));
            skills.add(createMarketSkill("ms-002", "自动化运维工具", "ops", "published", 89, 4.5));
            skills.add(createMarketSkill("ms-003", "AI图像处理", "ai", "reviewing", 45, 4.2));
            
            result.setData(skills);
            result.setSize(skills.size());
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取市场技能列表失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取市场技能列表失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/enterprise/list")
    @ResponseBody
    public ListResultModel<List<Map<String, Object>>> getEnterpriseAPIs() {
        ListResultModel<List<Map<String, Object>>> result = new ListResultModel<>();
        try {
            List<Map<String, Object>> apis = new ArrayList<>(enterpriseAPIs.values());
            result.setData(apis);
            result.setSize(apis.size());
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取企业API列表失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取企业API列表失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/enterprise/get")
    @ResponseBody
    public ResultModel<Map<String, Object>> getEnterpriseAPI(@RequestBody Map<String, String> request) {
        ResultModel<Map<String, Object>> result = new ResultModel<>();
        try {
            String id = request.get("id");
            Map<String, Object> api = enterpriseAPIs.get(id);
            if (api == null) {
                result.setRequestStatus(404);
                result.setMessage("API不存在");
                return result;
            }
            result.setData(api);
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取企业API详情失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取企业API详情失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/enterprise/update")
    @ResponseBody
    public ResultModel<Boolean> updateEnterpriseAPI(@RequestBody Map<String, Object> request) {
        ResultModel<Boolean> result = new ResultModel<>();
        try {
            String id = (String) request.get("id");
            Map<String, Object> api = enterpriseAPIs.get(id);
            if (api == null) {
                result.setRequestStatus(404);
                result.setMessage("API不存在");
                result.setData(false);
                return result;
            }
            
            if (request.get("name") != null) {
                api.put("name", request.get("name"));
            }
            if (request.get("type") != null) {
                api.put("type", request.get("type"));
            }
            if (request.get("status") != null) {
                api.put("status", request.get("status"));
            }
            
            result.setData(true);
            result.setRequestStatus(200);
            result.setMessage("更新成功");
        } catch (Exception e) {
            log.error("更新企业API失败", e);
            result.setRequestStatus(500);
            result.setMessage("更新企业API失败: " + e.getMessage());
            result.setData(false);
        }
        return result;
    }

    private Map<String, Object> createMarketSkill(String id, String name, String category, String status, int downloads, double rating) {
        Map<String, Object> skill = new LinkedHashMap<>();
        skill.put("id", id);
        skill.put("name", name);
        skill.put("category", category);
        skill.put("status", status);
        skill.put("downloads", downloads);
        skill.put("rating", rating);
        return skill;
    }

    private Map<String, Object> createEnterpriseAPI(String id, String name, String type, String status, int calls) {
        Map<String, Object> api = new LinkedHashMap<>();
        api.put("id", id);
        api.put("name", name);
        api.put("type", type);
        api.put("status", status);
        api.put("dailyCalls", calls);
        return api;
    }
}
