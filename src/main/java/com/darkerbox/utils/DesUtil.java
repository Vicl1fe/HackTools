package com.darkerbox.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * @author yunfeng
 * @version V1.0
 * @date 2018-06-07 22:13
 */
public class DesUtil {

	private static final String ALGORITHM = "DES";
	/**
	 * 加密
	 *
	 * @param text byte[]
	 * @param key   String
	 * @return byte[]
	 */
	public static byte[] encrypt(byte[] text,byte[] key) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(key);
			//创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
			SecretKey securekey = keyFactory.generateSecret(desKey);
			//Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			//用密匙初始化Cipher对象,ENCRYPT_MODE用于将 Cipher 初始化为加密模式的常量
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			//现在，获取数据并加密 正式执行加密操作
			//按单部分操作加密或解密数据，或者结束一个多部分操作
			return cipher.doFinal(text);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 *
	 * @param encryptText      byte[]
	 * @param key String
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] encryptText,byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(key);
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);//返回实现指定转换的 Cipher 对象
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 真正开始解密操作
		return cipher.doFinal(encryptText);
	}

}
