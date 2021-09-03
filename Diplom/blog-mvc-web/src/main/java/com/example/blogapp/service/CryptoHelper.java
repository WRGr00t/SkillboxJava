package com.example.blogapp.service;

import javax.crypto.*;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class CryptoHelper {
    private static KeyGenerator keygen;
    private static Cipher cipher;
    static {
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            keygen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }
    private static final Key key = keygen.generateKey();

    public CryptoHelper() {
        keygen.init(256);
    }

    public static String encrypt(String pass) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(pass.getBytes());
        return DatatypeConverter.printHexBinary(encrypted);
    }


    public String decrypt(String secret) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] data = DatatypeConverter.parseHexBinary(secret);
        return new String(cipher.doFinal(data));
    }
}
