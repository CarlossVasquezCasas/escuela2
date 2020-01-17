package com.everis.escuela.Controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.everis.escuela.dto.CantidadDTO;
import com.everis.escuela.dto.DetalleOrdenReducidoDTO;
import com.everis.escuela.dto.OrdenDTO;
import com.everis.escuela.dto.OrdenReducidoDTO;
import com.everis.escuela.dto.ProductoDTO;
import com.everis.escuela.entidad.DetalleOrden;
import com.everis.escuela.entidad.Orden;
import com.everis.escuela.exceptions.ValidationException;
import com.everis.escuela.service.OrdenService;


@RefreshScope
@RestController
public class OrdenController {
	
	
	@Autowired
	private DiscoveryClient client;
	
	@Autowired
	private OrdenService  ordenService;

	
	
	public CantidadDTO getCantidad(String service,Long idProducto)	{
		List<ServiceInstance> list = client.getInstances(service);
		if( list != null && list.size() > 0)
		{
			int rand = (int)Math.round(Math.random()) % list.size();
			URI uri = list.get(rand).getUri();
			if ( uri != null )
			{
				return (new RestTemplate()).getForObject(uri.toString() + "/stock/acumulado/producto/{idProducto}", CantidadDTO.class,idProducto);
			}
		}
		return null;
	}
	
	public ProductoDTO getProducto(String service,Long idProducto)	{
		List<ServiceInstance> list = client.getInstances(service);
		if( list != null && list.size() > 0)
		{
			int rand = (int)Math.round(Math.random()) % list.size();
			URI uri = list.get(rand).getUri();
			if ( uri != null )
			{
				return (new RestTemplate()).getForObject(uri.toString() + "/productos/{idProducto}", ProductoDTO.class,idProducto);
			}
		}
		return null;
	}
	
	
	
	
	@PostMapping("/orden")
	public OrdenDTO guardarOrden(@Valid @RequestBody OrdenReducidoDTO ordenReducidoDto)  throws Exception{
		ModelMapper mapper = new ModelMapper();	
		Orden orden = mapper.map( ordenReducidoDto , Orden.class);
		
		int cantidadStock = 0;
		BigDecimal precioProducto =  BigDecimal.ZERO;
		
		Double totalOrden =(double) 0;
		
		
		
		List<DetalleOrden> detalleOrdenes= new ArrayList<DetalleOrden>();
		DetalleOrden detalleOrden; 
		for (DetalleOrdenReducidoDTO obj : ordenReducidoDto.getDetalle()) {
			
			if( getProducto("producto-ms",obj.getIdProducto()) ==null )
			{
				throw new ValidationException("No se pudo obtener informacion  del producto");
				
			}
			
			cantidadStock  = getCantidad("almacen-ms",obj.getIdProducto() ).getCantidad();
			precioProducto = getProducto("producto-ms",obj.getIdProducto() ).getPrecio();
			
			
			if( cantidadStock < obj.getCantidad())
			{
				throw new ValidationException("No hay stock para la cantidad solicitada del producto " + obj.getIdProducto());
				
			}
			detalleOrden = new DetalleOrden();
			detalleOrden.setIdProducto(obj.getIdProducto());
			detalleOrden.setCantidad(obj.getCantidad());
			detalleOrdenes.add(detalleOrden);	
			
			totalOrden = totalOrden + obj.getCantidad()*precioProducto.doubleValue();
			
		}
		
		
		orden.setDetalle(detalleOrdenes);	
		
		orden.setFecha(new Date());
		
		orden.setTotal(new BigDecimal(totalOrden));
		
		return mapper.map(ordenService.guardarOrden(orden), OrdenDTO.class);
		
		
	}
	
	
	

}
