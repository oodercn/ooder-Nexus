#!/usr/bin/env python3
"""
包迁移脚本 - 批量修改Java文件的包声明和import
"""

import os
import re
import shutil
from pathlib import Path

# 定义迁移规则
MIGRATION_RULES = [
    # Protocol模块
    {
        'source_dir': 'src/main/java/net/ooder/nexus/protocol',
        'target_dir': 'src/main/java/net/ooder/nexus/core/protocol',
        'package_mapping': {
            'net.ooder.nexus.protocol': 'net.ooder.nexus.core.protocol',
            'net.ooder.nexus.protocol.adapter': 'net.ooder.nexus.core.protocol.adapter',
            'net.ooder.nexus.model.protocol': 'net.ooder.nexus.core.protocol.model',
        }
    },
    # Model protocol模块
    {
        'source_dir': 'src/main/java/net/ooder/nexus/model/protocol',
        'target_dir': 'src/main/java/net/ooder/nexus/core/protocol/model',
        'package_mapping': {
            'net.ooder.nexus.model.protocol': 'net.ooder.nexus.core.protocol.model',
        }
    },
    # Storage模块
    {
        'source_dir': 'src/main/java/net/ooder/nexus/storage',
        'target_dir': 'src/main/java/net/ooder/nexus/core/storage',
        'package_mapping': {
            'net.ooder.nexus.storage': 'net.ooder.nexus.core.storage',
            'net.ooder.nexus.storage.vfs': 'net.ooder.nexus.core.storage.vfs',
        }
    },
    # Skill模块
    {
        'source_dir': 'src/main/java/net/ooder/nexus/skill',
        'target_dir': 'src/main/java/net/ooder/nexus/core/skill',
        'package_mapping': {
            'net.ooder.nexus.skill': 'net.ooder.nexus.core.skill',
            'net.ooder.nexus.skill.impl': 'net.ooder.nexus.core.skill.impl',
        }
    },
]

def migrate_file(source_file, target_file, package_mapping):
    """迁移单个文件"""
    with open(source_file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # 替换包声明和import
    for old_pkg, new_pkg in package_mapping.items():
        # 替换package声明
        content = re.sub(
            rf'^package {re.escape(old_pkg)};',
            f'package {new_pkg};',
            content,
            flags=re.MULTILINE
        )
        # 替换import语句
        content = re.sub(
            rf'^import {re.escape(old_pkg)}',
            f'import {new_pkg}',
            content,
            flags=re.MULTILINE
        )
    
    # 确保目标目录存在
    os.makedirs(os.path.dirname(target_file), exist_ok=True)
    
    # 写入新文件
    with open(target_file, 'w', encoding='utf-8') as f:
        f.write(content)
    
    print(f"Migrated: {source_file} -> {target_file}")

def migrate_directory(rule):
    """迁移整个目录"""
    source_dir = rule['source_dir']
    target_dir = rule['target_dir']
    package_mapping = rule['package_mapping']
    
    if not os.path.exists(source_dir):
        print(f"Source directory not found: {source_dir}")
        return
    
    for root, dirs, files in os.walk(source_dir):
        for file in files:
            if file.endswith('.java'):
                source_file = os.path.join(root, file)
                # 计算相对路径
                rel_path = os.path.relpath(source_file, source_dir)
                target_file = os.path.join(target_dir, rel_path)
                
                migrate_file(source_file, target_file, package_mapping)

def main():
    """主函数"""
    print("Starting package migration...")
    
    for rule in MIGRATION_RULES:
        print(f"\nMigrating: {rule['source_dir']}")
        migrate_directory(rule)
    
    print("\nMigration completed!")
    print("\nNext steps:")
    print("1. Review the migrated files")
    print("2. Run 'mvn clean compile' to verify")
    print("3. Delete old directories after verification")

if __name__ == '__main__':
    main()
