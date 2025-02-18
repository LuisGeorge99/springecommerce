package com.ecommerproyecto.service;

import com.ecommerproyecto.model.Orden;
import com.ecommerproyecto.repository.IOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenServiceImpl implements IOrdenService{

    @Autowired
    private IOrdenRepository ordenRepository;

    @Override
    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }

    public String generarNumeroOrden() {
        int numero=0;
        String numeroConCatenado ="";

        List<Orden> ordenes = findAll();
        List<Integer> numeros = new ArrayList<Integer>();
        ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));

        if(ordenes.isEmpty()) {
            numero=1;

        }else {
            numero= numeros.stream().max(Integer::compare).get();
            numero++;
        }

        if(numero<10) {
            numeroConCatenado="000000000"+String.valueOf(numero);
        }else if (numero<100) {
            numeroConCatenado="00000000"+String.valueOf(numero);
        }else if (numero<1000) {
            numeroConCatenado="0000000"+String.valueOf(numero);
        }else if (numero<10000) {
            numeroConCatenado="000000"+String.valueOf(numero);
        }


        return numeroConCatenado;
    }
}
