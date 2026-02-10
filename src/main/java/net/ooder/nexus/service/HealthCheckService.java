package net.ooder.nexus.service;

import net.ooder.nexus.model.Result;
import net.ooder.nexus.domain.system.model.HealthCheckResult;
import net.ooder.nexus.domain.system.model.HealthReport;
import net.ooder.nexus.domain.system.model.HealthCheckSchedule;
import net.ooder.nexus.domain.system.model.ServiceCheckResult;

import java.util.Map;

/**
 * 健康检查服务接口
 * 提供系统健康检查、报告导出、定时检查调度等功能
 */
public interface HealthCheckService {

    /**
     * 运行健康检查
     */
    Result<HealthCheckResult> runHealthCheck(Map<String, Object> params);

    /**
     * 导出健康报告
     */
    Result<HealthReport> exportHealthReport();

    /**
     * 调度健康检查
     */
    Result<HealthCheckSchedule> scheduleHealthCheck(Map<String, Object> params);

    /**
     * 检查指定服务
     */
    Result<ServiceCheckResult> checkService(String serviceName);
}
