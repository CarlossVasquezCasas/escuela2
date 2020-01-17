package com.everis.escuela.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class ProductoReducidoDTO {
	
	private String nombre;
	private String codigo;
	private String descripcion;
	private BigDecimal precio;
	@JsonProperty(value="codigo_producto")
	private String codigoProducto;
	@JsonProperty(value="ruta_imagen")
	private String rutaImagen;
	@JsonProperty(value="ruta_Thumbnail")
	private String rutaThumbnail;
	
}
