package net.ooder.nexus.adapter.inbound.controller.admin;

import net.ooder.config.ResultModel;
import net.ooder.config.ListResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/skills")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
public class AdminSkillController {

    private static final Logger log = LoggerFactory.getLogger(AdminSkillController.class);

    private final Map<String, Map<String, Object>> skills = new LinkedHashMap<>();

    public AdminSkillController() {
        skills.put("skill-001", createSkill("skill-001", "文本分析工具", "文本分析和处理", "文本处理", "approved", "user-001"));
        skills.put("skill-002", createSkill("skill-002", "数据处理脚本", "数据清洗和转换", "数据分析", "approved", "user-002"));
        skills.put("skill-003", createSkill("skill-003", "自动化部署工具", "CI/CD自动化部署", "开发工具", "pending", "user-003"));
        skills.put("skill-004", createSkill("skill-004", "日志分析器", "日志收集和分析", "系统管理", "approved", "user-001"));
        skills.put("skill-005", createSkill("skill-005", "网络监控工具", "网络状态监控", "网络工具", "approved", "user-002"));
    }

    @PostMapping("/list")
    @ResponseBody
    public ListResultModel<List<Map<String, Object>>> getSkillList() {
        ListResultModel<List<Map<String, Object>>> result = new ListResultModel<>();
        try {
            List<Map<String, Object>> skillList = new ArrayList<>(skills.values());
            result.setData(skillList);
            result.setSize(skillList.size());
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取技能列表失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取技能列表失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/get")
    @ResponseBody
    public ResultModel<Map<String, Object>> getSkill(@RequestBody Map<String, String> request) {
        ResultModel<Map<String, Object>> result = new ResultModel<>();
        try {
            String id = request.get("id");
            Map<String, Object> skill = skills.get(id);
            if (skill == null) {
                result.setRequestStatus(404);
                result.setMessage("技能不存在");
                return result;
            }
            result.setData(skill);
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取技能详情失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取技能详情失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResultModel<Boolean> deleteSkill(@RequestBody Map<String, String> request) {
        ResultModel<Boolean> result = new ResultModel<>();
        try {
            String id = request.get("id");
            Map<String, Object> removed = skills.remove(id);
            if (removed == null) {
                result.setRequestStatus(404);
                result.setMessage("技能不存在");
                result.setData(false);
                return result;
            }
            result.setData(true);
            result.setRequestStatus(200);
            result.setMessage("删除成功");
        } catch (Exception e) {
            log.error("删除技能失败", e);
            result.setRequestStatus(500);
            result.setMessage("删除技能失败: " + e.getMessage());
            result.setData(false);
        }
        return result;
    }

    @PostMapping("/approve")
    @ResponseBody
    public ResultModel<Boolean> approveSkill(@RequestBody Map<String, String> request) {
        ResultModel<Boolean> result = new ResultModel<>();
        try {
            String id = request.get("id");
            Map<String, Object> skill = skills.get(id);
            if (skill == null) {
                result.setRequestStatus(404);
                result.setMessage("技能不存在");
                result.setData(false);
                return result;
            }
            skill.put("status", "approved");
            result.setData(true);
            result.setRequestStatus(200);
            result.setMessage("审批通过");
        } catch (Exception e) {
            log.error("审批技能失败", e);
            result.setRequestStatus(500);
            result.setMessage("审批技能失败: " + e.getMessage());
            result.setData(false);
        }
        return result;
    }

    @PostMapping("/reject")
    @ResponseBody
    public ResultModel<Boolean> rejectSkill(@RequestBody Map<String, String> request) {
        ResultModel<Boolean> result = new ResultModel<>();
        try {
            String id = request.get("id");
            Map<String, Object> skill = skills.get(id);
            if (skill == null) {
                result.setRequestStatus(404);
                result.setMessage("技能不存在");
                result.setData(false);
                return result;
            }
            skill.put("status", "rejected");
            result.setData(true);
            result.setRequestStatus(200);
            result.setMessage("已拒绝");
        } catch (Exception e) {
            log.error("拒绝技能失败", e);
            result.setRequestStatus(500);
            result.setMessage("拒绝技能失败: " + e.getMessage());
            result.setData(false);
        }
        return result;
    }

    private Map<String, Object> createSkill(String id, String name, String description, String category, String status, String author) {
        Map<String, Object> skill = new LinkedHashMap<>();
        skill.put("id", id);
        skill.put("name", name);
        skill.put("description", description);
        skill.put("category", category);
        skill.put("status", status);
        skill.put("author", author);
        skill.put("downloads", (int)(Math.random() * 100));
        skill.put("rating", Math.round(Math.random() * 20) / 10.0 + 3.0);
        skill.put("createdAt", "2026-02-01");
        return skill;
    }
}
