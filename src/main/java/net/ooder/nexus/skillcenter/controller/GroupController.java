package net.ooder.nexus.skillcenter.controller;

import net.ooder.nexus.common.model.ApiResponse;
import net.ooder.nexus.skillcenter.model.Group;
import net.ooder.nexus.skillcenter.model.GroupMember;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 群组管理REST API控制器
 */
@RestController
@RequestMapping("/api/skillcenter/groups")
public class GroupController {

    private final Map<String, Group> groupStore = new ConcurrentHashMap<>();
    private final Map<String, List<GroupMember>> groupMembers = new ConcurrentHashMap<>();

    public GroupController() {
        loadSampleData();
    }

    /**
     * 获取所有群组
     * @return 群组列表
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Group>>> getAllGroups() {
        try {
            List<Group> groups = new ArrayList<>(groupStore.values());
            return ResponseEntity.ok(ApiResponse.success(groups));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("500", "Failed to get groups: " + e.getMessage()));
        }
    }

    /**
     * 获取群组详情
     * @param groupId 群组ID
     * @return 群组详情
     */
    @GetMapping("/{groupId}")
    public ResponseEntity<ApiResponse<Group>> getGroup(@PathVariable String groupId) {
        try {
            Group group = groupStore.get(groupId);
            if (group == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("404", "Group not found"));
            }
            return ResponseEntity.ok(ApiResponse.success(group));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("500", "Failed to get group: " + e.getMessage()));
        }
    }

    /**
     * 创建群组
     * @param request 创建请求
     * @return 创建的群组
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Group>> createGroup(@RequestBody CreateGroupRequest request) {
        try {
            if (request.getName() == null || request.getName().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("400", "Group name is required"));
            }

            String groupId = "group-" + UUID.randomUUID().toString().substring(0, 8);
            String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            Group group = new Group();
            group.setId(groupId);
            group.setName(request.getName());
            group.setDescription(request.getDescription());
            group.setCreatedAt(now);
            group.setRole("owner");
            group.setOwnerId("current-user");
            group.setStatus("active");
            group.setMemberCount(1);

            groupStore.put(groupId, group);

            // 添加创建者为管理员
            GroupMember owner = new GroupMember();
            owner.setId("member-" + UUID.randomUUID().toString().substring(0, 8));
            owner.setGroupId(groupId);
            owner.setUserId("current-user");
            owner.setUsername("当前用户");
            owner.setRole("admin");
            owner.setJoinedAt(now);
            owner.setStatus("active");

            groupMembers.put(groupId, new ArrayList<>(Collections.singletonList(owner)));

            return ResponseEntity.ok(ApiResponse.success(group));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("500", "Failed to create group: " + e.getMessage()));
        }
    }

    /**
     * 更新群组
     * @param groupId 群组ID
     * @param request 更新请求
     * @return 更新后的群组
     */
    @PutMapping("/{groupId}")
    public ResponseEntity<ApiResponse<Group>> updateGroup(@PathVariable String groupId, @RequestBody UpdateGroupRequest request) {
        try {
            Group group = groupStore.get(groupId);
            if (group == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("404", "Group not found"));
            }

            if (request.getName() != null) {
                group.setName(request.getName());
            }
            if (request.getDescription() != null) {
                group.setDescription(request.getDescription());
            }

            return ResponseEntity.ok(ApiResponse.success(group));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("500", "Failed to update group: " + e.getMessage()));
        }
    }

    /**
     * 删除群组
     * @param groupId 群组ID
     * @return 删除结果
     */
    @DeleteMapping("/{groupId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteGroup(@PathVariable String groupId) {
        try {
            Group removed = groupStore.remove(groupId);
            if (removed == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("404", "Group not found"));
            }
            groupMembers.remove(groupId);
            return ResponseEntity.ok(ApiResponse.success(true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("500", "Failed to delete group: " + e.getMessage()));
        }
    }

    /**
     * 获取群组成员
     * @param groupId 群组ID
     * @return 成员列表
     */
    @GetMapping("/{groupId}/members")
    public ResponseEntity<ApiResponse<List<GroupMember>>> getGroupMembers(@PathVariable String groupId) {
        try {
            List<GroupMember> members = groupMembers.getOrDefault(groupId, new ArrayList<>());
            return ResponseEntity.ok(ApiResponse.success(members));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("500", "Failed to get members: " + e.getMessage()));
        }
    }

    /**
     * 添加群组成员
     * @param groupId 群组ID
     * @param request 添加请求
     * @return 添加的成员
     */
    @PostMapping("/{groupId}/members")
    public ResponseEntity<ApiResponse<GroupMember>> addMember(@PathVariable String groupId, @RequestBody AddMemberRequest request) {
        try {
            Group group = groupStore.get(groupId);
            if (group == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("404", "Group not found"));
            }

            String memberId = "member-" + UUID.randomUUID().toString().substring(0, 8);
            String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            GroupMember member = new GroupMember();
            member.setId(memberId);
            member.setGroupId(groupId);
            member.setUserId(request.getUserId());
            member.setUsername(request.getUsername());
            member.setRole(request.getRole() != null ? request.getRole() : "member");
            member.setJoinedAt(now);
            member.setStatus("active");

            groupMembers.computeIfAbsent(groupId, k -> new ArrayList<>()).add(member);

            // 更新成员数量
            group.setMemberCount(groupMembers.get(groupId).size());

            return ResponseEntity.ok(ApiResponse.success(member));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("500", "Failed to add member: " + e.getMessage()));
        }
    }

    /**
     * 移除群组成员
     * @param groupId 群组ID
     * @param memberId 成员ID
     * @return 移除结果
     */
    @DeleteMapping("/{groupId}/members/{memberId}")
    public ResponseEntity<ApiResponse<Boolean>> removeMember(@PathVariable String groupId, @PathVariable String memberId) {
        try {
            List<GroupMember> members = groupMembers.get(groupId);
            if (members == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("404", "Group not found"));
            }

            boolean removed = members.removeIf(m -> m.getId().equals(memberId));
            if (!removed) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("404", "Member not found"));
            }

            // 更新成员数量
            Group group = groupStore.get(groupId);
            if (group != null) {
                group.setMemberCount(members.size());
            }

            return ResponseEntity.ok(ApiResponse.success(true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("500", "Failed to remove member: " + e.getMessage()));
        }
    }

    /**
     * 加载示例数据
     */
    private void loadSampleData() {
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // 开发团队
        Group group1 = new Group();
        group1.setId("group-001");
        group1.setName("开发团队");
        group1.setDescription("Nexus开发团队，共享开发相关技能");
        group1.setCreatedAt(now);
        group1.setRole("owner");
        group1.setOwnerId("user001");
        group1.setStatus("active");
        group1.setMemberCount(5);
        groupStore.put(group1.getId(), group1);

        List<GroupMember> members1 = new ArrayList<>();
        members1.add(createMember("member-001", "group-001", "user001", "张三", "admin", now));
        members1.add(createMember("member-002", "group-001", "user002", "李四", "member", now));
        members1.add(createMember("member-003", "group-001", "user003", "王五", "member", now));
        groupMembers.put(group1.getId(), members1);

        // 家庭组
        Group group2 = new Group();
        group2.setId("group-002");
        group2.setName("家庭组");
        group2.setDescription("家庭网络管理组");
        group2.setCreatedAt(now);
        group2.setRole("member");
        group2.setOwnerId("user002");
        group2.setStatus("active");
        group2.setMemberCount(4);
        groupStore.put(group2.getId(), group2);

        List<GroupMember> members2 = new ArrayList<>();
        members2.add(createMember("member-004", "group-002", "user002", "李四", "admin", now));
        members2.add(createMember("member-005", "group-002", "user004", "赵六", "member", now));
        groupMembers.put(group2.getId(), members2);

        // 网络管理组
        Group group3 = new Group();
        group3.setId("group-003");
        group3.setName("网络管理组");
        group3.setDescription("OpenWrt网络管理技能分享");
        group3.setCreatedAt(now);
        group3.setRole("member");
        group3.setOwnerId("user003");
        group3.setStatus("active");
        group3.setMemberCount(3);
        groupStore.put(group3.getId(), group3);

        List<GroupMember> members3 = new ArrayList<>();
        members3.add(createMember("member-006", "group-003", "user003", "王五", "admin", now));
        members3.add(createMember("member-007", "group-003", "user005", "钱七", "member", now));
        groupMembers.put(group3.getId(), members3);
    }

    private GroupMember createMember(String id, String groupId, String userId, String username, String role, String joinedAt) {
        GroupMember member = new GroupMember();
        member.setId(id);
        member.setGroupId(groupId);
        member.setUserId(userId);
        member.setUsername(username);
        member.setRole(role);
        member.setJoinedAt(joinedAt);
        member.setStatus("active");
        return member;
    }

    // 请求体类
    public static class CreateGroupRequest {
        private String name;
        private String description;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class UpdateGroupRequest {
        private String name;
        private String description;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class AddMemberRequest {
        private String userId;
        private String username;
        private String role;

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}
