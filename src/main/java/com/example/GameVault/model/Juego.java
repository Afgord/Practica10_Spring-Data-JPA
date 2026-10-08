package com.example.GameVault.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "juegos")
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String titulo;

    @Column(length = 1000, nullable = false)
    private String descripcion;

    // Almacenaremos el nombre del archivo o de la URL de la imagen
    @Column(name = "portada_url")
    private String portadaUrl;

}
