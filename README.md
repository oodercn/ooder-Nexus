# ooderNexus - P2P AI 能力分发枢纽

## 项目概述

ooderNexus 是一个基于 Ooder Agent 架构的 P2P AI 能力分发枢纽产品，采用 MIT 开源协议。本项目旨在打造一个去中心化的 AI 能力共享和管理平台，支持个人、小微企业及部门内部部署，实现 AI 能力的安全、高效分发。

当前版本是在 Ooder Agent 核心 0.6.6 开发的预览版程序，0.6.6 以后会独立建立分支。部分程序未经严格测试，正式发布版本前仅供研究学习 Ooder Agent 框架。相关应用推荐配合 ooder-skillsCenter 使用。

### SDK 0.6.6 与示例程序说明

SDK 0.6.6 实现了完整的 Ooder Agent 协议，为 ooderNexus 提供了稳定的底层通信能力。ooderNexus 在 SDK 基础的通讯和能力管理上，针对常见的路由器安装、智能设备安装提供了网络和路由管理示例页面，同时针对家庭网关的特殊设备也提供了相应的开发示例。

开发者可以通过 skillsCenter 下载相应的 skills 插件，安装在自己的路由器或网关上，就可以有针对性地进行开发，使其支持跨生态的 AI 分发服务。

## Ooder Agent 与 ooderNexus 的关系

### 架构层次关系

| 层次 | 组件 | 职责 |
|------|------|------|
| 基础层 | Ooder Agent | 提供 P2P 网络通信、服务发现、命令处理等基础功能 |
| 应用层 | ooderNexus | 基于 Ooder Agent 构建的 AI 能力分发枢纽，提供高级功能 |

### 核心关系

1. **基础依赖**：ooderNexus 依赖 Ooder Agent 提供的底层能力，如 P2P 网络通信、服务发现等
2. **功能扩展**：ooderNexus 扩展了 Ooder Agent 的功能，实现了个人中心、技能管理、存储管理等高级功能
3. **集成运行**：ooderNexus 与 MCP Agent 集成在同一个 Spring Boot 应用程序中运行
4. **技能体系**：两者共享同一个技能体系，ooderNexus 提供更丰富的技能管理功能
5. **网络架构**：都采用 P2P 自组网架构，但 ooderNexus 提供了更高级的网络管理功能

### 技术集成

- **Agent SDK**：ooderNexus 使用 Ooder Agent SDK (v0.6.6) 进行 P2P 网络通信
- **技能管理**：ooderNexus 扩展了 Ooder Agent 的技能管理能力，提供更丰富的技能发布、分享、执行功能
- **存储系统**：ooderNexus 实现了基于本地文件系统的存储管理功能
- **监控系统**：ooderNexus 提供了更全面的系统监控和告警功能

### 部署关系

- **独立部署**：ooderNexus 可以作为独立应用部署，内置了 Ooder Agent 功能
- **网络互联**：多个 ooderNexus 节点可以通过 P2P 网络互联，形成分布式 AI 能力分发网络
- **扩展性**：ooderNexus 支持通过技能扩展功能，无需修改底层 Ooder Agent 代码

## 版本信息

**当前版本**：2.0.0

## 核心特性

### 1. P2P 自组网
- **无中心架构**：基于 Ooder Agent 架构，在无中心状态下采用 P2P 协议自组网
- **智能发现**：自动发现网络中的其他 Nexus 节点
- **弹性网络**：支持节点动态加入和退出，网络自动恢复

### 2. 多平台支持
- **PC 部署**：支持 Windows、Linux、macOS 等桌面系统
- **NAS 存储**：支持各类 NAS 设备，实现数据持久化存储
- **智能设备**：支持电视、路由器、智能家居网关等智能设备部署
- **OpenWrt 支持**：原生支持 OpenWrt 路由器系统，提供专用管理界面

### 3. 协议仿真与调试
- **协议仿真器**：内置 MCP 和 Route 协议仿真器，支持离线调试
- **可视化调试**：提供协议执行流程的可视化展示
- **场景模拟**：支持自定义场景进行协议行为模拟
- **执行日志**：详细的协议执行日志记录和分析

### 4. 可视化增强
- **网络拓扑可视化**：直观的 P2P 网络拓扑图展示
- **实时监控图表**：系统指标的实时图表展示
- **链路状态可视化**：节点间链路状态一目了然
- **交互式控制台**：现代化的 Web 控制台界面

### 5. AI 能力分发
- **能力共享**：支持 AI 技能的发布、分享和接收
- **技能市场**：提供技能发现和安装功能
- **执行管理**：支持技能的远程执行和结果管理
- **技能同步**：支持技能的多节点同步和版本管理

### 5. 本地存储支持
- **虚拟文件系统**：提供统一的虚拟文件系统接口
- **文件管理**：支持文件的创建、读取、更新、删除
- **文件夹管理**：支持文件夹的创建、读取、更新、删除
- **版本控制**：支持文件版本管理
- **文件哈希**：基于 MD5 的文件哈希计算和缓存

## 技术架构

### 前端架构
- **技术栈**：HTML5, CSS3, JavaScript (ES6+)
- **UI 框架**：Ooder Rad Style 风格
- **图标库**：Remixicon (ri- 开源图标)
- **响应式设计**：支持多种屏幕尺寸和设备类型
- **页面结构**：独立 HTML 页面 + JSON 菜单配置

### 后端架构
- **技术栈**：Java 8, Spring Boot 2.7.0
- **API 风格**：RESTful API
- **服务组件**：
  - Personal Controller：个人中心 API
  - MCP Agent Controller：主控节点 API
  - Monitor Controller：系统监控 API
  - Network Controller：网络管理 API
  - System Controller：系统管理 API

### P2P 网络架构
- **协议**：基于 Ooder SDK 的 P2P 协议
- **发现机制**：UDP 广播 + 心跳检测
- **数据传输**：支持点对点数据传输和技能分发
- **安全机制**：支持加密通信和身份验证

### SDK 集成
- **Agent SDK**：v0.6.6
- **功能**：网络通信、命令处理、服务发现、P2P 组网

### 存储架构
- **虚拟文件系统**：本地磁盘存储支持
- **文件哈希**：基于 MD5 的文件哈希计算
- **版本控制**：文件版本管理
- **缓存机制**：基于哈希的文件缓存

## 功能模块

### 1. 个人中心
- **个人仪表盘**：个人统计数据和活动记录
- **我的技能**：个人技能的发布、编辑、删除
- **执行历史**：技能执行记录和结果查看
- **分享管理**：技能分享和接收管理
- **我的群组**：群组创建和管理
- **个人身份**：个人信息管理和身份映射

### 2. 系统监控
- **健康检查**：系统、网络、服务状态监控
- **系统指标**：CPU、内存、磁盘、负载监控
- **告警管理**：基于阈值的自动告警
- **日志管理**：系统日志查看和过滤

### 3. 网络管理
- **网络拓扑**：P2P 网络拓扑可视化
- **节点管理**：网络节点的发现和管理
- **链路管理**：节点间链路的创建和管理
- **场景管理**：网络场景配置和切换
- **流量监控**：实时网络流量监控和分析
- **IP 管理**：IP 地址分配和管理

### 4. OpenWrt 管理
- **系统状态**：OpenWrt 路由器系统状态监控
- **网络设置**：路由器网络配置管理
- **IP 管理**：路由器 IP 地址管理
- **黑白名单**：访问控制黑白名单管理
- **配置文件**：OpenWrt 配置文件管理
- **命令执行**：远程执行 OpenWrt 命令

### 5. 协议仿真与调试
- **MCP 仿真器**：MCP 协议仿真和调试
- **Route 仿真器**：Route 协议仿真和调试
- **场景调试**：自定义场景进行协议调试
- **执行分析**：协议执行结果分析和展示
- **日志记录**：详细的协议执行日志

### 4. 命令体系
- **命令发送**：向网络节点发送命令
- **命令队列**：命令队列管理和监控
- **执行统计**：命令执行统计和分析
- **网络链接**：网络链接状态管理



### 6. 存储管理
- **文件管理**：文件的创建、读取、更新、删除
- **文件夹管理**：文件夹的创建、读取、更新、删除
- **版本控制**：文件版本管理
- **文件哈希**：基于 MD5 的文件哈希计算
- **文件缓存**：基于哈希的文件缓存机制

## 部署场景

### 1. 个人使用
- **PC 部署**：在个人电脑上部署，管理个人 AI 技能
- **家庭网络**：在家庭网络中部署，实现家庭设备间的 AI 能力共享
- **移动办公**：支持远程访问和管理

### 2. 小微企业
- **部门部署**：在部门内部部署，实现部门内的 AI 能力共享
- **私有网络**：创建企业私有 P2P 网络，确保数据安全
- **成本优化**：利用现有设备，降低 AI 能力获取成本

### 3. 智能设备
- **路由器部署**：在路由器上部署，实现网络级别的 AI 能力分发
- **智能家居网关**：在智能家居网关上部署，实现智能设备间的 AI 能力协同
- **电视部署**：在智能电视上部署，实现家庭娱乐 AI 能力

## API 接口文档

### 个人中心 API (/api/personal)

#### 仪表盘统计
- **GET /dashboard/stats**
- **响应**：个人统计数据、活动记录、执行统计

#### 技能管理
- **GET /skills** - 获取个人技能列表
- **POST /skills** - 发布个人技能
- **PUT /skills/{id}** - 更新个人技能
- **DELETE /skills/{id}** - 删除个人技能

#### 执行管理
- **POST /execution/execute/{skillId}** - 执行个人技能
- **GET /execution/history** - 获取执行历史
- **GET /execution/result/{executionId}** - 获取执行结果

#### 分享管理
- **GET /sharing/shared** - 获取分享的技能列表
- **GET /sharing/received** - 获取收到的技能列表
- **POST /sharing** - 分享技能

#### 群组管理
- **GET /groups** - 获取我的群组列表
- **GET /groups/{groupId}/skills** - 获取群组技能列表

#### 身份管理
- **GET /identity** - 获取个人身份信息
- **PUT /identity** - 更新个人身份信息

#### 帮助与支持
- **GET /help** - 获取帮助文档
- **GET /system/info** - 获取系统信息

### P2P 网络 API (/api/network)

#### 网络发现
- **GET /discovery** - 发现网络中的其他节点
- **POST /join** - 加入 P2P 网络

#### 节点管理
- **GET /nodes** - 获取网络节点列表
- **GET /nodes/{nodeId}** - 获取节点详情
- **DELETE /nodes/{nodeId}** - 移除节点

#### 技能分发
- **POST /skill/publish** - 发布技能到网络
- **POST /skill/subscribe** - 订阅网络中的技能
- **GET /skill/market** - 获取技能市场列表

### 监控 API (/api/monitor)

#### 系统指标
- **GET /metrics** - 获取系统指标
- **GET /metrics/history** - 获取历史指标数据

#### 告警管理
- **GET /alerts** - 获取告警列表
- **POST /alerts/clear** - 清除告警

### 存储管理 API (/api/storage)

#### 存储空间
- **GET /space** - 获取存储空间信息

#### 文件夹管理
- **GET /folder/{folderId}/children** - 获取文件夹内容
- **POST /folder** - 创建文件夹
- **DELETE /folder/{folderId}** - 删除文件夹

#### 文件管理
- **POST /upload** - 上传文件
- **GET /download/{fileId}** - 下载文件
- **DELETE /file/{fileId}** - 删除文件
- **PUT /file/{fileId}** - 更新文件信息

#### 版本管理
- **GET /file/{fileId}/versions** - 获取文件版本列表
- **POST /file/{fileId}/restore/{versionId}** - 恢复文件版本

#### 存储清理
- **POST /cleanup** - 清理存储缓存

#### 文件分享
- **GET /shared** - 获取分享的文件列表
- **POST /share** - 分享文件
- **GET /received** - 获取收到的文件列表

## 前端页面使用指南

### 1. 访问控制台
- **URL**：http://localhost:8091/console/index.html
- **默认端口**：8091

### 2. 主要功能
- **个人中心**：个人仪表盘、我的技能、执行历史、分享管理、我的群组
- **网络管理**：网络拓扑、节点管理、链路管理、场景管理
- **系统监控**：健康检查、系统指标、告警管理、日志管理
- **存储管理**：文件管理、文件夹管理、文件上传、文件下载、版本控制

## 后端服务配置

### 配置文件
- **application.yml**：Spring Boot 应用配置

### 主要配置项
```yaml
server:
  port: 8091

spring:
  application:
    name: ooder-nexus

ooder:
  agent:
    id: "nexus-001"
    name: "Nexus"
    type: "nexus"
    description: "P2P AI Capability Distribution Hub"
  network:
    mode: "p2p"
    discovery:
      enabled: true
      interval: 30000
    security:
      enabled: true
      encryption: "AES-256"
  p2p:
    port: 9876
    host: 0.0.0.0
    timeout: 30000
    retry: 3
    max-nodes: 100
  skill:
    base-package: "net.ooder.nexus.skill"
    auto-discovery: true
  storage:
    type: "local"
    local:
      root-path: "./data/storage"
      cache-path: "./data/cache"
      temp-path: "./data/temp"
    version:
      enabled: true
      max-versions: 10
    hash:
      algorithm: "MD5"
    cleanup:
      enabled: true
      interval: 86400
```

### 环境要求
- **Java**：8+
- **Maven**：3.6+
- **操作系统**：Windows / Linux / macOS
- **内存**：最低 2GB，推荐 4GB+
- **磁盘**：最低 500MB，推荐 2GB+

## 部署指南

### 1. 编译打包
```bash
mvn clean package
```

### 2. 运行服务
```bash
java -jar target/independent-nexus-0.6.6.jar
```

### 3. 访问控制台
打开浏览器，访问：http://localhost:8091/console/index.html

### 4. P2P 网络配置
1. 启动第一个 Nexus 节点作为网络种子
2. 其他节点通过种子节点加入网络
3. 节点自动发现并建立 P2P 连接
4. 开始共享和使用 AI 能力



## 安全特性

### 1. 数据安全
- **加密通信**：所有 P2P 通信均采用 AES-256 加密
- **身份验证**：支持基于 KEY 的身份验证
- **权限控制**：细粒度的权限控制

### 2. 网络安全
- **私有网络**：支持企业私有网络部署
- **访问控制**：支持白名单和黑名单
- **防火墙友好**：支持 NAT 穿透和防火墙友好配置

### 3. 数据隐私
- **本地存储**：数据默认本地存储，不上传云端
- **隐私保护**：支持匿名模式
- **数据所有权**：用户完全控制自己的数据

## 常见问题解答

### 1. 节点无法发现
- **检查网络**：确保节点在同一网络或可互相访问
- **检查防火墙**：确保防火墙允许 P2P 通信端口
- **检查配置**：确保 P2P 配置正确

### 2. 技能无法执行
- **检查权限**：确保有执行该技能的权限
- **检查依赖**：确保技能依赖已安装
- **查看日志**：检查执行日志获取详细错误信息

### 3. 网络连接不稳定
- **检查网络质量**：确保网络质量良好
- **检查节点数量**：确保网络中有足够的节点
- **检查配置**：确保 P2P 配置参数合理

### 4. 数据同步问题
- **检查存储**：确保存储路径有足够的磁盘空间
- **检查备份**：确保备份配置正确
- **检查网络**：确保网络连接稳定

## 版本历史

### v2.0.0
- **协议仿真**：新增 MCP 和 Route 协议仿真器，支持离线调试
- **可视化增强**：网络拓扑可视化、实时监控图表、交互式控制台
- **OpenWrt 支持**：原生支持 OpenWrt 路由器，提供专用管理界面
- **技能同步**：新增技能中心同步功能，支持技能的多节点同步
- **存储分享**：新增文件分享功能，支持接收和分享文件
- **流量监控**：新增网络流量监控和分析功能
- **前端优化**：修复大量前端页面问题，优化用户体验
- **API 扩展**：新增大量 RESTful API 接口

### v1.0.0
- 初始版本
- 实现基本的 P2P 网络功能
- 支持个人中心功能
- 支持技能发布和分享
- 支持系统监控和管理
- 支持多平台部署
- 集成本地磁盘存储支持

## 许可证

MIT License

Copyright (c) 2026 ooder

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

## 联系信息

- **作者**：北京紫蜂智联智能科技有限公司
- **官方地址**：https://gitee.com/ooderCN
- **官方网站**：ooder.net
- **GitHub地址**：https://github.com/oodercn/ooder-Nexus.git

## 文档中心

- **用户指南**：[USER-GUIDE.md](USER-GUIDE.md) - 终端用户操作指南
- **管理员指南**：[ADMIN-GUIDE.md](ADMIN-GUIDE.md) - 系统管理员配置和维护指南
- **存储指南**：[STORAGE-GUIDE.md](STORAGE-GUIDE.md) - 存储管理和最佳实践
- **技能开发指南**：[SKILL-DEVELOPMENT.md](SKILL-DEVELOPMENT.md) - AI 技能开发教程
- **技能同步设计**：[docs/SKILL-SYNC-DESIGN.md](docs/SKILL-SYNC-DESIGN.md) - 技能同步模块架构设计
- **技能同步使用**：[docs/SKILL-SYNC-GUIDE.md](docs/SKILL-SYNC-GUIDE.md) - 技能同步功能使用说明
- **开发文档**：[DEVELOPMENT.md](DEVELOPMENT.md) - 项目开发和整合指南
- **API 手册**：[API-MANUAL.md](API-MANUAL.md) - 详细的 API 接口文档
- **测试用例报告**：[TEST-CASE-REPORT.md](TEST-CASE-REPORT.md) - 测试覆盖情况和测试结果分析



## 贡献指南

欢迎贡献代码、报告问题或提出建议。请遵循以下步骤：

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 致谢

感谢所有为本项目做出贡献的开发者。

## 免责声明

本项目采用 MIT 许可证开源，使用者需自行承担使用本项目的风险。项目作者不对因使用本项目而导致的任何损失负责。
