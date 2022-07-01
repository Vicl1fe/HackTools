package com.darkerbox.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @program: CSDN
 * @description: Aes工具类
 * @author: Alian
 * @create: 2021-06-05 09:20:10
 **/
public class AesUtils {

	private static final String ALGORITHM = "AES";
	public static String AES_CBC_PADDING = "AES/CBC/PKCS5Padding";//AES/CBC/PKCS7Padding
	public static String AES_ECB_PADDING = "AES/ECB/PKCS5Padding";//AES/ECB/PKCS7Padding

	/**
	 * 也可以通过这种方式获取密钥
	 *
	 * @param key
	 * @return
	 */
	private static SecretKey getSecretKey(byte[] key) {
		try {
			//获取指定的密钥生成器
			KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
			//加密强随机数
			SecureRandom secureRandom = new SecureRandom();
			secureRandom.setSeed(key);
			//这里可以是128、192、256、越大越安全
			keyGen.init(256, secureRandom);
			return keyGen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("AES获取密钥出现错误,算法异常");
		}
	}

	/**
	 * Aes加密(CBC工作模式)
	 *
	 * @param key   密钥,key长度必须大于等于 3*8 = 24,并且是8的倍数
	 * @param keyIv 初始化向量,keyIv长度必须等于16
	 * @param data  明文
	 * @return 密文
	 * @throws Exception
	 */
	public static byte[] encryptByCBC( byte[] data,byte[] key, byte[] keyIv)
			throws Exception {
		//获取SecretKey对象,也可以使用getSecretKey()方法
		Key secretKey = new SecretKeySpec(key, ALGORITHM);
		//获取指定转换的密码对象Cipher（参数：算法/工作模式/填充模式）
		Cipher cipher = Cipher.getInstance(AES_CBC_PADDING);
		//创建向量参数规范也就是初始化向量
		IvParameterSpec ips = new IvParameterSpec(keyIv);
		//用密钥和一组算法参数规范初始化此Cipher对象（加密模式）
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ips);
		//执行加密操作
		return cipher.doFinal(data);
	}

	/**
	 * Aes加密(ECB工作模式)
	 *
	 * @param key   密钥,key长度必须大于等于 3*8 = 24,并且是8的倍数
	 * @param keyIv 初始化向量,keyIv长度必须等于16
	 * @param data  密文
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] decryptByCBC(byte[] data,byte[] key, byte[] keyIv)
			throws Exception {
		//获取SecretKey对象,也可以使用getSecretKey()方法
		Key secretKey = new SecretKeySpec(key, ALGORITHM);
		//获取指定转换的密码对象Cipher（参数：算法/工作模式/填充模式）
		Cipher cipher = Cipher.getInstance(AES_CBC_PADDING);
		//创建向量参数规范也就是初始化向量
		IvParameterSpec ips = new IvParameterSpec(keyIv);
		//用密钥和一组算法参数规范初始化此Cipher对象（加密模式）
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ips);
		//执行加密操作
		return cipher.doFinal(data);
	}

	/**
	 * Aes加密(ECB工作模式),不要IV
	 *
	 * @param key  密钥,key长度必须大于等于 3*8 = 24,并且是8的倍数
	 * @param data 明文
	 * @return 密文
	 * @throws Exception
	 */
	public static byte[] encryptByECB(byte[] data,byte[] key) throws Exception {
		//获取SecretKey对象,也可以使用getSecretKey()方法
		SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
		//获取指定转换的密码对象Cipher（参数：算法/工作模式/填充模式）
		Cipher cipher = Cipher.getInstance(AES_ECB_PADDING);
		//用密钥和一组算法参数规范初始化此Cipher对象（加密模式）
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		//执行加密操作
		return cipher.doFinal(data);
	}

	/**
	 * Aes解密(ECB工作模式),不要IV
	 *
	 * @param key  密钥,key长度必须大于等于 3*8 = 24,并且是8的倍数
	 * @param data 密文
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] decryptByECB(byte[] data,byte[] key) throws Exception {
		//获取SecretKey对象,也可以使用getSecretKey()方法
		SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
		//获取指定转换的密码对象Cipher（参数：算法/工作模式/填充模式）
		Cipher cipher = Cipher.getInstance(AES_ECB_PADDING);
		//用密钥和一组算法参数规范初始化此Cipher对象（加密模式）
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		//执行加密操作
		return cipher.doFinal(data);
	}



}