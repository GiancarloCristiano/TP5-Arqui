package com.tudai.ventas;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

import com.tudai.ventas.services.ClienteService;
import com.tudai.ventas.services.ProductoService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Main del proyecto
 */
@SpringBootApplication
public class Tp5IntegradorSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(Tp5IntegradorSpringApplication.class, args);
		cargarDatos();


	}

	/**
	 * Configuracion de CORS
	 */
	@Configuration
	public class CorsConfig {
	    @Bean
	    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.setAllowedOriginPatterns(Collections.singletonList("*"));
	        config.setAllowedMethods(Collections.singletonList("*"));
	        config.setAllowedHeaders(Collections.singletonList("*"));
	        source.registerCorsConfiguration("/**", config);
	        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
	        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
	        return bean;
	    }
	}

	/**
	 * Cargador de datos desde un csv para las tres endidades
	 */
	public static void cargarDatos() {
//		try {
//			CSVParser clienteCSV = CSVFormat.DEFAULT.withHeader().parse(new FileReader("csv/cliente.csv"));
//			ClienteService clienteService = new ClienteService();
//			clienteService.agregarClientes(clienteCSV);

//            CSVParser productoCSV = CSVFormat.DEFAULT.withHeader().parse(new FileReader("csv/producto.csv"));
//            ProductoService productoService = new ProductoService();
//            productoService.agregarProductos(productoCSV);

//
//            CSVParser ventasCSV = CSVFormat.DEFAULT.withHeader().parse(new FileReader("csv/ventas.csv"));
//            VentasService ventasService = new VentasService();
//            ventasService.agregarVentas(ventasCSV);

//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
