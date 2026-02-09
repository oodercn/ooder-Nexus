# ooderAgent 协议增强功能整理

## 1. 协议管理页面

### 1.1 现有页面
- **路径**: `/console/pages/nexus/protocol-management.html`
- **功能**: 协议处理器管理、命令处理、协议列表展示
- **状态**: 已存在，需要更新资源路径

### 1.2 页面功能模块

#### 协议操作区
- 注册协议处理器
- 移除协议处理器
- 处理命令
- 刷新列表

#### 协议处理器列表
- 命令类型
- 处理器名称
- 处理器描述
- 注册时间
- 状态
- 操作按钮

#### 命令处理区
- 命令数据输入（JSON格式）
- 处理按钮
- 清空数据

#### 处理结果展示
- JSON格式化展示

---

## 2. 协议增强功能规划

### 2.1 协议适配器管理
**新页面**: `/console/pages/nexus/protocol-adapter-management.html`

**功能**:
- 协议适配器注册/注销
- 适配器配置管理
- 适配器状态监控
- 适配器日志查看

**字段**:
- 适配器ID
- 适配器名称
- 协议类型（MCP, END, ROUTE, P2P等）
- 状态（启用/禁用）
- 配置参数
- 创建时间
- 最后活跃时间

### 2.2 协议命令调试
**新页面**: `/console/pages/nexus/protocol-command-debug.html`

**功能**:
- 命令构造器
- 命令发送测试
- 响应解析
- 历史记录

**字段**:
- 命令类型选择
- 命令参数配置
- 目标Agent
- 超时设置
- 原始响应
- 解析结果

### 2.3 协议流量监控
**新页面**: `/console/pages/nexus/protocol-traffic-monitor.html`

**功能**:
- 实时流量图表
- 命令统计
- 响应时间分析
- 错误率监控

**指标**:
- QPS（每秒查询率）
- 平均响应时间
- 成功率
- 错误分类统计

### 2.4 协议版本管理
**新页面**: `/console/pages/nexus/protocol-version-management.html`

**功能**:
- 协议版本列表
- 版本兼容性检查
- 版本升级/回滚
- 变更日志

**字段**:
- 协议名称
- 版本号
- 发布日期
- 状态（稳定/测试/废弃）
- 兼容性说明

---

## 3. 协议类型定义

### 3.1 核心协议类型
```java
public enum ProtocolType {
    MCP,        // Master Control Protocol
    END,        // End Agent Protocol
    ROUTE,      // Route Protocol
    P2P,        // Peer to Peer Protocol
    DISCOVERY,  // Service Discovery Protocol
    HEARTBEAT,  // Heartbeat Protocol
    STATUS,     // Status Report Protocol
    COMMAND     // Generic Command Protocol
}
```

### 3.2 命令类型定义
```java
public enum CommandType {
    // MCP Commands
    MCP_REGISTER,
    MCP_DEREGISTER,
    MCP_HEARTBEAT,
    MCP_STATUS,
    MCP_DISCOVER,
    
    // END Commands
    END_REGISTER,
    END_DEREGISTER,
    END_COMMAND,
    END_STATUS,
    
    // ROUTE Commands
    ROUTE_REGISTER,
    ROUTE_UNREGISTER,
    ROUTE_UPDATE,
    
    // P2P Commands
    P2P_CONNECT,
    P2P_DISCONNECT,
    P2P_DATA
}
```

---

## 4. 页面导航结构

### 4.1 协议管理菜单
```
协议管理
├── 协议管理（已有）
├── 适配器管理（新增）
├── 命令调试（新增）
├── 流量监控（新增）
└── 版本管理（新增）
```

### 4.2 菜单配置
```javascript
{
    id: 'protocol',
    name: '协议管理',
    icon: 'ri-exchange-line',
    children: [
        {
            id: 'protocol-management',
            name: '协议管理',
            url: '/console/pages/nexus/protocol-management.html',
            icon: 'ri-settings-3-line'
        },
        {
            id: 'protocol-adapter',
            name: '适配器管理',
            url: '/console/pages/nexus/protocol-adapter-management.html',
            icon: 'ri-plug-line'
        },
        {
            id: 'protocol-debug',
            name: '命令调试',
            url: '/console/pages/nexus/protocol-command-debug.html',
            icon: 'ri-bug-line'
        },
        {
            id: 'protocol-monitor',
            name: '流量监控',
            url: '/console/pages/nexus/protocol-traffic-monitor.html',
            icon: 'ri-dashboard-line'
        },
        {
            id: 'protocol-version',
            name: '版本管理',
            url: '/console/pages/nexus/protocol-version-management.html',
            icon: 'ri-git-branch-line'
        }
    ]
}
```

---

## 5. API 接口设计

### 5.1 协议处理器管理
```java
// 获取协议处理器列表
GET /api/protocol/handlers

// 注册协议处理器
POST /api/protocol/handlers
{
    "commandType": "MCP_REGISTER",
    "handlerName": "注册处理器",
    "handlerDescription": "处理MCP注册命令",
    "handlerClass": "com.example.MCPRegisterHandler"
}

// 移除协议处理器
DELETE /api/protocol/handlers/{commandType}

// 处理命令
POST /api/protocol/commands
{
    "commandType": "MCP_REGISTER",
    "data": {...}
}
```

### 5.2 适配器管理
```java
// 获取适配器列表
GET /api/protocol/adapters

// 注册适配器
POST /api/protocol/adapters
{
    "adapterId": "adapter-001",
    "adapterName": "MCP适配器",
    "protocolType": "MCP",
    "config": {...}
}

// 更新适配器配置
PUT /api/protocol/adapters/{adapterId}

// 注销适配器
DELETE /api/protocol/adapters/{adapterId}
```

### 5.3 流量监控
```java
// 获取实时流量数据
GET /api/protocol/traffic/realtime

// 获取历史统计数据
GET /api/protocol/traffic/stats?startTime=xxx&endTime=xxx

// 获取命令统计
GET /api/protocol/commands/stats
```

---

## 6. 页面更新计划

### 6.1 现有页面更新
1. **protocol-management.html**
   - 更新资源路径为绝对路径
   - 添加 theme.css 引用
   - 修复初始化函数

### 6.2 新页面开发
1. **protocol-adapter-management.html**
   - 适配器CRUD操作
   - 配置表单
   - 状态监控

2. **protocol-command-debug.html**
   - 命令构造器UI
   - JSON编辑器
   - 响应展示

3. **protocol-traffic-monitor.html**
   - 实时图表（ECharts）
   - 统计卡片
   - 告警展示

4. **protocol-version-management.html**
   - 版本列表
   - 兼容性矩阵
   - 变更日志

---

## 7. 技术实现要点

### 7.1 前端技术栈
- HTML5 + CSS3 (theme.css)
- JavaScript (ES6+)
- ECharts (图表)
- JSON Editor (JSON编辑)

### 7.2 样式规范
- 使用 nexus 前缀命名
- 统一使用 theme.css 变量
- 响应式布局支持

### 7.3 API 封装
```javascript
// protocol-api.js
const protocolApi = {
    // 协议处理器
    getHandlers: () => apiClient.get('/protocol/handlers'),
    registerHandler: (data) => apiClient.post('/protocol/handlers', data),
    removeHandler: (type) => apiClient.delete(`/protocol/handlers/${type}`),
    handleCommand: (data) => apiClient.post('/protocol/commands', data),
    
    // 适配器
    getAdapters: () => apiClient.get('/protocol/adapters'),
    registerAdapter: (data) => apiClient.post('/protocol/adapters', data),
    updateAdapter: (id, data) => apiClient.put(`/protocol/adapters/${id}`, data),
    removeAdapter: (id) => apiClient.delete(`/protocol/adapters/${id}`),
    
    // 流量监控
    getRealtimeTraffic: () => apiClient.get('/protocol/traffic/realtime'),
    getTrafficStats: (params) => apiClient.get('/protocol/traffic/stats', params),
    getCommandStats: () => apiClient.get('/protocol/commands/stats')
};
```

---

## 8. 实施优先级

### P0 - 高优先级
1. 更新现有 protocol-management.html 页面
2. 开发 protocol-adapter-management.html

### P1 - 中优先级
3. 开发 protocol-command-debug.html
4. 开发 protocol-traffic-monitor.html

### P2 - 低优先级
5. 开发 protocol-version-management.html
6. 完善文档和测试
