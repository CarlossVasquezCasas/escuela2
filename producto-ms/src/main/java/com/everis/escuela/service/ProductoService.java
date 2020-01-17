package com.everis.escuela.service;

import com.everis.escuela.entidad.Producto;
import com.everis.escuela.exceptions.ResourceNotFoundException;

public interface ProductoService {
	
	public Iterable<Producto> obtenerProductos();
	
	public Producto guardarProducto(Producto producto) throws ResourceNotFoundException;
	
	public Producto obtenerProductoPorId(Long id) throws ResourceNotFoundException;
}
