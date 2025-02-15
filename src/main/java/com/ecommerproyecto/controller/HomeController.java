package com.ecommerproyecto.controller;

import com.ecommerproyecto.model.DetalleOrden;
import com.ecommerproyecto.model.Orden;
import com.ecommerproyecto.model.Producto;
import com.ecommerproyecto.repository.ProductoRepository;
import com.ecommerproyecto.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoService productoService;

    //para almacenar los detalles de la orden
    private List<DetalleOrden> detalleOrdens = new ArrayList<DetalleOrden>();

    //almacena datos de la orden
    Orden orden = new Orden();


    @GetMapping("")
    public String home(Model model) {

        model.addAttribute("productos", productoService.findAll());
        return "usuario/home";
    }

    @GetMapping("/productohome/{id}")
    public String homeProducto(@PathVariable Integer id,Model model) {
        log.info("Id enviado como parametro {}", id);
        Producto producto = new Producto();
        Optional<Producto> productoOptional = productoService.get(id);
        producto = productoOptional.get();

        model.addAttribute("producto", producto);

        return "usuario/productohome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal=0;

        Optional<Producto> optionalProducto = productoService.get(id);
        log.info("El producto ananido : {}", optionalProducto.get());
        log.info("Cantidad : {}", cantidad);

        producto=optionalProducto.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);

        detalleOrdens.add(detalleOrden);

        sumaTotal=detalleOrdens.stream().mapToDouble(dt->dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalleOrdens);
        model.addAttribute("orden", orden);





        return "usuario/carrito";
    }


    // quitar un producto del carrito

    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Integer id, Model model) {
        //Lista nueva de productos
        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

        for(DetalleOrden detalleOrden: detalleOrdens) {
            if(detalleOrden.getProducto().getId()!=id) {
                ordenesNueva.add(detalleOrden);
            }


        }
        //poner la nueva lista con los productos restantes
        detalleOrdens=ordenesNueva;

        double sumaTotal=0;

        sumaTotal=detalleOrdens.stream().mapToDouble(dt->dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalleOrdens);
        model.addAttribute("orden", orden);


        return "usuario/carrito";
    }

}
