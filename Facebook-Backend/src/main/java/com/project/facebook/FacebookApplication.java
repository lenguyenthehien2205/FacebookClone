package com.project.facebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FacebookApplication {
	public static void main(String[] args) {
		SpringApplication.run(FacebookApplication.class, args);
	}
	// response: 0 có khóa ngoại, khóa chính và các thuộc tính tự sinh
	// model: có tất cả
	// dto: chỉ cần khóa ngoại
}
