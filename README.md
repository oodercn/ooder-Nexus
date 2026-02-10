# ooderNexus - P2P AI 能力分发枢纽

## 1. 项目简介

### 1.1 什么是 ooderNexus？

ooderNexus 是一个基于 Ooder Agent 架构的 **P2P AI 能力分发枢纽**，采用 MIT 开源协议。它将去中心化的 P2P 网络技术与 AI 能力管理相结合，让用户能够在本地网络中构建私有的 AI 能力共享平台。

**核心能力**：
- 🤝 **去中心化组网** - 无需中心服务器，节点间直接通信
- 🧠 **AI 技能管理** - 发布、分享、执行 AI 技能
- 🔧 **网络管理中枢** - 可视化网络拓扑和设备管理
- 📡 **OpenWrt 集成** - 深度集成路由器系统
- 🧪 **协议仿真调试** - 离线开发和测试

### 1.2 版本信息

**当前版本：2.0.0**（生产就绪版本）

- ✅ 完整功能实现，可用于生产环境
- ✅ 经过多场景测试验证
- ✅ 企业级稳定性和安全性

### 1.3 适用场景

| 场景 | 部署设备 | 主要用途 |
|------|----------|----------|
| 🏠 **家庭智能中枢** | OpenWrt 路由器 | 智能家居控制、网络管理 |
| 🏢 **企业 AI 平台** | 服务器/工控机 | 内部 AI 能力共享 |
| 🌐 **边缘计算网络** | 树莓派/开发板 | 分布式计算节点 |
| 💻 **开发测试平台** | PC/笔记本 | 协议开发、技能调试 |

---

## 2. 核心功能

### 2.1 功能概览

ooderNexus 2.0.0 包含以下六大核心功能模块：

```
┌─────────────────────────────────────────────────────────────┐
│                     ooderNexus 2.0.0                        │
├─────────────┬─────────────┬─────────────┬───────────────────┤
│  P2P 网络   │  AI 技能    │  OpenWrt   │   协议仿真        │
│   管理      │   中心      │   集成     │   调试           │
├─────────────┼─────────────┼─────────────┼───────────────────┤
│ • 网络拓扑  │ • 技能发布  │ • 系统监控 │ • MCP 仿真       │
│ • 节点发现  │ • 技能分享  │ • 网络配置 │ • Route 仿真     │
│ • 链路管理  │ • 技能执行  │ • IP 管理  │ • 场景测试       │
│ • 流量监控  │ • 技能同步  │ • 命令执行 │ • 日志分析       │
├─────────────┴─────────────┴─────────────┴───────────────────┤
│              存储管理  │  系统监控  │  用户中心            │
└─────────────────────────────────────────────────────────────┘
```

### 2.2 功能详解

#### 2.2.1 P2P 网络管理
- **无中心架构**：基于 Ooder Agent 协议，自动组网
- **智能发现**：UDP 广播 + 心跳检测，自动发现节点
- **网络拓扑**：可视化展示 P2P 网络结构
- **链路管理**：节点间链路创建、监控、断开
- **流量监控**：实时网络流量统计和分析

#### 2.2.2 AI 技能中心
- **技能发布**：将本地 AI 能力发布到网络
- **技能分享**：分享给特定用户或群组
- **技能执行**：远程执行网络中的技能
- **技能同步**：多节点间技能自动同步
- **技能市场**：浏览和安装网络中的技能

#### 2.2.3 OpenWrt 集成（特色功能）
- **系统监控**：CPU、内存、温度、运行时间
- **网络配置**：接口管理、DHCP、防火墙
- **IP 管理**：静态 IP 分配、DHCP 租约
- **访问控制**：黑白名单、MAC 过滤
- **命令执行**：Web 界面执行 Shell 命令
- **配置文件**：查看和编辑系统配置

#### 2.2.4 协议仿真与调试
- **MCP 仿真器**：模拟 MCP 协议通信
- **Route 仿真器**：模拟 Route 协议路由
- **场景测试**：自定义测试场景
- **可视化调试**：图形化展示协议流程
- **执行日志**：详细的协议执行记录

#### 2.2.5 存储管理
- **虚拟文件系统**：统一的文件管理接口
- **文件版本**：自动版本控制和回滚
- **文件分享**：接收和分享文件
- **哈希校验**：MD5 文件完整性校验

#### 2.2.6 系统监控
- **健康检查**：系统、网络、服务状态
- **性能指标**：CPU、内存、磁盘监控
- **告警管理**：阈值告警和通知
- **日志管理**：系统日志查看和分析

---

## 3. 技术架构

### 3.1 系统架构图

```
┌─────────────────────────────────────────────────────────────┐
│                        用户界面层                            │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐       │
│  │ Web 控制台│ │  REST API │ │  协议仿真 │ │  OpenWrt │       │
│  │(HTML/JS) │ │           │ │   界面   │ │  管理界面│       │
│  └────┬─────┘ └────┬─────┘ └────┬─────┘ └────┬─────┘       │
└───────┼────────────┼────────────┼────────────┼──────────────┘
        │            │            │            │
        └────────────┴────────────┴────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────────┐
│                        应用服务层                            │
│  ┌──────────────┐ ┌──────────────┐ ┌──────────────┐         │
│  │  P2P 网络    │ │  AI 技能     │ │  存储管理    │         │
│  │  管理服务    │ │  管理服务    │ │  服务        │         │
│  └──────────────┘ └──────────────┘ └──────────────┘         │
│  ┌──────────────┐ ┌──────────────┐ ┌──────────────┐         │
│  │ OpenWrt      │ │ 协议仿真     │ │ 系统监控     │         │
│  │ 集成服务     │ │ 引擎         │ │ 服务         │         │
│  └──────────────┘ └──────────────┘ └──────────────┘         │
└─────────────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────────┐
│                        基础平台层                            │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              Ooder Agent SDK 0.6.6                  │   │
│  │  (P2P 通信、服务发现、命令处理、协议适配)            │   │
│  └─────────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              Spring Boot 2.7.0 + Java 8              │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

### 3.2 技术栈

| 层次 | 技术 | 用途 |
|------|------|------|
| **前端** | HTML5, CSS3, JavaScript | Web 控制台界面 |
| **后端** | Spring Boot 2.7.0, Java 8 | 业务逻辑处理 |
| **P2P 网络** | Ooder Agent SDK 0.6.6 | 底层网络通信 |
| **存储** | 本地文件系统 + VFS | 数据和文件存储 |
| **配置** | UCI (OpenWrt) | 路由器配置管理 |

### 3.3 与 Ooder Agent 的关系

ooderNexus 基于 Ooder Agent SDK 构建，两者的关系如下：

| 对比项 | Ooder Agent | ooderNexus |
|--------|-------------|------------|
| **定位** | P2P 网络基础库 | AI 能力分发应用 |
| **功能** | 网络通信、服务发现 | 技能管理、Web 控制台 |
| **界面** | 无 | 完整的 Web 界面 |
| **部署** | 嵌入式集成 | 独立应用 |
| **用户** | 开发者 | 终端用户 |

---

## 4. 快速开始

### 4.1 环境要求

- **操作系统**：Windows / Linux / macOS / OpenWrt
- **Java**：JDK 8 或更高（OpenWrt 除外）
- **内存**：最低 64MB，推荐 128MB+
- **存储**：最低 100MB，推荐 256MB+

### 4.2 安装方式

#### 方式一：通用安装（推荐）

适用于 Windows、Linux、macOS：

```bash
# 1. 下载安装包
wget https://github.com/oodercn/ooder-Nexus/releases/download/v2.0.0-openwrt-preview/ooder-nexus-2.0.0-openwrt-preview.tar.gz

# 2. 解压
tar -xzf ooder-nexus-2.0.0-openwrt-preview.tar.gz
cd ooder-nexus-2.0.0-openwrt-preview

# 3. 启动
./bin/start.sh
```

访问：`http://localhost:8091/console/index.html`

#### 方式一（Windows）：OpenWrt 预览版

适用于 Windows 用户测试 OpenWrt 管理功能：

```bash
# 1. 下载 Windows OpenWrt 预览版
https://github.com/oodercn/ooder-Nexus/releases/download/v2.0.0-openwrt-preview/ooder-nexus-2.0.0-openwrt-preview-windows.zip

# 2. 解压到任意目录

# 3. 双击运行 start.bat
```

访问：`http://localhost:8081/console/index.html`

> **注意**：OpenWrt 预览版专注于 OpenWrt 路由器管理功能，默认关闭 Mock 模式，需要连接真实的 OpenWrt 设备进行测试。

#### 方式二：OpenWrt 一键安装

适用于 OpenWrt 路由器：

```bash
# 在路由器上执行
wget -O /tmp/install.sh https://github.com/oodercn/ooder-Nexus/releases/download/v2.0.0-openwrt-preview/install-openwrt.sh
chmod +x /tmp/install.sh
/tmp/install.sh
```

访问：`http://路由器IP:8091/console/index.html`

#### 方式三：Docker 安装

```bash
docker run -d \
  --name ooder-nexus \
  -p 8091:8091 \
  -p 9876:9876 \
  -v ./data:/app/data \
  oodercn/ooder-nexus:2.0.0
```

### 4.3 首次使用

1. **访问 Web 控制台**：浏览器打开 `http://IP:8091/console/index.html`
2. **查看仪表盘**：了解系统状态和基本信息
3. **探索功能菜单**：熟悉各个功能模块
4. **加入 P2P 网络**：配置网络发现，连接其他节点

### 4.4 配置说明

#### 4.4.1 Mock 模式开关（重要）

ooderNexus 支持两种运行模式：

| 模式 | 说明 | 适用场景 |
|------|------|----------|
| **真实模式** (默认) | 连接真实的 OpenWrt 路由器和设备 | 生产环境、真实设备管理 |
| **Mock 模式** | 使用模拟数据，无需真实设备 | 开发测试、功能演示、离线体验 |

**配置文件位置**：`config/application.yml`

**修改 Mock 开关**：

```yaml
# Mock 模式开关配置
# 设置为 true 启用模拟数据模式（无需真实设备即可测试功能）
# 设置为 false 启用真实设备模式（需要连接真实的 OpenWrt 路由器）
mock:
  enabled: false  # 默认关闭，使用真实模式
```

**快速切换**：

```bash
# 启用 Mock 模式（开发测试）
sed -i 's/mock:\s*enabled:\s*false/mock:\n  enabled: true/' config/application.yml

# 禁用 Mock 模式（生产环境）
sed -i 's/mock:\s*enabled:\s*true/mock:\n  enabled: false/' config/application.yml
```

**注意事项**：
- 🚨 **生产环境**：务必设置为 `mock.enabled: false`，确保管理真实设备
- 🧪 **开发测试**：可以设置为 `mock.enabled: true`，无需真实设备即可测试功能
- 🔄 **修改后重启**：修改配置后需要重启服务才能生效
- 📊 **数据隔离**：Mock 模式和真实模式的数据是隔离的，切换模式不会影响另一种模式的数据

#### 4.4.2 其他常用配置

```yaml
server:
  port: 8081  # Web 服务端口

ooder:
  agent:
    id: "mcp-agent-001"     # Agent ID
    name: "Nexus Agent"      # Agent 名称
    type: "mcp"              # Agent 类型: mcp/routeAgent/nexus
  udp:
    port: 9876               # UDP 通信端口
    host: 0.0.0.0            # 监听地址
```

### 4.5 数据存储说明

ooderNexus 使用本地文件系统存储数据，所有数据默认保存在 `./storage/` 目录（OpenWrt 为 `/opt/ooder-nexus/storage/`）。

#### 4.5.1 存储目录结构

```
storage/                          # 主存储目录
├── agents/                       # Agent 节点信息
│   └── end-agents.json          # 终端代理列表
├── devices/                      # 设备资产管理
│   └── device-assets.json       # 设备资产数据
├── network/                      # 网络配置数据
│   ├── ip-allocations.json      # IP 地址分配记录
│   ├── ip-pool.json             # IP 地址池配置
│   └── traffic-stats.json       # 流量统计信息
├── sdk/                          # Ooder SDK 核心数据
│   ├── capabilities/            # 能力定义
│   │   ├── endagent-discovery.json
│   │   ├── link-management.json
│   │   ├── network-topology.json
│   │   └── route-discovery.json
│   ├── devices/                 # 设备实例数据
│   │   ├── device-001.json
│   │   ├── device-002.json
│   │   └── ...
│   ├── endagents/               # 终端代理详情
│   │   ├── endagent-001.json
│   │   ├── endagent-002.json
│   │   └── ...
│   ├── routes/                  # 路由配置
│   │   ├── route-001.json
│   │   └── ...
│   └── system_status/           # 系统状态
│       └── main.json
├── sync/                         # 同步任务数据
│   └── sync-tasks.json          # 技能同步任务
└── tasks/                        # 数据提取任务
    ├── extract-tasks.json
    └── list-extract-tasks.json
```

#### 4.5.2 Ooder SDK 0.6.6 存储架构

ooderNexus 基于 **Ooder Agent SDK 0.6.6** 构建，SDK 采用 **VFS（Virtual File System，虚拟文件系统）+ JSON 存储** 的混合架构。

##### VFS 存储策略

**什么是 VFS？**

VFS 是 Agent SDK 的核心存储抽象层，提供统一的文件管理接口，将物理存储与逻辑存储分离：

```
┌─────────────────────────────────────────────────────────────┐
│                      应用层 (ooderNexus)                     │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐       │
│  │ 文件管理  │ │ 版本控制  │ │ 文件分享  │ │ 存储管理  │       │
│  └────┬─────┘ └────┬─────┘ └────┬─────┘ └────┬─────┘       │
└───────┼────────────┼────────────┼────────────┼──────────────┘
        │            │            │            │
        └────────────┴──────┬─────┴────────────┘
                            │
                    ┌───────▼────────┐
                    │   VFS 抽象层    │
                    │ (LocalVFSManager)│
                    └───────┬────────┘
                            │
        ┌───────────────────┼───────────────────┐
        ▼                   ▼                   ▼
┌───────────────┐   ┌───────────────┐   ┌───────────────┐
│  本地文件系统  │   │  内存缓存     │   │  网络存储     │
│  (storage/)   │   │  (Hash Cache) │   │  (P2P Sync)   │
└───────────────┘   └───────────────┘   └───────────────┘
```

**VFS 核心组件**：

| 组件 | 类名 | 功能 |
|------|------|------|
| **VFS 管理器** | `LocalVFSManager` | 单例模式管理所有文件操作 |
| **文件对象** | `LocalFileObject` | 基于 MD5 哈希的文件存储 |
| **文件信息** | `LocalFile` | 文件元数据管理 |
| **文件夹** | `LocalFolder` | 目录结构管理 |
| **版本控制** | `LocalFileVersion` | 文件版本管理 |

**VFS 存储特点**：

1. **哈希去重存储**
   - 文件内容使用 **MD5 哈希** 作为唯一标识
   - 相同内容只存储一份，节省空间
   - 文件路径：`storage/data/cache/{hash}`

2. **写时复制（Copy-on-Write）**
   ```java
   // 文件修改时创建新版本，不覆盖原文件
   LocalFileVersion newVersion = file.createFileVersion();
   newVersion.setFileObjectId(hash);
   ```

3. **原子操作保证**
   - 使用临时文件 + 重命名机制
   - 防止写入过程中断导致数据损坏
   - 示例流程：
     ```
     1. 写入临时文件: file.tmp
     2. 计算 MD5 哈希
     3. 重命名为: {hash}
     4. 更新索引
     ```

4. **内存缓存加速**
   - 热点文件缓存到内存
   - 减少磁盘 I/O 操作
   - 缓存目录：`storage/data/cache/`

##### Agent SDK 数据存储

**存储机制**：

Agent SDK 使用 **JSON 文件存储** 管理结构化数据，与 VFS 文件存储形成互补：

| 存储类型 | 技术 | 适用场景 |
|---------|------|----------|
| **VFS 存储** | MD5 哈希 + 文件系统 | 大文件、二进制数据、版本控制 |
| **JSON 存储** | 结构化 JSON 文件 | 配置数据、元数据、状态信息 |

**数据分类与存储位置**：

| 数据类型 | 存储位置 | 格式 | 说明 |
|---------|----------|------|------|
| **网络能力** | `sdk/capabilities/` | JSON | P2P 网络能力定义 |
| **设备信息** | `sdk/devices/` | JSON | 每个设备独立文件 |
| **终端代理** | `sdk/endagents/` | JSON | 代理节点详细信息 |
| **路由配置** | `sdk/routes/` | JSON | 网络路由规则 |
| **系统状态** | `sdk/system_status/` | JSON | 运行时状态 |
| **文件内容** | `data/cache/{hash}` | Binary | VFS 哈希存储 |
| **网络数据** | `network/` | JSON | IP、流量统计 |
| **任务数据** | `tasks/`, `sync/` | JSON | 异步任务 |

**数据关系与同步**：

```
┌─────────────────────────────────────────────────────────────┐
│                    Agent SDK 数据关系                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│   ┌─────────────┐     ┌─────────────┐     ┌─────────────┐  │
│   │  网络能力    │────▶│   设备实例   │────▶│  终端代理   │  │
│   │capabilities │     │   devices   │     │  endagents  │  │
│   │  (JSON)     │     │   (JSON)    │     │   (JSON)    │  │
│   └─────────────┘     └──────┬──────┘     └──────┬──────┘  │
│          │                   │                   │         │
│          │                   ▼                   │         │
│          │            ┌─────────────┐            │         │
│          │            │  VFS 文件   │◀───────────┘         │
│          │            │  (Hash)     │                      │
│          │            └─────────────┘                      │
│          │                                                   │
│          ▼                   ▼                              │
│   ┌─────────────┐     ┌─────────────┐                      │
│   │   路由配置   │◀────│   系统状态   │                      │
│   │   routes    │     │ system_status│                      │
│   │   (JSON)    │     │   (JSON)    │                      │
│   └─────────────┘     └─────────────┘                      │
│                                                             │
│   ═══════════════════════════════════════════════════════  │
│                         P2P 同步层                          │
│   默认情况下，所有 Agent 数据会自动同步到网络中的其他节点      │
└─────────────────────────────────────────────────────────────┘
```

**数据同步策略**：

Agent SDK 0.6.6 内置 **P2P 数据同步机制**：

1. **自动同步范围**
   - ✅ `sdk/` 目录下的所有 JSON 数据
   - ✅ 网络拓扑信息
   - ✅ 设备发现信息
   - ✅ 路由配置信息
   - ❌ VFS 文件内容（大文件按需同步）
   - ❌ 日志文件

2. **同步触发条件**
   - 节点加入网络时全量同步
   - 数据变更时增量同步
   - 定时心跳检测差异同步

3. **冲突解决**
   - 时间戳优先：`_updatedAt` 字段较新的胜出
   - 手动合并：冲突时保留多个版本

**存储优势**：

| 优势 | 说明 |
|------|------|
| ✅ **无需数据库** | 零依赖，开箱即用 |
| ✅ **去重存储** | MD5 哈希避免重复文件 |
| ✅ **版本控制** | 自动文件版本管理 |
| ✅ **P2P 同步** | 数据自动同步到网络节点 |
| ✅ **易于备份** | 直接复制目录即可 |
| ✅ **人类可读** | JSON 格式便于调试 |

**注意事项**：

| 注意点 | 说明 |
|--------|------|
| ⚠️ **不要手动修改** | 运行时不要编辑 JSON 文件 |
| ⚠️ **备份 storage/** | 必须备份整个 storage 目录 |
| ⚠️ **权限设置** | 确保程序有读写权限 |
| ⚠️ **存储空间** | 监控磁盘空间，避免满盘 |
| ⚠️ **同步延迟** | P2P 同步有延迟，非实时 |

#### 4.5.3 数据备份与恢复

**⚠️ 重要提示**：备份时必须包含完整的 `storage/` 目录，因为 VFS 文件和 JSON 数据是相互关联的。

**备份数据**：

```bash
# 方式 1：直接复制目录（推荐，保留文件权限）
cp -rp storage/ storage-backup-$(date +%Y%m%d)/

# 方式 2：打包压缩（适合传输和长期保存）
tar -czpf ooder-nexus-backup-$(date +%Y%m%d).tar.gz storage/ config/

# 方式 3：使用 rsync（增量备份，适合自动化）
rsync -avz --delete storage/ backup-server:/backups/ooder-nexus/storage/
rsync -avz --delete config/ backup-server:/backups/ooder-nexus/config/
```

**备份内容说明**：

| 目录 | 必须备份 | 说明 |
|------|----------|------|
| `storage/sdk/` | ✅ 必须 | Agent SDK 核心数据（网络、设备、路由） |
| `storage/data/` | ✅ 必须 | VFS 文件存储（哈希文件、缓存） |
| `storage/network/` | ✅ 必须 | 网络配置和流量统计 |
| `storage/tasks/` | ✅ 必须 | 任务数据 |
| `config/` | ⚠️ 建议 | 应用配置文件 |
| `logs/` | ❌ 不需要 | 日志文件可随时生成 |

**恢复数据**：

```bash
# 1. 停止服务
/etc/init.d/ooder-nexus stop  # OpenWrt
# 或
./bin/stop.sh                 # 通用安装

# 2. 备份当前数据（以防万一）
mv storage/ storage-old-$(date +%Y%m%d)/

# 3. 恢复数据
tar -xzpf ooder-nexus-backup-20240101.tar.gz

# 4. 检查数据完整性
ls -la storage/sdk/           # 检查 JSON 文件
ls -la storage/data/cache/    # 检查 VFS 缓存

# 5. 启动服务
/etc/init.d/ooder-nexus start

# 6. 验证恢复
# 登录 Web 控制台，检查数据是否完整
```

**VFS 数据一致性检查**：

如果怀疑 VFS 数据损坏，可以运行一致性检查：

```bash
# 检查所有哈希文件是否存在
find storage/data/cache/ -type f | while read file; do
  hash=$(basename "$file")
  echo "检查: $hash"
done

# 检查 JSON 索引中的文件引用
# 如果索引中引用的哈希文件不存在，需要清理无效索引
```

**自动备份脚本（生产环境推荐）**：

```bash
#!/bin/sh
# 保存为 /opt/ooder-nexus/backup.sh
# 生产环境自动备份脚本

set -e

INSTALL_DIR="/opt/ooder-nexus"
BACKUP_DIR="/mnt/usb/backups"
DATE=$(date +%Y%m%d_%H%M%S)
KEEP_DAYS=30
LOG_FILE="/var/log/ooder-nexus-backup.log"

# 日志函数
log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" | tee -a $LOG_FILE
}

log "开始备份..."

# 检查目录
if [ ! -d "$INSTALL_DIR/storage" ]; then
    log "错误: storage 目录不存在"
    exit 1
fi

# 创建备份目录
mkdir -p $BACKUP_DIR

# 创建临时备份目录
TEMP_DIR=$(mktemp -d)

# 复制数据（保持权限）
log "复制 storage 目录..."
cp -rp $INSTALL_DIR/storage $TEMP_DIR/

log "复制 config 目录..."
cp -rp $INSTALL_DIR/config $TEMP_DIR/

# 创建备份信息文件
cat > $TEMP_DIR/backup-info.txt << EOF
备份时间: $(date)
备份版本: $(cat $INSTALL_DIR/version 2>/dev/null || echo "unknown")
主机名: $(hostname)
EOF

# 打包压缩
log "创建压缩包..."
tar -czpf ${BACKUP_DIR}/ooder-nexus-backup-${DATE}.tar.gz -C $TEMP_DIR .

# 清理临时目录
rm -rf $TEMP_DIR

# 删除旧备份
log "清理旧备份..."
find ${BACKUP_DIR} -name "ooder-nexus-backup-*.tar.gz" -mtime +${KEEP_DAYS} -delete

# 检查备份文件
BACKUP_SIZE=$(du -h ${BACKUP_DIR}/ooder-nexus-backup-${DATE}.tar.gz | cut -f1)
log "备份完成: ${BACKUP_DIR}/ooder-nexus-backup-${DATE}.tar.gz (大小: $BACKUP_SIZE)"

# 可选：上传到远程服务器
# scp ${BACKUP_DIR}/ooder-nexus-backup-${DATE}.tar.gz user@backup-server:/backups/
```

**添加到定时任务**：

```bash
# 编辑 crontab
crontab -e

# 每天凌晨 3 点执行备份
0 3 * * * /opt/ooder-nexus/backup.sh

# 每周日凌晨 3 点执行备份并上传到远程服务器
0 3 * * 0 /opt/ooder-nexus/backup.sh && scp /mnt/usb/backups/ooder-nexus-backup-$(date +\%Y\%m\%d)*.tar.gz user@backup-server:/backups/
```

**P2P 网络数据恢复注意事项**：

当从备份恢复数据后，节点重新加入 P2P 网络时：

1. **数据同步**：节点会自动与网络中的其他节点同步数据
2. **冲突处理**：如果备份数据较旧，可能会被网络中的新数据覆盖
3. **建议操作**：
   - 恢复后先断开网络连接
   - 检查数据完整性
   - 确认无误后再连接网络

```bash
# 恢复后断开网络同步（临时）
# 编辑配置，禁用自动发现
uci set ooder-nexus.network.auto_discovery=0
uci commit ooder-nexus

# 启动服务检查数据
/etc/init.d/ooder-nexus start

# 确认数据完整后，重新启用网络
uci set ooder-nexus.network.auto_discovery=1
uci commit ooder-nexus
/etc/init.d/ooder-nexus restart
```

---

## 5. 部署指南

### 5.1 部署场景选择

| 场景 | 推荐设备 | 部署方式 | Agent 角色 | 特点 |
|------|----------|----------|------------|------|
| 个人使用 | PC / NAS | 通用安装 | `mcp` / `nexus` | 功能完整，易于管理 |
| 家庭网络 | OpenWrt 路由器 | 一键安装 | **`routeAgent`** ⭐ | 24小时运行，网络中枢，自动路由 |
| 企业部署 | 服务器 | Docker | `mcp` / `routeAgent` | 高可用，易扩展 |
| 边缘节点 | 树莓派 | 通用安装 | `mcp` / `nexus` | 低功耗，分布式 |

**Agent 角色说明**：
- **`routeAgent`**：路由代理，具备网络路由能力，适合部署在路由器/网关设备上
- **`mcp`**：标准 MCP 节点，普通终端设备角色
- **`nexus`**：基础节点，最小功能集

**OpenWrt 自动设置**：当 ooderNexus 部署在 OpenWrt 设备上时，系统会**自动**将 Agent 角色设置为 `routeAgent`，无需手动配置。

### 5.2 OpenWrt 部署详解

OpenWrt 是 ooderNexus 的典型部署场景，提供完整的集成能力。

#### 5.2.1 为什么选择 OpenWrt？

- ✅ 路由器 24 小时运行，无需额外设备
- ✅ 网络中枢位置，便于管理所有设备
- ✅ 原生集成，深度管理路由器功能
- ✅ 低功耗，适合长期运行

#### 5.2.2 支持的设备

| 设备类型 | 代表型号 | 性能 | 难度 |
|----------|----------|------|------|
| x86 软路由 | J4125/N5105 | ⭐⭐⭐⭐⭐ | ⭐ |
| ARM 开发板 | 树莓派 4 | ⭐⭐⭐⭐ | ⭐⭐ |
| MIPS 路由器 | 红米 AC2100 | ⭐⭐ | ⭐⭐⭐ |

#### 5.2.3 安装步骤

详见 [OpenWrt 安装指南](#6-openwrt-安装指南)

---

## 6. OpenWrt 安装指南

> ⚠️ **重要提示**：本章节专为 OpenWrt 用户编写，包含详细的集成说明和操作步骤。

### 6.1 集成概述

#### 6.1.1 什么是 OpenWrt 集成？

ooderNexus 安装到 OpenWrt 路由器后，可以通过 Web 界面直接管理路由器的各种功能，包括：

- 查看系统状态（CPU、内存、温度）
- 管理网络配置（接口、DHCP、防火墙）
- 执行系统命令
- 监控网络流量

#### 6.1.2 OpenWrt 自动角色设置（重要）

**自动检测机制**：

ooderNexus 启动时会**自动检测**当前系统是否为 OpenWrt：

| 检测方式 | 检测目标 | 说明 |
|---------|---------|------|
| 文件检测 | `/etc/openwrt_release` | OpenWrt 版本文件 |
| 内容检测 | `/etc/os-release` | 系统标识文件 |
| 命令检测 | `/bin/opkg` 或 `/usr/bin/opkg` | 包管理器 |
| 命令检测 | `/bin/uci` 或 `/usr/bin/uci` | 配置管理工具 |

**自动角色切换**：

当检测到 OpenWrt 系统时，ooderNexus 会**自动将 Agent 角色设置为 `routeAgent`**：

```
启动日志示例：
[INFO] 检测到 OpenWrt 系统: /etc/openwrt_release 文件存在
[INFO] OpenWrt 系统检测到，自动将 Agent 角色设置为 'routeAgent'
[INFO] 原始配置 agent.type: mcp，已覆盖为: routeAgent
[INFO] Agent 配置完成: id=mcp-agent-001, name=Independent MCP Agent, type=routeAgent
```

**为什么需要设置为 routeAgent？**

| 角色 | 适用场景 | 功能特点 |
|------|----------|----------|
| `routeAgent` | **路由器/网关设备** | 具备路由能力，可管理网络拓扑，转发数据包 |
| `mcp` | 普通终端设备 | 标准 MCP 协议支持，作为终端节点接入网络 |
| `nexus` | 独立节点 | 基础 P2P 功能，不参与路由决策 |

**OpenWrt 设备作为 routeAgent 的优势**：
- ✅ **网络中枢**：位于网络核心位置，便于管理所有设备
- ✅ **路由能力**：可以转发其他节点的数据包
- ✅ **拓扑核心**：在 P2P 网络拓扑中作为关键节点
- ✅ **网关功能**：可以代理其他节点的网络请求

**注意事项**：
- 自动检测和角色切换在**启动时**完成
- 如需手动覆盖，可修改 `application.yml` 中的 `agent.type` 配置
- 角色切换后，其他节点会通过 P2P 网络自动发现该 routeAgent

#### 6.1.3 集成架构

```
用户浏览器 → ooderNexus Web → OpenWrt API → OpenWrt 系统
                ↓
           P2P 网络服务
```

### 6.2 安装前准备

#### 6.2.1 系统要求

- **OpenWrt 版本**：21.02 或更高
- **内存**：最低 64MB，推荐 128MB+
- **存储**：最低 100MB，推荐 256MB+
- **网络**：已配置并能访问互联网

#### 6.2.2 检查系统

```bash
# 检查 OpenWrt 版本
cat /etc/openwrt_release

# 检查内存
free

# 检查存储
df -h
```

#### 6.2.3 配置防火墙

必须开放以下端口：
- `8091/tcp` - Web 控制台
- `9876/tcp+udp` - P2P 通信

**图形界面配置**：
1. 登录 OpenWrt 后台 → 网络 → 防火墙
2. 添加通信规则：
   - 名称：`ooderNexus`
   - 协议：TCP+UDP
   - 端口：8091,9876
   - 操作：接受

**命令行配置**：
```bash
uci add firewall rule
uci set firewall.@rule[-1].name='ooderNexus'
uci set firewall.@rule[-1].src='wan'
uci set firewall.@rule[-1].dest_port='8091 9876'
uci set firewall.@rule[-1].proto='tcp udp'
uci set firewall.@rule[-1].target='ACCEPT'
uci commit firewall
/etc/init.d/firewall restart
```

### 6.3 安装步骤

#### 6.3.1 一键安装（推荐）

```bash
wget -O /tmp/install.sh https://github.com/oodercn/ooder-Nexus/releases/download/v2.0.0-openwrt-preview/install-openwrt.sh
chmod +x /tmp/install.sh
/tmp/install.sh
```

安装脚本会自动完成：
1. 检测系统架构
2. 检查/安装 Java
3. 下载安装包
4. 配置服务
5. 启动服务

#### 6.3.2 验证安装

```bash
# 检查服务状态
/etc/init.d/ooder-nexus status

# 查看日志
tail -f /opt/ooder-nexus/logs/system.log
```

访问：`http://路由器IP:8091/console/index.html`

### 6.4 安装后配置

#### 6.4.1 常用命令

```bash
# 启动/停止/重启
/etc/init.d/ooder-nexus start
/etc/init.d/ooder-nexus stop
/etc/init.d/ooder-nexus restart

# 开机自启
/etc/init.d/ooder-nexus enable

# 查看状态
/etc/init.d/ooder-nexus status
```

#### 6.4.2 文件位置

```
/opt/ooder-nexus/
├── bin/start.sh          # 启动脚本
├── config/               # 配置文件
├── lib/                  # 程序文件
├── storage/              # 数据存储目录（重要）
│   ├── agents/          # Agent 节点信息
│   ├── devices/         # 设备资产数据
│   ├── network/         # 网络配置数据
│   ├── sdk/             # Ooder SDK 核心数据
│   ├── sync/            # 同步任务数据
│   └── tasks/           # 数据提取任务
├── data/                 # 旧版数据目录（兼容）
└── logs/                 # 日志目录
    ├── system.log       # 系统日志
    └── error.log        # 错误日志
```

**重要提示**：`storage/` 目录包含所有业务数据，**必须定期备份**！

#### 6.4.3 故障排查

**问题 1：无法访问 Web 界面**
- 检查防火墙规则
- 检查服务状态
- 查看错误日志

**问题 2：OpenWrt 菜单不显示**
- 清除浏览器缓存
- 检查系统识别日志
- 重启服务

**问题 3：内存不足**
- 创建交换分区
- 减小 Java 内存配置

---

## 7. 使用指南

### 7.1 Web 控制台

#### 7.1.1 界面布局

```
┌─────────────────────────────────────────────────────┐
│  Logo    搜索                    通知  用户  设置   │
├────────┬────────────────────────────────────────────┤
│        │                                            │
│  菜单   │              主内容区                      │
│        │                                            │
│  - 仪表盘│                                            │
│  - 网络 │                                            │
│  - 技能 │                                            │
│  - 存储 │                                            │
│  - 系统 │                                            │
│        │                                            │
└────────┴────────────────────────────────────────────┘
```

#### 7.1.2 主要菜单

| 菜单 | 功能 |
|------|------|
| **仪表盘** | 系统概览、统计数据 |
| **P2P 网络** | 网络拓扑、节点管理 |
| **AI 技能** | 技能管理、市场、执行历史 |
| **存储管理** | 文件管理、版本控制 |
| **OpenWrt** | 路由器管理（仅 OpenWrt） |
| **系统监控** | 健康检查、日志、告警 |

### 7.2 典型操作

#### 7.2.1 发布技能

1. 进入「AI 技能」→「我的技能」
2. 点击「发布技能」
3. 填写技能信息（名称、描述、命令）
4. 点击「发布」

#### 7.2.2 加入 P2P 网络

1. 进入「P2P 网络」→「网络设置」
2. 开启「自动发现」
3. 等待节点发现
4. 查看「网络拓扑」确认连接

#### 7.2.3 分享文件

1. 进入「存储管理」→「我的文件」
2. 选择文件，点击「分享」
3. 设置分享密码（可选）
4. 复制分享链接

---

## 8. API 文档

### 8.1 个人中心 API

#### 获取仪表盘统计
```http
GET /api/personal/dashboard/stats
```

#### 获取技能列表
```http
GET /api/personal/skills
```

#### 执行技能
```http
POST /api/personal/execution/execute/{skillId}
```

### 8.2 网络管理 API

#### 获取节点列表
```http
GET /api/network/nodes
```

#### 发现网络
```http
POST /api/network/discovery
```

### 8.3 OpenWrt API（OpenWrt 专属）

#### 获取系统状态
```http
GET /api/openwrt/status
```

#### 执行命令
```http
POST /api/openwrt/command
Content-Type: application/json

{
  "command": "uptime"
}
```

---

## 9. 开发指南

### 9.1 协议仿真开发

#### 9.1.1 创建测试场景

1. 进入「协议仿真」→「场景管理」
2. 点击「新建场景」
3. 添加步骤（发送命令、等待响应、验证结果）
4. 保存并运行

#### 9.1.2 调试协议

1. 选择「MCP 仿真器」或「Route 仿真器」
2. 输入协议命令
3. 查看执行结果和日志
4. 分析协议行为

### 9.2 技能开发

#### 9.2.1 技能规范

技能是一个可执行的命令或脚本，包含：
- **名称**：技能的显示名称
- **描述**：功能说明
- **命令**：实际执行的命令
- **参数**：输入参数定义
- **输出**：返回结果格式

#### 9.2.2 示例技能

```json
{
  "name": "系统信息",
  "description": "获取系统基本信息",
  "command": "uname -a && free -h",
  "params": [],
  "output": "text"
}
```

---

## 10. 常见问题

### Q1: ooderNexus 和 skillsCenter 的关系？

**A**: skillsCenter 是技能发布和分享的平台，ooderNexus 是技能运行和管理的节点。两者配合实现完整的 AI 能力生态。

### Q2: 是否必须运行在 OpenWrt 上？

**A**: 不是。ooderNexus 支持 Windows、Linux、macOS 等多种平台。OpenWrt 只是其中一个典型部署场景。

### Q3: 如何备份数据？

**A**: ooderNexus 使用 **VFS + JSON 混合存储**，备份时必须包含完整的 `storage/` 目录。

**OpenWrt 安装**：
```bash
# 备份所有数据（必须包含 storage/ 和 config/）
tar -czpf backup.tar.gz /opt/ooder-nexus/storage/ /opt/ooder-nexus/config/
```

**通用安装**：
```bash
# 备份所有数据
tar -czpf backup.tar.gz ./storage/ ./config/
```

**重要数据说明**：

| 目录 | 必须备份 | 说明 |
|------|----------|------|
| `storage/sdk/` | ✅ 必须 | Agent SDK 核心数据（网络拓扑、设备、路由） |
| `storage/data/` | ✅ 必须 | **VFS 文件存储**（哈希文件、缓存，与 JSON 数据关联） |
| `storage/network/` | ✅ 必须 | 网络配置和流量统计 |
| `storage/tasks/` | ✅ 必须 | 任务数据 |
| `config/` | ⚠️ 建议 | 应用配置文件 |
| `logs/` | ❌ 不需要 | 日志文件可随时生成 |

**⚠️ 特别注意**：
- VFS 文件使用 **MD5 哈希** 存储在 `storage/data/cache/`
- JSON 数据中引用的是哈希值，不是文件路径
- **必须同时备份** JSON 索引和 VFS 哈希文件，否则文件无法恢复

**P2P 同步说明**：
- 默认情况下，Agent SDK 会自动同步 `sdk/` 目录数据到网络中的其他节点
- VFS 大文件（`storage/data/`）默认**不会自动同步**，需要手动分享
- 恢复数据后重新加入网络，可能会与网络中的数据进行合并

详见 [数据存储说明](#44-数据存储说明) 章节。

### Q4: 支持哪些 AI 技能？

**A**: 理论上支持任何可命令行执行的 AI 能力，包括但不限于：
- 大语言模型（LLM）调用
- 图像识别和处理
- 语音识别和合成
- 数据分析和处理

### Q5: 如何保证安全？

**A**: 
- P2P 通信支持加密
- 技能执行有权限控制
- 支持访问白名单
- 所有数据本地存储

---

## 11. 版本历史

### v2.0.0-openwrt-preview（当前版本）- 2026-02-11

#### 🎯 主要更新

**OpenWrt 集成增强**
- ✨ **自动角色检测**：启动时自动检测 OpenWrt 系统，自动设置 Agent 角色为 `routeAgent`
- ✨ **深度系统集成**：支持路由器系统状态监控、网络配置管理、IP 地址管理
- ✨ **一键安装脚本**：提供 OpenWrt 专用安装脚本，自动完成环境配置

**存储架构升级**
- ✨ **VFS 虚拟文件系统**：基于 MD5 哈希的去重存储，支持文件版本控制
- ✨ **P2P 数据同步**：Agent SDK 数据自动同步到网络节点
- ✨ **混合存储策略**：JSON 结构化数据 + VFS 二进制文件存储

**协议仿真与调试**
- ✨ **MCP 协议仿真器**：离线模拟 MCP 协议通信
- ✨ **Route 协议仿真器**：模拟路由协议行为
- ✨ **场景化测试**：支持自定义测试场景和用例

**网络管理功能**
- ✨ **网络拓扑可视化**：图形化展示 P2P 网络结构
- ✨ **链路管理**：节点间链路创建、监控、断开
- ✨ **流量监控**：实时网络流量统计和分析

#### 🔧 优化与改进
- 🔧 重构前端界面，提升用户体验
- 🔧 优化 P2P 网络发现机制
- 🔧 增强系统监控和告警能力
- 🔧 改进技能管理和执行流程
- 🔧 完善 API 文档和错误处理

#### 🐛 问题修复
- 🐛 修复网络连接稳定性问题
- 🐛 修复文件存储并发访问问题
- 🐛 修复内存泄漏问题
- 🐛 修复多线程安全问题

#### 📦 发布文件

| 文件 | 说明 | 适用平台 |
|------|------|----------|
| `ooder-nexus-2.0.0-openwrt-preview.jar` | 可执行 JAR 包 | Windows/Linux/macOS |
| `ooder-nexus-2.0.0-openwrt-preview.tar.gz` | 通用安装包 | Windows/Linux/macOS |
| `ooder-nexus-2.0.0-openwrt-preview-windows.zip` | Windows 安装包 | Windows 10/11 |
| `ooder-nexus-2.0.0-openwrt-x86_64.tar.gz` | OpenWrt x86_64 | x86_64 软路由 |
| `ooder-nexus-2.0.0-openwrt-aarch64.tar.gz` | OpenWrt aarch64 | ARM64 设备 |
| `ooder-nexus-2.0.0-openwrt-armv7.tar.gz` | OpenWrt armv7 | ARMv7 设备 |
| `install-openwrt.sh` | OpenWrt 一键安装脚本 | OpenWrt 通用 |

### v1.0.0 - 2024-12-01
- 🎉 初始版本发布
- ✨ P2P 网络功能
- ✨ 基础技能管理
- ✨ 本地存储支持

---

## 12. 社区与支持

### 12.1 获取帮助

- 📖 **文档**：https://github.com/oodercn/ooder-Nexus/wiki
- 🐛 **Issue**：https://github.com/oodercn/ooder-Nexus/issues
- 💬 **讨论**：https://github.com/oodercn/ooder-Nexus/discussions

### 12.2 贡献代码

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

### 12.3 许可证

本项目采用 MIT 开源许可证 - 详见 [LICENSE](LICENSE) 文件

---

## 13. 致谢

- [Ooder Agent](https://github.com/oodercn/ooder-agent) - 底层 P2P 网络通信框架
- [Spring Boot](https://spring.io/projects/spring-boot) - 后端开发框架
- [OpenWrt](https://openwrt.org/) - 开源路由器操作系统

---

<p align="center">
  <b>ooderNexus - 让 AI 能力无处不在</b><br>
  Made with ❤️ by ooder Team
</p>
