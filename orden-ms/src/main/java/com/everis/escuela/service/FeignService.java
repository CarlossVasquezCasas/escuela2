package com.everis.escuela.service;

import com.everis.escuela.dto.CantidadDTO;
import com.everis.escuela.exceptions.ResourceNotFoundException;

public interface FeignService {
	
	
	public CantidadDTO obtenerCantidadProductosTodasTiendas(Long id) throws ResourceNotFoundException;
	
}
