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
		Optional<Producto> po = serviceProducto.findById(v.getProducto());
		Optional<Cliente> co = serviceCliente.findById(v.getCliente());
		Long cantVentas = serviceCliente.cantVentasPorCliente(v.getCliente());
		boolean ok = false;
		if ((cantVentas < 3) && !po.isEmpty() && !co.isEmpty()) {
			Producto p = po.get();
			Cliente c = co.get();
			ok = this.service.addVenta(new Ventas(p,c)) != null;
		}
		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<VentasJson>(v,HttpStatus.OK);
		}
		
	}


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
		if (!v.isEmpty()) {
			this.service.deleteVenta(serial);
			return this.service.addVenta(newv);
		} else {
			return new Ventas();
		}
	}


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
