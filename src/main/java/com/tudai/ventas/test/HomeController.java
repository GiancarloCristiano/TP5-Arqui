package com.tudai.ventas.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controlador base para tests
 * @see MainTest
 */
@Controller
public class HomeController {

    /**
     * Obtener una respuesta en la home
     * @return string de 'Hello, World'
     */
    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return "Hello, World";
    }

}