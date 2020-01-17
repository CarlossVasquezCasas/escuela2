package com.everis.escuela.service;

import com.everis.escuela.entidad.Orden;
import com.everis.escuela.exceptions.ResourceNotFoundException;

public interface OrdenService {
	
//	public Iterable<Orden> obtenerProductos();
	
	public Orden guardarOrden(Orden orden) throws ResourceNotFoundException;
	
//	public Orden obtenerOrdenPorId(Long id) throws ResourceNotFoundException;
}
