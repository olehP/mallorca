package com.mallorca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MallorcaHackApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(MallorcaHackApplication.class, args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
