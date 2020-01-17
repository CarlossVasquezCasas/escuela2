package com.everis.escuela.Controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.everis.escuela.dto.CantidadDTO;
import com.everis.escuela.dto.ProductoDTO;
import com.everis.escuela.dto.ProductoReducidoDTO;
import com.everis.escuela.entidad.ImagenProducto;
import com.everis.escuela.entidad.Producto;
import com.everis.escuela.entidad.TipoProducto;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;
import com.everis.escuela.service.ProductoService;


@RefreshScope
@RestController
public class ProductoController {
	
	
	@Autowired
	private DiscoveryClient client;
	
	@Value("${tipcambio}")
	private String tipcambio;
	
	
	@Autowired
	private ProductoService  productoService;

	
	
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
	
	
	@GetMapping("/productos")
	public List<ProductoDTO> obtenerProductos(){
		ModelMapper mapper =  new ModelMapper();
		return StreamSupport.stream(productoService.obtenerProductos().spliterator(), false).map(p ->mapper.map(p, ProductoDTO.class) )
		.collect(Collectors.toList());		
		
	}
	
	@GetMapping("/productos/{id}")
	public ProductoDTO obtenerProductoPorId(@PathVariable("id") Long id) throws ResourceNotFoundException{
		ModelMapper mapper = new ModelMapper();
		
		ProductoDTO producto = mapper.map(productoService.obtenerProductoPorId(id),ProductoDTO.class) ;
		
		producto.setCantidad(getCantidad("almacen-ms", id).getCantidad());
		return producto;
		
	}
	
	@PostMapping("/productos")
	public ProductoDTO guardarProducto(@Valid @RequestBody ProductoReducidoDTO productoReducidoDto)  throws ValidationException,ResourceNotFoundException{
		ModelMapper mapper = new ModelMapper();	
		Producto producto = mapper.map( productoReducidoDto , Producto.class);
		TipoProducto tipoProducto = new TipoProducto();
		tipoProducto.setCodigo(productoReducidoDto.getCodigoProducto());
		
		ImagenProducto imagenProducto = new ImagenProducto();
		imagenProducto.setRutaImagen(productoReducidoDto.getRutaImagen());
		imagenProducto.setRutaThumbnail(productoReducidoDto.getRutaThumbnail());
		
		producto.setTipoProducto(tipoProducto);
		producto.setImagenProducto(imagenProducto);
						
		return mapper.map(productoService.guardarProducto(producto), ProductoDTO.class);
		
		
	}
	
	
	@GetMapping("/tipcambio")
	public String getTipCambio() {
		
		return "El tipo de cambio de hoy es : " + tipcambio;
	}
	

}
