package net.ooder.nexus.domain.security.model;

/**
 * 安全状�?�实体Bean
 * 
 * 用于表示系统的安全状态信�?
 * 
 * @version 1.0.0
 */
public class SecurityStatus {
    
    /**
     * 安全状�??
     */
    private String status;
    
    /**
     * 状�?�描�?
     */
    private String description;
    
    /**
     * 用户数量
     */
    private int userCount;
    
    /**
     * 活跃会话�?
     */
    private int activeSessions;
    
    /**
     * 认证状�??
     */
    private boolean authenticationEnabled;
    
    /**
     * 授权状�??
     */
    private boolean authorizationEnabled;
    
    /**
     * 加密状�??
     */
    private boolean encryptionEnabled;
    
    /**
     * 审计日志状�??
     */
    private boolean auditLoggingEnabled;
    
    /**
     * �?后更新时间戳
     */
    private long lastUpdated;
    
    /**
     * 构�?�方�?
     */
    public SecurityStatus() {
    }
    
    /**
     * 构�?�方�?
     * 
     * @param status 安全状�??
     * @param description 状�?�描�?
     * @param userCount 用户数量
     * @param activeSessions 活跃会话�?
     * @param authenticationEnabled 认证状�??
     * @param authorizationEnabled 授权状�??
     * @param encryptionEnabled 加密状�??
     * @param auditLoggingEnabled 审计日志状�??
     * @param lastUpdated �?后更新时间戳
     */
    public SecurityStatus(String status, String description, int userCount, int activeSessions, 
                         boolean authenticationEnabled, boolean authorizationEnabled, 
                         boolean encryptionEnabled, boolean auditLoggingEnabled, long lastUpdated) {
        this.status = status;
        this.description = description;
        this.userCount = userCount;
        this.activeSessions = activeSessions;
        this.authenticationEnabled = authenticationEnabled;
        this.authorizationEnabled = authorizationEnabled;
        this.encryptionEnabled = encryptionEnabled;
        this.auditLoggingEnabled = auditLoggingEnabled;
        this.lastUpdated = lastUpdated;
    }
    
    // Getters and Setters
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getUserCount() {
        return userCount;
    }
    
    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
    
    public int getActiveSessions() {
        return activeSessions;
    }
    
    public void setActiveSessions(int activeSessions) {
        this.activeSessions = activeSessions;
    }
    
    public boolean isAuthenticationEnabled() {
        return authenticationEnabled;
    }
    
    public void setAuthenticationEnabled(boolean authenticationEnabled) {
        this.authenticationEnabled = authenticationEnabled;
    }
    
    public boolean isAuthorizationEnabled() {
        return authorizationEnabled;
    }
    
    public void setAuthorizationEnabled(boolean authorizationEnabled) {
        this.authorizationEnabled = authorizationEnabled;
    }
    
    public boolean isEncryptionEnabled() {
        return encryptionEnabled;
    }
    
    public void setEncryptionEnabled(boolean encryptionEnabled) {
        this.encryptionEnabled = encryptionEnabled;
    }
    
    public boolean isAuditLoggingEnabled() {
        return auditLoggingEnabled;
    }
    
    public void setAuditLoggingEnabled(boolean auditLoggingEnabled) {
        this.auditLoggingEnabled = auditLoggingEnabled;
    }
    
    public long getLastUpdated() {
        return lastUpdated;
    }
    
    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}