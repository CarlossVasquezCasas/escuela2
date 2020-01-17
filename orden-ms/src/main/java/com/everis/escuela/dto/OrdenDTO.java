package com.everis.escuela.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.everis.escuela.entidad.DetalleOrden;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrdenDTO {
	
	
	private Long idCliente;
	private Date fechaEnvio;	
	private BigDecimal total; //se calcula sumatoria de la cantidad * precio de cada detalle
	private List<DetalleOrdenDTO> detalle;
	 
}
