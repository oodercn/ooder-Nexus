@echo off
chcp 65001 >nul
echo ==========================================
echo ooderNexus 包结构迁移脚本
echo ==========================================
echo.

set BASE_DIR=src\main\java\net\ooder\nexus

REM 创建新目录结构
echo [1/4] 创建新目录结构...
mkdir %BASE_DIR%\core\protocol\adapter 2>nul
mkdir %BASE_DIR%\core\protocol\model 2>nul
mkdir %BASE_DIR%\core\skill\impl 2>nul
mkdir %BASE_DIR%\core\storage\vfs 2>nul
mkdir %BASE_DIR%\common\constant 2>nul
mkdir %BASE_DIR%\common\exception 2>nul
mkdir %BASE_DIR%\common\utils 2>nul
mkdir %BASE_DIR%\common\model 2>nul
echo 目录创建完成
echo.

REM 移动 protocol 文件
echo [2/4] 移动 protocol 模块...
if exist %BASE_DIR%\protocol (
    xcopy /E /I /Y %BASE_DIR%\protocol\* %BASE_DIR%\core\protocol\ >nul 2>&1
    echo   protocol 文件已复制到 core/protocol/
)

REM 移动 model/protocol 文件
echo [3/4] 移动 protocol model 模块...
if exist %BASE_DIR%\model\protocol (
    xcopy /E /I /Y %BASE_DIR%\model\protocol\* %BASE_DIR%\core\protocol\model\ >nul 2>&1
    echo   model/protocol 文件已复制到 core/protocol/model/
)

REM 移动 storage 文件
echo [4/4] 移动 storage 模块...
if exist %BASE_DIR%\storage (
    xcopy /E /I /Y %BASE_DIR%\storage\* %BASE_DIR%\core\storage\ >nul 2>&1
    echo   storage 文件已复制到 core/storage/
)

REM 移动 skill 文件
if exist %BASE_DIR%\skill (
    xcopy /E /I /Y %BASE_DIR%\skill\* %BASE_DIR%\core\skill\ >nul 2>&1
    echo   skill 文件已复制到 core/skill/
)

echo.
echo ==========================================
echo 文件移动完成！
echo ==========================================
echo.
echo 接下来请在IDE中完成以下操作：
echo 1. 刷新项目（Refresh）
echo 2. 修改新文件中的包声明（package语句）
echo 3. 修改import语句中的旧包引用
echo 4. 删除旧目录：
echo    - protocol/
echo    - model/protocol/
echo    - storage/
echo    - skill/
echo 5. 运行 mvn clean compile 验证
echo.
pause
