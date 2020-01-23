package com.everis.escuela.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.everis.escuela.entidad.Orden;
import com.everis.escuela.util.CustomRepository;

@Repository
public interface OrdenRepository extends CustomRepository <Orden, Long> {

	@Query(value = "SELECT * FROM orden o WHERE o.fecha_envio >= ?1  ",			    
			    nativeQuery = true)	
	List<Orden> findByOrdenesPorFechaEnvio(Date fechaEnvio);
		
}
