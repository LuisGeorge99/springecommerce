package com.ecommerproyecto.service;

import com.ecommerproyecto.model.DetalleOrden;
import com.ecommerproyecto.repository.IDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenServiceImpl implements IDetalleOrdenService{

    @Autowired
    private IDetalleRepository detalleRepository;

    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return detalleRepository.save(detalleOrden);
    }
}
