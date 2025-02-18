package com.ecommerproyecto.service;

import com.ecommerproyecto.model.Orden;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IOrdenService {

    List<Orden> findAll();

    Orden save (Orden orden);

    String generarNumeroOrden();

}
