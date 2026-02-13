package net.ooder.nexus.adapter.inbound.controller.admin;

import net.ooder.config.ResultModel;
import net.ooder.config.ListResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/categories")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
public class AdminCategoryController {

    private static final Logger log = LoggerFactory.getLogger(AdminCategoryController.class);

    private final Map<String, Map<String, Object>> categories = new LinkedHashMap<>();

    public AdminCategoryController() {
        categories.put("cat-001", createCategory("cat-001", "文本处理", "文本分析和处理相关技能", "ri-text", 15));
        categories.put("cat-002", createCategory("cat-002", "开发工具", "开发辅助工具和自动化", "ri-code-line", 23));
        categories.put("cat-003", createCategory("cat-003", "数据分析", "数据处理和分析技能", "ri-bar-chart-line", 18));
        categories.put("cat-004", createCategory("cat-004", "系统管理", "系统运维和管理工具", "ri-server-line", 12));
        categories.put("cat-005", createCategory("cat-005", "网络工具", "网络监控和管理工具", "ri-global-line", 8));
    }

    @PostMapping("/list")
    @ResponseBody
    public ListResultModel<List<Map<String, Object>>> getCategoryList() {
        ListResultModel<List<Map<String, Object>>> result = new ListResultModel<>();
        try {
            List<Map<String, Object>> categoryList = new ArrayList<>(categories.values());
            result.setData(categoryList);
            result.setSize(categoryList.size());
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取分类列表失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取分类列表失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/get")
    @ResponseBody
    public ResultModel<Map<String, Object>> getCategory(@RequestBody Map<String, String> request) {
        ResultModel<Map<String, Object>> result = new ResultModel<>();
        try {
            String id = request.get("id");
            Map<String, Object> category = categories.get(id);
            if (category == null) {
                result.setRequestStatus(404);
                result.setMessage("分类不存在");
                return result;
            }
            result.setData(category);
            result.setRequestStatus(200);
            result.setMessage("获取成功");
        } catch (Exception e) {
            log.error("获取分类详情失败", e);
            result.setRequestStatus(500);
            result.setMessage("获取分类详情失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/create")
    @ResponseBody
    public ResultModel<Map<String, Object>> createCategory(@RequestBody Map<String, Object> request) {
        ResultModel<Map<String, Object>> result = new ResultModel<>();
        try {
            String name = (String) request.get("name");
            String description = (String) request.get("description");
            String icon = (String) request.getOrDefault("icon", "ri-folder-line");

            if (name == null || name.isEmpty()) {
                result.setRequestStatus(400);
                result.setMessage("分类名称不能为空");
                return result;
            }

            String id = "cat-" + System.currentTimeMillis();
            Map<String, Object> category = createCategory(id, name, description, icon, 0);
            categories.put(id, category);

            result.setData(category);
            result.setRequestStatus(200);
            result.setMessage("创建成功");
        } catch (Exception e) {
            log.error("创建分类失败", e);
            result.setRequestStatus(500);
            result.setMessage("创建分类失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/update")
    @ResponseBody
    public ResultModel<Boolean> updateCategory(@RequestBody Map<String, Object> request) {
        ResultModel<Boolean> result = new ResultModel<>();
        try {
            String id = (String) request.get("id");
            Map<String, Object> category = categories.get(id);
            if (category == null) {
                result.setRequestStatus(404);
                result.setMessage("分类不存在");
                result.setData(false);
                return result;
            }

            if (request.get("name") != null) {
                category.put("name", request.get("name"));
            }
            if (request.get("description") != null) {
                category.put("description", request.get("description"));
            }
            if (request.get("icon") != null) {
                category.put("icon", request.get("icon"));
            }

            result.setData(true);
            result.setRequestStatus(200);
            result.setMessage("更新成功");
        } catch (Exception e) {
            log.error("更新分类失败", e);
            result.setRequestStatus(500);
            result.setMessage("更新分类失败: " + e.getMessage());
            result.setData(false);
        }
        return result;
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResultModel<Boolean> deleteCategory(@RequestBody Map<String, String> request) {
        ResultModel<Boolean> result = new ResultModel<>();
        try {
            String id = request.get("id");
            Map<String, Object> removed = categories.remove(id);
            if (removed == null) {
                result.setRequestStatus(404);
                result.setMessage("分类不存在");
                result.setData(false);
                return result;
            }
            result.setData(true);
            result.setRequestStatus(200);
            result.setMessage("删除成功");
        } catch (Exception e) {
            log.error("删除分类失败", e);
            result.setRequestStatus(500);
            result.setMessage("删除分类失败: " + e.getMessage());
            result.setData(false);
        }
        return result;
    }

    private Map<String, Object> createCategory(String id, String name, String description, String icon, int skillCount) {
        Map<String, Object> category = new LinkedHashMap<>();
        category.put("id", id);
        category.put("name", name);
        category.put("description", description);
        category.put("icon", icon);
        category.put("skillCount", skillCount);
        category.put("createdAt", "2026-02-01");
        return category;
    }
}
