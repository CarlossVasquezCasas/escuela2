package com.everis.escuela.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everis.escuela.dto.StockProductoDTO;
import com.everis.escuela.entidad.Stock;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;
import com.everis.escuela.repository.StockRepository;
import com.everis.escuela.service.StockService;
@
Transactional(readOnly = true)
@Service
public class StockServiceImp implements StockService {
	
	
	
	
	@Autowired
	private StockRepository stockRepository;

	@Override
	public int obtenerCantidadProductoTodasTiendas(Long idproducto) throws ResourceNotFoundException {
		int cantidad = 0;
		
		List<Stock> listastock = StreamSupport.stream(stockRepository.findAll().spliterator(), false).collect(Collectors.toList());
		
		
		
		if( listastock.size() == 0)
		{
			return 0;
		}
		for (Stock obj : listastock) {
					if (obj.getIdProducto().equals(idproducto)  ) {
						
						cantidad= cantidad + obj.getCantidad() ;
					}
				
			}
		
		return cantidad; 
	}

	@Override
	public int obtenerProductosPorTienda(Long idproducto, Long idtienda) throws ResourceNotFoundException, ValidationException {
//		int cantidad = 0;
//		List<Stock> listastock = StreamSupport.stream(stockRepository.findAll().spliterator(), false).collect(Collectors.toList());
//		
//		int cantidad2 = 0;
//		List<Stock> listastock2 = StreamSupport.stream(stockRepository.findByIdProductoAndIdTienda(idproducto, idtienda).spliterator(), false).collect(Collectors.toList());
//		
//		
//		if( listastock.size() == 0)
//		{
//			return 0;
//		}
//				
//		
//		for (Stock obj : listastock) {
//				if ((obj.getIdProducto().equals(idproducto) && obj.getIdTienda().equals(idtienda) )) {
//				
//					cantidad= cantidad + obj.getCantidad() ;
//				}
//			}
//		
//		for (Stock obj : listastock2) {
//			cantidad2= cantidad2 + obj.getCantidad() ; 
//		}
		
		
		return stockRepository.findProductosPorTienda(idproducto, idtienda).orElseThrow(() -> new ValidationException("Error")); 
	}
	
	@Transactional(readOnly = false)
	@Override
	public void actualizarStockProducto(List<StockProductoDTO> lststockproducto) throws ValidationException {

		if( lststockproducto.size() == 0)
		{
			throw new ValidationException("No se envio corretamente el listado de productos a actualizar stock");
		}
		
		int resultado = 0;
		int valrestar = 0;
		Integer numstock=0;
		
		for (StockProductoDTO objdetalleorden : lststockproducto) {
			numstock=0;
			
			numstock = stockRepository.findCantidadProductos(objdetalleorden.getIdproducto());
			if (numstock < objdetalleorden.getCantidad())
			{
				throw new ValidationException("No se cuenta con stock del producto" + objdetalleorden.getIdproducto());
			}
			
			List<Stock> listastock = StreamSupport.stream(stockRepository.findByIdProducto(objdetalleorden.getIdproducto()).spliterator(), false).collect(Collectors.toList());
			valrestar = objdetalleorden.getCantidad() ;
			for (Stock stock : listastock) {
				
				
				
				
				resultado = stock.getCantidad() - valrestar ;
				
				if (resultado<=0) 
				{	
					valrestar = resultado*-1;
					stock.setCantidad(0);
					
				}else
				{
					stock.setCantidad(resultado);
					valrestar = 0;
				}
				
				stockRepository.save(stock);
				
			}
			
		
		}
		
		
		
		
		
		
		
		
		
	}
	
	
}
