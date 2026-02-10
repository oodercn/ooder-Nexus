package net.ooder.nexus.controller;

import net.ooder.nexus.common.model.ApiResponse;
import net.ooder.nexus.domain.task.model.DataExtractTask;
import net.ooder.nexus.service.DataExtractTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据抽取任务管理控制器
 */
@RestController
@RequestMapping("/api/tasks")
public class DataExtractTaskController {

    private static final Logger log = LoggerFactory.getLogger(DataExtractTaskController.class);

    @Autowired
    private DataExtractTaskService taskService;

    /**
     * 获取所有任务
     */
    @GetMapping
    public ApiResponse<List<DataExtractTask>> getAllTasks() {
        try {
            List<DataExtractTask> tasks = taskService.getAllTasks();
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
    public ApiResponse<DataExtractTask> getTaskById(@PathVariable String id) {
        try {
            DataExtractTask task = taskService.getTaskById(id);
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
    public ApiResponse<DataExtractTask> createTask(@RequestBody DataExtractTask task) {
        try {
            DataExtractTask created = taskService.createTask(task);
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
    public ApiResponse<DataExtractTask> updateTask(@PathVariable String id, @RequestBody DataExtractTask task) {
        try {
            DataExtractTask updated = taskService.updateTask(id, task);
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
    public ApiResponse<DataExtractTaskService.TaskStats> getTaskStats() {
        try {
            DataExtractTaskService.TaskStats stats = taskService.getTaskStats();
            return ApiResponse.success(stats);
        } catch (Exception e) {
            log.error("获取任务统计信息失败", e);
            return ApiResponse.error("获取任务统计信息失败: " + e.getMessage());
        }
    }

    /**
     * 获取任务类型列表
     */
    @GetMapping("/types")
    public ApiResponse<List<String>> getTaskTypes() {
        try {
            List<String> types = Arrays.asList(
                "DATABASE",
                "API",
                "FILE",
                "LOG",
                "CUSTOM"
            );
            return ApiResponse.success(types);
        } catch (Exception e) {
            log.error("获取任务类型列表失败", e);
            return ApiResponse.error("获取任务类型列表失败: " + e.getMessage());
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
}
