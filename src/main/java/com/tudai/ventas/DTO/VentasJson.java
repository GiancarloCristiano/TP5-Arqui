package com.tudai.ventas.DTO;

import java.sql.Date;


public class VentasJson {
	private Date fecha_venta;

    private Long producto;

    private Long cliente;

	public VentasJson(Date fecha_venta, Long productos, Long clientes) {
		super();
		this.fecha_venta = fecha_venta;
		this.producto = productos;
		this.cliente = clientes;
	}

	public Date getFecha_venta() {
		return fecha_venta;
	}

	public void setFecha_venta(Date fecha_venta) {
		this.fecha_venta = fecha_venta;
	}

	public Long getProducto() {
		return producto;
	}

	public void setProductos(Long productos) {
		this.producto = productos;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setClientes(Long clientes) {
		this.cliente = clientes;
	}    
    
}
