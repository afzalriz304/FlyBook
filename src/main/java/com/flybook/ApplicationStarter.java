package com.flybook;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApplicationStarter extends SpringBootServletInitializer{
	
	/***
	 * this is starting method of application
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApplicationStarter.class, args);

	}
	
    

}
