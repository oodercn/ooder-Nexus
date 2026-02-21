# 系统配置与服务监控设计

**版本**: 1.0  
**日期**: 2026-02-21  
**状态**: 设计中

---

## 1. 设计背景

### 1.1 现状分析

当前系统配置存在以下问题：

1. **服务状态分散**：各服务端口状态分散在不同页面
2. **实时性不足**：无法实时看到服务运行状况
3. **告警机制缺失**：服务异常时无法及时通知
4. **配置入口不统一**：系统配置分散在多个位置

### 1.2 设计目标

1. 统一系统配置入口
2. 实时展示已连接Skill服务端口运行状况
3. 提供服务健康检查和告警机制
4. 支持配置热更新

---

## 2. 系统配置架构

### 2.1 配置分类

```
┌─────────────────────────────────────────────────────────────┐
│                      系统配置架构                            │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              1. 基础配置 (Basic)                     │   │
│  │  - 系统名称、版本、语言、时区                        │   │
│  │  - 日志级别、存储路径                                │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              2. 网络配置 (Network)                   │   │
│  │  - 服务端口、连接数、超时设置                        │   │
│  │  - TLS/SSL配置                                       │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              3. 服务配置 (Service)                   │   │
│  │  - 心跳间隔、服务检查间隔                            │   │
│  │  - 最大服务实例数                                    │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              4. 安全配置 (Security)                  │   │
│  │  - 认证开关、加密设置                                │   │
│  │  - API Key管理                                       │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              5. 存储配置 (Storage)                   │   │
│  │  - 数据目录、临时目录、备份目录                      │   │
│  │  - 存储配额设置                                      │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              6. 性能配置 (Performance)               │   │
│  │  - 线程池大小、内存阈值、CPU阈值                     │   │
│  │  - GC策略                                            │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### 2.2 服务监控架构

```
┌─────────────────────────────────────────────────────────────┐
│                      服务监控架构                            │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────┐   ┌─────────────┐   ┌─────────────┐       │
│  │ Nexus Core  │   │ Scene Engine│   │  Msg Queue  │       │
│  │  :9876      │   │  :8081      │   │  :5672      │       │
│  └──────┬──────┘   └──────┬──────┘   └──────┬──────┘       │
│         │                 │                 │               │
│         └─────────────────┼─────────────────┘               │
│                           │                                 │
│                    ┌──────▼──────┐                         │
│                    │ Health Check│                         │
│                    │   Service   │                         │
│                    └──────┬──────┘                         │
│                           │                                 │
│         ┌─────────────────┼─────────────────┐               │
│         │                 │                 │               │
│  ┌──────▼──────┐   ┌──────▼──────┐   ┌──────▼──────┐       │
│  │  Dashboard  │   │   Alert     │   │    Log      │       │
│  │   Display   │   │   Service   │   │   Service   │       │
│  └─────────────┘   └─────────────┘   └─────────────┘       │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 3. 用户故事

### 3.1 系统配置管理

#### US-SYS-CONFIG-001: 查看系统配置

**作为** 系统管理员  
**我希望** 能够查看当前系统配置  
**以便** 了解系统运行参数

**验收标准**:
- 支持按分类查看配置
- 显示配置项名称、当前值、默认值
- 显示配置项描述

#### US-SYS-CONFIG-002: 修改系统配置

**作为** 系统管理员  
**我希望** 能够修改系统配置  
**以便** 调整系统运行参数

**验收标准**:
- 支持修改可编辑的配置项
- 修改前需要验证配置值有效性
- 支持配置热更新（部分配置）
- 记录配置修改历史

#### US-SYS-CONFIG-003: 重置系统配置

**作为** 系统管理员  
**我希望** 能够重置系统配置到默认值  
**以便** 恢复系统默认设置

**验收标准**:
- 支持单个配置项重置
- 支持整个分类配置重置
- 重置前需要确认

### 3.2 服务监控

#### US-SYS-MONITOR-001: 查看服务状态

**作为** 系统管理员  
**我希望** 能够查看所有服务的运行状态  
**以便** 监控系统健康状况

**验收标准**:
- 显示所有已配置服务的状态
- 显示服务端口、响应时间
- 支持手动刷新状态

#### US-SYS-MONITOR-002: 查看Skill服务状态

**作为** 系统管理员  
**我希望** 能够查看已连接Skill服务的状态  
**以便** 监控外部服务连接状况

**验收标准**:
- 显示已配置的Skill服务状态
- 显示连接状态、响应时间
- 显示最后检查时间

#### US-SYS-MONITOR-003: 服务告警配置

**作为** 系统管理员  
**我希望** 能够配置服务告警规则  
**以便** 在服务异常时及时收到通知

**验收标准**:
- 支持配置告警阈值（响应时间、失败次数）
- 支持配置告警通知方式（邮件、钉钉等）
- 支持启用/停用告警

---

## 4. API 设计

### 4.1 系统配置 API

#### 获取系统配置

```
GET /api/system/config/{category}
```

**路径参数**:
- `category`: basic, network, service, security, storage, performance

**响应**:
```json
{
  "requestStatus": 200,
  "data": {
    "category": "basic",
    "name": "基础配置",
    "configs": [
      {
        "key": "systemName",
        "label": "系统名称",
        "value": "Nexus Platform",
        "defaultValue": "Nexus",
        "type": "string",
        "description": "系统显示名称",
        "editable": true,
        "requiresRestart": false
      },
      {
        "key": "defaultLanguage",
        "label": "默认语言",
        "value": "zh-CN",
        "defaultValue": "zh-CN",
        "type": "select",
        "options": [
          {"value": "zh-CN", "label": "简体中文"},
          {"value": "en-US", "label": "English"}
        ],
        "description": "系统默认语言",
        "editable": true,
        "requiresRestart": false
      }
    ]
  }
}
```

#### 更新系统配置

```
POST /api/system/config/{category}/update
```

**请求体**:
```json
{
  "configs": {
    "systemName": "My Nexus",
    "defaultLanguage": "en-US"
  }
}
```

**响应**:
```json
{
  "requestStatus": 200,
  "message": "配置更新成功",
  "data": {
    "updated": 2,
    "requiresRestart": false,
    "changes": [
      {
        "key": "systemName",
        "oldValue": "Nexus Platform",
        "newValue": "My Nexus"
      }
    ]
  }
}
```

#### 重置系统配置

```
POST /api/system/config/{category}/reset
```

**请求体**:
```json
{
  "keys": ["systemName", "defaultLanguage"]
}
```

### 4.2 服务监控 API

#### 获取服务状态概览

```
GET /api/system/services/overview
```

**响应**:
```json
{
  "requestStatus": 200,
  "data": {
    "total": 6,
    "running": 5,
    "stopped": 1,
    "warning": 0,
    "services": [
      {
        "serviceId": "nexus-core",
        "name": "Nexus Core Service",
        "type": "core",
        "port": 9876,
        "status": "RUNNING",
        "uptime": 86400000,
        "responseTime": 5,
        "lastChecked": "2026-02-21T10:00:00Z",
        "metrics": {
          "cpu": 15.5,
          "memory": 256,
          "connections": 10
        }
      },
      {
        "serviceId": "scene-engine",
        "name": "Scene Engine",
        "type": "engine",
        "port": 8081,
        "status": "RUNNING",
        "uptime": 86300000,
        "responseTime": 10,
        "lastChecked": "2026-02-21T10:00:00Z"
      }
    ]
  }
}
```

#### 获取Skill服务状态

```
GET /api/system/services/skills
```

**响应**:
```json
{
  "requestStatus": 200,
  "data": {
    "total": 4,
    "connected": 2,
    "disconnected": 1,
    "notConfigured": 1,
    "skills": [
      {
        "skillId": "skill-org-dingding",
        "name": "钉钉组织服务",
        "type": "org",
        "status": "CONNECTED",
        "endpoint": "https://oapi.dingtalk.com",
        "responseTime": 150,
        "lastChecked": "2026-02-21T10:00:00Z",
        "error": null
      },
      {
        "skillId": "skill-org-feishu",
        "name": "飞书组织服务",
        "type": "org",
        "status": "CONNECTION_FAILED",
        "endpoint": "https://open.feishu.cn",
        "responseTime": null,
        "lastChecked": "2026-02-21T10:00:00Z",
        "error": "认证失败: App Secret无效"
      },
      {
        "skillId": "skill-db-mysql",
        "name": "MySQL数据库",
        "type": "database",
        "status": "CONNECTED",
        "endpoint": "192.168.1.100:3306",
        "responseTime": 5,
        "lastChecked": "2026-02-21T10:00:00Z"
      }
    ]
  }
}
```

#### 手动检查服务状态

```
POST /api/system/services/{serviceId}/check
```

**响应**:
```json
{
  "requestStatus": 200,
  "data": {
    "serviceId": "skill-org-dingding",
    "status": "CONNECTED",
    "responseTime": 150,
    "checkedAt": "2026-02-21T10:00:00Z",
    "details": {
      "apiVersion": "1.0",
      "corpName": "示例企业"
    }
  }
}
```

#### 获取服务历史状态

```
GET /api/system/services/{serviceId}/history
```

**查询参数**:
- `period`: 1h, 6h, 24h, 7d

**响应**:
```json
{
  "requestStatus": 200,
  "data": {
    "serviceId": "nexus-core",
    "period": "24h",
    "dataPoints": [
      {
        "timestamp": "2026-02-21T00:00:00Z",
        "status": "RUNNING",
        "responseTime": 5
      },
      {
        "timestamp": "2026-02-21T01:00:00Z",
        "status": "RUNNING",
        "responseTime": 8
      }
    ],
    "statistics": {
      "avgResponseTime": 6.5,
      "maxResponseTime": 25,
      "minResponseTime": 3,
      "uptime": 99.9
    }
  }
}
```

### 4.3 告警配置 API

#### 获取告警配置

```
GET /api/system/alerts/config
```

**响应**:
```json
{
  "requestStatus": 200,
  "data": {
    "enabled": true,
    "rules": [
      {
        "ruleId": "rule-001",
        "name": "服务响应时间告警",
        "type": "response_time",
        "threshold": 1000,
        "unit": "ms",
        "consecutive": 3,
        "enabled": true
      },
      {
        "ruleId": "rule-002",
        "name": "服务离线告警",
        "type": "service_down",
        "consecutive": 1,
        "enabled": true
      }
    ],
    "notifications": [
      {
        "type": "dingding",
        "webhook": "https://oapi.dingtalk.com/robot/send?access_token=xxx",
        "enabled": true
      },
      {
        "type": "email",
        "recipients": ["admin@example.com"],
        "enabled": false
      }
    ]
  }
}
```

#### 更新告警配置

```
POST /api/system/alerts/config/update
```

**请求体**:
```json
{
  "enabled": true,
  "rules": [...],
  "notifications": [...]
}
```

---

## 5. 界面设计

### 5.1 系统配置页面

```
┌─────────────────────────────────────────────────────────────────────┐
│  系统配置                                                           │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐  │
│  │ 基础配置 │ │ 网络配置 │ │ 服务配置 │ │ 安全配置 │ │ 性能配置 │  │
│  └──────────┘ └──────────┘ └──────────┘ └──────────┘ └──────────┘  │
│                                                                     │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │  基础配置                                                    │   │
│  │                                                             │   │
│  │  系统名称                                                   │   │
│  │  ┌─────────────────────────────────────────────────────┐   │   │
│  │  │ Nexus Platform                                      │   │   │
│  │  └─────────────────────────────────────────────────────┘   │   │
│  │  系统显示名称                                              │   │
│  │                                                             │   │
│  │  默认语言                                                   │   │
│  │  ┌─────────────────────────────────────────────────────┐   │   │
│  │  │ 简体中文 ▼                                           │   │   │
│  │  └─────────────────────────────────────────────────────┘   │   │
│  │  系统默认语言                                              │   │
│  │                                                             │   │
│  │  时区                                                       │   │
│  │  ┌─────────────────────────────────────────────────────┐   │   │
│  │  │ Asia/Shanghai ▼                                      │   │   │
│  │  └─────────────────────────────────────────────────────┘   │   │
│  │  系统时区设置                                              │   │
│  │                                                             │   │
│  │  日志级别                                                   │   │
│  │  ┌─────────────────────────────────────────────────────┐   │   │
│  │  │ INFO ▼                                               │   │   │
│  │  └─────────────────────────────────────────────────────┘   │   │
│  │  系统日志级别: DEBUG, INFO, WARN, ERROR                   │   │
│  │                                                             │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                                                                     │
│                              [重置]  [保存配置]                     │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

### 5.2 服务监控页面

```
┌─────────────────────────────────────────────────────────────────────┐
│  服务监控                                           [刷新] [设置]   │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐               │
│  │ 总数 6   │ │ 运行中 5 │ │ 已停止 1 │ │ 告警 0   │               │
│  └──────────┘ └──────────┘ └──────────┘ └──────────┘               │
│                                                                     │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │  核心服务                                                    │   │
│  │                                                             │   │
│  │  ┌───────────────────────────────────────────────────────┐ │   │
│  │  │ ● Nexus Core Service                     端口: 9876   │ │   │
│  │  │ 状态: 运行中  |  运行时间: 1天2小时  |  响应: 5ms      │ │   │
│  │  │ CPU: 15.5%  |  内存: 256MB  |  连接数: 10            │ │   │
│  │  │                                    [详情] [重启]      │ │   │
│  │  └───────────────────────────────────────────────────────┘ │   │
│  │  ┌───────────────────────────────────────────────────────┐ │   │
│  │  │ ● Scene Engine                           端口: 8081   │ │   │
│  │  │ 状态: 运行中  |  运行时间: 1天2小时  |  响应: 10ms     │ │   │
│  │  │                                    [详情] [重启]      │ │   │
│  │  └───────────────────────────────────────────────────────┘ │   │
│  │  ┌───────────────────────────────────────────────────────┐ │   │
│  │  │ ○ Message Queue                          端口: 5672   │ │   │
│  │  │ 状态: 已停止  |  最后运行: 2026-02-20 10:00:00       │ │   │
│  │  │                                    [详情] [启动]      │ │   │
│  │  └───────────────────────────────────────────────────────┘ │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                                                                     │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │  Skill 服务状态                                              │   │
│  │                                                             │   │
│  │  ┌───────────────────────────────────────────────────────┐ │   │
│  │  │ ● 钉钉组织服务                          响应: 150ms   │ │   │
│  │  │ 状态: 已连接  |  端点: https://oapi.dingtalk.com     │ │   │
│  │  │ 最后检查: 2026-02-21 10:00:00        [测试] [配置]  │ │   │
│  │  └───────────────────────────────────────────────────────┘ │   │
│  │  ┌───────────────────────────────────────────────────────┐ │   │
│  │  │ ⚠ 飞书组织服务                          响应: --     │ │   │
│  │  │ 状态: 连接失败  |  端点: https://open.feishu.cn      │ │   │
│  │  │ 错误: 认证失败 - App Secret无效      [测试] [配置]  │ │   │
│  │  └───────────────────────────────────────────────────────┘ │   │
│  │  ┌───────────────────────────────────────────────────────┐ │   │
│  │  │ ● MySQL数据库                            响应: 5ms    │ │   │
│  │  │ 状态: 已连接  |  端点: 192.168.1.100:3306            │ │   │
│  │  │ 最后检查: 2026-02-21 10:00:00        [测试] [配置]  │ │   │
│  │  └───────────────────────────────────────────────────────┘ │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

### 5.3 服务详情页面

```
┌─────────────────────────────────────────────────────────────────────┐
│  Nexus Core Service - 详情                          [返回列表]     │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │  基本信息                                                    │   │
│  │                                                             │   │
│  │  服务ID: nexus-core                                         │   │
│  │  服务名称: Nexus Core Service                               │   │
│  │  服务类型: core                                             │   │
│  │  端口: 9876                                                 │   │
│  │  状态: ● 运行中                                             │   │
│  │  运行时间: 1天2小时30分钟                                   │   │
│  │                                                             │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                                                                     │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │  性能指标                                                    │   │
│  │                                                             │   │
│  │  ┌─────────────────┐  ┌─────────────────┐                  │   │
│  │  │ CPU 使用率      │  │ 内存使用        │                  │   │
│  │  │    15.5%        │  │   256MB / 1GB   │                  │   │
│  │  │ ████████░░░░░░░ │  │ ████████░░░░░░░ │                  │   │
│  │  └─────────────────┘  └─────────────────┘                  │   │
│  │                                                             │   │
│  │  ┌─────────────────┐  ┌─────────────────┐                  │   │
│  │  │ 活跃连接        │  │ 响应时间        │                  │   │
│  │  │     10          │  │      5ms        │                  │   │
│  │  │ ██████░░░░░░░░░ │  │ █░░░░░░░░░░░░░░ │                  │   │
│  │  └─────────────────┘  └─────────────────┘                  │   │
│  │                                                             │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                                                                     │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │  响应时间趋势 (24小时)                                       │   │
│  │                                                             │   │
│  │  30ms ┤                                                     │   │
│  │       │     ╭─╮                                             │   │
│  │  20ms ┤    ╭╯  ╰╮                                           │   │
│  │       │   ╭╯    ╰╮                                          │   │
│  │  10ms ┤───╯      ╰─────────────────────────────────────    │   │
│  │       │                                                     │   │
│  │    0ms └─────────────────────────────────────────────────  │   │
│  │        00:00  06:00  12:00  18:00  24:00                    │   │
│  │                                                             │   │
│  │  平均: 6.5ms  |  最大: 25ms  |  最小: 3ms  |  可用性: 99.9% │   │
│  │                                                             │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                                                                     │
│                              [重启服务]  [停止服务]                 │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

---

## 6. 数据模型

### 6.1 系统配置模型

```java
public class SystemConfig {
    private String category;
    private String name;
    private List<ConfigItem> configs;
    private Date lastUpdated;
}

public class ConfigItem {
    private String key;
    private String label;
    private Object value;
    private Object defaultValue;
    private String type;        // string, number, boolean, select, password
    private String description;
    private boolean editable;
    private boolean requiresRestart;
    private List<SelectOption> options;
    private ValidationRule validation;
}

public class SelectOption {
    private String value;
    private String label;
}

public class ValidationRule {
    private String pattern;
    private Integer minLength;
    private Integer maxLength;
    private Integer min;
    private Integer max;
}
```

### 6.2 服务状态模型

```java
public class ServiceStatus {
    private String serviceId;
    private String name;
    private String type;        // core, engine, skill, database
    private int port;
    private String status;      // RUNNING, STOPPED, WARNING, ERROR
    private long uptime;
    private Long responseTime;
    private Date lastChecked;
    private ServiceMetrics metrics;
    private String error;
}

public class ServiceMetrics {
    private double cpu;
    private long memory;
    private int connections;
    private int threads;
    private long totalRequests;
    private long failedRequests;
}

public class SkillServiceStatus {
    private String skillId;
    private String name;
    private String type;
    private String status;      // CONNECTED, DISCONNECTED, NOT_CONFIGURED, CONNECTION_FAILED
    private String endpoint;
    private Long responseTime;
    private Date lastChecked;
    private String error;
    private Map<String, Object> details;
}
```

### 6.3 告警配置模型

```java
public class AlertConfig {
    private boolean enabled;
    private List<AlertRule> rules;
    private List<AlertNotification> notifications;
}

public class AlertRule {
    private String ruleId;
    private String name;
    private String type;        // response_time, service_down, cpu_high, memory_high
    private long threshold;
    private String unit;
    private int consecutive;
    private boolean enabled;
}

public class AlertNotification {
    private String type;        // dingding, email, webhook
    private String webhook;
    private List<String> recipients;
    private boolean enabled;
}
```

---

## 7. 实现计划

### 7.1 阶段一：系统配置

| 任务 | 说明 | 优先级 |
|------|------|--------|
| SystemConfigController | 系统配置API | P0 |
| SystemConfigService | 配置管理逻辑 | P0 |
| system-config.html | 系统配置页面 | P0 |
| 配置热更新 | 支持部分配置热更新 | P1 |

### 7.2 阶段二：服务监控

| 任务 | 说明 | 优先级 |
|------|------|--------|
| ServiceMonitorController | 服务监控API | P0 |
| HealthCheckService | 健康检查服务 | P0 |
| service-monitor.html | 服务监控页面 | P0 |
| WebSocket推送 | 实时状态推送 | P1 |

### 7.3 阶段三：告警系统

| 任务 | 说明 | 优先级 |
|------|------|--------|
| AlertService | 告警逻辑 | P1 |
| AlertController | 告警配置API | P1 |
| 钉钉通知集成 | 钉钉机器人通知 | P1 |
| 邮件通知集成 | 邮件通知 | P2 |

---

**版本**: 1.0  
**日期**: 2026-02-21  
**作者**: ooder Team
