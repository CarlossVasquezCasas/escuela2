package com.everis.escuela.service;

import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;

public interface StockService {
	
	public int obtenerCantidadProductoTodasTiendas(Long idproducto) throws ResourceNotFoundException;
	
	public int obtenerProductosPorTienda(Long idproducto, Long iptienda) throws ResourceNotFoundException, ValidationException;
}
