package com.everis.escuela.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everis.escuela.entidad.Empresa;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.repository.EmpresaRepository;
import com.everis.escuela.service.EmpresaService;
@Transactional(readOnly = true)
@Service
public class EmpresaServiceImpl implements EmpresaService {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
//	@Autowired
//	private EmpresaRepository detalleOrdenRepository;
	





	@Override
	public Empresa findById(Long id) throws ResourceNotFoundException {
		return empresaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontro la empresa con el id") );
	
	}

	
	
}
