package com.tudai.ventas.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tudai.ventas.models.Producto;
import com.tudai.ventas.services.ProductoService;

@RestController
@RequestMapping("producto")
public class ProductoController {

	@Autowired
	private ProductoService service;
	private static Logger log = LoggerFactory.getLogger(ProductoController.class);
	
	@GetMapping("")
	public Iterable<Producto> getProductos(){return service.getProductos();}
	
	@GetMapping("/masvendidos")
	public Iterable<Producto> getProductosMasVendidos(){return service.getProdMasVend();}
	
	@GetMapping("/{id}")
	public ResponseEntity<Producto> getProductoById(@PathVariable long id){
		Optional<Producto> producto = this.service.findById(id);
		if (!producto.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(producto.get(),HttpStatus.OK);
		}
	}
	
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Producto> addProducto(@RequestBody Producto p){
		boolean ok = this.service.addProducto(p);
		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<Producto>(p,HttpStatus.OK);
		}
		
	}
	
	@PutMapping("/{serial}")
	Producto replaceProducto(@RequestBody Producto newp,@PathVariable Long serial){
		Optional<Producto> p = service.findById(serial);
		if (p.isPresent()) {
			this.service.deleteProducto(serial);
			return this.service.saveProducto(newp);
		} else {
			return new Producto();
		}
	}
	
	@DeleteMapping("/{serial}")
	void deleteProducto(@PathVariable Long serial) {
		this.service.deleteProducto(serial);
	}
}
