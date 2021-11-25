package com.tudai.ventas.controller;

import java.util.List;
import java.util.Optional;

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
public class VentasController {

	@Autowired
	private VentasService service;
	@Autowired
	private ProductoService serviceProducto;
	@Autowired
	private ClienteService serviceCliente;
	
	private static Logger log = LoggerFactory.getLogger(ProductoController.class);
	
	@GetMapping("")
	public Iterable<Ventas> getProductos(){return service.getVentas();}
	
	@GetMapping("/total/dia")
	public List<Ventas> getTotalVentas(){
		return service.ventasTotalesPorDia();
	}
	
	@PostMapping(value="/",consumes = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<VentasJson> addVenta(@RequestBody VentasJson v){
		Optional<Producto> po = serviceProducto.findById(v.getProducto());
		Optional<Cliente> co = serviceCliente.findById(v.getCliente());
		Long cantVentas = serviceCliente.cantVentasPorCliente(v.getCliente());
		boolean ok = false;
		if ((cantVentas < 3) && po.isPresent() && co.isPresent()) {
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
	
//	@PutMapping("/{serial}")
//	Ventas replaceVenta(@RequestBody Ventas newv,@PathVariable Long serial){
//		Optional<Ventas> v = service.findById(serial);
//		if (!v.isEmpty()) {
//			this.service.deleteVenta(serial);
//			return this.service.addVenta(newv);
//		} else {
//			return new Ventas();
//		}
//	}
	
	@DeleteMapping("/{serial}")
	void deleteProducto(@PathVariable Long serial) {
		this.service.deleteVenta(serial);
	}
}
