package com.kawsay.ia;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ai.chat.memory.jdbc.JdbcChatMemory;
import org.springframework.context.annotation.ComponentScan;


import javax.sql.DataSource;

@SpringBootApplication
@ComponentScan(basePackages = {"com.kawsay.ia", "com.kawsay.user"})
public class IaApplication {

	public static void main(String[] args) {
		SpringApplication.run(IaApplication.class, args);
	}

}
