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
 * ooderNexus 应用程序核心配置类
 *
 * <p>负责配置和初始化应用程序的核心组件，包括：</p>
 * <ul>
 *   <li>AgentSDK - P2P 网络通信核心</li>
 *   <li>NexusSkill - AI 技能管理</li>
 *   <li>NexusManager - 系统管理</li>
 * </ul>
 *
 * <p>特色功能：自动检测 OpenWrt 系统并配置为 routeAgent 角色</p>
 *
 * @author ooder Team
 * @version 2.0.0-openwrt-preview
 * @since 1.0.0
 * @see org.springframework.context.annotation.Configuration
 */
@Configuration
public class AppConfig {

    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    /** Agent 唯一标识 */
    @Value("${ooder.agent.id}")
    private String agentId;

    /** Agent 名称 */
    @Value("${ooder.agent.name}")
    private String agentName;

    /** Agent 类型（在 OpenWrt 系统上会自动覆盖为 routeAgent） */
    @Value("${ooder.agent.type}")
    private String agentType;

    /** UDP 通信端口 */
    @Value("${ooder.udp.port}")
    private int udpPort;

    /** 心跳检测间隔（毫秒） */
    @Value("${ooder.heartbeat.interval}")
    private long heartbeatInterval;

    /** 心跳检测超时时间（毫秒） */
    @Value("${ooder.heartbeat.timeout}")
    private long heartbeatTimeout;

    /**
     * 检测当前系统是否为 OpenWrt 系统
     *
     * <p>使用多种方式检测，提高准确性：</p>
     * <ol>
     *   <li>检查 /etc/openwrt_release 文件是否存在</li>
     *   <li>检查 /etc/os-release 文件内容是否包含 "openwrt"</li>
     *   <li>检查 opkg 包管理器命令是否存在</li>
     *   <li>检查 uci 配置工具命令是否存在</li>
     * </ol>
     *
     * @return true 如果检测到 OpenWrt 系统，否则返回 false
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
     * 配置并初始化 AgentSDK
     *
     * <p>AgentSDK 是 ooderNexus 的核心组件，负责 P2P 网络通信。</p>
     *
     * <p><strong>OpenWrt 自动适配：</strong></p>
     * <p>如果检测到 OpenWrt 系统，会自动将 Agent 角色设置为 "routeAgent"，
     * 以便更好地发挥路由器在网络中的核心作用。</p>
     *
     * @return 配置好的 AgentSDK 实例，如果初始化失败则返回 null
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
     * 配置并初始化 NexusSkill
     *
     * <p>NexusSkill 负责 AI 技能的管理，包括技能的发布、分享、执行等功能。</p>
     *
     * @param agentSDK AgentSDK 实例，用于 P2P 网络通信
     * @return 配置好的 NexusSkill 实例，如果初始化失败则返回 null
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
            log.error("初始化 NexusSkill 失败: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 配置并初始化 NexusManager
     *
     * <p>NexusManager 负责系统的整体管理，包括节点管理、网络配置等。</p>
     *
     * @param agentSDK AgentSDK 实例，用于 P2P 网络通信
     * @return 配置好的 NexusManager 实例，如果初始化失败则返回 null
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
            log.error("初始化 NexusManager 失败: {}", e.getMessage(), e);
            return null;
        }
    }
}
