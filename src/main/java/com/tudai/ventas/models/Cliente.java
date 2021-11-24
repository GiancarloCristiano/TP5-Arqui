package com.tudai.ventas.models;

import java.util.ArrayList;
import java.util.List;

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
 * Entidad de cliente
 */
@Entity
@Table(name="cliente")
public class Cliente {

	@Id
	private long documento;
	
	@Column
	private String nombre;
	
	@Column
	private String apellido;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="clientes",cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Ventas> ventas;
	
	public Cliente() {
		super();
	};

	public Cliente(String nombre, String apellido) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.ventas = new ArrayList<Ventas>();
	}
	
	public Cliente(Long documento, String nombre, String apellido) {
		this.documento = documento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.ventas = new ArrayList<Ventas>();
	}
	
	public long getDocumento() {
		return documento;
	}

	public void setDocumento(long documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public void setVentas(List<Ventas> ventas) {
		this.ventas = ventas;
	}
	
	public List<Ventas> getVentas(){
		return this.ventas;
	}
	
	public void addVenta(Ventas p) {
		this.ventas.add(p);
	}

	@Override
	public String toString() {
		return "Cliente [documento=" + documento + ", nombre=" + nombre + ", apellido=" + apellido + ", productos="
				+ ventas
				+ "]";
	}

	
}
