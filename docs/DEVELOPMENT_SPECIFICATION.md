# ooderNexus 开发规范手册

## 目录

1. [项目概述](#1-项目概述)
2. [技术架构](#2-技术架构)
3. [开发环境搭建](#3-开发环境搭建)
4. [项目结构](#4-项目结构)
5. [Java 开发规范](#5-java-开发规范)
6. [前端开发规范](#6-前端开发规范)
7. [REST API 规范](#7-rest-api-规范)
8. [核心模块开发](#8-核心模块开发)
9. [测试规范](#9-测试规范)
10. [部署与运维](#10-部署与运维)
11. [常见问题](#11-常见问题)

---

## 1. 项目概述

### 1.1 项目简介

ooderNexus 是一个基于 **Ooder Agent 架构**的 **P2P AI 能力分发枢纽**，采用 MIT 开源协议。它将去中心化的 P2P 网络技术与 AI 能力管理相结合，让用户能够在本地网络中构建私有的 AI 能力共享平台。

### 1.2 核心特性

- **去中心化组网** - 无需中心服务器，节点间直接通信
- **AI 技能管理** - 发布、分享、执行 AI 技能
- **网络管理中枢** - 可视化网络拓扑和设备管理
- **OpenWrt 集成** - 深度集成路由器系统
- **协议仿真调试** - 离线开发和测试

### 1.3 技术栈

| 层级 | 技术 | 版本 |
|------|------|------|
| 后端框架 | Spring Boot | 2.7.0 |
| 编程语言 | Java | 8+ |
| P2P网络 | Ooder Agent SDK | 0.6.6 |
| 前端 | HTML5 + JavaScript + CSS | ES5+ |
| 构建工具 | Maven | 3.6+ |
| 测试框架 | JUnit 5 + Mockito | - |

### 1.4 项目定位

- **个人开发者**：快速构建和分享 AI 能力
- **小微企业**：低成本部署内部 AI 服务
- **部门团队**：实现团队内部 AI 能力的共享和管理
- **智能设备生态**：为智能设备提供 AI 能力支持

---

## 2. 技术架构

### 2.1 系统架构

Nexus 采用分层架构设计：

| 层次 | 组件 | 职责 |
|------|------|------|
| 基础层 | ooderAgent | 提供 P2P 网络通信、服务发现、命令处理等基础功能 |
| 应用层 | Nexus | 基于 ooderAgent 构建的 AI 能力分发枢纽，提供高级功能 |
| 技能层 | Skills | AI 能力的封装和执行单元 |
| 存储层 | VFS | 虚拟文件系统，提供统一的存储接口 |

### 2.2 SDK 模块架构

| 模块 | 职责 | 主要类 |
|------|------|--------|
| 网络模块 | P2P 网络通信 | NetworkModule |
| 服务模块 | 服务发现和调用 | ServiceModule |
| 命令模块 | 命令处理 | CommandModule |
| 安全模块 | 安全机制 | SecurityModule |
| 配置模块 | 系统配置 | ConfigModule |
| 监控模块 | 系统监控 | MonitorModule |

---

## 3. 开发环境搭建

### 3.1 系统要求

| 组件 | 版本/要求 |
|------|-----------|
| JDK | 8 或更高版本 |
| Maven | 3.6 或更高版本 |
| Git | 2.0 或更高版本 |
| 操作系统 | Windows/Linux/macOS |
| 内存 | 至少 4GB RAM |
| 磁盘 | 至少 2GB 可用空间 |

### 3.2 安装验证

```bash
java -version
mvn -version
```

### 3.3 克隆仓库

```bash
# GitHub
git clone https://github.com/oodercn/ooder-Nexus.git

# Gitee 镜像（国内）
git clone https://gitee.com/ooderCN/ooder-nexus.git

cd ooder-Nexus
```

### 3.4 构建项目

```bash
# 编译并运行测试
mvn clean test -s settings.xml

# 打包（跳过测试）
mvn clean package -DskipTests -s settings.xml

# 运行
java -jar target/independent-nexus-2.0.jar
```

---

## 4. 项目结构

```
ooder-Nexus/
├── src/
│   ├── main/
│   │   ├── java/net/ooder/nexus/           # Java 源代码
│   │   │   ├── NexusSpringApplication.java # 主入口
│   │   │   ├── config/                     # 配置类
│   │   │   ├── controller/                 # REST API 控制器
│   │   │   ├── service/                    # 业务逻辑层
│   │   │   ├── repository/                 # 数据访问层
│   │   │   ├── model/                      # 实体类
│   │   │   └── core/                       # 核心功能
│   │   └── resources/
│   │       ├── application.yml             # 主配置文件
│   │       ├── application-dev.yml         # 开发环境配置
│   │       ├── application-prod.yml        # 生产环境配置
│   │       └── static/console/             # Web 控制台
│   └── test/                               # 测试代码
├── docs/                                   # 文档
├── release/                                # 发布文件
├── pom.xml                                 # Maven 配置
└── README.md                               # 项目说明
```

### 4.1 前端目录结构

```
console/
├── css/
│   ├── nexus.css          # 核心框架样式（必须引用）
│   ├── theme.css          # 主题变量定义
│   ├── remixicon/         # 图标字体
│   ├── components/        # 组件库样式
│   └── pages/             # 页面专用样式
├── js/
│   ├── nexus.js           # 核心框架脚本（必须引用）
│   ├── nexus-menu.js      # 菜单加载器
│   ├── page-init.js       # 页面初始化
│   └── pages/             # 页面专用脚本
└── pages/                 # HTML 页面
```

---

## 5. Java 开发规范

### 5.1 Java 版本兼容性

**项目使用 Java 8，禁止使用 Java 9+ 的特性。**

#### 禁止使用的特性

```java
// 错误：Map.of() 是 Java 9+ 特性
Map<String, Object> map = Map.of("key", "value");  // 错误

// 正确：使用 Java 8 兼容的方式
Map<String, Object> map = new HashMap<>();  // 正确
map.put("key", "value");
```

#### Java 8 兼容的 Map 创建方式

```java
// 方式 1：传统 put
Map<String, Object> map = new HashMap<>();
map.put("key1", value1);
map.put("key2", value2);

// 方式 2：双括号初始化
Map<String, Object> map = new HashMap<String, Object>() {{
    put("key1", value1);
    put("key2", value2);
}};
```

### 5.2 代码风格

遵循阿里巴巴 Java 开发手册：

- 类名使用大驼峰（UpperCamelCase）
- 方法名和变量名使用小驼峰（lowerCamelCase）
- 常量使用全大写+下划线（UPPER_SNAKE_CASE）
- 缩进使用 4 个空格
- 每行不超过 120 个字符

### 5.3 注释规范

```java
/**
 * 类功能说明
 *
 * @author 作者
 * @version 版本号
 * @since 起始版本
 */
public class ExampleClass {

    /**
     * 方法功能说明
     *
     * @param param1 参数1说明
     * @param param2 参数2说明
     * @return 返回值说明
     */
    public ReturnType methodName(Type1 param1, Type2 param2) {
        // 实现代码
    }
}
```

### 5.4 返回值规范

使用 `Map<String, Object>` 作为通用返回类型：

```java
Map<String, Object> result = new HashMap<>();
result.put("success", true);           // 操作是否成功
result.put("message", "操作成功");      // 提示信息
result.put("data", data);              // 返回数据（可选）
result.put("error", "错误信息");        // 错误信息（失败时）
```

---

## 6. 前端开发规范

### 6.1 CSS 变量使用规则

项目使用统一的 CSS 变量系统，定义在 `theme.css` 中。**所有页面必须使用这些变量，禁止硬编码颜色值。**

#### 变量对照表

| 用途 | 正确变量 | 错误变量/值 |
|------|---------|------------|
| 主要文字颜色 | `--ns-dark` | `--ns-text`, `--ooder-text` |
| 次要文字颜色 | `--ns-secondary` | - |
| 主背景色 | `--ns-background` | - |
| 卡片背景 | `--ns-card-bg` | `#121212`, `#1a1a1a` |
| 输入框背景 | `--ns-input-bg` | - |
| 边框颜色 | `--ns-border` | `#2a2a2a` |
| 悬停背景色 | `--ns-bg-hover` | `--ns-light`, `--ooder-light` |
| 主色调 | `--nx-primary` | - |
| 成功色 | `--ns-success` | - |
| 警告色 | `--ns-warning` | - |
| 危险色 | `--ns-danger` | - |

#### 主题变量定义

```css
/* 深色主题（默认） */
:root {
    --nx-primary: #3b82f6;
    --ns-secondary: #94a3b8;
    --ns-success: #22c55e;
    --ns-warning: #f59e0b;
    --ns-danger: #ef4444;
    --ns-dark: #ffffff;        /* 主要文字颜色 */
    --ns-light: #0f0f0f;       /* 浅色背景（少用） */
    --ns-border: #2a2a2a;
    --ns-background: #000000;
    --ns-card-bg: #121212;
    --ns-sidebar-bg: #121212;
    --ns-input-bg: #1a1a1a;
    --ns-bg-hover: #2a2a2a;    /* 悬停背景色 */
}

/* 浅色主题 */
.light-theme {
    --ns-dark: #1e293b;        /* 主要文字颜色 */
    --ns-light: #f8fafc;
    --ns-border: #e2e8f0;
    --ns-background: #ffffff;
    --ns-card-bg: #f1f5f9;
    --ns-sidebar-bg: #f8fafc;
    --ns-input-bg: #f8fafc;
    --ns-bg-hover: #e2e8f0;    /* 悬停背景色 */
}
```

### 6.2 内联代码阈值

| 类型 | 阈值 | 处理方式 |
|------|------|----------|
| 内联CSS (`<style>`) | 50行 | 提取到 `css/pages/` |
| 内联JS (`<script>`) | 100行 | 提取到 `js/pages/` |
| 内联样式 (`style=""`) | 0个 | 使用工具类替代 |
| 内联事件 (`onclick=""`) | 0个 | 使用事件监听器 |

### 6.3 CSS 引入顺序

**必须按照以下顺序引入：**

```html
<!-- 1. 图标字体（最先引入） -->
<link rel="stylesheet" href="css/remixicon/remixicon.css">

<!-- 2. 主题变量（必须引入） -->
<link rel="stylesheet" href="css/theme.css">

<!-- 3. 组件库样式（可选） -->
<link rel="stylesheet" href="css/components/index.css">

<!-- 4. 页面特定样式（最后引入） -->
<link rel="stylesheet" href="css/pages/your-page.css">
```

### 6.4 页面 JS 模块规范

使用 IIFE 模块模式，ES5 兼容性：

```javascript
(function(global) {
    'use strict';

    var ModuleName = {
        init: function() {
            window.onPageInit = function() {
                ModuleName.loadData();
            };
        },
        
        loadData: function() {
            // 实现代码
        }
    };

    ModuleName.init();

    // 暴露全局函数供 onclick 使用
    global.functionName = ModuleName.functionName;

})(typeof window !== 'undefined' ? window : this);
```

### 6.5 命名规范

| 类型 | 命名规范 | 示例 |
|------|---------|------|
| 组件 | 小写，短横线连接 | `.btn`, `.form-control` |
| 组件变体 | 基础类 + 修饰符 | `.btn-primary`, `.btn-lg` |
| 状态 | `is-` 或 `has-` 前缀 | `.is-active`, `.has-error` |
| 布局 | 语义化命名 | `.container`, `.sidebar` |
| 工具类 | 功能命名 | `.text-center`, `.hidden` |

---

## 7. REST API 规范

### 7.1 URL 设计

```
/api/{module}/{resource}/{action}
```

示例：
- `GET /api/openwrt/p2p/version` - 获取版本
- `POST /api/openwrt/p2p/agents/register` - 注册 Agent
- `POST /api/devices/list` - 获取设备列表

### 7.2 请求/响应格式

#### 请求

```json
{
    "param1": "value1",
    "param2": 123
}
```

#### 成功响应

```json
{
    "success": true,
    "message": "操作成功",
    "data": { ... }
}
```

#### 错误响应

```json
{
    "success": false,
    "error": "错误信息",
    "message": "详细错误描述"
}
```

---

## 8. 核心模块开发

### 8.1 添加 REST API

```java
package net.ooder.nexus.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @GetMapping("/hello")
    public Map<String, Object> hello(@RequestParam String name) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Hello, " + name + "!");
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }

    @PostMapping("/echo")
    public Map<String, Object> echo(@RequestBody Map<String, Object> data) {
        data.put("echo", true);
        data.put("timestamp", System.currentTimeMillis());
        return data;
    }
}
```

### 8.2 添加服务层

```java
package net.ooder.nexus.service;

import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    public String process(String input) {
        return "Processed: " + input;
    }
}
```

---

## 9. 测试规范

### 9.1 单元测试

```java
package net.ooder.nexus.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExampleController.class)
public class ExampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHello() throws Exception {
        mockMvc.perform(get("/api/example/hello")
                .param("name", "Developer"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Hello, Developer!"));
    }
}
```

### 9.2 运行测试

```bash
# 运行所有测试
mvn test -s settings.xml

# 运行特定测试类
mvn test -Dtest=ExampleControllerTest -s settings.xml
```

---

## 10. 部署与运维

### 10.1 配置文件

**application.yml:**

```yaml
server:
  port: 8081

spring:
  application:
    name: nexus
  profiles:
    active: dev

ooder:
  agent:
    id: ${OODER_AGENT_ID:nexus-001}
    name: ${OODER_AGENT_NAME:nexus}
    type: ${OODER_AGENT_TYPE:nexusAgent}
  udp:
    port: ${OODER_UDP_PORT:8091}
```

### 10.2 环境变量

| 变量名 | 说明 | 默认值 |
|--------|------|--------|
| `OODER_AGENT_ID` | Agent 唯一标识 | nexus-001 |
| `OODER_AGENT_NAME` | Agent 显示名称 | nexus |
| `OODER_AGENT_TYPE` | Agent 类型 | nexusAgent |
| `OODER_UDP_PORT` | UDP 通信端口 | 8091 |
| `SERVER_PORT` | HTTP 服务端口 | 8081 |

### 10.3 部署方式

#### 本地部署

```bash
# 从源码编译
git clone https://gitee.com/ooderCN/ooder-nexus.git
cd ooder-nexus
mvn clean package
java -jar target/independent-nexus-2.0.jar
```

#### Docker 部署

```bash
# 构建镜像
docker build -t ooder-nexus:latest .

# 运行容器
docker run -d -p 8081:8081 --name ooder-nexus ooder-nexus:latest
```

---

## 11. 常见问题

### Q1: Maven 依赖下载失败

**解决:**
```bash
mvn clean -U
# 或更换镜像源，在 settings.xml 中添加阿里云镜像
```

### Q2: 端口被占用

**解决:**
```bash
# Windows
netstat -ano | findstr :8081
taskkill /PID <PID> /F

# 或修改配置文件使用其他端口
```

### Q3: 编译错误 "找不到符号 Map.of"

**原因**：使用了 Java 9+ 的 `Map.of()` 方法。

**解决**：替换为 Java 8 兼容的代码：
```java
// 错误
Map<String, Object> map = Map.of("key", value);

// 正确
Map<String, Object> map = new HashMap<>();
map.put("key", value);
```

### Q4: 主题切换后按钮颜色不对

**原因**：使用了错误的 CSS 变量。

**解决**：
- 文字颜色使用 `--ns-dark`，不是 `--ns-text`
- 悬停背景使用 `--ns-bg-hover`，不是 `--ns-light`

### Q5: 页面刷新后主题闪烁

**原因**：主题在 DOM 加载后才应用。

**解决**：确保在 HTML 的 `<head>` 中引入主题管理脚本。

---

## 代码审查清单

- [ ] CSS 中没有硬编码颜色值
- [ ] 使用 `--ns-dark` 而不是 `--ns-text` 作为文字颜色
- [ ] 使用 `--ns-bg-hover` 而不是 `--ns-light` 作为悬停背景
- [ ] Java 代码兼容 Java 8（不使用 Map.of 等）
- [ ] 接口有完整的 JavaDoc 注释
- [ ] REST API 返回格式符合规范
- [ ] 新页面引入了 nexus.css 和 nexus.js
- [ ] 内联 JS 提取到外部文件（超过100行）
- [ ] 内联 CSS 提取到外部文件（超过50行）

---

## 参考资源

- **GitHub**: https://github.com/oodercn/ooder-Nexus
- **Gitee**: https://gitee.com/ooderCN/ooder-nexus
- **问题反馈**: https://gitee.com/ooderCN/ooder-nexus/issues
- **邮箱**: ooder@ooder.cn

---

**文档版本**: v1.0.0  
**最后更新**: 2026-02
