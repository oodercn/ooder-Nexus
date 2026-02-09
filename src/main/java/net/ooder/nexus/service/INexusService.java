package net.ooder.nexus.service;

import net.ooder.nexus.model.Result;
import net.ooder.nexus.domain.network.model.NetworkSetting;
import net.ooder.nexus.domain.network.model.IPAddress;
import net.ooder.nexus.domain.network.model.IPBlacklist;
import net.ooder.nexus.domain.system.model.SystemInfo;
import net.ooder.nexus.domain.system.model.ServiceStatus;
import net.ooder.nexus.domain.system.model.ResourceUsage;
import net.ooder.nexus.domain.system.model.VersionInfo;
import net.ooder.nexus.domain.system.model.SystemHealthData;
import net.ooder.nexus.domain.system.model.SystemLoadData;
import net.ooder.nexus.domain.network.model.NetworkStatusData;
import net.ooder.nexus.domain.system.model.CommandStatsData;
import net.ooder.nexus.domain.config.model.BasicConfig;
import net.ooder.nexus.domain.config.model.AdvancedConfig;
import net.ooder.nexus.domain.config.model.SecurityConfig;
import net.ooder.nexus.domain.config.model.TerminalConfig;
import net.ooder.nexus.domain.config.model.ServiceConfig;
import net.ooder.nexus.domain.config.model.SystemConfig;
import net.ooder.nexus.domain.config.model.NetworkConfig;
import net.ooder.nexus.domain.config.model.ConfigsResult;
import net.ooder.nexus.domain.config.model.ConfigHistoryItemsResult;
import net.ooder.nexus.domain.network.model.EndAgent;
import net.ooder.nexus.domain.mcp.model.LogEntry;
import net.ooder.nexus.domain.security.model.SecurityStatus;
import net.ooder.nexus.domain.security.model.UserInfo;
import net.ooder.nexus.domain.security.model.PermissionsData;
import net.ooder.nexus.domain.security.model.SecurityLogsResult;
import net.ooder.nexus.domain.mcp.model.Capability;
import net.ooder.nexus.domain.mcp.model.ProtocolHandlerData;
import net.ooder.nexus.domain.system.model.HealthCheckResult;
import net.ooder.nexus.domain.system.model.HealthReport;
import net.ooder.nexus.domain.system.model.HealthCheckSchedule;
import net.ooder.nexus.domain.system.model.ServiceCheckResult;
import net.ooder.nexus.model.TestCommandResult;
import net.ooder.nexus.model.ConfigResult;
import net.ooder.nexus.model.LogExportResult;
import net.ooder.nexus.model.ConfigDataResult;
import net.ooder.nexus.model.ConfigExportResult;
import net.ooder.nexus.model.ConfigImportResult;
import net.ooder.nexus.model.ConfigResetResult;
import net.ooder.nexus.model.ConfigHistoryResult;

import java.util.List;
import java.util.Map;

/**
 * Nexus Service 接口定义
 * 
 * 本接口定义了 Nexus 的所有功能接口，支持两种实现方式�?
 * 1. Mock Service：使用模拟数据，用于�?发和测试
 * 2. Real Service：调用真实的 ooderAgent 0.6.5 接口，用于生产环�?
 * 
 * 配置说明�?
 * - Service 实现方式通过配置文件或环境变量控�?
 * - 默认使用 Mock Service，方便开发和测试
 * - 生产环境建议使用 Real Service，确保数据准确�??
 * - 支持动�?�切�? Service 实现方式，无�?重启应用
 * 
 * @version 1.0.0
 */
public interface INexusService {

    /**
     * 初始�? Service
     * @param config 配置参数
     */
    void initialize(Map<String, Object> config);

    /**
     * 获取 Service 类型
     * @return Service 类型�?'mock' | 'real'
     */
    String getServiceType();

    /**
     * 获取版本信息
     * @return 版本信息
     */
    Result<VersionInfo> getVersion();

    // ==================== 网络配置模块 ====================

    /**
     * 获取网络设置
     * @param settingType 设置类型
     * @return 网络设置
     */
    Result<NetworkSetting> getNetworkSetting(String settingType);

    /**
     * 获取�?有网络设�?
     * @return �?有网络设�?
     */
    Result<List<NetworkSetting>> getAllNetworkSettings();

    /**
     * 更新网络设置
     * @param settingType 设置类型
     * @param settingData 设置数据
     * @return 更新结果
     */
    Result<NetworkSetting> updateNetworkSetting(String settingType, Map<String, Object> settingData);

    /**
     * 获取 IP 地址列表
     * @param type IP 类型
     * @param status IP 状�??
     * @return IP 地址列表
     */
    Result<List<IPAddress>> getIPAddresses(String type, String status);

    /**
     * 添加静�?? IP 地址
     * @param ipData IP 数据
     * @return 添加结果
     */
    Result<IPAddress> addStaticIPAddress(Map<String, Object> ipData);

    /**
     * 删除 IP 地址
     * @param ipId IP ID
     * @return 删除结果
     */
    Result<IPAddress> deleteIPAddress(String ipId);

    /**
     * 获取 IP 黑名�?
     * @return IP 黑名�?
     */
    Result<List<IPBlacklist>> getIPBlacklist();

    /**
     * 添加 IP 到黑名单
     * @param blacklistData 黑名单数�?
     * @return 添加结果
     */
    Result<IPBlacklist> addIPToBlacklist(Map<String, Object> blacklistData);

    /**
     * 从黑名单移除 IP
     * @param blacklistId 黑名�? ID
     * @return 移除结果
     */
    Result<IPBlacklist> removeIPFromBlacklist(String blacklistId);

    // ==================== 系统状�?�模�? ====================

    /**
     * 获取系统基本信息
     * @return 系统基本信息
     */
    Result<SystemInfo> getSystemInfo();

    /**
     * 获取系统健康状�??
     * @return 系统健康状�??
     */
    Result<SystemHealthData> getSystemHealth();

    /**
     * 获取服务状�?�列�?
     * @return 服务状�?�列�?
     */
    Result<List<ServiceStatus>> getServiceStatuses();

    /**
     * 获取服务状�?�详�?
     * @param serviceId 服务 ID
     * @return 服务状�?�详�?
     */
    Result<ServiceStatus> getServiceStatus(String serviceId);

    /**
     * 获取系统资源使用情况
     * @return 系统资源使用情况
     */
    Result<ResourceUsage> getResourceUsage();

    /**
     * 获取系统负载
     * @return 系统负载
     */
    Result<SystemLoadData> getSystemLoad();

    /**
     * 重启服务
     * @param serviceId 服务 ID
     * @return 重启结果
     */
    Result<ServiceStatus> restartService(String serviceId);

    // ==================== Nexus 核心模块 ====================

    /**
     * 获取网络状�??
     * @return 网络状�??
     */
    Result<NetworkStatusData> getNetworkStatus();

    /**
     * 获取命令统计
     * @return 命令统计
     */
    Result<CommandStatsData> getCommandStats();

    /**
     * 获取终端代理列表
     * @return 终端代理列表
     */
    Result<List<EndAgent>> getEndAgents();

    /**
     * 添加终端代理
     * @param agentData 终端代理数据
     * @return 添加结果
     */
    Result<EndAgent> addEndAgent(Map<String, Object> agentData);

    /**
     * 编辑终端代理
     * @param agentId 终端代理 ID
     * @param agentData 终端代理数据
     * @return 编辑结果
     */
    Result<EndAgent> editEndAgent(String agentId, Map<String, Object> agentData);

    /**
     * 删除终端代理
     * @param agentId 终端代理 ID
     * @return 删除结果
     */
    Result<EndAgent> deleteEndAgent(String agentId);

    /**
     * 获取终端代理详情
     * @param agentId 终端代理 ID
     * @return 终端代理详情
     */
    Result<EndAgent> getEndAgentDetails(String agentId);

    /**
     * 测试命令
     * @param commandData 命令数据
     * @return 测试结果
     */
    Result<TestCommandResult> testCommand(Map<String, Object> commandData);

    /**
     * 获取日志列表
     * @param limit 限制数量
     * @return 日志列表
     */
    Result<List<LogEntry>> getLogList(int limit);

    /**
     * 清空日志
     * @return 清空结果
     */
    Result<Void> clearLog();

    /**
     * 获取基本配置
     * @return 基本配置
     */
    Result<BasicConfig> getBasicConfig();

    /**
     * 获取高级配置
     * @return 高级配置
     */
    Result<AdvancedConfig> getAdvancedConfig();

    /**
     * 保存配置
     * @param configData 配置数据
     * @return 保存结果
     */
    Result<ConfigResult> saveConfig(Map<String, Object> configData);

    /**
     * 重置配置
     * @return 重置结果
     */
    Result<ConfigResult> resetConfig();

    /**
     * 获取安全配置
     * @return 安全配置
     */
    Result<SecurityConfig> getSecurityConfig();

    

    // ==================== 安全管理模块 ====================

    /**
     * 获取安全状�??
     * @return 安全状�??
     */
    Result<SecurityStatus> getSecurityStatus();

    /**
     * 获取用户列表
     * @return 用户列表
     */
    Result<List<UserInfo>> getUsers();

    /**
     * 添加用户
     * @param userData 用户数据
     * @return 添加结果
     */
    Result<UserInfo> addUser(Map<String, Object> userData);

    /**
     * 编辑用户
     * @param userId 用户 ID
     * @param userData 用户数据
     * @return 编辑结果
     */
    Result<UserInfo> editUser(String userId, Map<String, Object> userData);

    /**
     * 删除用户
     * @param userId 用户 ID
     * @return 删除结果
     */
    Result<UserInfo> deleteUser(String userId);

    /**
     * 启用用户
     * @param userId 用户 ID
     * @return 启用结果
     */
    Result<UserInfo> enableUser(String userId);

    /**
     * 禁用用户
     * @param userId 用户 ID
     * @return 禁用结果
     */
    Result<UserInfo> disableUser(String userId);

    /**
     * 获取权限列表
     * @return 权限列表
     */
    Result<List<PermissionsData>> getPermissions();

    /**
     * 保存权限设置
     * @param permissions 权限设置
     * @return 保存结果
     */
    Result<PermissionsData> savePermissions(Map<String, Object> permissions);

    /**
     * 获取安全日志
     * @return 安全日志
     */
    Result<SecurityLogsResult> getSecurityLogs();

    // ==================== 健康�?查模�? ====================

    /**
     * 运行健康�?�?
     * @param params �?查参�?
     * @return �?查结�?
     */
    Result<HealthCheckResult> runHealthCheck(Map<String, Object> params);

    /**
     * 导出健康报告
     * @return 导出结果
     */
    Result<HealthReport> exportHealthReport();

    /**
     * 设置定时健康�?�?
     * @param params 定时参数
     * @return 设置结果
     */
    Result<HealthCheckSchedule> scheduleHealthCheck(Map<String, Object> params);

    /**
     * �?查服务状�?
     * @param serviceName 服务名称
     * @return 服务状�??
     */
    Result<ServiceCheckResult> checkService(String serviceName);

    // ==================== 日志管理模块 ====================

    /**
     * 获取日志列表
     * @param params 查询参数
     * @return 日志列表
     */
    Result<List<LogEntry>> getLogs(Map<String, Object> params);

    /**
     * 刷新日志
     * @return 刷新结果
     */
    Result<List<LogEntry>> refreshLogs();

    /**
     * 导出日志
     * @param params 导出参数
     * @return 导出结果
     */
    Result<LogExportResult> exportLogs(Map<String, Object> params);

    /**
     * 清空日志
     * @return 清空结果
     */
    Result<Void> clearLogs();

    /**
     * 过滤日志
     * @param filters 过滤条件
     * @return 过滤结果
     */
    Result<List<LogEntry>> filterLogs(Map<String, Object> filters);

    /**
     * 获取日志详情
     * @param logId 日志 ID
     * @return 日志详情
     */
    Result<LogEntry> getLogDetails(String logId);

    // ==================== 配置管理模块 ====================

    /**
     * 获取配置列表
     * @return 配置列表
     */
    Result<ConfigsResult> getConfigs();

    /**
     * 获取系统配置
     * @return 系统配置
     */
    Result<SystemConfig> getSystemConfig();

    /**
     * 获取网络配置
     * @return 网络配置
     */
    Result<NetworkConfig> getNetworkConfig();

    /**
     * 获取终端配置
     * @return 终端配置
     */
    Result<TerminalConfig> getTerminalConfig();

    /**
     * 获取服务配置
     * @return 服务配置
     */
    Result<ServiceConfig> getServiceConfig();

    /**
     * 保存配置
     * @param configData 配置数据
     * @return 保存结果
     */
    Result<ConfigDataResult> saveConfigData(Map<String, Object> configData);

    /**
     * 导出配置
     * @param params 导出参数
     * @return 导出结果
     */
    Result<ConfigExportResult> exportConfig(Map<String, Object> params);

    /**
     * 导入配置
     * @param params 导入参数
     * @return 导入结果
     */
    Result<ConfigImportResult> importConfig(Map<String, Object> params);

    /**
     * 重置配置
     * @return 重置结果
     */
    Result<ConfigResetResult> resetConfigData();

    /**
     * 获取配置历史
     * @param params 查询参数
     * @return 配置历史
     */
    Result<ConfigHistoryItemsResult> getConfigHistory(Map<String, Object> params);

    /**
     * 应用配置历史
     * @param historyId 历史 ID
     * @return 应用结果
     */
    Result<ConfigHistoryResult> applyConfigHistory(String historyId);

    // ==================== 协议管理模块 ====================

    /**
     * 获取协议处理器列�?
     * @param params 查询参数
     * @return 协议处理器列�?
     */
    Result<List<ProtocolHandlerData>> getProtocolHandlers(Map<String, Object> params);

    /**
     * 注册协议处理�?
     * @param handlerData 处理器数�?
     * @return 注册结果
     */
    Result<ProtocolHandlerData> registerProtocolHandler(Map<String, Object> handlerData);

    /**
     * 移除协议处理�?
     * @param commandType 命令类型
     * @return 移除结果
     */
    Result<ProtocolHandlerData> removeProtocolHandler(String commandType);

    /**
     * 处理协议命令
     * @param commandData 命令数据
     * @return 处理结果
     */
    Result<ProtocolHandlerData> handleProtocolCommand(Map<String, Object> commandData);

    /**
     * 刷新协议处理器列�?
     * @return 刷新结果
     */
    Result<List<ProtocolHandlerData>> refreshProtocolHandlers();

    /**
     * 搜索协议处理�?
     * @param params 搜索参数
     * @return 搜索结果
     */
    Result<List<ProtocolHandlerData>> searchProtocolHandlers(Map<String, Object> params);

    // ==================== 通用方法 ====================

    /**
     * 格式化时间戳
     * @param timestamp 时间�?
     * @return 格式化后的时间字符串
     */
    String formatTimestamp(long timestamp);

    /**
     * 格式化数�?
     * @param number 数字
     * @return 格式化后的数字字符串
     */
    String formatNumber(long number);

    /**
     * 验证 API 响应
     * @param response API 响应
     * @return 验证后的响应
     */
    Map<String, Object> validateApiResponse(Map<String, Object> response);

    /**
     * 获取网络设备列表
     * @return 网络设备列表
     */
    Result<List<net.ooder.nexus.domain.network.model.NetworkDevice>> getNetworkDevices();
}