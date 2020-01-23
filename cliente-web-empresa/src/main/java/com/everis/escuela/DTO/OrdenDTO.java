package com.everis.escuela.DTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdenDTO {
	
	
	private Long idCliente;
	private Date fechaEnvio;	
	private BigDecimal total; //se calcula sumatoria de la cantidad * precio de cada detalle
	private List<DetalleOrdenDTO> detalle;
	 
}
