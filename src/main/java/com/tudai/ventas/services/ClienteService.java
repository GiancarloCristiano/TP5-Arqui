package com.tudai.ventas.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tudai.ventas.DTO.ComprasPorClienteDTO;
import com.tudai.ventas.models.Cliente;
import com.tudai.ventas.repositories.ClienteRepository;

/**
 * Servicio Rest para la entidad de Cliente
 * @see Cliente
 */
@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clientes;

	/**
	 * Obtener un cliente dado su nro de documento
	 * @param id nro de documento del cliente
	 * @return optional de cliente
	 */
	public Optional<Cliente> findById(Long id){
		return this.clientes.findById(id);
	}

	/**
	 * Obtener todos los clientes
	 * @return lista de clientes
	 */
	public List<Cliente> getClientes(){
		return this.clientes.findAll();
	}

	/**
	 * Agregar un nuevo cliente
	 * @param c cliente
	 * @return nuevo cliente
	 */
	@Transactional
	public Cliente addCliente(Cliente c) {
		return this.clientes.save(c);
	}

	/**
	 * Eliminar un cliente dado su nro de documento
	 * @param documento nro de documento del cliente
	 */
	@Transactional
	public void deleteCliente(Long documento) {
		this.clientes.deleteById(documento);
	}

	/**
	 * Agregar clientes desde un archivo csv
	 * @param c archivo csv
	 */
	@Transactional
	public void agregarClientes(CSVParser c) {
		for(CSVRecord row: c) {
			Cliente cliente = new Cliente(Long.parseLong(row.get("documento")), row.get("nombre"), row.get("apellido"));
			this.addCliente(cliente);
		}
	}

	/**
	 * Obtener todos los clientes y el monto total de sus compras
	 * @return lista de clientes con su monto de compras
	 */
	public List<ComprasPorClienteDTO> totalComprasClientes(){
		return this.clientes.selectTotalComprasClientes();
	}

	/**
	 * Obtener la cantidad de compras de un cliente dado por id
	 * @param cliente numero de documento del cliente
	 * @return cantidad de compras
	 */
    public Long cantVentasPorCliente(Long cliente) {
		return this.clientes.getCantVentas(cliente);
	}

	/**
	 * Obtener la cantidad de compras en una fecha dada de un cliente dado por id
	 * @param cliente numero de documento del cliente
	 * @param fecha fecha de la compra
	 * @return cantidad de compras
	 */
	public Long cantVentasPorClientePorDia(Long cliente, Date fecha) {
		return this.clientes.getCantVentasPorDia(cliente, fecha);
	}
}
