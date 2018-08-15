package com.dc.service;

import org.springframework.stereotype.Service;

@Service
public interface EncryptService {
	String decrypt(String jsonValue) throws Exception;
}
