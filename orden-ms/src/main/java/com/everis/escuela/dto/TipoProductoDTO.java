package com.everis.escuela.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TipoProductoDTO {
	@ApiModelProperty
	private String nombre;
	private String codigo;
}
