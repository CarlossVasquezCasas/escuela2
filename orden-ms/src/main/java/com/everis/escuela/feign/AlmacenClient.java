package com.everis.escuela.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.everis.escuela.dto.CantidadDTO;
import com.everis.escuela.dto.StockProductoDTO;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;

@FeignClient("almacen-ms")
public interface AlmacenClient {

	@GetMapping("/stock/acumulado/producto/{idProducto}")
	public CantidadDTO /*String*/ obtenerCantidadProductosTodasTiendas(@PathVariable("idProducto") Long idProducto) throws ResourceNotFoundException;
	
	@PostMapping("/stock/")
	public void /*String*/ actualizarStockProducto(@RequestBody List<StockProductoDTO> stockproducto) ;
	
	@PostMapping("/stock/eliminados")
	public void /*String*/ actualizarStockOrdenEliminada(@RequestBody List<StockProductoDTO> lststockproducto) throws ValidationException ;
	
}
