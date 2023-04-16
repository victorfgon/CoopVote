package com.example.coopvote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan(basePackages={"com.example.coopvote.repository","com.example.coopvote.controller","com.example.coopvote.service"})
@SpringBootApplication
@EnableSwagger2
public class CoopVoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoopVoteApplication.class, args);
	}

}