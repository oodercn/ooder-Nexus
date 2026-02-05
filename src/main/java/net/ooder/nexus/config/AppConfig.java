package net.ooder.nexus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import net.ooder.sdk.agent.model.AgentConfig;
import net.ooder.sdk.AgentSDK;
import net.ooder.nexus.skill.NexusSkill;
import net.ooder.nexus.skill.impl.NexusSkillImpl;
import net.ooder.nexus.management.NexusManager;
import net.ooder.nexus.management.impl.NexusManagerImpl;

/**
 * 应用配置类
 */
@Configuration
public class AppConfig {
    
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
     * 配置AgentSDK
     */
    @Bean
    public AgentSDK agentSDK() {
        try {
            // 创建Agent配置
            AgentConfig config = AgentConfig.builder()
                    .agentId(agentId)
                    .agentName(agentName)
                    .agentType(agentType)
                    .endpoint("localhost:" + udpPort)
                    .udpPort(udpPort)
                    .heartbeatInterval(heartbeatInterval)
                    .build();
            
            // 初始化AgentSDK
            AgentSDK sdk = new AgentSDK(config);
            
            return sdk;
        } catch (Exception e) {
            // 捕获异常，返回null，避免Spring Boot上下文初始化失败
            // 在实际使用时，需要确保相关代码能处理null情况
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