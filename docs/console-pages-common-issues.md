# Console 页面常见问题及解决方案

## 1. 主题切换相关

### 问题 1.1: TypeError: Cannot read properties of null (reading 'classList')
**错误信息:**
```
TypeError: Cannot read properties of null (reading 'classList')
    at ThemeManager.applyTheme (theme-manager.js:45:27)
```

**原因:**
`theme-manager.js` 在 `<head>` 中加载时，`document.body` 还不存在。

**解决方案:**
在 `applyTheme()` 方法中添加 body 存在性检查：
```javascript
applyTheme() {
    // 确保 body 存在
    if (!document.body) {
        document.addEventListener('DOMContentLoaded', () => this.applyTheme());
        return;
    }
    // ... 后续代码
}
```

---

### 问题 1.2: 主题设置无法跨页面同步
**原因:**
不同页面使用不同的存储方式（localStorage vs cookie）或不同的 key。

**解决方案:**
统一使用 cookie 存储，key 为 `nexus_theme`：
```javascript
// 设置 cookie
document.cookie = 'nexus_theme=' + theme + '; expires=' + expires + '; path=/; SameSite=Lax';

// 读取 cookie
function getCookie(name) {
    const nameEQ = name + "=";
    const ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}
```

---

## 2. 页面布局相关

### 问题 2.1: Nexus 页面面板消失/布局错乱
**现象:**
页面内容不显示或布局完全错乱。

**原因:**
缺少 `mcpagent/common.css` 样式文件，该文件包含 Nexus 特有布局类（`.nexus-container`, `.nexus-sidebar`, `.nexus-main-content` 等）的样式定义。

**解决方案:**
在 Nexus 页面的 `<head>` 中添加 CSS 引用：
```html
<link rel="stylesheet" href="/console/css/mcpagent/common.css">
```

**受影响的页面:**
- `pages/nexus/dashboard.html`
- `pages/nexus/health-check.html`
- `pages/nexus/protocol-management.html`
- `pages/nexus/security-management.html`
- `pages/nexus/log-management.html`
- `pages/nexus/scenario-management.html`
- `pages/nexus/network-topology.html`
- `pages/nexus/endagent-management.html`
- `pages/nexus/system-status.html`
- `pages/nexus/config-management.html`

---

### 问题 2.2: 页面头部结构不统一
**现象:**
有些页面使用 `.content-header`，有些使用 `.header`，导致样式不一致。

**解决方案:**
在 `theme.css` 和 `common.js` 中同时支持两种结构：
```css
/* CSS 支持两种头部 */
.content-header, .header { ... }
.content-actions, .header-actions { ... }
```

```javascript
// JS 支持两种头部
const contentHeader = document.querySelector('.content-header, .header');
const contentActions = contentHeader.querySelector('.content-actions, .header-actions');
```

---

## 3. 资源路径相关

### 问题 3.1: 相对路径导致资源加载失败
**现象:**
页面在不同层级目录时，CSS/JS 文件加载失败（404）。

**原因:**
使用相对路径如 `../../css/theme.css`，当页面路径变化时无法正确加载。

**解决方案:**
统一使用绝对路径（以 `/console/` 开头）：
```html
<!-- 错误 -->
<link rel="stylesheet" href="../../css/theme.css">
<script src="../../js/theme-manager.js"></script>

<!-- 正确 -->
<link rel="stylesheet" href="/console/css/theme.css">
<script src="/console/js/theme-manager.js"></script>
```

---

### 问题 3.2: 缺少必要的公共 JS 文件
**每个页面必须引用的文件:**
```html
<link rel="stylesheet" href="/console/css/remixicon/remixicon.css">
<link rel="stylesheet" href="/console/css/theme.css">
<script src="/console/js/theme-manager.js"></script>
<script src="/console/js/menu-loader.js"></script>
<script src="/console/js/common.js"></script>
```

**Nexus 页面额外需要:**
```html
<link rel="stylesheet" href="/console/css/mcpagent/common.css">
```

---

## 4. JavaScript 错误

### 问题 4.1: ReferenceError: xxxApi is not defined
**现象:**
```
ReferenceError: openwrtApi is not defined
```

**原因:**
页面使用了 API 对象但未定义。

**解决方案:**
在页面脚本开头定义 API 对象：
```javascript
const openwrtApi = {
    baseUrl: '/api/openwrt',
    async get(endpoint) {
        const response = await fetch(`${this.baseUrl}${endpoint}`);
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        return await response.json();
    },
    async post(endpoint, data = {}) {
        const response = await fetch(`${this.baseUrl}${endpoint}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        return await response.json();
    }
};
```

---

### 问题 4.2: 页面初始化时机错误
**现象:**
脚本执行时 DOM 元素还未渲染，导致获取元素失败。

**原因:**
使用 `window.onload` 或直接在脚本中操作 DOM，但此时 DOM 可能未准备好。

**解决方案:**
统一使用 `window.onPageInit`：
```javascript
// 错误
window.onload = function() { ... };

// 正确
window.onPageInit = function() { ... };
```

`common.js` 会自动处理 DOMContentLoaded 事件并调用 `onPageInit`。

---

## 5. 主题切换按钮

### 问题 5.1: 页面没有主题切换按钮
**解决方案:**
`common.js` 会自动为所有页面添加主题切换按钮。确保页面有以下结构之一：
```html
<!-- 结构 1 -->
<div class="content-header">
    <h1>...</h1>
    <div class="content-actions">
        <!-- 按钮会自动添加到这里 -->
    </div>
</div>

<!-- 结构 2 -->
<div class="header">
    <h1>...</h1>
    <div class="header-actions">
        <!-- 按钮会自动添加到这里 -->
    </div>
</div>
```

如果没有上述结构，`common.js` 会自动创建一个头部并添加按钮。

---

## 6. 菜单加载

### 问题 6.1: 左侧菜单不显示
**原因:**
1. 缺少 `menu-loader.js`
2. 页面没有 `<ul class="nav-menu" id="nav-menu">` 元素

**解决方案:**
确保页面引用 `menu-loader.js` 并包含菜单容器：
```html
<script src="/console/js/menu-loader.js"></script>
<ul class="nav-menu" id="nav-menu"></ul>
```

---

## 7. 页面开发规范

### 7.1 标准页面模板
```html
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>页面标题 - Nexus</title>
    <!-- 基础样式 -->
    <link rel="stylesheet" href="/console/css/remixicon/remixicon.css">
    <link rel="stylesheet" href="/console/css/theme.css">
    <!-- Nexus 页面额外样式 -->
    <link rel="stylesheet" href="/console/css/mcpagent/common.css">
    <!-- 基础脚本 -->
    <script src="/console/js/theme-manager.js"></script>
    <script src="/console/js/menu-loader.js"></script>
    <script src="/console/js/common.js"></script>
</head>
<body class="nexus-page">
    <div class="nexus-container">
        <!-- 侧边栏 -->
        <div class="nexus-sidebar">
            <h2><i class="ri-server-line"></i> Nexus Console</h2>
            <ul class="nav-menu" id="nav-menu"></ul>
        </div>
        
        <!-- 主内容区 -->
        <div class="nexus-main-content">
            <div class="nexus-header">
                <h1><i class="ri-xxx-line"></i> 页面标题</h1>
                <!-- 主题切换按钮会自动添加 -->
            </div>
            <!-- 页面内容 -->
        </div>
    </div>

    <!-- 页面初始化 -->
    <script>
        window.onPageInit = function() {
            // 页面初始化代码
        };
    </script>
</body>
</html>
```

### 7.2 关键要点
1. 使用绝对路径 `/console/...`
2. 必须引用 `theme-manager.js`, `menu-loader.js`, `common.js`
3. 使用 `window.onPageInit` 进行页面初始化
4. Nexus 页面需要 `mcpagent/common.css`
5. 保持统一的 cookie key: `nexus_theme`
