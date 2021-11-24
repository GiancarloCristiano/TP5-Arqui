package com.tudai.ventas.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tudai.ventas.DTO.ComprasPorClienteDTO;
import com.tudai.ventas.models.Cliente;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Repositorio de cliente
 * @see com.tudai.ventas.services.ClienteService
 */
public interface ClienteRepository extends JpaRepository<Cliente,Long>{

	/**
	 * Obtener un cliente dado su nombre
	 * @param name nombre del cliente
	 */
	@Query("SELECT c FROM Cliente c WHERE c.nombre  = :name")
	public List<Cliente> findByName(String name);


	/**
	 * Generar reporte de clientes y el monto total de sus compras
	 */
	@GetMapping(value="/totalcompras",produces= MediaType.APPLICATION_JSON_VALUE)
	@Query("SELECT NEW com.tudai.ventas.DTO.ComprasPorClienteDTO(c.documento, c.nombre, c.apellido, SUM(p.precio) AS total_compras) "
			+ "FROM Cliente AS c JOIN c.ventas AS v JOIN v.productos AS p GROUP BY c.documento, c.nombre, c.apellido ORDER BY total_compras DESC")
	public List<ComprasPorClienteDTO> selectTotalComprasClientes();

	/**
	 * Obtener la cantidad de compras de un cliente dado por id
	 * @param cliente numero de documento del cliente
	 */
	@Query("SELECT size(c.ventas) FROM Cliente c WHERE c.documento = :cliente")
	Long getCantVentas(Long cliente);


	/**
	 * Obtener la cantidad de compras en una fecha dada de un cliente dado por id
	 * @param cliente numero de documento del cliente
	 * @param fecha fecha de la compra
	 */
	@Query("SELECT size(c.ventas) FROM Cliente c JOIN c.ventas AS v WHERE c.documento = :cliente AND v.fecha_venta = :fecha")
	Long getCantVentasPorDia(Long cliente, Date fecha);
}
