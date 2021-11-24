package com.tudai.ventas.DTO;

/**
 * DTO de compras por cliente
 * @see com.tudai.ventas.models.Cliente
 */
public class ComprasPorClienteDTO {
	private Long documento;
	private String nombre;
	private String apellido;
	private Long total_compras;
	
	
	public ComprasPorClienteDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ComprasPorClienteDTO(Long documento, String nombre, String apellido, Long total_compras) {
		this.documento = documento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.total_compras = total_compras;
	}

	public Long getDocumento() {
		return documento;
	}

	public void setDocumento(Long documento) {
		this.documento = documento;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getTotal_Compras() {
		return total_compras;
	}
	public void setTotal_Compras(Long total_compras) {
		this.total_compras = total_compras;
	}

	@Override
	public String toString() {
		return "ComprasPorClienteDTO [nombre=" + nombre + ", total_compras=" + total_compras + "]";
	}
	
	
}
