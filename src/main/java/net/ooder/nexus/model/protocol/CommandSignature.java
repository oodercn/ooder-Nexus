package net.ooder.nexus.model.protocol;

import java.io.Serializable;

/**
 * 命令数字签名
 */
public class CommandSignature implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 签名算法
     */
    private String algorithm;

    /**
     * 签名值（Base64编码）
     */
    private String value;

    /**
     * 证书指纹
     */
    private String certificateFingerprint;

    public CommandSignature() {
    }

    public CommandSignature(String algorithm, String value) {
        this.algorithm = algorithm;
        this.value = value;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCertificateFingerprint() {
        return certificateFingerprint;
    }

    public void setCertificateFingerprint(String certificateFingerprint) {
        this.certificateFingerprint = certificateFingerprint;
    }

    @Override
    public String toString() {
        return "CommandSignature{" +
                "algorithm='" + algorithm + '\'' +
                ", value='" + (value != null ? value.substring(0, Math.min(20, value.length())) + "..." : null) + '\'' +
                ", certificateFingerprint='" + certificateFingerprint + '\'' +
                '}';
    }
}
