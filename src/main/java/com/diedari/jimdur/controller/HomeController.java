package com.diedari.jimdur.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.diedari.jimdur.model.Categoria;
import com.diedari.jimdur.model.Producto;
import com.diedari.jimdur.service.CategoriaService;
import com.diedari.jimdur.service.ProductoService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String index(Model model) {
        List<Producto> ultimosProductos = productoService.listarUltimosProductos(4);
        List<Categoria> categorias = categoriaService.obtenerCategoriaPorEstado(true);
        model.addAttribute("ultimosProductos", ultimosProductos);
        model.addAttribute("categorias", categorias);

        // NAVBAR DINAMICA:
        model.addAttribute("activePage", "inicio");

        return "index";
    }

    @GetMapping("/nosotros")
    public String nosotrosForm(Model model) {

        // NAVBAR DINAMICA:
        model.addAttribute("activePage", "nosotros");
        return "/user/nosotros";
    }

    @GetMapping("/productos")
    public String productosForm(Model model) {
        List<Producto> productos = productoService.listarTodosLosProductos();
        model.addAttribute("productos", productos);

        // NAVBAR DINAMICA:
        model.addAttribute("activePage", "productos");

        return "/user/productos";
    }

    @GetMapping("/contacto")
    public String contactoForm(Model model) {
        String mapUrl = "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3973.6374988954863!2d-80.65618082542369!3d-5.161883952143082!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x904a1b88948eae13%3A0x1d2875411b61bb19!2sGrupo%20Jimdur%20%26%20Motorepuestos%20Jimenez!5e0!3m2!1ses!2spe!4v1730094624124!5m2!1ses!2spe";
        model.addAttribute("mapUrl", mapUrl);

        // NAVBAR DINAMICA:
        model.addAttribute("activePage", "contacto");

        return "/user/contacto";
    }

    @GetMapping("/producto/{slug}")
    public String detalleProductoForm(@PathVariable String slug, Model model) {
        // NAVBAR DINAMICA:
        Producto producto = productoService.obtenerProductoPorSlug(slug);
        if (producto == null) {
            return "redirect:/productos"; // Redirigir a la lista de productos si no se encuentra el producto
        }
        model.addAttribute("producto", producto);
        return "/user/detalle-producto";
    }

}
