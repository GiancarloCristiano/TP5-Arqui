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

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clientes;
	
	public Optional<Cliente> findById(Long id){
		return this.clientes.findById(id);
	}
	
	public List<Cliente> getClientes(){
		return this.clientes.findAll();
	}
	
	@Transactional
	public Cliente addCliente(Cliente c) {
		return this.clientes.save(c);
	}
	
	@Transactional
	public void deleteCliente(Long documento) {
		this.clientes.deleteById(documento);
	}

	@Transactional
	public void agregarClientes(CSVParser c) {
		for(CSVRecord row: c) {
			Cliente cliente = new Cliente(Long.parseLong(row.get("documento")), row.get("nombre"), row.get("apellido"));
			this.addCliente(cliente);
		}
	}

	
	public List<ComprasPorClienteDTO> totalComprasClientes(){
		return this.clientes.selectTotalComprasClientes();
	}

    public Long cantVentasPorCliente(Long cliente) {
		return this.clientes.getCantVentas(cliente);
	}

	public Long cantVentasPorClientePorDia(Long cliente, Date fecha) {
		return this.clientes.getCantVentasPorDia(cliente, fecha);
	}
}
