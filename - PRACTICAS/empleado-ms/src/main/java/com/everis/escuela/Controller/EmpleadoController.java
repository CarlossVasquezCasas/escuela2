package com.everis.escuela.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.everis.escuela.dto.EmpleadoDTO;
import com.everis.escuela.entidad.Empleado;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.service.EmpleadoService;
import com.everis.escuela.service.impl.FeignServiceImp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@RefreshScope
@RestController
@Api(value = "Microservicio Empleado",produces = MediaType.APPLICATION_JSON_VALUE)
public class EmpleadoController {
	
	@Autowired
	private EmpleadoService  empleadoService;

	@Autowired
	private FeignServiceImp feignServiceImp;
	
	@ApiOperation(value = "Encontrar un Empleado", notes = "Retorna un empleado por su id" )
	@ApiImplicitParams({
         @ApiImplicitParam(name = "id", value = "ID de empleado",paramType = "path")
	 })
	@GetMapping("/{id}")
	public EmpleadoDTO obtenerEmpleadoPorId(@PathVariable("id") Long id) throws ResourceNotFoundException{
		ModelMapper mapper = new ModelMapper();
		
		Empleado empleado = empleadoService.findById(id);
		EmpleadoDTO empleadodto = mapper.map(empleado,EmpleadoDTO.class) ;		
		empleadodto.setEmpresa(feignServiceImp.findByIdEmpresa(empleado.getIdEmpresa()).getNombre());
		
		
		return empleadodto;
		
	}
	
	

	
	
	

}
