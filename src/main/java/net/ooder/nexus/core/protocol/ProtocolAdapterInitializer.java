package net.ooder.nexus.core.protocol;

import net.ooder.nexus.core.protocol.adapter.EndProtocolAdapter;
import net.ooder.nexus.core.protocol.adapter.McpProtocolAdapter;
import net.ooder.nexus.core.protocol.adapter.RouteProtocolAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 协议适配器初始化�?
 * 在应用启动时自动注册�?有协议�?�配�?
 */
@Component
public class ProtocolAdapterInitializer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ProtocolAdapterInitializer.class);

    @Autowired
    private ProtocolHub protocolHub;

    @Autowired
    private McpProtocolAdapter mcpProtocolAdapter;

    @Autowired
    private RouteProtocolAdapter routeProtocolAdapter;

    @Autowired
    private EndProtocolAdapter endProtocolAdapter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Initializing protocol adapters...");

        // 注册MCP协议适配�?
        try {
            protocolHub.registerProtocolHandler(McpProtocolAdapter.PROTOCOL_TYPE, mcpProtocolAdapter);
            logger.info("MCP protocol adapter registered successfully");
        } catch (Exception e) {
            logger.error("Failed to register MCP protocol adapter", e);
        }

        // 注册Route协议适配�?
        try {
            protocolHub.registerProtocolHandler(RouteProtocolAdapter.PROTOCOL_TYPE, routeProtocolAdapter);
            logger.info("Route protocol adapter registered successfully");
        } catch (Exception e) {
            logger.error("Failed to register Route protocol adapter", e);
        }

        // 注册End协议适配�?
        try {
            protocolHub.registerProtocolHandler(EndProtocolAdapter.PROTOCOL_TYPE, endProtocolAdapter);
            logger.info("End protocol adapter registered successfully");
        } catch (Exception e) {
            logger.error("Failed to register End protocol adapter", e);
        }

        logger.info("Protocol adapters initialization completed. Registered protocols: {}",
                protocolHub.getSupportedProtocols());
    }
}
