package net.ooder.nexus.controller;

import net.ooder.nexus.model.Result;
import net.ooder.nexus.service.SdkDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * SDK数据管理控制器
 * 提供RESTful API用于管理JSON数据存储
 * 数据存储路径: ./storage/sdk/
 */
@RestController
@RequestMapping("/api/sdk")
@CrossOrigin(origins = "*")
public class SdkDataController {

    @Autowired
    private SdkDataService sdkDataService;

    /**
     * 保存数据
     * POST /api/sdk/{collection}/{id}
     */
    @PostMapping("/{collection}/{id}")
    public Result<Map<String, Object>> save(
            @PathVariable String collection,
            @PathVariable String id,
            @RequestBody Map<String, Object> data) {
        return sdkDataService.save(collection, id, data);
    }

    /**
     * 批量保存数据
     * POST /api/sdk/{collection}/batch
     */
    @PostMapping("/{collection}/batch")
    public Result<Map<String, Object>> saveBatch(
            @PathVariable String collection,
            @RequestBody List<Map<String, Object>> dataList) {
        return sdkDataService.saveBatch(collection, dataList);
    }

    /**
     * 根据ID获取数据
     * GET /api/sdk/{collection}/{id}
     */
    @GetMapping("/{collection}/{id}")
    public Result<Map<String, Object>> getById(
            @PathVariable String collection,
            @PathVariable String id) {
        return sdkDataService.getById(collection, id);
    }

    /**
     * 获取集合中的所有数据
     * GET /api/sdk/{collection}
     */
    @GetMapping("/{collection}")
    public Result<List<Map<String, Object>>> getAll(@PathVariable String collection) {
        return sdkDataService.getAll(collection);
    }

    /**
     * 根据条件查询数据
     * POST /api/sdk/{collection}/query
     */
    @PostMapping("/{collection}/query")
    public Result<List<Map<String, Object>>> query(
            @PathVariable String collection,
            @RequestBody Map<String, Object> conditions) {
        return sdkDataService.query(collection, conditions);
    }

    /**
     * 更新数据
     * PUT /api/sdk/{collection}/{id}
     */
    @PutMapping("/{collection}/{id}")
    public Result<Map<String, Object>> update(
            @PathVariable String collection,
            @PathVariable String id,
            @RequestBody Map<String, Object> data) {
        return sdkDataService.update(collection, id, data);
    }

    /**
     * 删除数据
     * DELETE /api/sdk/{collection}/{id}
     */
    @DeleteMapping("/{collection}/{id}")
    public Result<Void> delete(
            @PathVariable String collection,
            @PathVariable String id) {
        return sdkDataService.delete(collection, id);
    }

    /**
     * 删除集合中的所有数据
     * DELETE /api/sdk/{collection}
     */
    @DeleteMapping("/{collection}")
    public Result<Void> deleteAll(@PathVariable String collection) {
        return sdkDataService.deleteAll(collection);
    }

    /**
     * 获取所有集合列表
     * GET /api/sdk/collections
     */
    @GetMapping("/collections")
    public Result<List<String>> getCollections() {
        return sdkDataService.getCollections();
    }

    /**
     * 检查数据是否存在
     * GET /api/sdk/{collection}/{id}/exists
     */
    @GetMapping("/{collection}/{id}/exists")
    public Result<Boolean> exists(
            @PathVariable String collection,
            @PathVariable String id) {
        return sdkDataService.exists(collection, id);
    }

    /**
     * 获取集合中的数据数量
     * GET /api/sdk/{collection}/count
     */
    @GetMapping("/{collection}/count")
    public Result<Long> count(@PathVariable String collection) {
        return sdkDataService.count(collection);
    }
}
