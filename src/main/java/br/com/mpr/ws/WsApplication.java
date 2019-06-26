package br.com.mpr.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WsApplication {

	public static void main(String[] args) {

		System.out.println(" __  __ ____  ____           __        ______");
		System.out.println("|  \\/  |  _ \\|  _ \\          \\ \\      / / ___| ");
		System.out.println("| |\\/| | |_) | |_) |  _____   \\ \\ /\\ / /\\___ \\ ");
		System.out.println("| |  | |  __/|  _ <  |_____|   \\ V  V /  ___) |");
		System.out.println("|_|  |_|_|   |_| \\_\\            \\_/\\_/  |____/");
		System.out.println("                                   v1.0.0");
		System.out.println();

		SpringApplication.run(WsApplication.class, args);
	}
}
