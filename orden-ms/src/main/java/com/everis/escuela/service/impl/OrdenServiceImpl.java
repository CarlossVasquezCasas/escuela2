package com.everis.escuela.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everis.escuela.entidad.Orden;
import com.everis.escuela.dto.DetalleOrdenReducidoDTO;
import com.everis.escuela.entidad.DetalleOrden;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.repository.OrdenRepository;
import com.everis.escuela.repository.DetalleOrdenRepository;
import com.everis.escuela.service.OrdenService;
@Transactional(readOnly = true)
@Service
public class OrdenServiceImpl implements OrdenService {
	
	@Autowired
	private OrdenRepository ordenRepository;
	@Autowired
	private DetalleOrdenRepository detalleOrdenRepository;
	
//	@Override
//	public Iterable<Orden> obtenerProductos() {		
//		return ordenRepository.findAll();
//	}
//	@Transactional(readOnly = false)
//	@Override
//	public Orden guardarProducto(Orden orden) throws ResourceNotFoundException {
//		
////		DetalleOrden tipoProducto = detalleOrdenRepository.findByCodigo(orden.getTipoProducto().getCodigo()).orElseThrow(()->new ResourceNotFoundException("no se encontro"));
////		orden.setTipoProducto(tipoProducto);
////		orden.setActivo(true);
//		
//	}
	@Transactional(readOnly = false)
	@Override
	public Orden guardarOrden(Orden orden) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Orden response = ordenRepository.save(orden);
		List<DetalleOrden> lista = response.getDetalle();
		
		for (DetalleOrden obj : lista) {
			obj.setOrden(response);			
			detalleOrdenRepository.save(obj);
		} 
		
		return orden;
	}

//	@Override
//	public Orden obtenerProductoPorId(Long id) throws ResourceNotFoundException {		
//		return ordenRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontro el producto con el id") );
//	}

	
	
}
