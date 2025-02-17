package com.ecommerproyecto.service;

import com.ecommerproyecto.model.DetalleOrden;
import org.springframework.stereotype.Service;

@Service
public interface IDetalleOrdenService {

    DetalleOrden save (DetalleOrden detalleOrden);

}
