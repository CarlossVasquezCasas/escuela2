package com.everis.escuela.repository;



import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.everis.escuela.entidad.Orden;
import com.everis.escuela.entidad.DetalleOrden;
import com.everis.escuela.util.CustomRepository;

@Repository
public interface DetalleOrdenRepository extends CustomRepository <DetalleOrden, Long> {

	
	 
//		Optional<DetalleOrden> findByCodigo(String codigo);
			
	
 
		
}
