package com.spiwer.androidstandard.util;

import android.util.Base64;
import android.util.Log;

import com.spiwer.androidstandard.exception.AppException;
import com.spiwer.androidstandard.lasting.EMessageStandard;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {

    private String key;
    private String initVector;
    private static final String UTF8 = "UTF-8";

    private EncryptionUtil() {
    }

    private EncryptionUtil(String key, String initVector) {
        this.key = key;
        this.initVector = initVector;
    }

    public static EncryptionUtil init() {
        return new EncryptionUtil();
    }

    public static EncryptionUtil init(String key, String initVector) {
        return new EncryptionUtil(key, initVector);
    }

    public String encrypt(String value)
            throws AppException {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(UTF8));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(UTF8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            Log.e("encrypt", ex.getMessage(), ex);
            throw new AppException(EMessageStandard.ERROR_ENCRYPTION_FAILED);
        }
    }

    public String decrypt(String encrypted)
            throws AppException {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(UTF8));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(UTF8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT));

            return new String(original);
        } catch (Exception ex) {
            Log.e("decrypt", ex.getMessage(), ex);
            throw new AppException(EMessageStandard.ERROR_DECIPHER_FAILED);
        }
    }

    public String sha256(String text)
            throws AppException {
        return encryptText("SHA-256", text);
    }

    public String md5(String text)
            throws AppException {
        return encryptText("MD5", text);
    }

    private String encryptText(String algorithm, String text)
            throws AppException {

        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] textEncrypt = digest.digest(text.getBytes(UTF8));
            StringBuilder textHexadecimal = new StringBuilder();
            for (byte b : textEncrypt) {
                textHexadecimal.append(String.format("%02X", b));
            }
            return textHexadecimal.toString();
        } catch (Exception ex) {
            Log.e("encryptText", ex.getMessage(), ex);
            throw new AppException(EMessageStandard.ERROR_ENCRYPTION_FAILED);
        }
    }

}
