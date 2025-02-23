package com.ecommerproyecto.controller;

import com.ecommerproyecto.model.Usuario;
import com.ecommerproyecto.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {


    private final Logger logger= LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    IUsuarioService usuarioService;

    @GetMapping("/registro")
    public String create() {
        return "/usuario/registro";
    }

    @PostMapping("/save")
    public String save(Usuario usuario ) {
        logger.info("Usuario registro {}", usuario);
        usuario.setTipo("USER");
        usuarioService.save(usuario);


        return "redirect:/";
    }

}
