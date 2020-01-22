package com.everis.escuela.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.escuela.dto.EmpresaDTO;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.feign.EmpresaClient;
import com.everis.escuela.service.FeignService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class FeignServiceImp implements FeignService {

	@Autowired
	private EmpresaClient empresaClient;

	@HystrixCommand(fallbackMethod = "findByIdEmpresaDefecto", groupKey = "findByIdEmpresa", threadPoolKey = "findByIdEmpresa", commandProperties = {
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"), // nro de intentos para
																							// cambiar el estado a open
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500") // numero de
																										// espera de la
																										// peticion para
																										// generar
																										// timeout
	})
	@Override
	public EmpresaDTO findByIdEmpresa(Long id) throws ResourceNotFoundException {

		
		return empresaClient.finByIdEmpresa(id);
	}

	public EmpresaDTO findByIdEmpresaDefecto(Long id) {

		EmpresaDTO empresadto = new EmpresaDTO();
		empresadto.setNombre("");

		return empresadto;
	}

}
