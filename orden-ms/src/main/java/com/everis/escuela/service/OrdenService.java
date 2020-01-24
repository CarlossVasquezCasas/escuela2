package com.everis.escuela.service;

import java.util.Date;
import java.util.List;

import org.springframework.cloud.client.ServiceInstance;

import com.everis.escuela.dto.OrdenDTO;
import com.everis.escuela.entidad.Orden;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;

public interface OrdenService {
	
//	public Iterable<Orden> obtenerProductos();
	
	public Orden guardarOrden(Orden orden) throws ResourceNotFoundException;
	
	public List<Orden> listarOrdenes(Date fecha) throws ResourceNotFoundException;

	public List<Orden> listarOrdenesPorProducto(Long idProducto);

	public void eliminarOrden(Long idOrden) throws ResourceNotFoundException, ValidationException;

	public Orden actualizarOrden(Long idOrden, Date fechaEnvio) throws ResourceNotFoundException;
	
//	public Orden obtenerOrdenPorId(Long id) throws ResourceNotFoundException;
}
