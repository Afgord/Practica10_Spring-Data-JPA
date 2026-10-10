package com.example.GameVault.controller;

import com.example.GameVault.model.Juego;
import com.example.GameVault.service.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GameController {

    @Autowired
    private JuegoService juegoService;

    @GetMapping("/fragments-demo")
    public String fragmentsDemo() {
        return "fragments-demo";
    }

    @GetMapping({"/", "/juegos"})
    public String listarJuegos(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "plataforma", required = false) String plataforma,
            @RequestParam(value = "precioMax", required = false) Double precioMax,
            @RequestParam(value = "orden", required = false) String orden,
            Model model) {

        model.addAttribute(
                "juegos",
                juegoService.buscarCatalogo(
                        q,
                        plataforma,
                        precioMax,
                        orden
                )
        );

        model.addAttribute("q", q);
        model.addAttribute("plataforma", plataforma);
        model.addAttribute("precioMax", precioMax);
        model.addAttribute("orden", orden);

        return "juegos";
    }

    @GetMapping("/juegos/nuevo")
    public String mostrarFormulario() {
        return "formulario";
    }

    @PostMapping("/juegos")
    public String guardarJuego(@RequestParam("titulo") String titulo,
                               @RequestParam("genero") String genero,
                               @RequestParam("plataforma") String plataforma,
                               @RequestParam("precio") Double precio,
                               @RequestParam(value = "anio", required = false) Integer anio,
                               @RequestParam("descripcion") String descripcion,
                               @RequestParam("portada") MultipartFile portada){

        Juego nuevoJuego = new Juego();

        nuevoJuego.setTitulo(titulo);
        nuevoJuego.setGenero(genero);
        nuevoJuego.setPlataforma(plataforma);
        nuevoJuego.setPrecio(precio);
        nuevoJuego.setAnio(anio);
        nuevoJuego.setDescripcion(descripcion);

        juegoService.guardarJuego(nuevoJuego, portada);

        return "redirect:/juegos";
    }
}
