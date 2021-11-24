package com.tudai.ventas.controller;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

/**
 * Controlador Rest para la entidad de Cliente
 * @see Cliente
 */
@RestController
@RequestMapping("cliente")
@Api(description = "Api Rest de Cliente", tags = "Cliente")
public class ClienteController {

	@Autowired
	private ClienteService service;
	private static Logger log = LoggerFactory.getLogger(ClienteService.class);

	/**
	 * Obtener todos los clientes
	 * @return lista de clientes
	 */
	@ApiOperation(value = "Obtener todos los clientes", response = Iterable.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 401, message = "Acceso no autorizado"),
			@ApiResponse(code = 403, message = "Acceso prohibido"),
			@ApiResponse(code = 404, message = "No encontrado"),
			@ApiResponse(code = 500, message = "Error interno del servidor")
	})
	@GetMapping("")
	public Iterable<Cliente> getClientes(){return service.getClientes();}


	/**
	 * Obtener todos los clientes y el monto total de sus compras
	 * @return lista de clientes con su monto de compras
	 */
	@ApiOperation(value = "Obtener todos los clientes y el monto total de sus compras", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 401, message = "Acceso no autorizado"),
			@ApiResponse(code = 403, message = "Acceso prohibido"),
			@ApiResponse(code = 404, message = "No encontrado"),
			@ApiResponse(code = 500, message = "Error interno del servidor")
	})
	@GetMapping("/totalcompras")
	public List<ComprasPorClienteDTO> getComprasTotales(){
		return service.totalComprasClientes();
	}


	/**
	 * Obtener un cliente dado su nro de documento
	 * @param documento nro de documento del cliente
	 * @return entidad de respuesta con el cliente y/o codigo de estado http
	 */
	@ApiOperation(value = "Obtener un cliente dado su nro de documento", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 401, message = "Acceso no autorizado"),
			@ApiResponse(code = 403, message = "Acceso prohibido"),
			@ApiResponse(code = 404, message = "No encontrado"),
			@ApiResponse(code = 500, message = "Error interno del servidor")
	})
	@GetMapping("/{documento}")
	public ResponseEntity<Cliente> getClienteById(@PathVariable long documento){
		Optional<Cliente> cliente = this.service.findById(documento);
		if (!cliente.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(cliente.get(),HttpStatus.OK);
		}
	}


	/**
	 * Agregar un nuevo cliente
	 * @param c cliente
	 * @return entidad de respuesta con el cliente y/o codigo de estado http
	 */
	@ApiOperation(value = "Agregar un nuevo cliente", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 401, message = "Acceso no autorizado"),
			@ApiResponse(code = 403, message = "Acceso prohibido"),
			@ApiResponse(code = 404, message = "No encontrado"),
			@ApiResponse(code = 500, message = "Error interno del servidor")
	})
	@PostMapping("")
	public ResponseEntity<Cliente> addCliente(@RequestBody Cliente c){
		boolean ok = this.service.addCliente(c) != null;
		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<Cliente>(c,HttpStatus.OK);
		}	
	}


	/**
	 * Modificar un cliente dado su numero de documento
	 * @param cliente nuevo objeto cliente
	 * @param documento id del cliente a reemplazar por el nuevo
	 * @return nuevo cliente
	 */
	@ApiOperation(value = "Modificar un cliente dado su nro. de documento", response = Cliente.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 401, message = "Acceso no autorizado"),
			@ApiResponse(code = 403, message = "Acceso prohibido"),
			@ApiResponse(code = 404, message = "No encontrado"),
			@ApiResponse(code = 500, message = "Error interno del servidor")
	})
	@PutMapping("/{documento}")
	Cliente replaceCliente(@RequestBody Cliente cliente,@PathVariable Long documento) {
		Optional<Cliente> c = service.findById(documento);
		if (c.isPresent()) {
			this.service.deleteCliente(documento);
			return this.service.addCliente(cliente);
		}
		return new Cliente();
	}


	/**
	 * Eliminar un cliente dado su nro de documento
	 * @param documento nro de documento del cliente
	 */
	@ApiOperation(value = "Eliminar un cliente dado su nro. de documento")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 401, message = "Acceso no autorizado"),
			@ApiResponse(code = 403, message = "Acceso prohibido"),
			@ApiResponse(code = 404, message = "No encontrado"),
			@ApiResponse(code = 500, message = "Error interno del servidor")
	})
	@DeleteMapping("/{documento}")
	void deleteCliente(@PathVariable Long documento) {
		this.service.deleteCliente(documento);
	}
}
