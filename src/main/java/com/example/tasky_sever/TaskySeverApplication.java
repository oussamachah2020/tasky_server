package com.example.tasky_sever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class TaskySeverApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskySeverApplication.class, args);
	}

}
