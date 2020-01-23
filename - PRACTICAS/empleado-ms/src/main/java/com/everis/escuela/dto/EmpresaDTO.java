package com.everis.escuela.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("Model Empresa")
public class EmpresaDTO {
	@ApiModelProperty(value = "Nombre de Empresa")
	private String nombre;
}
