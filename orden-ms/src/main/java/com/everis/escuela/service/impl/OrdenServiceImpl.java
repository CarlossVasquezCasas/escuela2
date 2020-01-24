package com.everis.escuela.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everis.escuela.entidad.Orden;
import com.everis.escuela.dto.DetalleOrdenReducidoDTO;
import com.everis.escuela.dto.StockProductoDTO;
import com.everis.escuela.entidad.DetalleOrden;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;
import com.everis.escuela.feign.AlmacenClient;
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
	@Autowired
	private AlmacenClient almacenClient;
	
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
//		List<DetalleOrden> lista = response.getDetalle();
//		
//		for (DetalleOrden obj : lista) {
//			obj.setOrden(response);			
//			detalleOrdenRepository.save(obj);
//		} 
		
		return orden;
	}

	@Override
	public List<Orden> listarOrdenes(Date fecha) throws ResourceNotFoundException {
		
		
		return ordenRepository.findByOrdenesPorFechaEnvio(fecha);
	}

	@Override
	public List<Orden> listarOrdenesPorProducto(Long idProducto) {
	
//INICIO		
		//estas dos formas se pueden usar
		//return ordenRepository.listarOrdenesPorProducto(idProducto);
		return ordenRepository.findByDetalleIdProducto(idProducto);
//FIN	
	}

	@Transactional(readOnly = false)	
	@Override
	public void eliminarOrden(Long idOrden) throws ResourceNotFoundException, ValidationException {
		// TODO Auto-generated method stub
		Orden orden =  ordenRepository.findById(idOrden).orElseThrow(()->new ResourceNotFoundException("no se encontro"));
		int cantidadorden = 0;
		Long idProducto = 0L;
		List<StockProductoDTO> listastock = new ArrayList<StockProductoDTO>();
		
		for (DetalleOrden obj : orden.getDetalle()) {
			
			cantidadorden = obj.getCantidad();
			idProducto = obj.getIdProducto();
			
			StockProductoDTO  stockproducdto = new  StockProductoDTO();
			stockproducdto.setCantidad(cantidadorden);
			stockproducdto.setIdproducto(idProducto);
			listastock.add(stockproducdto);
			
		} 
		
		almacenClient.actualizarStockOrdenEliminada(listastock);
		
		//detalleOrdenRepository.deleteAll(orden.getDetalle()); 
		
		
		ordenRepository.deleteById(idOrden);
		//ordenRepository.eliminarOrdenCarcada(idOrden);
		
	}

	@Override
	public Orden actualizarOrden(Long idOrden, Date fechaEnvio) throws ResourceNotFoundException {
		
		Orden orden =  ordenRepository.findById(idOrden).orElseThrow(()->new ResourceNotFoundException("no se encontro"));
		
		
		orden.setFechaEnvio(fechaEnvio);
		
		return ordenRepository.save(orden);
	}

//	@Override
//	public Orden obtenerProductoPorId(Long id) throws ResourceNotFoundException {		
//		return ordenRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontro el producto con el id") );
//	}
	
	

	
	
}
