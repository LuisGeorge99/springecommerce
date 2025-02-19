package com.ecommerproyecto.service;

import com.ecommerproyecto.model.Usuario;
import com.ecommerproyecto.repository.IUsuarioRepository;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    @Autowired
    private IUsuarioRepository usuarioRepository;


    @Override
    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
