package com.everis.escuela.service;

import com.everis.escuela.dto.EmpresaDTO;
import com.everis.escuela.exceptions.ResourceNotFoundException;

public interface FeignService {
	
	
	public EmpresaDTO findByIdEmpresa(Long id) throws ResourceNotFoundException;
	
}
