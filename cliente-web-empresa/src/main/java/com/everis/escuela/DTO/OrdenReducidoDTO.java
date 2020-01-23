package com.everis.escuela.DTO;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdenReducidoDTO {
	
	
	private Long idCliente;	
	private Date fechaEnvio;
	private List<DetalleOrdenReducidoDTO> detalle;
	 
}
