package com.everis.escuela.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everis.escuela.entidad.Producto;
import com.everis.escuela.entidad.TipoProducto;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.repository.ProductoRepository;
import com.everis.escuela.repository.TipoProductoRepository;
import com.everis.escuela.service.ProductoService;
@Transactional(readOnly = true)
@Service
public class ProductoServiceImp implements ProductoService {
	
	@Autowired
	private ProductoRepository produtoRepository;
	@Autowired
	private TipoProductoRepository tipoProdutoRepository;
	
	@Override
	public Iterable<Producto> obtenerProductos() {		
		return produtoRepository.findAll();
	}
	@Transactional(readOnly = false)
	@Override
	public Producto guardarProducto(Producto producto) throws ResourceNotFoundException {
		
		TipoProducto tipoProducto = tipoProdutoRepository.findByCodigo(producto.getTipoProducto().getCodigo()).orElseThrow(()->new ResourceNotFoundException("no se encontro"));
		producto.setTipoProducto(tipoProducto);
		producto.setActivo(true);
		return produtoRepository.save(producto);
	}

	@Override
	public Producto obtenerProductoPorId(Long id) throws ResourceNotFoundException {		
		return produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontro el producto con el id") );
	}

	
	
}
