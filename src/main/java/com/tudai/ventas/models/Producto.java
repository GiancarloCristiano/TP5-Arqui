package com.tudai.ventas.models;

import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * Entidad de producto
 */
@Entity
@Table(name="producto")
public class Producto {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long serial;
	
	@Column
	private String nombre;
	
	@Column
	private Integer precio;
	
	@Column
	private Long stock;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="productos",cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Ventas> compradores;
	
	public Producto() {
		super();
	}

	public Producto(String nombre, Integer precio) {
		super();
		this.nombre = nombre;
		this.precio = precio;
	}

	public Producto(long serial, String nombre, Integer precio, long stock) {
		super();
		this.serial = serial;
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
	}
	
	public Producto(String nombre, Integer precio, long stock) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
	}

	public Long getSerial() {
		return serial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String name) {
		this.nombre = name;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer price) {
		this.precio = price;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public List<Ventas> getCompradores() {
		return compradores;
	}

	public void setCompradores(List<Ventas> compradores) {
		this.compradores = compradores;
	}

	@Override
	public String toString() {
		return "Producto [serial=" + serial + ", name=" + nombre + ", price=" + precio + ", stock=" + stock
				+ ", compradores=" + compradores + "]";
	}

	
}
