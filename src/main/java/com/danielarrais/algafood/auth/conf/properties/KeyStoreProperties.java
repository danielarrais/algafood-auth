package com.danielarrais.algafood.auth.conf.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("algafood.security.keystore")
public class KeyStoreProperties {
    private String fileUrl;
    private String storePass;
    private String pairAlias;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getStorePass() {
        return storePass;
    }

    public void setStorePass(String storePass) {
        this.storePass = storePass;
    }

    public String getPairAlias() {
        return pairAlias;
    }

    public void setPairAlias(String pairAlias) {
        this.pairAlias = pairAlias;
    }
}
