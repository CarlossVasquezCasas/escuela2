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
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.everis.escuela.entidad.DetalleOrden;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrdenReducidoDTO {
	
	
	private Long idCliente;
	@FutureOrPresent
	private Date fechaEnvio;
	private List<DetalleOrdenReducidoDTO> detalle;
	 
}
