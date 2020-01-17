package com.everis.escuela.repository;



import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.everis.escuela.entidad.Producto;
import com.everis.escuela.entidad.TipoProducto;
import com.everis.escuela.util.CustomRepository;

@Repository
public interface TipoProductoRepository extends CustomRepository <TipoProducto, Long> {

	
	 
		Optional<TipoProducto> findByCodigo(String codigo);
			
	
 
		
}
