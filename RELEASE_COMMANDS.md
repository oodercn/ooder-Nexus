# GitHub 发布操作指南

## 步骤 1: 清理 Git 锁文件

在 Windows PowerShell 中执行：
```powershell
# 关闭所有使用 git 的程序
# 然后手动删除锁文件
cd E:\github\ooder-Nexus
Remove-Item -Force .git\index.lock
```

## 步骤 2: 添加文件到暂存区

```bash
cd E:/github/ooder-Nexus

# 添加修改的文件
git add README.md
git add src/main/java/net/ooder/nexus/config/AppConfig.java
git add scripts/install-openwrt.sh

# 检查状态
git status
```

## 步骤 3: 提交代码

```bash
# 创建提交
git commit -m "release: v2.0.0 - OpenWrt 自动角色检测与 routeAgent 支持

主要更新：
- 添加 OpenWrt 系统自动检测功能（检测 /etc/openwrt_release、/etc/os-release、opkg、uci）
- 自动将 Agent 角色设置为 routeAgent
- 更新 README 文档，添加详细使用说明和 VFS 存储架构说明
- 完善版本历史和发布说明
- 优化数据备份与恢复文档

技术细节：
- AppConfig.java 添加 isOpenWrt() 检测方法
- 支持 4 种 OpenWrt 检测方式
- VFS + JSON 混合存储架构
- P2P 数据同步机制"
```

## 步骤 4: 推送到 GitHub

```bash
# 推送到 main 分支
git push origin main

# 创建标签
git tag -a v2.0.0 -m "ooderNexus v2.0.0 - 生产就绪版本，支持 OpenWrt 自动角色检测"

# 推送标签
git push origin v2.0.0
```

## 步骤 5: 创建 GitHub Release

### 5.1 访问 GitHub Release 页面
打开浏览器访问：
```
https://github.com/oodercn/ooder-Nexus/releases/new
```

### 5.2 填写 Release 信息

**版本号**: `v2.0.0`

**标题**: `ooderNexus v2.0.0 - 生产就绪版本`

**内容**:
```markdown
## 🎉 ooderNexus v2.0.0 正式发布

这是一个生产就绪版本，包含完整的 P2P AI 能力分发功能。

### ✨ 主要新特性

#### OpenWrt 集成增强
- **自动角色检测**: 启动时自动检测 OpenWrt 系统，自动设置 Agent 角色为 `routeAgent`
- **深度系统集成**: 支持路由器系统状态监控、网络配置管理、IP 地址管理
- **一键安装脚本**: 提供 OpenWrt 专用安装脚本，自动完成环境配置

#### 存储架构升级
- **VFS 虚拟文件系统**: 基于 MD5 哈希的去重存储，支持文件版本控制
- **P2P 数据同步**: Agent SDK 数据自动同步到网络节点
- **混合存储策略**: JSON 结构化数据 + VFS 二进制文件存储

#### 协议仿真与调试
- **MCP 协议仿真器**: 离线模拟 MCP 协议通信
- **Route 协议仿真器**: 模拟路由协议行为
- **场景化测试**: 支持自定义测试场景和用例

#### 网络管理功能
- **网络拓扑可视化**: 图形化展示 P2P 网络结构
- **链路管理**: 节点间链路创建、监控、断开
- **流量监控**: 实时网络流量统计和分析

### 📦 安装方式

#### OpenWrt 路由器（推荐）
```bash
wget -O /tmp/install.sh https://github.com/oodercn/ooder-Nexus/releases/download/v2.0.0/install-openwrt.sh
chmod +x /tmp/install.sh
/tmp/install.sh
```

#### 通用平台
下载 `ooder-nexus-2.0.0.jar`，然后运行：
```bash
java -jar ooder-nexus-2.0.0.jar
```

### 📋 系统要求

- **OpenWrt**: 21.02 或更高版本
- **Java**: 11 或更高版本
- **内存**: 最低 64MB，推荐 128MB+
- **存储**: 最低 100MB，推荐 256MB+

### 🔧 快速开始

1. 安装完成后访问 Web 控制台：`http://路由器IP:8091/console/index.html`
2. 查看仪表盘了解系统状态
3. 配置 P2P 网络发现，连接其他节点
4. 开始使用 AI 技能和网络管理功能

### 📖 文档

详细文档请查看 [README.md](https://github.com/oodercn/ooder-Nexus/blob/main/README.md)

### 🙏 致谢

感谢所有贡献者和测试者的支持！
```

### 5.3 上传发布文件

点击 "Attach binaries by dropping them here or selecting them" 区域，上传以下文件：
- `release/ooder-nexus-2.0.0.jar`
- `release/install-openwrt.sh`

### 5.4 发布

- 勾选 "This is a pre-release" （如果是预发布版本）
- 点击 "Publish release"

## 步骤 6: 验证发布

1. 访问 `https://github.com/oodercn/ooder-Nexus/releases`
2. 确认 v2.0.0 版本已显示
3. 确认文件可以正常下载
4. 测试安装脚本是否可以正常执行

## 发布文件清单

| 文件 | 大小 | 说明 |
|------|------|------|
| ooder-nexus-2.0.0.jar | 44.7 MB | 可执行 JAR 包 |
| install-openwrt.sh | 6.9 KB | OpenWrt 一键安装脚本 |

## 注意事项

1. 确保所有测试已通过
2. 确保 README 文档已更新
3. 确保版本号一致（代码、README、脚本）
4. 发布后验证下载链接可用
