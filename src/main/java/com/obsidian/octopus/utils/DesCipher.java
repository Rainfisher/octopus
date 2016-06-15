package com.obsidian.octopus.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Alex Chou
 */
public class DesCipher {

    public static byte[] encrypt(String key, byte[] data)
            throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException,
            InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {
        return encrypt(key.getBytes(), data);
    }

    public static byte[] encrypt(byte[] key, byte[] data)
            throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException,
            InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {
        return encrypt(key, data, 0, data.length);
    }

    public static byte[] encrypt(byte[] key, byte[] data, int offset, int length)
            throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException,
            InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        IvParameterSpec iv = new IvParameterSpec(key);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(data, offset, length);
    }

    public static byte[] decrypt(String key, byte[] data)
            throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException,
            InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(key.getBytes(), data);
    }

    public static byte[] decrypt(byte[] key, byte[] data)
            throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException,
            InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        IvParameterSpec iv = new IvParameterSpec(key);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        return cipher.doFinal(data);
    }

    public static String encrypt2Base64(String key, Serializable instance)
            throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException,
            InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IOException {
        byte[] encryptRaw;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
                objectOutputStream.writeObject(instance);
            }
            encryptRaw = encrypt(key.getBytes(), outputStream.toByteArray(), 0, outputStream.size());
        }
        return Base64.encodeBase64String(encryptRaw);
    }

    public static Object decryptByBase64(String key, String base64String)
            throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException,
            InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IOException, ClassNotFoundException {
        byte[] encryptRaw = Base64.decodeBase64(base64String);
        byte[] decryptRaw = decrypt(key.getBytes(), encryptRaw);
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(decryptRaw)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                return objectInputStream.readObject();
            }
        }
    }
}
