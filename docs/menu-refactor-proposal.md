# Nexus 菜单重构方案

## 一、功能合并方案

### 1.1 网络管理模块合并

**现状问题：**
- OpenWrt 和 小局域网 两个模块存在大量重复功能
- API 层面需要兼容统一
- 用户容易混淆

**合并后结构：**

```
网络管理 (network-management)
├── 网络概览 (network-overview) [合并: router-dashboard + lan-dashboard]
│   └── 统一仪表盘，支持多设备类型
├── 网络配置 (network-config) [合并: openwrt-network + lan-network-settings]
│   └── 通用网络参数配置
├── IP地址管理 (ip-address-management) [合并: openwrt-ip + lan-ip-management]
│   └── 统一IP分配、静态IP、DHCP管理
├── 设备资产 (device-assets) [保留: network-devices + device-details]
│   └── 网络设备清单和详情
├── 流量监控 (traffic-monitor) [保留: bandwidth-monitor]
│   └── 带宽使用监控
├── 访问控制 (access-control) [重命名: blacklist]
│   └── 黑名单、ACL规则
└── 远程终端 (remote-terminal) [保留: command]
    └── SSH/Telnet命令执行
```

### 1.2 功能对应关系

| 新功能ID | 新名称 | 合并来源 | API兼容方案 |
|---------|--------|---------|------------|
| network-overview | 网络概览 | router-dashboard + lan-dashboard | 统一接口 `/api/network/overview`，根据设备类型返回不同数据 |
| network-config | 网络配置 | openwrt-network + lan-network-settings | 统一接口 `/api/network/config`，适配层处理设备差异 |
| ip-management | IP地址管理 | openwrt-ip + lan-ip-management | 统一接口 `/api/network/ip`，支持多设备IP管理 |
| device-assets | 设备资产 | network-devices + device-details | 保留原API，增加设备类型字段 |
| traffic-monitor | 流量监控 | bandwidth-monitor | 保留原API |
| access-control | 访问控制 | blacklist | 保留原API，路径调整为 `/api/network/access-control` |
| remote-terminal | 远程终端 | command | 保留原API，路径调整为 `/api/network/terminal` |

## 二、术语标准化对照表

### 2.1 一级菜单

| 原名称 | 新名称 | 图标 | 变更说明 |
|-------|--------|------|---------|
| 仪表盘 | 仪表盘 | ri-dashboard-line | 保持不变 |
| 个人中心 | 用户中心 | ri-user-line | 更标准 |
| AI 技能中心 | 智能技能 | ri-robot-line | 更简洁 |
| 云端同步 | 数据同步 | ri-cloud-line | 更准确 |
| 网络管理 | 网络管理 | ri-wifi-line | 保持不变 |
| Nexus Agent | 系统服务 | ri-server-line | 避免品牌重复 |
| 存储管理 | 存储管理 | ri-folder-5-line | 保持不变 |
| 系统工具 | 运维工具 | ri-tools-line | 更准确 |
| 管理中心 | 系统管理 | ri-admin-line | 更标准 |

### 2.2 网络管理子菜单

| 原名称 | 新名称 | 图标 | 变更说明 |
|-------|--------|------|---------|
| OpenWrt 路由器 | ~~删除~~ | - | 合并到网络概览 |
| 小局域网 | ~~删除~~ | - | 合并到网络概览 |
| 路由器管理 | 网络概览 | ri-dashboard-line | 合并后统一入口 |
| 局域网仪表盘 | ~~删除~~ | - | 合并到网络概览 |
| 网络设置 | 网络配置 | ri-settings-3-line | 更专业 |
| IP 地址管理 | IP地址管理 | ri-computer-line | 名称统一 |
| IP管理 | ~~删除~~ | - | 合并到IP地址管理 |
| 黑名单管理 | 访问控制 | ri-shield-cross-line | 专业术语 |
| 配置文件 | 配置管理 | ri-file-settings-line | 更专业 |
| 系统状态 | 设备监控 | ri-server-line | 避免混淆 |
| 命令执行 | 远程终端 | ri-terminal-line | 更准确 |
| 设备列表 | 设备资产 | ri-list-check | 资产管理概念 |
| 设备详情 | ~~保留为详情页~~ | - | 不作为菜单项 |
| 网络状态 | ~~合并到设备监控~~ | - | 功能合并 |
| 带宽监控 | 流量监控 | ri-equalizer-line | 更专业 |

### 2.3 其他模块

| 原名称 | 新名称 | 图标 | 变更说明 |
|-------|--------|------|---------|
| 技能管理 | 技能管理 | ri-apps-line | 保持不变 |
| 技能市场 | 技能市场 | ri-store-2-line | 保持不变 |
| 技能执行 | 技能执行 | ri-play-circle-line | 保持不变 |
| 用户偏好 | 偏好设置 | ri-settings-3-line | 更简洁 |
| 技能调试 | 技能调试 | ri-bug-line | 保持不变 |
| 同步仪表盘 | 同步概览 | ri-dashboard-line | 更简洁 |
| 技能上传 | 导入技能 | ri-upload-line | 用户友好 |
| 技能下载 | 导出技能 | ri-download-line | 用户友好 |
| 技能分类 | 技能分类 | ri-folder-line | 保持不变 |
| 同步状态 | 同步状态 | ri-bar-chart-line | 保持不变 |
| 文件管理 | 文件管理 | ri-folder-line | 保持不变 |
| 我的分享 | 我的分享 | ri-share-line | 保持不变 |
| 收到的分享 | 接收分享 | ri-download-line | 更准确 |
| 健康检查 | 健康检查 | ri-heart-pulse-line | 保持不变 |
| 管理仪表盘 | 管理概览 | ri-dashboard-line | 更简洁 |
| 市场管理 | 市场管理 | ri-shopping-cart-line | 保持不变 |
| 技能认证 | 技能认证 | ri-shield-check-line | 保持不变 |
| 群组管理 | 群组管理 | ri-group-line | 保持不变 |
| 远程托管 | 远程托管 | ri-cloud-line | 保持不变 |
| 存储管理 | 存储管理 | ri-database-line | 保持不变 |

## 三、图标替换清单

### 3.1 需要替换的图标

检查所有菜单图标，以下图标需要确认或替换：

| 当前图标 | 状态 | 建议替换 | 说明 |
|---------|------|---------|------|
| ri-list-check | ✅ 存在 | - | 可用 |
| ri-information-line | ✅ 存在 | - | 可用 |
| ri-signal-line | ✅ 存在 | - | 可用 |
| ri-equalizer-line | ✅ 存在 | - | 可用 |
| ri-node-tree | ✅ 存在 | - | 可用 |
| ri-links-line | ✅ 存在 | - | 可用 |
| ri-map-line | ✅ 存在 | - | 可用 |
| ri-global-line | ✅ 存在 | - | 可用 |
| ri-heart-pulse-line | ✅ 存在 | - | 可用 |
| ri-file-text-line | ✅ 存在 | - | 可用 |
| ri-shield-check-line | ✅ 存在 | - | 可用 |

### 3.2 图标使用规范

```
一级菜单图标：使用 -line 后缀，尺寸 24px
二级菜单图标：使用 -line 后缀，尺寸 20px
三级菜单图标：使用 -line 后缀，尺寸 18px

常用图标映射：
- 仪表盘: ri-dashboard-line
- 用户: ri-user-line
- 设置: ri-settings-3-line / ri-settings-line
- 网络: ri-wifi-line / ri-global-line
- 安全: ri-shield-line / ri-shield-check-line
- 存储: ri-hard-drive-line / ri-folder-line
- 系统: ri-server-line / ri-computer-line
- 工具: ri-tools-line
- 管理: ri-admin-line
- 日志: ri-file-text-line
- 监控: ri-bar-chart-line / ri-equalizer-line
- 终端: ri-terminal-line
- 帮助: ri-question-line
```

## 四、新菜单结构

```json
{
  "menu": [
    {
      "id": "dashboard",
      "name": "仪表盘",
      "icon": "ri-dashboard-line"
    },
    {
      "id": "user-center",
      "name": "用户中心",
      "icon": "ri-user-line",
      "children": [...]
    },
    {
      "id": "ai-skills",
      "name": "智能技能",
      "icon": "ri-robot-line",
      "children": [...]
    },
    {
      "id": "data-sync",
      "name": "数据同步",
      "icon": "ri-cloud-line",
      "children": [...]
    },
    {
      "id": "network-management",
      "name": "网络管理",
      "icon": "ri-wifi-line",
      "children": [
        {
          "id": "network-overview",
          "name": "网络概览",
          "icon": "ri-dashboard-line"
        },
        {
          "id": "network-config",
          "name": "网络配置",
          "icon": "ri-settings-3-line"
        },
        {
          "id": "ip-management",
          "name": "IP地址管理",
          "icon": "ri-computer-line"
        },
        {
          "id": "device-assets",
          "name": "设备资产",
          "icon": "ri-list-check"
        },
        {
          "id": "traffic-monitor",
          "name": "流量监控",
          "icon": "ri-equalizer-line"
        },
        {
          "id": "access-control",
          "name": "访问控制",
          "icon": "ri-shield-cross-line"
        },
        {
          "id": "config-management",
          "name": "配置管理",
          "icon": "ri-file-settings-line"
        },
        {
          "id": "device-monitor",
          "name": "设备监控",
          "icon": "ri-server-line"
        },
        {
          "id": "remote-terminal",
          "name": "远程终端",
          "icon": "ri-terminal-line"
        }
      ]
    },
    {
      "id": "system-services",
      "name": "系统服务",
      "icon": "ri-server-line",
      "children": [...]
    },
    {
      "id": "storage",
      "name": "存储管理",
      "icon": "ri-folder-5-line",
      "children": [...]
    },
    {
      "id": "ops-tools",
      "name": "运维工具",
      "icon": "ri-tools-line",
      "children": [...]
    },
    {
      "id": "system-admin",
      "name": "系统管理",
      "icon": "ri-admin-line",
      "children": [...]
    }
  ]
}
```

## 五、实施步骤

### Phase 1: API 兼容层
1. 创建统一的网络管理 API 接口
2. 实现 OpenWrt 和 LAN 的适配器
3. 保持向后兼容

### Phase 2: 菜单重构
1. 更新 menu-config.json
2. 重命名/合并页面文件
3. 更新路由和链接

### Phase 3: 术语替换
1. 更新所有页面标题
2. 更新按钮文本
3. 更新提示信息

### Phase 4: 图标检查
1. 验证所有图标存在性
2. 替换不支持的图标
3. 统一图标风格

## 六、风险与注意事项

1. **API 兼容性**：确保旧 API 仍能工作，或提供迁移期
2. **用户习惯**：重大菜单变更需要用户引导
3. **书签失效**：页面路径变更会影响用户书签
4. **权限控制**：合并后需要重新设计权限粒度
