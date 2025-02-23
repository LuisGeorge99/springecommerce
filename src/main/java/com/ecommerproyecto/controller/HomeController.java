package com.ecommerproyecto.controller;

import com.ecommerproyecto.model.DetalleOrden;
import com.ecommerproyecto.model.Orden;
import com.ecommerproyecto.model.Producto;
import com.ecommerproyecto.model.Usuario;
import com.ecommerproyecto.service.IDetalleOrdenService;
import com.ecommerproyecto.service.IOrdenService;
import com.ecommerproyecto.service.IUsuarioService;
import com.ecommerproyecto.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    @Autowired
    private IDetalleOrdenService detalleOrdenService;

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

        // validar que el producto no se anada dos veces
        Integer idProducto = producto.getId();
        boolean ingresado = detalleOrdens.stream().anyMatch(p -> p.getProducto().getId()==idProducto);

        if(!ingresado) {
            detalleOrdens.add(detalleOrden);
        }




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

    @GetMapping("/getcart")
    public String getCart(Model model) {
        model.addAttribute("cart", detalleOrdens);
        model.addAttribute("orden", orden);

        return "/usuario/carrito";
    }


    @GetMapping("/order")
    public String order(Model model) {
        Usuario usuario = usuarioService.findById(1).get();


        model.addAttribute("cart", detalleOrdens);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);


        return "/usuario/resumenorden";
    }

    @GetMapping("/saveOrder")
    public String saveOrder() {
        Date fechaACreacion = new Date();
        orden.setFechaCreacion(fechaACreacion);
        orden.setNumero(ordenService.generarNumeroOrden());

        //usuario
        Usuario usuario = usuarioService.findById(1).get();

        orden.setUsuario(usuario);
        ordenService.save(orden);

        //guardar la parte de los detalles
        for(DetalleOrden dt: detalleOrdens) {
            detalleOrdenService.save(dt);
        }

        //Limpieza lista y orden
        orden = new Orden();

        detalleOrdens.clear();

        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre, Model model) {
        log.info("Nombre del producto : {}", nombre );
        List<Producto> productos =productoService.findAll().stream().filter(p ->p.getNombre().contains(nombre)).collect(Collectors.toList());

        model.addAttribute("productos", productos);
        return "/usuario/home";
    }
}
