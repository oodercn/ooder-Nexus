package net.ooder.nexus.adapter.inbound.controller.admin;

import net.ooder.config.ResultModel;
import net.ooder.config.ListResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/groups")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
public class AdminGroupController {

    private static final Logger log = LoggerFactory.getLogger(AdminGroupController.class);

    private final Map<String, Map<String, Object>> groups = new LinkedHashMap<>();

    public AdminGroupController() {
        groups.put("g-001", createGroup("g-001", "开发团队", "开发相关技能共享", 8, "active"));
        groups.put("g-002", createGroup("g-002", "数据分析组", "数据分析工具和脚本", 5, "active"));
        groups.put("g-003", createGroup("g-003", "运维团队", "运维自动化工具", 6, "active"));
    }

    @PostMapping("/list")
    @ResponseBody
    public ListResultModel<List<Map<String, Object>>> getGroupList() {
        ListResultModel<List<Map<String, Object>>> result = new ListResultModel<>();
        try {
            List<Map<String, Object>> groupList = new ArrayList<>(groups.values());
            result.setData(groupList);
            result.setSize(groupList.size());
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取群组列表失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取群组列表失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/get")
    @ResponseBody
    public ResultModel<Map<String, Object>> getGroup(@RequestBody Map<String, String> request) {
        ResultModel<Map<String, Object>> result = new ResultModel<>();
        try {
            String id = request.get("id");
            Map<String, Object> group = groups.get(id);
            if (group == null) {
                result.setRequestStatus(404);
                result.setMessage("群组不存在");
                return result;
            }
            result.setData(group);
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取群组详情失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取群组详情失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/create")
    @ResponseBody
    public ResultModel<Map<String, Object>> createGroup(@RequestBody Map<String, String> request) {
        ResultModel<Map<String, Object>> result = new ResultModel<>();
        try {
            String name = request.get("name");
            String description = request.get("description");
            
            String id = "g-" + System.currentTimeMillis();
            Map<String, Object> group = createGroup(id, name, description, 1, "active");
            groups.put(id, group);
            
            result.setData(group);
            result.setRequestStatus(200);
            result.setMessage("创建成功");
        } catch (Exception e) {
            log.error("创建群组失败", e);
            result.setRequestStatus(500);
            result.setMessage("创建群组失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResultModel<Boolean> deleteGroup(@RequestBody Map<String, String> request) {
        ResultModel<Boolean> result = new ResultModel<>();
        try {
            String id = request.get("id");
            Map<String, Object> removed = groups.remove(id);
            
            result.setData(removed != null);
            result.setRequestStatus(removed != null ? 200 : 404);
            result.setMessage(removed != null ? "删除成功" : "群组不存在");
        } catch (Exception e) {
            log.error("删除群组失败", e);
            result.setRequestStatus(500);
            result.setMessage("删除群组失败: " + e.getMessage());
            result.setData(false);
        }
        return result;
    }

    @PostMapping("/update")
    @ResponseBody
    public ResultModel<Boolean> updateGroup(@RequestBody Map<String, Object> request) {
        ResultModel<Boolean> result = new ResultModel<>();
        try {
            String id = (String) request.get("id");
            Map<String, Object> group = groups.get(id);
            if (group == null) {
                result.setRequestStatus(404);
                result.setMessage("群组不存在");
                result.setData(false);
                return result;
            }
            
            if (request.get("name") != null) {
                group.put("name", request.get("name"));
            }
            if (request.get("description") != null) {
                group.put("description", request.get("description"));
            }
            
            result.setData(true);
            result.setRequestStatus(200);
            result.setMessage("更新成功");
        } catch (Exception e) {
            log.error("更新群组失败", e);
            result.setRequestStatus(500);
            result.setMessage("更新群组失败: " + e.getMessage());
            result.setData(false);
        }
        return result;
    }

    private Map<String, Object> createGroup(String id, String name, String description, int memberCount, String status) {
        Map<String, Object> group = new LinkedHashMap<>();
        group.put("id", id);
        group.put("name", name);
        group.put("description", description);
        group.put("memberCount", memberCount);
        group.put("status", status);
        group.put("createdAt", "2026-02-01");
        return group;
    }
}
