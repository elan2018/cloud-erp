package com.elan.cloud.erp.gateway.config;

import com.elan.common.utils.EncrypUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class EncrypConfig {

    private Logger logger = LoggerFactory.getLogger(EncrypConfig.class);

    @Value("${encry-tools.name:AES}")
    private String secretName;

    @Value("${encry-tools.len:128}")
    private int keyLength;

    @Value("${encry-tools.key:dolphin}")
    private String secretkey;

    /**
     * token加解密
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */
    @Bean("encryToken")
    public EncrypUtil createTokenEncryp() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return new EncrypUtil(secretName,secretkey,keyLength);
    }

}
