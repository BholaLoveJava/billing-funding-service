package com.world.web;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
public class BillingFundingServiceApplication  implements CommandLineRunner, ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(BillingFundingServiceApplication.class, args);
	}

	/**
	 * @param args incoming main method arguments
	 * @throws Exception in-case of error scenarios
	 */
	@Override
	public void run(String... args) throws Exception {
       System.out.println("Commandline runner invoked!!!");
	}

	/**
	 * @param args incoming application arguments
	 * @throws Exception in case of error scenarios
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Application runner invoked!!!");
	}
}
