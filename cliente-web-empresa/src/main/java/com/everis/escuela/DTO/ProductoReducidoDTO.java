package com.everis.escuela.DTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoReducidoDTO {
	
	private String nombre;
	private String codigo;
	private String descripcion;
	private BigDecimal precio;
	
	private String codigoProducto;
	
	private String rutaImagen;
	
	private String rutaThumbnail;
	
}
