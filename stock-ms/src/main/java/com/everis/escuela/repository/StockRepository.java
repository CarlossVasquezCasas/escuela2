package com.everis.escuela.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.everis.escuela.entidad.Stock;
import com.everis.escuela.util.CustomRepository;

@Repository
public interface StockRepository extends CustomRepository <Stock, Long> {
 
	List<Stock>  findByIdProductoAndIdTienda(Long idProducto,Long idTienda);
	
	List<Stock>  findByIdProductoOrderByCantidadDesc(Long idProducto);
	
	//@Query(value = "SELECT coalesce(SUM(s.Cantidad),0) FROM STOCK s WHERE id_Producto = ?1 AND id_Tienda= ?2 and estado.codigo='A' ")		    
    
	 @Query(value = "SELECT coalesce(SUM(s.Cantidad),0) FROM STOCK s WHERE id_Producto = ?1 AND id_Tienda= ?2 ",			    
			    nativeQuery = true)	
	 Optional<Integer> findProductosPorTienda(Long idProducto,Long idTienda);
	 
	 @Query(value = "SELECT coalesce(SUM(s.Cantidad),0) FROM STOCK s WHERE id_Producto = ?1  ",			    
			    nativeQuery = true)	
	 Integer findCantidadProductos(Long idProducto);
	
	
}
