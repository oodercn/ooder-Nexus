@echo off
chcp 65001 >nul
echo ==========================================
echo    Ooder Nexus 2.0 - OpenWrt 预览版
echo    版本: 2.0.0-OpenWrt-Preview
echo ==========================================
echo.
echo 正在启动 Ooder Nexus...
echo.

REM 检查Java环境
java -version >nul 2>&1
if errorlevel 1 (
    echo [错误] 未检测到Java环境，请先安装Java 8或更高版本
    pause
    exit /b 1
)

echo [信息] Java环境检测通过
echo [信息] 启动模式: OpenWrt 真实设备模式 (Mock已关闭)
echo [信息] 服务端口: 8081
echo.
echo 启动后请访问: http://localhost:8081/console/index.html
echo.
echo 提示: 请确保您的OpenWrt设备已开启SSH访问权限
echo.
echo ==========================================
echo.

REM 启动服务
java -jar ooder-nexus-2.0.0-preview.jar

pause
