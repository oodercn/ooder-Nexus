package net.ooder.nexus.controller;

import net.ooder.nexus.common.model.ApiResponse;
import net.ooder.nexus.domain.sync.model.SyncTask;
import net.ooder.nexus.service.SkillSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 技能同步控制器
 * 参考agent-skillcenter设计，支持个人使用和小规模办公分享
 */
@RestController
@RequestMapping(value = "/api/sync", produces = "application/json;charset=UTF-8")
public class SkillSyncController {

    private static final Logger log = LoggerFactory.getLogger(SkillSyncController.class);

    @Autowired
    private SkillSyncService skillSyncService;

    /**
     * 获取所有同步任务
     */
    @GetMapping("/tasks")
    public ApiResponse<List<SyncTask>> getAllTasks() {
        try {
            List<SyncTask> tasks = skillSyncService.getAllTasks();
            return ApiResponse.success(tasks);
        } catch (Exception e) {
            log.error("获取同步任务列表失败", e);
            return ApiResponse.error("获取同步任务列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据状态获取同步任务
     */
    @GetMapping("/tasks/status/{status}")
    public ApiResponse<List<SyncTask>> getTasksByStatus(@PathVariable String status) {
        try {
            List<SyncTask> tasks = skillSyncService.getTasksByStatus(status);
            return ApiResponse.success(tasks);
        } catch (Exception e) {
            log.error("获取同步任务列表失败", e);
            return ApiResponse.error("获取同步任务列表失败: " + e.getMessage());
        }
    }

    /**
     * 创建同步任务
     */
    @PostMapping("/tasks")
    public ApiResponse<SyncTask> createTask(@RequestBody SyncTask task) {
        try {
            SyncTask created = skillSyncService.createTask(task);
            return ApiResponse.success("200", "同步任务创建成功", created);
        } catch (Exception e) {
            log.error("创建同步任务失败", e);
            return ApiResponse.error("创建同步任务失败: " + e.getMessage());
        }
    }

    /**
     * 执行同步任务
     */
    @PostMapping("/tasks/{id}/execute")
    public ApiResponse<SyncTask> executeTask(@PathVariable String id) {
        try {
            SyncTask task = skillSyncService.executeTask(id);
            if (task != null) {
                return ApiResponse.success("200", "同步任务开始执行", task);
            } else {
                return ApiResponse.error("同步任务不存在");
            }
        } catch (Exception e) {
            log.error("执行同步任务失败", e);
            return ApiResponse.error("执行同步任务失败: " + e.getMessage());
        }
    }

    /**
     * 取消同步任务
     */
    @PostMapping("/tasks/{id}/cancel")
    public ApiResponse<SyncTask> cancelTask(@PathVariable String id) {
        try {
            SyncTask task = skillSyncService.cancelTask(id);
            if (task != null) {
                return ApiResponse.success("200", "同步任务已取消", task);
            } else {
                return ApiResponse.error("同步任务不存在或无法取消");
            }
        } catch (Exception e) {
            log.error("取消同步任务失败", e);
            return ApiResponse.error("取消同步任务失败: " + e.getMessage());
        }
    }

    /**
     * 删除同步任务
     */
    @DeleteMapping("/tasks/{id}")
    public ApiResponse<String> deleteTask(@PathVariable String id) {
        try {
            boolean deleted = skillSyncService.deleteTask(id);
            if (deleted) {
                return ApiResponse.success("200", "同步任务删除成功");
            } else {
                return ApiResponse.error("同步任务不存在");
            }
        } catch (Exception e) {
            log.error("删除同步任务失败", e);
            return ApiResponse.error("删除同步任务失败: " + e.getMessage());
        }
    }

    /**
     * 获取同步统计信息
     */
    @GetMapping("/statistics")
    public ApiResponse<SkillSyncService.SyncStatistics> getStatistics() {
        try {
            SkillSyncService.SyncStatistics stats = skillSyncService.getStatistics();
            return ApiResponse.success(stats);
        } catch (Exception e) {
            log.error("获取同步统计信息失败", e);
            return ApiResponse.error("获取同步统计信息失败: " + e.getMessage());
        }
    }

    /**
     * 获取可同步的技能列表
     */
    @GetMapping("/skills/syncable")
    public ApiResponse<List<SkillSyncService.SyncableSkill>> getSyncableSkills() {
        try {
            List<SkillSyncService.SyncableSkill> skills = skillSyncService.getSyncableSkills();
            return ApiResponse.success(skills);
        } catch (Exception e) {
            log.error("获取可同步技能列表失败", e);
            return ApiResponse.error("获取可同步技能列表失败: " + e.getMessage());
        }
    }

    /**
     * 获取已同步的技能列表
     */
    @GetMapping("/skills/synced")
    public ApiResponse<List<SkillSyncService.SyncedSkill>> getSyncedSkills() {
        try {
            List<SkillSyncService.SyncedSkill> skills = skillSyncService.getSyncedSkills();
            return ApiResponse.success(skills);
        } catch (Exception e) {
            log.error("获取已同步技能列表失败", e);
            return ApiResponse.error("获取已同步技能列表失败: " + e.getMessage());
        }
    }

    /**
     * 上传技能
     */
    @PostMapping("/skills/{id}/upload")
    public ApiResponse<SyncTask> uploadSkill(@PathVariable String id, @RequestBody(required = false) Map<String, String> body) {
        try {
            String target = body != null ? body.get("target") : null;
            SyncTask task = skillSyncService.uploadSkill(id, target);
            return ApiResponse.success("200", "技能上传任务已创建", task);
        } catch (Exception e) {
            log.error("创建技能上传任务失败", e);
            return ApiResponse.error("创建技能上传任务失败: " + e.getMessage());
        }
    }

    /**
     * 下载技能
     */
    @PostMapping("/skills/{id}/download")
    public ApiResponse<SyncTask> downloadSkill(@PathVariable String id, @RequestBody(required = false) Map<String, String> body) {
        try {
            String source = body != null ? body.get("source") : null;
            SyncTask task = skillSyncService.downloadSkill(id, source);
            return ApiResponse.success("200", "技能下载任务已创建", task);
        } catch (Exception e) {
            log.error("创建技能下载任务失败", e);
            return ApiResponse.error("创建技能下载任务失败: " + e.getMessage());
        }
    }

    /**
     * 批量同步技能
     */
    @PostMapping("/batch")
    public ApiResponse<SyncTask> batchSync(@RequestBody Map<String, Object> request) {
        try {
            @SuppressWarnings("unchecked")
            List<String> skillIds = (List<String>) request.get("skillIds");
            String type = (String) request.get("type");
            
            if (skillIds == null || skillIds.isEmpty()) {
                return ApiResponse.error("技能ID列表不能为空");
            }
            
            SyncTask task = skillSyncService.batchSync(skillIds, type != null ? type : "bidirectional");
            return ApiResponse.success("200", "批量同步任务已创建", task);
        } catch (Exception e) {
            log.error("创建批量同步任务失败", e);
            return ApiResponse.error("创建批量同步任务失败: " + e.getMessage());
        }
    }

    /**
     * 初始化默认数据
     */
    @PostMapping("/init-default")
    public ApiResponse<String> initDefaultData() {
        try {
            skillSyncService.initDefaultData();
            return ApiResponse.success("200", "默认数据初始化成功");
        } catch (Exception e) {
            log.error("初始化默认数据失败", e);
            return ApiResponse.error("初始化默认数据失败: " + e.getMessage());
        }
    }
}
