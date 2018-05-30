package com.ge.digital.spo.security.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * 加密工具�?
 * 
 * md5加密出来的长度是32�?
 * 
 * sha加密出来的长度是40�?
 * 
 * @author z003makd
 * 
 */
public class Encrypt {
	private Encrypt(){
		
	}
	private final static Logger logger = Logger.getLogger(Encrypt.class);
	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// md5加密测试
		String md5_1 = md5("123456");
		logger.debug(md5_1 + "\n" );
		// sha加密测试
		String sha_1 = sha("123456");
		String sha_2 = sha("孙宇");
//		logger.debug(sha_1 + "\n" + sha_2);

	}

	/**
	 * 加密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String e(String inputText) {
		return md5(inputText);
	}

	/**
	 * 二次加密，应该破解不了了吧？
	 * 
	 * @param inputText
	 * @return
	 */
	public static String md5AndSha(String inputText) {
		return sha(md5(inputText));
	}

	/**
	 * md5加密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String md5(String inputText) {
		return encrypt(inputText, "md5");
	}

	/**
	 * sha加密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String sha(String inputText) {
		return encrypt(inputText, "sha-1");
	}

	/**
	 * md5或�?�sha-1加密
	 * 
	 * @param inputText
	 *            要加密的内容
	 * @param algorithmName
	 *            加密算法名称：md5或�?�sha-1，不区分大小�?
	 * @return
	 */
	private static String encrypt(String inputText, String algorithmName) {
		String algorithemNameT = algorithmName;
		if (inputText == null || "".equals(inputText.trim())) {
			throw new IllegalArgumentException("请输入要加密的内�?");
		}
		if (algorithmName == null || "".equals(algorithmName.trim())) {
			algorithemNameT = "md5";
		}
		String encryptText = null;
		try {
			MessageDigest m = MessageDigest.getInstance(algorithemNameT);
			m.update(inputText.getBytes("UTF8"));
			byte s[] = m.digest();
			return hex(s);
		} catch (NoSuchAlgorithmException|UnsupportedEncodingException e) {
			logger.error("Error!:", e); 
		} 
		return encryptText;
	}

	/**
	 * 返回十六进制字符�?
	 * 
	 * @param arr
	 * @return
	 */
	private static String hex(byte[] arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

}
