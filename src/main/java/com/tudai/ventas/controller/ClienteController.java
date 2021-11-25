package com.tudai.ventas.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tudai.ventas.DTO.ComprasPorClienteDTO;
import com.tudai.ventas.models.Cliente;
import com.tudai.ventas.services.ClienteService;

@RestController
@RequestMapping("cliente")
public class ClienteController {

	@Autowired
	private ClienteService service;
	private static Logger log = LoggerFactory.getLogger(ClienteService.class);
	
	@GetMapping("")
	public Iterable<Cliente> getClientes(){return service.getClientes();}
	
	@GetMapping("/totalcompras")
	public List<ComprasPorClienteDTO> getComprasTotales(){
		return service.totalComprasClientes();
	}
	
	@PostMapping("")
	public ResponseEntity<Cliente> addCliente(@RequestBody Cliente c){
		boolean ok = this.service.addCliente(c) != null;
		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<Cliente>(c,HttpStatus.OK);
		}	
	}
	
	@PutMapping("/{documento}")
	Cliente replaceCliente(@RequestBody Cliente cliente,@PathVariable Long documento) {
		Optional<Cliente> c = service.findById(documento);
		if (c.isPresent()) {
			this.service.deleteCliente(documento);
			return this.service.addCliente(cliente);
		}
		return new Cliente();
	}
	
	@DeleteMapping("/{documento}")
	void deleteProducto(@PathVariable Long documento) {
		this.service.deleteCliente(documento);
	}
}
