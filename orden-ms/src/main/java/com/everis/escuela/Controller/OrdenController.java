package com.everis.escuela.Controller;

import java.math.BigDecimal;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.everis.escuela.dto.CantidadDTO;
import com.everis.escuela.dto.DetalleOrdenReducidoDTO;
import com.everis.escuela.dto.FechaEnvioDTO;
import com.everis.escuela.dto.OrdenDTO;
import com.everis.escuela.dto.OrdenReducidoDTO;
import com.everis.escuela.dto.ProductoDTO;
import com.everis.escuela.dto.StockProductoDTO;
import com.everis.escuela.entidad.DetalleOrden;
import com.everis.escuela.entidad.Orden;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;
import com.everis.escuela.feign.AlmacenClient;
import com.everis.escuela.feign.ProductoClient;
import com.everis.escuela.service.OrdenService;
import com.everis.escuela.service.impl.FeignServiceImp;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RefreshScope
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrdenController {
	
	
	@Autowired
	private DiscoveryClient client;
	
	@Autowired
	private OrdenService  ordenService;

	@Autowired
	private ProductoClient productoClient;
	
	@Autowired
	private AlmacenClient almacenClient;
	
	@Autowired
	private FeignServiceImp feignServiceImp;
	
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
	
	
	@ApiOperation(value="guardar una orden de venta"
				  ,notes="al guardar una orden se verificara el stock y los almacenes de cada producto"
				  ,response = OrdenDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Se registro correctamente la orden",response = OrdenDTO.class),
			@ApiResponse(code = 404, message = "Recurso no Encontrado",response = ResourceNotFoundException.class),
			@ApiResponse(code = 200, message = "Validacion del negocio",response = ValidationException.class)
	})
	@ResponseStatus(HttpStatus.CREATED) // para cuando registre correctamente muestre un codigo 201 y no 200
	@HystrixCommand(fallbackMethod = "guardarSinStock")	
	@PostMapping("/orden")
	public OrdenDTO guardarOrden(@Valid @RequestBody OrdenReducidoDTO ordenReducidoDto  )  throws  Exception{
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // para obligar a que mapee q cada campo debe ser igual nombre y tipo
		Orden orden = mapper.map( ordenReducidoDto , Orden.class);
		
		
		int cantidadStock = 0;
		BigDecimal precioProducto =  BigDecimal.ZERO;
		
		BigDecimal totalOrden = new BigDecimal(0);
		
		/// ejemplo de foreach en una linea
		
//		ordenReducidoDto.getDetalle().forEach(ordendetalle-> {
//			int cantidad = getCantidad("almacen-ms", ordendetalle.getIdProducto()).getCantidad();
//			if(cantidad< ordendetalle.getCantidad())
//				
//		});
		
		List<DetalleOrden> detalleOrdenes= new ArrayList<DetalleOrden>();
		DetalleOrden detalleOrden; 
		
		List<StockProductoDTO>  lststockproducto = new ArrayList<StockProductoDTO>();
		
		
		
		for (DetalleOrdenReducidoDTO obj : ordenReducidoDto.getDetalle()) {
	
			try {
					
				//precioProducto = getProducto("producto-ms",obj.getIdProducto() ).getPrecio();
				precioProducto = productoClient.obtenerProductoPorId(obj.getIdProducto()).getPrecio() ;
			} catch (Exception e) {
				throw new ValidationException("Error: no se pudo obtener informacion del stock ");
			}
			
			try {
				//cantidadStock  =  getCantidad("almacen-ms",obj.getIdProducto() ).getCantidad();
//				cantidadStock  = almacenClient.obtenerCantidadProductosTodasTiendas(obj.getIdProducto()).getCantidad(); //
				cantidadStock  = feignServiceImp.obtenerCantidadProductosTodasTiendas(obj.getIdProducto()).getCantidad(); //
				
				
			} catch (Exception e) {
				throw new ValidationException("Error: no se pudo obtener informacion del stock ");
			}
				
			
			
			if( cantidadStock < obj.getCantidad()) 
			{	
				throw new ValidationException("No hay stock para la cantidad solicitada del producto " + obj.getIdProducto());				
			}
			detalleOrden = new DetalleOrden();
			detalleOrden.setIdProducto(obj.getIdProducto());
			detalleOrden.setCantidad(obj.getCantidad());
			detalleOrdenes.add(detalleOrden);	
			
			
			totalOrden = totalOrden.add(precioProducto.multiply(new BigDecimal(obj.getCantidad())));
			//detalleOrden.setOrden(orden);
			
			StockProductoDTO  stockproducto = new StockProductoDTO();
			stockproducto.setIdproducto(obj.getIdProducto());
			stockproducto.setCantidad(obj.getCantidad());
			lststockproducto.add(stockproducto);
			
		}

		
		
		
		orden.setDetalle(detalleOrdenes);	
		
		orden.setFecha(new Date());
		
		orden.setTotal(totalOrden);
		
		OrdenDTO ordenDTO = new OrdenDTO();
		ordenDTO = mapper.map(ordenService.guardarOrden(orden), OrdenDTO.class);
		 almacenClient.actualizarStockProducto(lststockproducto);
		
		return ordenDTO;
		
		
		//Uso del bigdecimal 
//		BigDecimal total = new BigDecimal(0);
//		total.add(new BigDecimal(12).multiply(new BigDecimal(2)));
		
			
		
		
		
		
	}
	
	public OrdenDTO guardarSinStock(@Valid @RequestBody OrdenReducidoDTO ordenReducidoDto  )  throws  Exception{
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // para obligar a que mapee q cada campo debe ser igual nombre y tipo
		Orden orden = mapper.map( ordenReducidoDto , Orden.class);
		
		BigDecimal precioProducto =  BigDecimal.ZERO;
		
		BigDecimal totalOrden = new BigDecimal(0);
		
		List<DetalleOrden> detalleOrdenes= new ArrayList<DetalleOrden>();
		DetalleOrden detalleOrden; 
		
		for (DetalleOrdenReducidoDTO obj : ordenReducidoDto.getDetalle()) {
		
			try {
					
				//precioProducto = getProducto("producto-ms",obj.getIdProducto() ).getPrecio();
				precioProducto = productoClient.obtenerProductoPorId(obj.getIdProducto()).getPrecio() ;
			} catch (Exception e) {
				throw new ValidationException("Error: no se pudo obtener informacion del stock ");
			}
			
			detalleOrden = new DetalleOrden();
			detalleOrden.setIdProducto(obj.getIdProducto());
			detalleOrden.setCantidad(obj.getCantidad());
			detalleOrdenes.add(detalleOrden);	
			
			
			totalOrden = totalOrden.add(precioProducto.multiply(new BigDecimal(obj.getCantidad())));
			//detalleOrden.setOrden(orden);
			
		}

		
		
		
		orden.setDetalle(detalleOrdenes);	
		
		orden.setFecha(new Date());
		
		orden.setTotal(totalOrden);
		
		OrdenDTO ordenDTO = new OrdenDTO();
		ordenDTO = mapper.map(ordenService.guardarOrden(orden), OrdenDTO.class);
		
		
		
		return ordenDTO;
	}
	
	
	@GetMapping("/orden/listado/{fechaEnvio}")
	public List<OrdenDTO> listarOrdenes(@PathVariable("fechaEnvio") String fechaEnvio  )  throws  Exception{
		
		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(fechaEnvio);  
		ModelMapper mapper = new ModelMapper();

		return StreamSupport.stream(ordenService.listarOrdenes(date1).spliterator(), false).map(p ->mapper.map(p, OrdenDTO.class) )
					.collect(Collectors.toList());
		
	}
	
	

	@GetMapping("/orden/detalle/{idProducto}")
	public List<OrdenDTO> listarOrdenesPorProducto(@PathVariable("idProducto") Long idProducto  )  throws  Exception{
		
		  
		ModelMapper mapper = new ModelMapper();

		return StreamSupport.stream(ordenService.listarOrdenesPorProducto(idProducto).spliterator(), false).map(p ->mapper.map(p, OrdenDTO.class) )
					.collect(Collectors.toList());
		
	}
	
	
	@DeleteMapping("/orden/{idOrden}")
	public void eliminarOrden(@PathVariable("idOrden") Long idOrden  )  throws  Exception{		
		  
		

		ordenService.eliminarOrden(idOrden);
		
	}
	
	
	@PutMapping("/orden/{idOrden}")
	public OrdenDTO listarOrdenes(@PathVariable("idOrden") Long idOrden, @RequestBody FechaEnvioDTO fechaEnvio  )  throws  Exception{
		
		//Date fechaEnvioActualizar=new SimpleDateFormat("yyyy-MM-dd").parse(fechaEnvio);  
		ModelMapper mapper = new ModelMapper();

		return  mapper.map(ordenService.actualizarOrden(idOrden,fechaEnvio.getFechaEnvio()), OrdenDTO.class) ;
				
		
	}
	
	

}
