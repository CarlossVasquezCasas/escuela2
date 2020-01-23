package com.everis.escuela.RestTamplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class EscuelaRestTamplate {
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
