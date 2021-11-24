package com.tudai.ventas.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Entidad de ventas
 */
@Entity
@Table(name="ventas")
public class Ventas implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long serial_venta;

	@Column
	private Date fecha_venta;

	@ManyToOne
	@JoinColumn(name = "serial")
	private Producto productos;

	@ManyToOne
	@JoinColumn(name = "documento")
	private Cliente clientes;

	public Ventas(){}

	public Ventas(Producto productos, Cliente clientes) {
		super();
		this.fecha_venta = new Date();
		this.productos = productos;
		this.clientes = clientes;
	}

	public Date getFecha_venta() {
		return fecha_venta;
	}

	public void setFecha_venta(Date fecha_venta) {
		this.fecha_venta = fecha_venta;
	}

	public Producto getProductos() {
		return productos;
	}

	public void setProductos(Producto productos) {
		this.productos = productos;
	}

	public Cliente getClientes() {
		return clientes;
	}

	public void setClientes(Cliente clientes) {
		this.clientes = clientes;
	}

	@Override
	public String toString() {
		return "Ventas [serial_venta=" + serial_venta + ", fecha_venta=" + fecha_venta + ", productos=" + productos
				+ ", clientes=" + clientes + "]";
	}
	
}
