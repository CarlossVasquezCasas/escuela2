package com.everis.escuela.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.everis.escuela.dto.CantidadDTO;
import com.everis.escuela.exceptions.ResourceNotFoundException;

@FeignClient("almacen-ms")
public interface AlmacenClient {

	@GetMapping("/stock/acumulado/producto/{idProducto}")
	public CantidadDTO /*String*/ obtenerCantidadProductosTodasTiendas(@PathVariable("idProducto") Long idProducto) throws ResourceNotFoundException;
	
	
}
