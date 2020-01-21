package com.everis.escuela;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.everis.escuela.util.CustomRepositoryImpl;

@EnableHystrixDashboard
@EnableHystrix
@EnableFeignClients
@EnableDiscoveryClient //sirve para usar los ids que esta registrados en eureka y poder conectarse con los demas micoservicios
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
@SpringBootApplication
public class ProductoMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductoMsApplication.class, args);
	}

}
