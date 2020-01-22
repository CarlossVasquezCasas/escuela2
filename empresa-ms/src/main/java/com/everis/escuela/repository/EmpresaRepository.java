package com.everis.escuela.repository;



import org.springframework.stereotype.Repository;

import com.everis.escuela.entidad.Empresa;
import com.everis.escuela.util.CustomRepository;

@Repository
public interface EmpresaRepository extends CustomRepository <Empresa, Long> {

	
		
}
