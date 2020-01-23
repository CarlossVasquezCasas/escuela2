package com.everis.escuela.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.everis.escuela.dto.EmpresaDTO;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.service.EmpresaService;


@RefreshScope
@RestController
public class EmpresaController {
	
	@Autowired
	private EmpresaService  empresaService;
	
	@GetMapping("/{id}")
	public EmpresaDTO obtenerEmpresaPorId(@PathVariable("id") Long id) throws ResourceNotFoundException{
		ModelMapper mapper = new ModelMapper();
		
		EmpresaDTO empleado = mapper.map(empresaService.findById(id),EmpresaDTO.class) ;
		
		return empleado;
		
	}	

}
