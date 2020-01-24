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


//INICIO	
//cualquiera de las 3 funcionan (en postgresql no permite poner alias con 2 caracteres solo es 1)
//	@Query(value = "SELECT orden from Orden orden inner join orden.detalle detalle where detalle.idProducto =  ?1")
	@Query(value = "select o.* from orden o inner join detalle_orden d on d.orden_id = o.id where d.id_producto = ?1 ",nativeQuery = true)	
	List<Orden> listarOrdenesPorProducto(Long idProducto);
	//esta es otra manera de ingresar al idproducto del objeto detalle de la orden
	List<Orden> findByDetalleIdProducto(Long idProducto);
//FIN	
	
	@Query(value = "delete orden,detalle_orden from orden o join detalle_orden d  on o.id=d.orden_id where o.id = ?1 ",nativeQuery = true)	
	List<Orden> eliminarOrdenCarcada(Long idOrden);
	
	
}
