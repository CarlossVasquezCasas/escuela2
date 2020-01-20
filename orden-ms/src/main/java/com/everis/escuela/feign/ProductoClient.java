package com.everis.escuela.feign;

import org.modelmapper.ModelMapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.everis.escuela.dto.ProductoDTO;
import com.everis.escuela.exceptions.ResourceNotFoundException;

@FeignClient("producto-ms")
public interface ProductoClient {
	
	@GetMapping("/productos/{id}")
	public ProductoDTO obtenerProductoPorId(@PathVariable("id") Long id) throws ResourceNotFoundException;

}
