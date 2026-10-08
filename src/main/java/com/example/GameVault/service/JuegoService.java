package com.example.GameVault.service;

import com.example.GameVault.model.Juego;
import com.example.GameVault.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class JuegoService {

    @Autowired
    private JuegoRepository juegoRepository;

    private static final String UPLOAD_DIR =
            "src/main/resources/static/uploads/";

    public List<Juego> listarTodos() {
        return juegoRepository.findAll();
    }

    public void guardarJuego(Juego juego, MultipartFile portada) {

        String nombreArchivo = "default.jpg";

        if (!portada.isEmpty()) {
            nombreArchivo = guardarImagenEnProyecto(portada);
        }

        juego.setPortadaUrl(nombreArchivo);
        juegoRepository.save(juego);
    }

    public String guardarImagenEnProyecto(MultipartFile portada) {

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String nombreArchivo =
                    UUID.randomUUID().toString()
                            + "_"
                            + portada.getOriginalFilename();

            Path filePath = uploadPath.resolve(nombreArchivo);

            Files.copy(
                    portada.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println(
                    "Archivo guardado correctamente en: "
                            + filePath.toAbsolutePath()
            );

            return nombreArchivo;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "default.jpg";
    }
}