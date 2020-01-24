package com.everis.escuela.Controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.everis.escuela.dto.CantidadDTO;
import com.everis.escuela.dto.StockProductoDTO;
import com.everis.escuela.entidad.Stock;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;
import com.everis.escuela.service.StockService;

@RefreshScope
@RestController
public class StockController {
	
	
	
	
	@Autowired
	private StockService  stockService;
	
	@GetMapping("/stock/acumulado/producto/{idProducto}")
	public CantidadDTO /*String*/ obtenerCantidadProductosTodasTiendas(@PathVariable("idProducto") Long idProducto) throws ResourceNotFoundException{
		CantidadDTO cantidad = new CantidadDTO();
		cantidad.setCantidad(stockService.obtenerCantidadProductoTodasTiendas(idProducto));
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
	
	@PostMapping("/stock/")
	public void /*String*/ actualizarStockProducto(@RequestBody List<StockProductoDTO> lststockproducto) throws ValidationException {
		
		stockService.actualizarStockProducto(lststockproducto);
	}
	
	
	@PostMapping("/stock/eliminados")
	public void /*String*/ actualizarStockOrdenEliminada(@RequestBody List<StockProductoDTO> lststockproducto) throws ValidationException {
		
		stockService.actualizarStockProductoEliminados(lststockproducto);
	}
	
	
	
	

}
