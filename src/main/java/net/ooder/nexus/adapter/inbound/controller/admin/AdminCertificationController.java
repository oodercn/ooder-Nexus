package net.ooder.nexus.adapter.inbound.controller.admin;

import net.ooder.config.ResultModel;
import net.ooder.config.ListResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/certification")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
public class AdminCertificationController {

    private static final Logger log = LoggerFactory.getLogger(AdminCertificationController.class);

    @PostMapping("/skills/list")
    @ResponseBody
    public ListResultModel<List<Map<String, Object>>> getCertificationSkills() {
        ListResultModel<List<Map<String, Object>>> result = new ListResultModel<>();
        try {
            List<Map<String, Object>> skills = new ArrayList<>();
            skills.add(createCertSkill("cs-001", "安全扫描工具", "security", "certified", "2026-02-10"));
            skills.add(createCertSkill("cs-002", "数据加密模块", "crypto", "pending", null));
            skills.add(createCertSkill("cs-003", "身份认证服务", "auth", "certified", "2026-02-08"));
            
            result.setData(skills);
            result.setSize(skills.size());
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取认证技能列表失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取认证技能列表失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/certificates/list")
    @ResponseBody
    public ListResultModel<List<Map<String, Object>>> getCertificateList() {
        ListResultModel<List<Map<String, Object>>> result = new ListResultModel<>();
        try {
            List<Map<String, Object>> certificates = new ArrayList<>();
            certificates.add(createCertificate("cert-001", "ISO 27001", "security", "valid", "2027-02-10"));
            certificates.add(createCertificate("cert-002", "SOC 2 Type II", "compliance", "valid", "2026-08-15"));
            certificates.add(createCertificate("cert-003", "GDPR 合规", "privacy", "expiring", "2026-03-01"));
            
            result.setData(certificates);
            result.setSize(certificates.size());
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取证书列表失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取证书列表失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/validations/list")
    @ResponseBody
    public ListResultModel<List<Map<String, Object>>> getValidationList() {
        ListResultModel<List<Map<String, Object>>> result = new ListResultModel<>();
        try {
            List<Map<String, Object>> validations = new ArrayList<>();
            validations.add(createValidation("v-001", "安全审计", "security", "passed", "2026-02-12"));
            validations.add(createValidation("v-002", "性能测试", "performance", "passed", "2026-02-11"));
            validations.add(createValidation("v-003", "渗透测试", "security", "in_progress", null));
            
            result.setData(validations);
            result.setSize(validations.size());
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取验证列表失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取验证列表失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/certify")
    @ResponseBody
    public ResultModel<Map<String, Object>> certifySkill(@RequestBody Map<String, String> request) {
        ResultModel<Map<String, Object>> result = new ResultModel<>();
        try {
            String skillId = request.get("skillId");
            String level = request.getOrDefault("level", "standard");
            
            Map<String, Object> certification = new LinkedHashMap<>();
            certification.put("skillId", skillId);
            certification.put("level", level);
            certification.put("certifiedAt", new Date().toString());
            certification.put("validUntil", "2027-02-13");
            
            result.setData(certification);
            result.setRequestStatus(200);
            result.setMessage("认证成功");
        } catch (Exception e) {
            log.error("认证技能失败", e);
            result.setRequestStatus(500);
            result.setMessage("认证技能失败: " + e.getMessage());
        }
        return result;
    }

    private Map<String, Object> createCertSkill(String id, String name, String type, String status, String date) {
        Map<String, Object> skill = new LinkedHashMap<>();
        skill.put("id", id);
        skill.put("name", name);
        skill.put("type", type);
        skill.put("status", status);
        skill.put("certifiedAt", date);
        return skill;
    }

    private Map<String, Object> createCertificate(String id, String name, String type, String status, String expiry) {
        Map<String, Object> cert = new LinkedHashMap<>();
        cert.put("id", id);
        cert.put("name", name);
        cert.put("type", type);
        cert.put("status", status);
        cert.put("expiryDate", expiry);
        return cert;
    }

    private Map<String, Object> createValidation(String id, String name, String type, String status, String date) {
        Map<String, Object> validation = new LinkedHashMap<>();
        validation.put("id", id);
        validation.put("name", name);
        validation.put("type", type);
        validation.put("status", status);
        validation.put("completedAt", date);
        return validation;
    }
}
