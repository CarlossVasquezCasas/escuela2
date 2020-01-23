package com.everis.escuela.dto;

import java.util.Date;

import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("Model Empleado")
public class EmpleadoDTO {
	@ApiModelProperty(value = "ID Empleado")
	private long id;
	@ApiModelProperty(value = "Nombre Empleado")
	private String nombre;	
	@JsonProperty(value="apellido_paterno")
	private String apellidoPaterno;
	@JsonProperty(value="apellido_materno")
	private String apellidoMaterno;
	private String dni;
	@PastOrPresent
	@JsonProperty(value="fecha_ingreso")
	private Date fechaIngreso;
	private Boolean activo;
	private String Empresa;
	
	
}
