package com.tudai.ventas.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.tudai.ventas.models.Producto;

/**
 * Repositorio de productos
 * @see com.tudai.ventas.services.ProductoService
 */
public interface ProductoRepository extends JpaRepository<Producto,Long> {

	/**
	 * Obtener un producto dado su nombre
	 * @param name nombre del producto
	 */
	@Query("SELECT p FROM Producto p WHERE p.nombre  = :name")
	public List<Producto> findByNombre(String name);


	/**
	 * Obtener el producto mas vendido
	 * @param pageable se trunca la lista en un solo producto
	 */
	@Query("SELECT p FROM Producto p JOIN p.compradores v ORDER BY size(p.compradores) DESC")
	public List<Producto> selectProductoMasVendido(Pageable pageable);

}
