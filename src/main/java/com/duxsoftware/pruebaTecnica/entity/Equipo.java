package com.duxsoftware.pruebaTecnica.entity;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity(name = "equipo")
@Data
@RequiredArgsConstructor
public class Equipo {
    @Hidden
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    @NotEmpty
    @NotBlank
    @NotNull
    @Column(name = "nombre", unique = true)
    private String nombre;
    @NotEmpty
    @NotBlank
    @NotNull
    @Column(name = "liga")
    private String liga;
    @NotEmpty
    @NotBlank
    @NotNull
    @Column(name = "pais")
    private String pais;

    public Equipo(Long id, String nombre, String liga, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.liga = liga;
        this.pais = pais;
    }
}