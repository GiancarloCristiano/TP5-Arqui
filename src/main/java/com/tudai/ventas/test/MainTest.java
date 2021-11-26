package com.tudai.ventas.test;

import com.tudai.ventas.DTO.VentasJson;
import com.tudai.ventas.controller.ClienteController;
import com.tudai.ventas.controller.ProductoController;
import com.tudai.ventas.controller.VentasController;
import com.tudai.ventas.models.Cliente;
import com.tudai.ventas.models.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Main para tests
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainTest {
    @LocalServerPort
    private int port;
    @Autowired
    ClienteController clienteController;
    @Autowired
    ProductoController productoController;
    @Autowired
    VentasController ventasController;
    @Autowired
    private TestRestTemplate restTemplate;


    /**
     * Prueba de alta de nuevo cliente
     */
    @Test
    public void altaClienteOK() {
        Cliente cliente = new Cliente(new Long(999999), "Juan", "Rodriguez" );
        clienteController.addCliente(cliente);
        assertEquals(cliente.getDocumento(), clienteController.getClienteById(999999).getBody().getDocumento());
    }

    /**
     * Prueba de alta de nuevo producto
     */
    @Test
    public void altaProductoOK() {
        Producto puerro = new Producto("puerro", 100, 10);
        productoController.addProducto(puerro);
        Iterable<Producto> iterable = productoController.getProductos();
        long cuentaSerial = iterable.spliterator().getExactSizeIfKnown();
        assertEquals(puerro.getSerial(), productoController.getProductoById(cuentaSerial).getBody().getSerial());
    }

    /**
     * Prueba del limitador de ventas por cliente
     */
    @Test
    public void limiteVenta() {
        long documento = (long)(Math.random() * 999999);
        Cliente cliente = new Cliente(documento, "Andres", "Diaz Pace" );
        clienteController.addCliente(cliente);
        java.sql.Date hoy = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        VentasJson v1 = new VentasJson(hoy, new Long (1), documento);
        VentasJson v2 = new VentasJson(hoy, new Long (2), documento);
        VentasJson v3 = new VentasJson(hoy, new Long (3), documento);
        VentasJson v4 = new VentasJson(hoy, new Long (4), documento);
        assertEquals(ventasController.addVenta(v1).getStatusCode(), HttpStatus.OK);
        assertEquals(ventasController.addVenta(v2).getStatusCode(), HttpStatus.OK);
        assertEquals(ventasController.addVenta(v3).getStatusCode(), HttpStatus.OK);
        assertEquals(ventasController.addVenta(v4).getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
    }

}