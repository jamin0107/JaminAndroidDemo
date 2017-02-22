package com.jamin.framework.util;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by jamin on 2017/2/22.
 */

public class AESUtil {

    public static final String DEFAULT_PASSWORD = "add8e13f7647f432442757ffe6d31bb3";
    public static final String IV = "1234567812345678";


    public static String decrypt(String plaintText) throws Exception {
        return decrypt2(plaintText ,IV.getBytes() , DEFAULT_PASSWORD);
    }

    private static String decrypt2(String encryptedData, byte[] initialVectorString, String secretKey) {
        String decryptedData = null;
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");

            IvParameterSpec initialVector = new IvParameterSpec(initialVectorString);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, initialVector);
            byte[] encryptedByteArray = Base64.decode(encryptedData.getBytes(), Base64.DEFAULT);
            byte[] decryptedByteArray = cipher.doFinal(encryptedByteArray);
            decryptedData = new String(decryptedByteArray, "UTF-8");
        } catch (Exception e) {
            LogUtil.d("error = " + e.getMessage());
        }
        return decryptedData;
    }

    public static String encrypt(String plaintText) throws Exception {
        return encrypt(plaintText ,IV.getBytes() , DEFAULT_PASSWORD);
    }


    public static String encrypt(String plainText, byte[] initialVectorString, String secretKey) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");

        IvParameterSpec initialVector = new IvParameterSpec(initialVectorString);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, initialVector);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encryptedText = new String(Base64.encode(encrypted,Base64.DEFAULT), "UTF-8");
        return encryptedText;
    }


}
