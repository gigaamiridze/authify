package com.cyber.authify.configuration.security;

import com.cyber.authify.model.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtConfiguration {

    @Autowired
    private JwtProperties jwtProperties;

    @Bean
    public KeyStore keyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance(jwtProperties.getKeyStoreType());
            InputStream inputStream = new ClassPathResource(jwtProperties.getKeyStorePath()).getInputStream();
            keyStore.load(inputStream, jwtProperties.getKeyStorePassword().toCharArray());
            return keyStore;
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException ex) {
            throw new IllegalStateException("Failed to load keystore", ex);
        }
    }

    @Bean
    public RSAPrivateKey jwtSigningKey(KeyStore keyStore) {
        try {
            Key key = keyStore.getKey(jwtProperties.getKeyAlias(), jwtProperties.getPrivateKeyPassphrase().toCharArray());
            if (key instanceof RSAPrivateKey rsaPrivateKey) {
                return rsaPrivateKey;
            }

            throw new IllegalStateException("Configured key is not an RSA private key");
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            throw new IllegalStateException("Failed to load RSA private key", ex);
        }
    }

    @Bean
    public RSAPublicKey jwtValidationKey(KeyStore keyStore) {
        try {
            Certificate certificate = keyStore.getCertificate(jwtProperties.getKeyAlias());
            PublicKey publicKey = certificate.getPublicKey();
            if (publicKey instanceof RSAPublicKey rsaPublicKey) {
                return rsaPublicKey;
            }

            throw new IllegalStateException("Configured key is not an RSA public key");
        } catch (KeyStoreException ex) {
            throw new IllegalStateException("Failed to load RSA public key", ex);
        }
    }
}
