package com.tudai.ventas.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tudai.ventas.models.Producto;
import com.tudai.ventas.repositories.ProductoRepository;

/**
 * Servicio Rest para la entidad de Producto
 * @see Producto
 */
@Service
public class ProductoService{
	
	@Autowired
	private ProductoRepository productos;

	/**
	 * Obtener un producto dado su nro de serial
	 * @param serial nro de serial del producto
	 * @return producto
	 */
	public Optional<Producto> findById(Long serial) {
		return this.productos.findById(serial);		 
	}

	/**
	 * Obtener todos los productos
	 * @return lista de productos
	 */
	public List<Producto> getProductos(){
		return this.productos.findAll();
	}

	/**
	 * Agregar un nuevo producto
	 * @param p producto
	 * @return nuevo producto
	 */
	@Transactional
	public boolean addProducto(Producto p) {
		return this.productos.save(p) != null;
	}

	/**
	 * Guardar un producto
	 * @param p producto
	 * @return producto
	 */
	@Transactional	
	public Producto saveProducto(Producto p) {
		return this.productos.save(p);
	}

	/**
	 * Eliminar un producto dado su nro de serial
	 * @param serial nro de serial del producto
	 */
	@Transactional
	public void deleteProducto(Long serial) {
		this.productos.deleteById(serial);
	}

	/**
	 * Obtener el producto mas vendido
	 * @return lista de en un solo producto
	 */
	public List<Producto> getProdMasVend(){
		return this.productos.selectProductoMasVendido(PageRequest.of(0,1));
	}

	/*
	@Transactional
	public void agregarProductos(CSVParser c) {
		for(CSVRecord row: c) {
			Producto producto = new Producto(Long.parseLong(row.get("serial")), row.get("nombre"), Integer.parseInt(row.get("precio")), Long.parseLong(row.get("stock")));
			this.addProducto(producto);
		}
	}
	*/
}
