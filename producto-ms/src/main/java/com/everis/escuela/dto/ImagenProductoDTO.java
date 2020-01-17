package com.everis.escuela.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImagenProductoDTO { // mascara que se muestra en la respuesta 	
	private String rutaThumbnail;
	private String rutaImagen;
}
