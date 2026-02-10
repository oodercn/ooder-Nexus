package net.ooder.nexus.config;

import net.ooder.nexus.core.skill.NexusSkill;
import net.ooder.nexus.core.skill.impl.NexusSkillImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import net.ooder.sdk.agent.model.AgentConfig;
import net.ooder.sdk.AgentSDK;
import net.ooder.nexus.infrastructure.management.NexusManager;
import net.ooder.nexus.infrastructure.management.NexusManagerImpl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 应用配置类
 */
@Configuration
public class AppConfig {

    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Value("${ooder.agent.id}")
    private String agentId;

    @Value("${ooder.agent.name}")
    private String agentName;

    @Value("${ooder.agent.type}")
    private String agentType;

    @Value("${ooder.udp.port}")
    private int udpPort;

    @Value("${ooder.heartbeat.interval}")
    private long heartbeatInterval;

    @Value("${ooder.heartbeat.timeout}")
    private long heartbeatTimeout;

    /**
     * 检测当前系统是否为 OpenWrt
     * @return true 如果是 OpenWrt 系统
     */
    private boolean isOpenWrt() {
        try {
            // 方法1: 检查 /etc/openwrt_release 文件
            if (Files.exists(Paths.get("/etc/openwrt_release"))) {
                log.info("检测到 OpenWrt 系统: /etc/openwrt_release 文件存在");
                return true;
            }

            // 方法2: 检查 /etc/os-release 文件内容
            if (Files.exists(Paths.get("/etc/os-release"))) {
                String content = new String(Files.readAllBytes(Paths.get("/etc/os-release")));
                if (content.toLowerCase().contains("openwrt")) {
                    log.info("检测到 OpenWrt 系统: /etc/os-release 包含 openwrt");
                    return true;
                }
            }

            // 方法3: 检查 opkg 命令是否存在
            if (Files.exists(Paths.get("/bin/opkg")) || Files.exists(Paths.get("/usr/bin/opkg"))) {
                log.info("检测到 OpenWrt 系统: opkg 命令存在");
                return true;
            }

            // 方法4: 检查 uci 命令是否存在
            if (Files.exists(Paths.get("/bin/uci")) || Files.exists(Paths.get("/usr/bin/uci"))) {
                log.info("检测到 OpenWrt 系统: uci 命令存在");
                return true;
            }

            return false;
        } catch (Exception e) {
            log.warn("检测 OpenWrt 系统时出错: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 配置AgentSDK
     */
    @Bean
    public AgentSDK agentSDK() {
        try {
            // 检测是否为 OpenWrt 系统
            boolean isOpenWrt = isOpenWrt();

            // 如果是 OpenWrt 系统，自动设置为 routeAgent 角色
            String finalAgentType = agentType;
            if (isOpenWrt && !"routeAgent".equals(agentType)) {
                finalAgentType = "routeAgent";
                log.info("OpenWrt 系统检测到，自动将 Agent 角色设置为 'routeAgent'");
                log.info("原始配置 agent.type: {}，已覆盖为: routeAgent", agentType);
            }

            // 创建Agent配置
            AgentConfig config = AgentConfig.builder()
                    .agentId(agentId)
                    .agentName(agentName)
                    .agentType(finalAgentType)
                    .endpoint("localhost:" + udpPort)
                    .udpPort(udpPort)
                    .heartbeatInterval(heartbeatInterval)
                    .build();

            log.info("Agent 配置完成: id={}, name={}, type={}", agentId, agentName, finalAgentType);

            // 初始化AgentSDK
            AgentSDK sdk = new AgentSDK(config);

            return sdk;
        } catch (Exception e) {
            // 捕获异常，返回null，避免Spring Boot上下文初始化失败
            // 在实际使用时，需要确保相关代码能处理null情况
            log.error("初始化 AgentSDK 失败: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 配置NexusSkill
     */
    @Bean
    public NexusSkill nexusSkill(AgentSDK agentSDK) {
        try {
            NexusSkill skill = new NexusSkillImpl();
            if (agentSDK != null) {
                skill.initialize(agentSDK);
            }
            return skill;
        } catch (Exception e) {
            // 捕获异常，返回null，避免Spring Boot上下文初始化失败
            return null;
        }
    }
    
    /**
     * 配置NexusManager
     */
    @Bean
    public NexusManager nexusManager(AgentSDK agentSDK) {
        try {
            NexusManager manager = new NexusManagerImpl();
            if (agentSDK != null) {
                manager.initialize(agentSDK);
            }
            return manager;
        } catch (Exception e) {
            // 捕获异常，返回null，避免Spring Boot上下文初始化失败
            return null;
        }
    }
}
