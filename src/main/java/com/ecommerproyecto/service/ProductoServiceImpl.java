package com.ecommerproyecto.service;

import com.ecommerproyecto.model.Producto;
import com.ecommerproyecto.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private IProductoRepository IProductoRepository;

    @Override
    public Producto save(Producto producto) {
        return IProductoRepository.save(producto);
    }

    @Override
    public Optional<Producto> get(Integer id) {
        return IProductoRepository.findById(id);
    }

    @Override
    public void update(Producto producto) {

       IProductoRepository.save(producto);

    }

    @Override
    public void delete(Integer id) {
        IProductoRepository.deleteById(id);

    }

    @Override
    public List<Producto> findAll() {
        return IProductoRepository.findAll();
    }
}
