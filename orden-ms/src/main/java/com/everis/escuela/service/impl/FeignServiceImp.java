package com.everis.escuela.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.escuela.dto.CantidadDTO;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.feign.AlmacenClient;
import com.everis.escuela.service.FeignService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class FeignServiceImp implements FeignService {
	
	@Autowired
	private AlmacenClient almacenClient;
		
	

	@HystrixCommand(fallbackMethod = "obtenerCantidadDefecto",groupKey = "obtenerCantidadStockProducto",threadPoolKey = "obtenerCantidadStockProducto"
			,commandProperties = {
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "20"),//nro de intentos para cambiar el estado a open
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "10000") // numero de espera de la peticion para generar timeout
			
			}
	)
	@Override
	public CantidadDTO obtenerCantidadProductosTodasTiendas(Long id) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return almacenClient.obtenerCantidadProductosTodasTiendas(id);
	}

	public CantidadDTO obtenerCantidadDefecto(Long id) {
		
		
		
		return new CantidadDTO(-1);
	}
	
}
