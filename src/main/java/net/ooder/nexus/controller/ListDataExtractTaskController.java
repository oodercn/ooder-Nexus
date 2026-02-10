package net.ooder.nexus.controller;

import net.ooder.nexus.common.model.ApiResponse;
import net.ooder.nexus.domain.task.model.ListDataExtractTask;
import net.ooder.nexus.service.ListDataExtractTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 列表数据抽取任务管理控制器
 */
@RestController
@RequestMapping("/api/list-tasks")
public class ListDataExtractTaskController {

    private static final Logger log = LoggerFactory.getLogger(ListDataExtractTaskController.class);

    @Autowired
    private ListDataExtractTaskService taskService;

    /**
     * 获取所有任务
     */
    @GetMapping
    public ApiResponse<List<ListDataExtractTask>> getAllTasks() {
        try {
            List<ListDataExtractTask> tasks = taskService.getAllTasks();
            return ApiResponse.success(tasks);
        } catch (Exception e) {
            log.error("获取任务列表失败", e);
            return ApiResponse.error("获取任务列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取任务
     */
    @GetMapping("/{id}")
    public ApiResponse<ListDataExtractTask> getTaskById(@PathVariable String id) {
        try {
            ListDataExtractTask task = taskService.getTaskById(id);
            if (task == null) {
                return ApiResponse.error("任务不存在");
            }
            return ApiResponse.success(task);
        } catch (Exception e) {
            log.error("获取任务详情失败", e);
            return ApiResponse.error("获取任务详情失败: " + e.getMessage());
        }
    }

    /**
     * 创建任务
     */
    @PostMapping
    public ApiResponse<ListDataExtractTask> createTask(@RequestBody ListDataExtractTask task) {
        try {
            ListDataExtractTask created = taskService.createTask(task);
            return ApiResponse.success("200", "任务创建成功", created);
        } catch (Exception e) {
            log.error("创建任务失败", e);
            return ApiResponse.error("创建任务失败: " + e.getMessage());
        }
    }

    /**
     * 更新任务
     */
    @PutMapping("/{id}")
    public ApiResponse<ListDataExtractTask> updateTask(@PathVariable String id, @RequestBody ListDataExtractTask task) {
        try {
            ListDataExtractTask updated = taskService.updateTask(id, task);
            if (updated == null) {
                return ApiResponse.error("任务不存在");
            }
            return ApiResponse.success("200", "任务更新成功", updated);
        } catch (Exception e) {
            log.error("更新任务失败", e);
            return ApiResponse.error("更新任务失败: " + e.getMessage());
        }
    }

    /**
     * 删除任务
     */
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteTask(@PathVariable String id) {
        try {
            boolean deleted = taskService.deleteTask(id);
            if (deleted) {
                return ApiResponse.success("200", "任务删除成功");
            } else {
                return ApiResponse.error("任务不存在");
            }
        } catch (Exception e) {
            log.error("删除任务失败", e);
            return ApiResponse.error("删除任务失败: " + e.getMessage());
        }
    }

    /**
     * 执行任务
     */
    @PostMapping("/{id}/execute")
    public ApiResponse<String> executeTask(@PathVariable String id) {
        try {
            boolean executed = taskService.executeTask(id);
            if (executed) {
                return ApiResponse.success("200", "任务开始执行");
            } else {
                return ApiResponse.error("任务不存在或执行失败");
            }
        } catch (Exception e) {
            log.error("执行任务失败", e);
            return ApiResponse.error("执行任务失败: " + e.getMessage());
        }
    }

    /**
     * 启用任务
     */
    @PostMapping("/{id}/enable")
    public ApiResponse<String> enableTask(@PathVariable String id) {
        try {
            boolean enabled = taskService.enableTask(id);
            if (enabled) {
                return ApiResponse.success("200", "任务已启用");
            } else {
                return ApiResponse.error("任务不存在");
            }
        } catch (Exception e) {
            log.error("启用任务失败", e);
            return ApiResponse.error("启用任务失败: " + e.getMessage());
        }
    }

    /**
     * 禁用任务
     */
    @PostMapping("/{id}/disable")
    public ApiResponse<String> disableTask(@PathVariable String id) {
        try {
            boolean disabled = taskService.disableTask(id);
            if (disabled) {
                return ApiResponse.success("200", "任务已禁用");
            } else {
                return ApiResponse.error("任务不存在");
            }
        } catch (Exception e) {
            log.error("禁用任务失败", e);
            return ApiResponse.error("禁用任务失败: " + e.getMessage());
        }
    }

    /**
     * 获取任务统计信息
     */
    @GetMapping("/stats")
    public ApiResponse<ListDataExtractTaskService.TaskStats> getTaskStats() {
        try {
            ListDataExtractTaskService.TaskStats stats = taskService.getTaskStats();
            return ApiResponse.success(stats);
        } catch (Exception e) {
            log.error("获取任务统计信息失败", e);
            return ApiResponse.error("获取任务统计信息失败: " + e.getMessage());
        }
    }

    /**
     * 测试连接
     */
    @PostMapping("/{id}/test-connection")
    public ApiResponse<Map<String, Object>> testConnection(@PathVariable String id) {
        try {
            boolean success = taskService.testConnection(id);
            Map<String, Object> result = new java.util.HashMap<>();
            result.put("success", success);
            result.put("message", success ? "连接测试成功" : "连接测试失败");
            return ApiResponse.success(result);
        } catch (Exception e) {
            log.error("测试连接失败", e);
            return ApiResponse.error("测试连接失败: " + e.getMessage());
        }
    }

    /**
     * 预览数据
     */
    @GetMapping("/{id}/preview")
    public ApiResponse<List<Map<String, Object>>> previewData(
            @PathVariable String id,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<Map<String, Object>> data = taskService.previewData(id, limit);
            return ApiResponse.success(data);
        } catch (Exception e) {
            log.error("预览数据失败", e);
            return ApiResponse.error("预览数据失败: " + e.getMessage());
        }
    }

    /**
     * 获取数据源类型列表
     */
    @GetMapping("/source-types")
    public ApiResponse<List<String>> getSourceTypes() {
        try {
            List<String> types = Arrays.asList(
                "DATABASE",
                "API",
                "FILE",
                "MESSAGE_QUEUE",
                "CUSTOM"
            );
            return ApiResponse.success(types);
        } catch (Exception e) {
            log.error("获取数据源类型列表失败", e);
            return ApiResponse.error("获取数据源类型列表失败: " + e.getMessage());
        }
    }

    /**
     * 获取任务状态列表
     */
    @GetMapping("/statuses")
    public ApiResponse<List<String>> getTaskStatuses() {
        try {
            List<String> statuses = Arrays.asList(
                "PENDING",
                "RUNNING",
                "COMPLETED",
                "FAILED",
                "CANCELLED"
            );
            return ApiResponse.success(statuses);
        } catch (Exception e) {
            log.error("获取任务状态列表失败", e);
            return ApiResponse.error("获取任务状态列表失败: " + e.getMessage());
        }
    }

    /**
     * 获取文件格式列表
     */
    @GetMapping("/file-formats")
    public ApiResponse<List<String>> getFileFormats() {
        try {
            List<String> formats = Arrays.asList(
                "JSON",
                "CSV",
                "XML",
                "EXCEL"
            );
            return ApiResponse.success(formats);
        } catch (Exception e) {
            log.error("获取文件格式列表失败", e);
            return ApiResponse.error("获取文件格式列表失败: " + e.getMessage());
        }
    }
}
