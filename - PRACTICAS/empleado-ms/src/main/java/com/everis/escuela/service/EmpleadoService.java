package com.everis.escuela.service;

import com.everis.escuela.entidad.Empleado;
import com.everis.escuela.exceptions.ResourceNotFoundException;

public interface EmpleadoService {
	
	//	public Iterable<Orden> obtenerProductos();
	
	public Empleado findById(Long  id) throws ResourceNotFoundException;
	

}
