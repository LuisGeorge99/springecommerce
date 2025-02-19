package com.ecommerproyecto.controller;

import com.ecommerproyecto.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    IUsuarioService usuarioService;

    @GetMapping("/registro")
    public String create() {
        return "/usuario/registro";
    }

}
