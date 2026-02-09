package net.ooder.nexus.model.protocol;

import java.io.Serializable;
import java.util.Map;

/**
 * 命令数据包
 * 南向协议命令的标准格式
 */
public class CommandPacket implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 命令头部信息
     */
    private CommandHeader header;

    /**
     * 命令负载数据
     */
    private Map<String, Object> payload;

    /**
     * 数字签名
     */
    private CommandSignature signature;

    public CommandPacket() {
    }

    public CommandPacket(CommandHeader header, Map<String, Object> payload) {
        this.header = header;
        this.payload = payload;
    }

    public CommandHeader getHeader() {
        return header;
    }

    public void setHeader(CommandHeader header) {
        this.header = header;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public CommandSignature getSignature() {
        return signature;
    }

    public void setSignature(CommandSignature signature) {
        this.signature = signature;
    }

    /**
     * 获取协议类型
     */
    public String getProtocolType() {
        return header != null ? header.getProtocolType() : null;
    }

    /**
     * 获取命令类型
     */
    public String getCommandType() {
        return header != null ? header.getCommandType() : null;
    }

    @Override
    public String toString() {
        return "CommandPacket{" +
                "header=" + header +
                ", payload=" + payload +
                ", signature=" + signature +
                '}';
    }
}
