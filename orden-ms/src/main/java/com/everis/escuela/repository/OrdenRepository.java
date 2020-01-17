package com.everis.escuela.repository;



import org.springframework.stereotype.Repository;

import com.everis.escuela.entidad.Orden;
import com.everis.escuela.util.CustomRepository;

@Repository
public interface OrdenRepository extends CustomRepository <Orden, Long> {

	
		
}
