package com.digitalchina.xa.it.util;



/**
 * 加密解密接口 
 * 
 * @author 徐江
 * @version 1.0
 */
public interface Encrypt {
	
	/**
	 * 将传进来的明文以AES算法进行加密
	 * 
	 * @param text
	 *            String
	 * @return String
	 */
	public String enCodeAES(String text) throws Exception;
	
	/**
	 * 将加密文本以AES算法进行解密；
	 * 
	 * @param encryptText
	 *            String
	 * @return String
	 */
	public String deCodeAES(String encryptText) throws Exception;

    /**
     *  将明文文本加密
     * @param text String
     * @return String
     */
    String encrypt(String text) throws Exception;

    /**
     * 将密文解密成明文；
     * @param encryptText String
     * @return String
     */
    String decrypt(String encryptText) throws Exception;
}
