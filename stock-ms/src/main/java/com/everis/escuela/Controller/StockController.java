package com.everis.escuela.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.everis.escuela.dto.CantidadDTO;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;
import com.everis.escuela.service.StockService;

@RefreshScope
@RestController
public class StockController {
	
	@Value("${tipcambio}")
	private String tipcambio;
	
	
	@Autowired
	private StockService  stockService;
	
	@GetMapping("/stock/acumulado/producto/{idProducto}")
	public CantidadDTO /*String*/ obtenerCantidadProductosTodasTiendas(@PathVariable("idProducto") Long idProducto) throws ResourceNotFoundException{
		CantidadDTO cantidad = new CantidadDTO();
		cantidad.setCantidad(12);//stockService.obtenerCantidadProductoTodasTiendas(idProducto));
		//return "La cantidad de productos acumulados en todas las tiendas es : " + cantidad;		
		return  cantidad;
	}
	
	@GetMapping("/stock/producto/{idProducto}/tienda/{idTienda}")
	public CantidadDTO /*String*/ obtenerProductoPorId(@PathVariable("idProducto") Long idProducto,@PathVariable("idTienda") Long idTienda) throws ResourceNotFoundException, ValidationException{
		CantidadDTO cantidad = new CantidadDTO();
		cantidad.setCantidad(stockService.obtenerProductosPorTienda(idProducto,idTienda));
		//return "La cantidad de productos en la tienda es : " + cantidad;
		return  cantidad;
	}
	
	
	
	
	@GetMapping("/tipcambio")
	public String getTipCambio() {
		
		return "El tipo de cambio de hoy es : " + tipcambio;
	}
	

}
