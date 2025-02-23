package com.ecommerproyecto.controller;

import com.ecommerproyecto.model.Producto;
import com.ecommerproyecto.model.Usuario;
import com.ecommerproyecto.service.ProductoService;
import com.ecommerproyecto.service.UploadFileService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private UploadFileService uploadFileService;

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("productos", productoService.findAll());

        return "productos/show";
    }

    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto,@RequestParam("img") MultipartFile file) throws IOException {
        LOGGER.info("Este es el objeto producto {}", producto);
        Usuario u = new Usuario(1, "", "", "","", "", "", "");
        producto.setUsuario(u);

        //imagen
        if(producto.getId()==null) { // validacion al crear un producto
            String nombreImagen= uploadFileService.saveImage(file);
            producto.setImagen(nombreImagen);

        }else {

        }


        productoService.save(producto);


        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Producto producto = new Producto();
        Optional<Producto> optionalProducto= productoService.get(id);
        producto = optionalProducto.get();

        LOGGER.info("Producto buscado: {}", producto);

        model.addAttribute("producto", producto);

        return "productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto,@RequestParam("img") MultipartFile file ) throws IOException {
        Producto p = new Producto();
        p=productoService.get(producto.getId()).get();


        if(file.isEmpty()) { // editamos producto pero no cambiamos imagen

            producto.setImagen(p.getImagen());
        } else { //cuando se edita tbn la imagen

            //eliminar cuando la imagen no sea la img por defecto
            if(!p.getImagen().equals("default.jpg")) {

                uploadFileService.deleteImage(p.getNombre());

            }

            String nombreImagen= uploadFileService.saveImage(file);
            producto.setImagen(nombreImagen);

        }

        producto.setUsuario(p.getUsuario());
        productoService.update(producto);


        return "redirect:/productos";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        Producto p = new Producto();
        p=productoService.get(id).get();

        //eliminar cuando la imagen no sea la img por defecto
        if(!p.getImagen().equals("default.jpg")) {

            uploadFileService.deleteImage(p.getNombre());

        }

        productoService.delete(id);
        return "redirect:/productos";
    }

}
