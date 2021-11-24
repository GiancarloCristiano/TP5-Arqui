package com.tudai.ventas.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tudai.ventas.models.Ventas;
import com.tudai.ventas.repositories.VentasRepository;

/**
 * Servicio Rest para la entidad de Ventas
 * @see Ventas
 */
@Service
public class VentasService {

	@Autowired
	private VentasRepository ventas;

	/**
	 * Obtener todas las ventas
	 * @return lista de ventas
	 */
	public List<Ventas> getVentas(){
		return this.ventas.findAll();
	}

	/**
	 * Obtener una venta dado su numero de serial
	 * @param serial numero identificador de ventas
	 * @return optional de venta
	 */
	public Optional<Ventas> findById(Long serial) {
		return this.ventas.findById(serial);
	}

	/**
	 * Agregar una nueva venta
	 * @param v venta
	 * @return venta nueva
	 */
	@Transactional
	public Ventas addVenta(Ventas v) {
		return this.ventas.save(v);
	}

	/**
	 * Eliminar una venta dado su numero de serial
	 * @param serial numero de serial de la venta
	 */
	@Transactional
	public void deleteVenta(Long serial) {
		this.ventas.deleteById(serial);
	}

	/**
	 * Obtener todas las ventas por dia
	 * @return lista de ventas de cada dia
	 */
	public List<Ventas> ventasTotalesPorDia(){
		return this.ventas.selectVentasDiarias();
	}

	/*
	@Transactional
	public void agregarVentas(CSVParser c) {
		for(CSVRecord row: c) {
			Ventas venta = new Ventas(row.get("fecha_venta"), row.get("productos"), Integer.parseInt(row.get("id_cliente")));
			this.addVenta(venta);
		}
	}
	*/

}
