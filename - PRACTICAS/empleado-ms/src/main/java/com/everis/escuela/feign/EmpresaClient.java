package com.everis.escuela.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.everis.escuela.dto.EmpresaDTO;
import com.everis.escuela.exceptions.ResourceNotFoundException;

@FeignClient("empresa-ms")
public interface EmpresaClient {

	@GetMapping("/{idempresa}")
	public EmpresaDTO finByIdEmpresa(@PathVariable("idempresa") Long idempresa) throws ResourceNotFoundException;
	
	
}
