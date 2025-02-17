package com.ecommerproyecto.service;

import com.ecommerproyecto.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface IUsuarioService {

    Optional<Usuario> findById(Integer id);
}
