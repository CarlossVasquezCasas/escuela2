package com.everis.escuela.repository;



import org.springframework.stereotype.Repository;

import com.everis.escuela.entidad.Producto;
import com.everis.escuela.util.CustomRepository;

@Repository
public interface ProductoRepository extends CustomRepository <Producto, Long> {

	
		
}
