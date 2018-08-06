package com.digitalchina.xa.it;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@EnableAutoConfiguration
@MapperScan("com.digitalchina.xa.it.dao")
public class SigninRewardApplication{
	public static void main(String[] args) {
		SpringApplication.run(SigninRewardApplication.class, args);
	}
}
