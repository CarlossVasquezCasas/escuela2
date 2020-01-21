package com.everis.escuela.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everis.escuela.dto.CantidadDTO;
import com.everis.escuela.entidad.Producto;
import com.everis.escuela.entidad.TipoProducto;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.feign.AlmacenClient;
import com.everis.escuela.repository.ProductoRepository;
import com.everis.escuela.repository.TipoProductoRepository;
import com.everis.escuela.service.FeignService;
import com.everis.escuela.service.ProductoService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class FeignServiceImp implements FeignService {
	
	@Autowired
	private AlmacenClient almacenClient;
		
	

	@HystrixCommand(fallbackMethod = "obtenerCantidadDefecto",groupKey = "obtenerCantidadStockProducto",threadPoolKey = "obtenerCantidadStockProducto",commandProperties = {
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "5"),//nro de intentos para cambiar el estado a open
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "500") // numero de espera de la peticion para generar timeout
			
	})
	@Override
	public CantidadDTO obtenerCantidadStockProducto(Long id) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return almacenClient.obtenerCantidadProductosTodasTiendas(id);
	}

	public CantidadDTO obtenerCantidadDefecto(Long id) {
		return new CantidadDTO(0);
	}
	
}
