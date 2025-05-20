package com.example.QuickVote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.QuickVote")
public class QuickVoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickVoteApplication.class, args);
	}
}
