package com.everis.escuela.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everis.escuela.entidad.Empleado;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.repository.EmpleadoRepository;
import com.everis.escuela.service.EmpleadoService;
@Transactional(readOnly = true)
@Service
public class EmpleadoServiceImpl implements EmpleadoService {
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
//	@Autowired
//	private EmpresaRepository detalleOrdenRepository;
	





	@Override
	public Empleado findById(Long id) throws ResourceNotFoundException {
		return empleadoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontro el empleado con el id") );
	
	}

	
	
}
