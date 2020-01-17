package com.everis.escuela.entidad;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DetalleOrden {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;


	@OneToOne (targetEntity = Orden.class)
	@JoinColumn(name = "orden_id") 
	@JsonBackReference("orden")
	@Fetch(FetchMode.JOIN)
	private Orden orden;
	
	@Column
	private Long idProducto;
	
	@Column
	private int cantidad;
	
}
