package com.everis.escuela.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockProductoDTO {
	@ApiModelProperty
	private long idproducto;
	private Integer cantidad;	
}
