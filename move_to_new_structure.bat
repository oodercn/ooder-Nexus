@echo off
chcp 65001 >nul
echo ==========================================
echo ooderNexus 包结构重构 - 文件移动脚本
echo ==========================================
echo.

set BASE_DIR=src\main\java\net\ooder\nexus

echo [1/5] 创建新的包结构...
mkdir %BASE_DIR%\infrastructure\management 2>nul
mkdir %BASE_DIR%\infrastructure\config 2>nul
mkdir %BASE_DIR%\infrastructure\persistence 2>nul
mkdir %BASE_DIR%\adapter\inbound\controller\mcp 2>nul
mkdir %BASE_DIR%\adapter\inbound\controller\route 2>nul
mkdir %BASE_DIR%\adapter\inbound\controller\end 2>nul
mkdir %BASE_DIR%\adapter\inbound\controller\network 2>nul
mkdir %BASE_DIR%\adapter\inbound\controller\config 2>nul
mkdir %BASE_DIR%\adapter\inbound\controller\system 2>nul
mkdir %BASE_DIR%\adapter\inbound\console 2>nul
mkdir %BASE_DIR%\application\service 2>nul
mkdir %BASE_DIR%\domain\mcp\model 2>nul
mkdir %BASE_DIR%\domain\route\model 2>nul
mkdir %BASE_DIR%\domain\end\model 2>nul
mkdir %BASE_DIR%\domain\network\model 2>nul
mkdir %BASE_DIR%\domain\config\model 2>nul
mkdir %BASE_DIR%\domain\security\model 2>nul
mkdir %BASE_DIR%\domain\system\model 2>nul
echo 目录创建完成
echo.

echo [2/5] 移动 management 和 manager...
if exist %BASE_DIR%\management (
    xcopy /Y %BASE_DIR%\management\*.java %BASE_DIR%\infrastructure\management\ >nul 2>&1
    xcopy /Y %BASE_DIR%\management\impl\*.java %BASE_DIR%\infrastructure\management\ >nul 2>&1
    echo   management 文件已移动
)
if exist %BASE_DIR%\manager (
    xcopy /Y %BASE_DIR%\manager\*.java %BASE_DIR%\infrastructure\management\ >nul 2>&1
    echo   manager 文件已移动
)

echo [3/5] 移动 console...
if exist %BASE_DIR%\console (
    xcopy /Y %BASE_DIR%\console\*.java %BASE_DIR%\adapter\inbound\console\ >nul 2>&1
    echo   console 文件已移动
)

echo [4/5] 移动 model 到 domain...
if exist %BASE_DIR%\model\mcp (
    xcopy /E /I /Y %BASE_DIR%\model\mcp\* %BASE_DIR%\domain\mcp\model\ >nul 2>&1
    echo   model/mcp 已移动
)
if exist %BASE_DIR%\model\network (
    xcopy /E /I /Y %BASE_DIR%\model\network\* %BASE_DIR%\domain\network\model\ >nul 2>&1
    echo   model/network 已移动
)
if exist %BASE_DIR%\model\device (
    xcopy /E /I /Y %BASE_DIR%\model\device\* %BASE_DIR%\domain\end\model\ >nul 2>&1
    echo   model/device 已移动
)
if exist %BASE_DIR%\model\endagent (
    xcopy /E /I /Y %BASE_DIR%\model\endagent\* %BASE_DIR%\domain\end\model\ >nul 2>&1
    echo   model/endagent 已移动
)
if exist %BASE_DIR%\model\config (
    xcopy /E /I /Y %BASE_DIR%\model\config\* %BASE_DIR%\domain\config\model\ >nul 2>&1
    echo   model/config 已移动
)
if exist %BASE_DIR%\model\security (
    xcopy /E /I /Y %BASE_DIR%\model\security\* %BASE_DIR%\domain\security\model\ >nul 2>&1
    echo   model/security 已移动
)
if exist %BASE_DIR%\model\system (
    xcopy /E /I /Y %BASE_DIR%\model\system\* %BASE_DIR%\domain\system\model\ >nul 2>&1
    echo   model/system 已移动
)

echo [5/5] 移动 controller 到 adapter...
if exist %BASE_DIR%\controller\McpAgentController.java (
    xcopy /Y %BASE_DIR%\controller\Mcp*.java %BASE_DIR%\adapter\inbound\controller\mcp\ >nul 2>&1
    echo   MCP控制器已移动
)
if exist %BASE_DIR%\controller\RouteController.java (
    xcopy /Y %BASE_DIR%\controller\Route*.java %BASE_DIR%\adapter\inbound\controller\route\ >nul 2>&1
    echo   Route控制器已移动
)
if exist %BASE_DIR%\controller\EndDeviceController.java (
    xcopy /Y %BASE_DIR%\controller\End*.java %BASE_DIR%\adapter\inbound\controller\end\ >nul 2>&1
    echo   End控制器已移动
)
if exist %BASE_DIR%\controller\NetworkController.java (
    xcopy /Y %BASE_DIR%\controller\Network*.java %BASE_DIR%\adapter\inbound\controller\network\ >nul 2>&1
    echo   Network控制器已移动
)
if exist %BASE_DIR%\controller\SystemConfigController.java (
    xcopy /Y %BASE_DIR%\controller\System*.java %BASE_DIR%\adapter\inbound\controller\system\ >nul 2>&1
    xcopy /Y %BASE_DIR%\controller\*Status*.java %BASE_DIR%\adapter\inbound\controller\system\ >nul 2>&1
    echo   System控制器已移动
)
if exist %BASE_DIR%\controller\*Config*.java (
    xcopy /Y %BASE_DIR%\controller\*Config*.java %BASE_DIR%\adapter\inbound\controller\config\ >nul 2>&1
    echo   Config控制器已移动
)

echo.
echo ==========================================
echo 文件移动完成！
echo ==========================================
echo.
echo 请在IDE中完成以下操作：
echo 1. 刷新项目（Refresh）
echo 2. 修改新文件中的包声明（package语句）
echo 3. 修改import语句中的旧包引用
echo 4. 删除旧目录：
echo    - management/
echo    - manager/
echo    - console/
echo    - model/mcp/
echo    - model/network/
echo    - model/device/
echo    - model/endagent/
echo    - model/config/
echo    - model/security/
echo    - model/system/
echo    - controller/（部分文件）
echo 5. 运行 mvn clean compile 验证
echo.
pause
