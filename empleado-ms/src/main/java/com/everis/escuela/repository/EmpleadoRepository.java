package com.everis.escuela.repository;



import org.springframework.stereotype.Repository;

import com.everis.escuela.entidad.Empleado;
import com.everis.escuela.util.CustomRepository;

@Repository
public interface EmpleadoRepository extends CustomRepository <Empleado, Long> {

	
		
}
