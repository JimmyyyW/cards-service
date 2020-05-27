package com.fdm0506.stockify.cardservice.service;

import org.bson.internal.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Service
public class EncryptionService {

    private static final String ALGO = "AES";
    private Key secretKey;

    @Value("encryption.key") private String key;

    public EncryptionService() {
        if (this.key == null) {
            this.key = "defaultKey";
        }
        initialiseSecretKey();
    }

    public String encrypt(String data) {
        try {
            return this.encrypt(data, this.secretKey);
        } catch (Exception e) {
            return null;
        }
    }

    public String decrypt(String encryptedData) {
        try {
            return this.decrypt(encryptedData, this.secretKey);
        } catch (Exception e) {
            return null;

        }
    }

    private String encrypt(String data, Key key) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException {
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptVal = c.doFinal(data.getBytes());
        return Base64.encode(encryptVal);
    }

    private String decrypt(String encryptedData, Key key) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException {
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.decode(encryptedData);
        byte[] decryptedValue = c.doFinal(decodedValue);
        return new String(decryptedValue);
    }

    void initialiseSecretKey() {
        byte[] keyValue;
        try {
            keyValue = this.key.getBytes();
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            keyValue = sha.digest(keyValue);
            keyValue = Arrays.copyOf(keyValue, 16);
            this.secretKey = new SecretKeySpec(keyValue, ALGO);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
