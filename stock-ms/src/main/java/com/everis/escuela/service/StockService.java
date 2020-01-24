package com.everis.escuela.service;

import java.util.List;

import com.everis.escuela.dto.StockProductoDTO;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;

public interface StockService {
	
	public int obtenerCantidadProductoTodasTiendas(Long idproducto) throws ResourceNotFoundException;
	
	public int obtenerProductosPorTienda(Long idproducto, Long iptienda) throws ResourceNotFoundException, ValidationException;
	
	
	public void actualizarStockProducto(List<StockProductoDTO> lststockproducto) throws ValidationException;

	void actualizarStockProductoEliminados(List<StockProductoDTO> lststockproducto) throws ValidationException; 
}
