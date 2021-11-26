package com.tudai.ventas.controller;

import java.sql.Date;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tudai.ventas.DTO.VentasJson;
import com.tudai.ventas.models.Cliente;
import com.tudai.ventas.models.Producto;
import com.tudai.ventas.models.Ventas;
import com.tudai.ventas.services.ClienteService;
import com.tudai.ventas.services.ProductoService;
import com.tudai.ventas.services.VentasService;

/**
 * Controlador Rest para la entidad de Ventas
 * @see Ventas
 */
@RestController
@RequestMapping("ventas")
@Api(description = "Api Rest de Ventas", tags = "Ventas")
public class VentasController {

	@Autowired
	private VentasService service;
	@Autowired
	private ProductoService serviceProducto;
	@Autowired
	private ClienteService serviceCliente;
	
	private static Logger log = LoggerFactory.getLogger(ProductoController.class);


	/**
	 * Obtener todas las ventas
	 * @return lista de ventas
	 */
	@ApiOperation(value = "Obtener todas las ventas", response = Iterable.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 401, message = "Acceso no autorizado"),
			@ApiResponse(code = 403, message = "Acceso prohibido"),
			@ApiResponse(code = 404, message = "No encontrado"),
			@ApiResponse(code = 500, message = "Error interno del servidor")
	})
	@GetMapping("")
	public Iterable<Ventas> getVentas(){return service.getVentas();}


	/**
	 * Obtener todas las ventas por dia
	 * @return lista de ventas de cada dia
	 */
	@ApiOperation(value = "Obtener todas las ventas por día", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 401, message = "Acceso no autorizado"),
			@ApiResponse(code = 403, message = "Acceso prohibido"),
			@ApiResponse(code = 404, message = "No encontrado"),
			@ApiResponse(code = 500, message = "Error interno del servidor")
	})
	@GetMapping("/total/dia")
	public List<Ventas> getTotalVentas(){
		return service.ventasTotalesPorDia();
	}


	/**
	 * Agregar una nueva venta
	 * @param v venta
	 * @return entidad de respuesta con la venta y/o codigo de estado http
	 */
	@ApiOperation(value = "Agregar una nueva venta", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 401, message = "Acceso no autorizado"),
			@ApiResponse(code = 403, message = "Acceso prohibido"),
			@ApiResponse(code = 404, message = "No encontrado"),
			@ApiResponse(code = 500, message = "Error interno del servidor")
	})
	@PostMapping(value="",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VentasJson> addVenta(@RequestBody VentasJson v){
		if (this.service.addVenta(v) != null) {
			return new ResponseEntity<VentasJson>(v,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}


	/**
	 * Modificar una venta dado su numero de serial
	 * @param newv nuevo objeto venta
	 * @param serial id de la venta a reemplazar por la nueva
	 * @return nueva venta
	 */
	@ApiOperation(value = "Actualizar una venta dado un serial", response = Ventas.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 401, message = "Acceso no autorizado"),
			@ApiResponse(code = 403, message = "Acceso prohibido"),
			@ApiResponse(code = 404, message = "No encontrado"),
			@ApiResponse(code = 500, message = "Error interno del servidor")
	})
	@PutMapping("/{serial}")
	Ventas replaceVenta(@RequestBody Ventas newv,@PathVariable Long serial){
		Optional<Ventas> v = service.findById(serial);
		if (v.isPresent()) {
			this.service.deleteVenta(serial);
			return this.service.addVenta(newv);
		} else {
			return new Ventas();
		}
	}


	/**
	 * Eliminar una venta dado su numero de serial
	 * @param serial numero de serial de la venta
	 */
	@ApiOperation(value = "Eliminar una venta dado un serial")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 401, message = "Acceso no autorizado"),
			@ApiResponse(code = 403, message = "Acceso prohibido"),
			@ApiResponse(code = 404, message = "No encontrado"),
			@ApiResponse(code = 500, message = "Error interno del servidor")
	})
	@DeleteMapping("/{serial}")
	void deleteProducto(@PathVariable Long serial) {
		this.service.deleteVenta(serial);
	}
}
