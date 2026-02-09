#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
批量修复HTML文件中的资源路径
将相对路径 ../../css/ 和 ../../js/ 改为绝对路径 /console/css/ 和 /console/js/
"""

import os
import re

# 需要修复的文件列表
files_to_fix = [
    "src/main/resources/static/console/pages/nexus/health-check.html",
    "src/main/resources/static/console/pages/nexus/protocol-management.html",
    "src/main/resources/static/console/pages/nexus/security-management.html",
    "src/main/resources/static/console/pages/nexus/log-management.html",
    "src/main/resources/static/console/pages/nexus/scenario-management.html",
    "src/main/resources/static/console/pages/nexus/network-topology.html",
    "src/main/resources/static/console/pages/nexus/endagent-management.html",
    "src/main/resources/static/console/pages/nexus/system-status.html",
    "src/main/resources/static/console/pages/nexus/config-management.html",
    "src/main/resources/static/console/pages/nexus/route-management.html",
    "src/main/resources/static/console/pages/nexus/network-nodes.html",
    "src/main/resources/static/console/pages/nexus/llm-management.html",
    "src/main/resources/static/console/pages/nexus/capability-management.html",
    "src/main/resources/static/console/pages/lan/lan-dashboard.html",
    "src/main/resources/static/console/pages/lan/ip-management.html",
    "src/main/resources/static/console/pages/lan/device-details.html",
    "src/main/resources/static/console/pages/lan/network-devices.html",
]

def fix_file(file_path):
    """修复单个文件"""
    full_path = os.path.join("e:/github/ooder-Nexus", file_path)

    if not os.path.exists(full_path):
        print(f"文件不存在: {file_path}")
        return False

    with open(full_path, 'r', encoding='utf-8') as f:
        content = f.read()

    original_content = content

    # 1. 修复 CSS 路径
    content = re.sub(r'href="../../css/remixicon/remixicon.css"', 'href="/console/css/remixicon/remixicon.css"', content)
    content = re.sub(r'href="../../css/styles.css"', 'href="/console/css/theme.css"', content)
    content = re.sub(r'href="../../css/theme.css"', 'href="/console/css/theme.css"', content)
    content = re.sub(r'href="../../css/dashboard.css"', 'href="/console/css/theme.css"', content)
    content = re.sub(r'href="../../css/mcpagent/common.css"', 'href="/console/css/theme.css"', content)

    # 2. 修复 JS 路径
    content = re.sub(r'src="../../js/theme-manager.js"', 'src="/console/js/theme-manager.js"', content)
    content = re.sub(r'src="../../js/menu-loader.js"', 'src="/console/js/menu-loader.js"', content)
    content = re.sub(r'src="../../js/common.js"', 'src="/console/js/common.js"', content)

    # 3. 修复 nexus 目录下的 JS 路径
    content = re.sub(r'src="../../js/nexus/', 'src="/console/js/nexus/', content)

    # 4. 检查是否需要在 head 中添加缺失的 JS 文件
    if 'theme-manager.js' not in content:
        # 在 </head> 前添加必要的 JS
        head_end = content.find('</head>')
        if head_end > 0:
            js_to_add = '''    <script src="/console/js/theme-manager.js"></script>
    <script src="/console/js/menu-loader.js"></script>
    <script src="/console/js/common.js"></script>
'''
            content = content[:head_end] + js_to_add + content[head_end:]

    # 5. 移除底部的 menu-loader.js（如果已经在 head 中）
    # 这个需要谨慎处理，先不移除

    # 6. 修复 window.onload -> window.onPageInit
    content = re.sub(r'window\.onload\s*=\s*function\(\)\s*\{', 'window.onPageInit = function() {', content)

    # 7. 移除 initMenu() 调用
    content = re.sub(r'//\s*初始化菜单\s*\n\s*initMenu\(\);\s*\n', '', content)
    content = re.sub(r'initMenu\(\);\s*\n', '', content)

    if content != original_content:
        with open(full_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"已修复: {file_path}")
        return True
    else:
        print(f"无需修改: {file_path}")
        return False

def main():
    fixed_count = 0
    for file_path in files_to_fix:
        if fix_file(file_path):
            fixed_count += 1

    print(f"\n总共修复了 {fixed_count} 个文件")

if __name__ == "__main__":
    main()
