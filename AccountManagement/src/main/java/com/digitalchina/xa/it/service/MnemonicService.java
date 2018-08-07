package com.digitalchina.xa.it.service;

import java.util.List;

public interface MnemonicService {
	/**
	 * @desc 在给定的单词中选取12个组成助记词组
	 * 
	 * @return 包含12个单词的字符串，以空格分隔
	 */
	String chooseMnemonic();
	
	/**
	 * @desc 可选。为助记词添加密码保护。
	 * @param pwd
	 * @return 使用密码为助记词加密
	 */
	List<String> lockMnemonicByPwd(String mnemonic, String pwd);
	
	/**
	 * @desc 使用merkleTree算法计算结果
	 * @param mnemonicList
	 * @return merkleTreeRoot字符串
	 */
	String merkleTreeRoot(List<String> mnemonicList);
}
