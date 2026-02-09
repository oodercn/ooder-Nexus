package net.ooder.nexus;

import net.ooder.nexus.core.skill.NexusSkill;
import net.ooder.nexus.core.skill.impl.NexusSkillImpl;
import net.ooder.sdk.agent.model.AgentConfig;
import net.ooder.sdk.AgentSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Nexus主应用类
 */
public class NexusApplication {
    
    private static final Logger log = LoggerFactory.getLogger(NexusApplication.class);
    
    public static void main(String[] args) {
        log.info("Starting Independent Nexus...");
        
        try {
            // 1. 创建Agent配置
            AgentConfig config = AgentConfig.builder()
                    .agentId("nexus-001")
                    .agentName("Independent Nexus")
                    .agentType("nexus")
                    .endpoint("localhost:9876")
                    .udpPort(9876)
                    .heartbeatInterval(30000)
                    .build();
            
            // 2. 创建AgentSDK实例
            AgentSDK agentSDK = new AgentSDK(config);
            
            // 3. 创建并初始化Nexus�?�?
            NexusSkill nexusSkill = new NexusSkillImpl();
            nexusSkill.initialize(agentSDK);
            
            // 4. 启动�?能和Agent SDK
            agentSDK.start();
            nexusSkill.start();
            
            log.info("Independent Nexus started successfully!");
            
            // 5. 保持应用运行
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                log.info("Shutting down Independent Nexus...");
                nexusSkill.stop();
                agentSDK.stop();
                log.info("Independent Nexus stopped successfully!");
            }));
            
            // 主线程等待，防止应用�?�?
            synchronized (NexusApplication.class) {
                NexusApplication.class.wait();
            }
            
        } catch (Exception e) {
            log.error("Failed to start Independent Nexus: {}", e.getMessage(), e);
            System.exit(1);
        }
    }
}