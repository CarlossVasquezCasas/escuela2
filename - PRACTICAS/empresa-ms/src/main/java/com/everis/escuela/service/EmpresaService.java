package com.everis.escuela.service;

import com.everis.escuela.entidad.Empresa;
import com.everis.escuela.exceptions.ResourceNotFoundException;

public interface EmpresaService {
	
	//	public Iterable<Orden> obtenerProductos();
	
	public Empresa findById(Long  id) throws ResourceNotFoundException;
	

}
