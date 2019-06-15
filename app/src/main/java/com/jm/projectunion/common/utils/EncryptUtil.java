package com.jm.projectunion.common.utils;


import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import static javax.crypto.Cipher.getInstance;

/**
 * 描述 ：加密签名相关工具
 * <p/>
 * 作者 ：hujianqiang
 * <p/>
 * 创建日期 ：2013-11-11
 */
public class EncryptUtil {

    final static String MD5 = "MD5";
    final static String SHA1 = "SHA-1";
    final static String ENC_UTF8 = "UTF-8";
    public static final String KEY_PWD_ENCRY = "b760929ae4e64f8b98166454";

    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            md.reset();
            byte[] res = md.digest(str.getBytes(ENC_UTF8));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < res.length; i++) {
                String s = Integer.toHexString(res[i] & 0xff);
                if (s.length() == 1)
                    sb.append("0").append(s);
                else
                    sb.append(s);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException:" + MD5, e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException:" + ENC_UTF8, e);
        }
    }

    public static String getSHA1(String str) {
        try {
            MessageDigest sha1MD = MessageDigest.getInstance(SHA1);
            sha1MD.update(str.getBytes(ENC_UTF8), 0, str.length());
            byte[] bytes = sha1MD.digest();
            StringBuffer sBuffer = new StringBuffer();
            for (byte hash : bytes) {
                sBuffer.append(Integer.toString((hash & 0xFF) + 0x100, 16).substring(1));
            }
            return sBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException:" + SHA1, e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException:" + ENC_UTF8, e);
        }
    }

    @NonNull
    public static String getEncryIV(String curTime) {
        String encTime = EncryptUtil.encrypt3desCbc("cdacb5d3a701f29d7ecb1d5e", "5f96b4ed", curTime);
        StringBuilder ivSb = new StringBuilder();
        for (int i = 13; i < encTime.length(); i++) {
            int j = i + 1;
            if (j % 2 == 0)
                ivSb.append(String.valueOf(encTime.charAt(i)));
            if (ivSb.length() >= 8) break;
        }
        String iv = ivSb.toString();
        return iv;
    }

    @NonNull
    public static String getEncryPwd(String pwd, String iv) {
        String encPwd = EncryptUtil.encrypt3desCbc(KEY_PWD_ENCRY, iv, pwd);
        return encPwd;
    }

    /**
     * 字符串加密
     *
     * @param encStr 加密密码
     * @param encKey 密钥（解密的时候密匙要与加密时的密匙相同才能解密出加密过的字符串）
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String encrypt(String encStr, String encKey) {
        // KeyGenerator keyGenerator =
        // KeyGenerator.getInstance("PBEWithMD5AndDES");
        byte passEn[] = new byte[0];
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            KeySpec keySpec = new PBEKeySpec(encKey.toCharArray());
            SecretKey secretKey = keyFactory.generateSecret(keySpec);
            PBEParameterSpec parameterSpec = new PBEParameterSpec(new byte[]{1, 2, 3, 4,
                    5, 6, 7, 8}, 1000);
            Cipher cipher = getInstance("PBEWithMD5AndDES");
            cipher.init(ENCRYPT_MODE, secretKey, parameterSpec);
            passEn = cipher.doFinal(encStr.getBytes());
            return bytesToHexString(passEn);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串解密
     *
     * @param deEncStr 加密后的密码
     * @param deEncKey 密钥（要与加密时的密匙相同才能解密出加密过的字符串）
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws Exception
     */
    public static String decrypt(String deEncStr, String deEncKey) {
        byte[] passDec = new byte[0];
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            KeySpec keySpec = new PBEKeySpec(deEncKey.toCharArray());
            SecretKey secretKey = keyFactory.generateSecret(keySpec);
            PBEParameterSpec parameterSpec = new PBEParameterSpec(new byte[]{1, 2, 3, 4,
                    5, 6, 7, 8}, 1000);
            Cipher cipher = getInstance("PBEWithMD5AndDES");
            cipher.init(DECRYPT_MODE, secretKey, parameterSpec);
            passDec = cipher.doFinal(hexStringToBytes(deEncStr));
            return new String(passDec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    //--------------------------------3DES加密-----------------------------//

    public static String encrypt3desEcb(String keySrc, String data) {
        try {
            byte[] key = keySrc.getBytes("UTF-8");
            byte[] plainText = data.getBytes("UTF-8");

            SecretKey secretKey = new SecretKeySpec(key, "DESede");
            //encrypt
            Cipher cipher = getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(plainText);
            String encHexString = bytesToHexString(encryptedData);
//            System.out.println("encrypt_3des_ecb: " + encHexString);

            return encHexString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String decrypt3desEcb(String keySrc, String data) {
        try {
            byte[] key = keySrc.getBytes("UTF-8");
            byte[] encHexString = data.getBytes("UTF-8");

            SecretKey secretKey = new SecretKeySpec(key, "DESede");
            Cipher cipher = getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(DECRYPT_MODE, secretKey);
            byte[] decryptDataArr = cipher.doFinal(encHexString);

            String decDataTxt = new String(decryptDataArr);
//            System.out.println("encrypt_3des_ecb: " + decDataTxt);

            return decDataTxt;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String encrypt3desCbc(String keySrc, String ivSrc, String data) {
        try {
            byte[] key = keySrc.getBytes("UTF-8");
            byte[] plainText = data.getBytes("UTF-8");
            IvParameterSpec iv = new IvParameterSpec(ivSrc.getBytes());

            SecretKey secretKey = new SecretKeySpec(key, "DESede");
            Cipher cipher = getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(ENCRYPT_MODE, secretKey, iv);
            byte[] encryptedData = cipher.doFinal(plainText);
            String encHexString = bytesToHexString(encryptedData);
//            System.out.println("encrypt_3des_ecb: " + encHexString);

            return encHexString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt3desCbc(String keySrc, String ivSrc, String data) {
        byte[] key = keySrc.getBytes();
        IvParameterSpec iv = new IvParameterSpec(ivSrc.getBytes());
        SecretKey secretKey = new SecretKeySpec(key, "DESede");
        try {
            Cipher cipher = getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(DECRYPT_MODE, secretKey, iv);
            byte[] decryptPlainText = cipher.doFinal(data.getBytes());
//            System.out.println("decryptPlainText:" + new String(decryptPlainText));
            return decryptPlainText;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回可逆算法DES的密钥
     *
     * @param key 前8字节将被用来生成密钥。
     * @return 生成的密钥
     * @throws Exception
     */
    public static Key getDESKey(byte[] key) throws Exception {
        DESKeySpec des = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        return keyFactory.generateSecret(des);
    }

    //--------------------------------通用加密方法-----------------------------//

    /**
     * 根据指定的密钥及算法，将字符串进行解密。
     *
     * @param data      要进行解密的数据，它是由原来的byte[]数组转化为字符串的结果。
     * @param key       密钥。
     * @param algorithm 算法。
     * @return 解密后的结果。它由解密后的byte[]重新创建为String对象。如果解密失败，将返回null。
     * @throws Exception
     */
    public static String decrypt(String data, Key key, String algorithm)
            throws Exception {
        Cipher cipher = getInstance(algorithm);
        cipher.init(DECRYPT_MODE, key);
        String result = new String(cipher.doFinal(StringUtils
                .hexStringToByteArray(data)), "utf8");
        return result;
    }

    /**
     * 根据指定的密钥及算法对指定字符串进行可逆加密。
     *
     * @param data      要进行加密的字符串。
     * @param key       密钥。
     * @param algorithm 算法。
     * @return 加密后的结果将由byte[]数组转换为16进制表示的数组。如果加密过程失败，将返回null。
     */
    public static String encrypt(String data, Key key, String algorithm)
            throws Exception {
        Cipher cipher = getInstance(algorithm);
        cipher.init(ENCRYPT_MODE, key);
        return StringUtils.byteArrayToHexString(cipher.doFinal(data
                .getBytes("utf8")));
    }

}
