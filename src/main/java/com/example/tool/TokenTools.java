package com.example.tool;

import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

@Slf4j
public class TokenTools {
	private static final String DESkey = "test-78@hj";// 设置密钥，略去
	private static final byte[] DESIV = { 0x12, 0x34, 0x56, 0x78, (byte) 0x90,
			(byte) 0xAB, (byte) 0xCD, (byte) 0xEF };// 设置向量，略去

	private static AlgorithmParameterSpec iv = null;// 加密算法的参数接口，IvParameterSpec是它的一个实现
	private static Key key = null;
	static {
		try {
			init();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static void init() throws Exception {
		init(TokenTools.DESkey);
	}

	public static void init(final String strkey) throws Exception {
		DESKeySpec keySpec = new DESKeySpec(strkey.getBytes("UTF-8"));
		iv = new IvParameterSpec(DESIV);// 设置向量
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
		key = keyFactory.generateSecret(keySpec);// 得到密钥对象
	}

	public static String encode(String data) {
		String result = "";
		try {
			Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得=到加密对象Cipher
			enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向量
			byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
			Base64Helper base64Helper = new Base64Helper();
			result = base64Helper.byteArrayToBase64(pasByte);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public static String decode(String data) throws Exception {
		String result = "";
		try {
			Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			deCipher.init(Cipher.DECRYPT_MODE, key, iv);
			Base64Helper base64Helper = new Base64Helper();
			byte[] pasByte = deCipher.doFinal(base64Helper
					.base64ToByteArray(data));
			result = new String(pasByte, "UTF-8");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			// 生成商户编码 格式商户id+当前时间
			String test = System.currentTimeMillis() + "-33000000";
			test = System.currentTimeMillis() + "-1665";
			System.out.println("加密前的字符：" + test);
			test = TokenTools.encode(test);
			System.out.println("加密后的字符：" + test);
			System.out.println("解密后的字符1：" + TokenTools.decode(test));
			System.out.println("解密后的字符2：" + TokenTools.decode(test));
			System.out.println("解密后的字符3：" + getFirst(test));
			System.out.println("解密后的字符4：" + getSecond(test));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static String getFirst(String token) {
		String[] ss = null;
		try {
			String a = TokenTools.decode(token);
			ss = a.split("-");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(), e);
		}

		return ss[0];
	}

	public static String getSecond(String token) {
		String[] ss = null;
		try {
			String a = TokenTools.decode(token);
			ss = a.split("-");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(), e);
		}

		return ss[1];
	}

	public static String getThird(String token) {
		String[] ss = null;
		try {
			String a = TokenTools.decode(token);
			ss = a.split("-");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(), e);
		}

		return ss[2];
	}

}
