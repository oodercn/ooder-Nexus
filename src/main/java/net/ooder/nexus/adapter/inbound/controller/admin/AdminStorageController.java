package net.ooder.nexus.adapter.inbound.controller.admin;

import net.ooder.config.ResultModel;
import net.ooder.config.ListResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/storage")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
public class AdminStorageController {

    private static final Logger log = LoggerFactory.getLogger(AdminStorageController.class);

    @PostMapping("/status")
    @ResponseBody
    public ResultModel<Map<String, Object>> getStorageStatus() {
        ResultModel<Map<String, Object>> result = new ResultModel<>();
        try {
            Map<String, Object> status = new LinkedHashMap<>();
            status.put("totalSpace", 500L * 1024 * 1024 * 1024);
            status.put("usedSpace", 150L * 1024 * 1024 * 1024);
            status.put("freeSpace", 350L * 1024 * 1024 * 1024);
            status.put("usagePercent", 30.0);
            status.put("fileCount", 1250);
            status.put("backupCount", 5);
            status.put("lastBackup", "2026-02-12 22:00:00");
            
            result.setData(status);
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取存储状态失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取存储状态失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/backups/list")
    @ResponseBody
    public ListResultModel<List<Map<String, Object>>> getBackupList() {
        ListResultModel<List<Map<String, Object>>> result = new ListResultModel<>();
        try {
            List<Map<String, Object>> backups = new ArrayList<>();
            
            backups.add(createBackup("backup-001", "完整备份", "2026-02-12 22:00:00", 1024 * 1024 * 500, "completed"));
            backups.add(createBackup("backup-002", "增量备份", "2026-02-11 22:00:00", 1024 * 1024 * 50, "completed"));
            backups.add(createBackup("backup-003", "完整备份", "2026-02-10 22:00:00", 1024 * 1024 * 480, "completed"));
            backups.add(createBackup("backup-004", "增量备份", "2026-02-09 22:00:00", 1024 * 1024 * 30, "completed"));
            
            result.setData(backups);
            result.setSize(backups.size());
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取备份列表失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取备份列表失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/settings")
    @ResponseBody
    public ResultModel<Map<String, Object>> getSettings() {
        ResultModel<Map<String, Object>> result = new ResultModel<>();
        try {
            Map<String, Object> settings = new LinkedHashMap<>();
            settings.put("autoBackup", true);
            settings.put("backupInterval", "daily");
            settings.put("backupTime", "22:00");
            settings.put("retentionDays", 30);
            settings.put("compressBackup", true);
            settings.put("storagePath", "/data/backups");
            
            result.setData(settings);
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取存储设置失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取存储设置失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/settings/update")
    @ResponseBody
    public ResultModel<Boolean> updateSettings(@RequestBody Map<String, Object> request) {
        ResultModel<Boolean> result = new ResultModel<>();
        try {
            result.setData(true);
            result.setRequestStatus(200);
            result.setMessage("更新成功");
        } catch (Exception e) {
            log.error("更新存储设置失败", e);
            result.setRequestStatus(500);
            result.setMessage("更新存储设置失败: " + e.getMessage());
            result.setData(false);
        }
        return result;
    }

    @PostMapping("/backup")
    @ResponseBody
    public ResultModel<Map<String, Object>> createBackup(@RequestBody Map<String, String> request) {
        ResultModel<Map<String, Object>> result = new ResultModel<>();
        try {
            String type = request.getOrDefault("type", "incremental");
            
            Map<String, Object> backup = createBackup(
                "backup-" + System.currentTimeMillis(),
                type.equals("full") ? "完整备份" : "增量备份",
                new Date().toString(),
                1024 * 1024 * 100,
                "running"
            );
            
            result.setData(backup);
            result.setRequestStatus(200);
            result.setMessage("备份任务已启动");
        } catch (Exception e) {
            log.error("创建备份失败", e);
            result.setRequestStatus(500);
            result.setMessage("创建备份失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/clean")
    @ResponseBody
    public ResultModel<Map<String, Object>> cleanStorage(@RequestBody Map<String, Object> request) {
        ResultModel<Map<String, Object>> result = new ResultModel<>();
        try {
            Map<String, Object> cleanResult = new LinkedHashMap<>();
            cleanResult.put("deletedFiles", 25);
            cleanResult.put("freedSpace", 1024 * 1024 * 50);
            cleanResult.put("cleanedAt", new Date().toString());
            
            result.setData(cleanResult);
            result.setRequestStatus(200);
            result.setMessage("清理完成");
        } catch (Exception e) {
            log.error("清理存储失败", e);
            result.setRequestStatus(500);
            result.setMessage("清理存储失败: " + e.getMessage());
        }
        return result;
    }

    private Map<String, Object> createBackup(String id, String type, String time, long size, String status) {
        Map<String, Object> backup = new LinkedHashMap<>();
        backup.put("id", id);
        backup.put("type", type);
        backup.put("createdAt", time);
        backup.put("size", size);
        backup.put("status", status);
        return backup;
    }
}
