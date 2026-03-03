package com.alura.challenge_literalura.model;

import com.alura.challenge_literalura.dto.DatosLibros;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String idioma;
    private Double cantidadDeDescargas;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {}

    @Override
    public String toString() {
        return """
                ---------------------------------------------
                Título: %s
                Autor: %s
                Idioma: %s
                Descargas: %.0f
                ---------------------------------------------"""
                .formatted(
                        titulo,
                        autor != null ? autor.getNombre() : "Desconocido",
                        idioma,
                        cantidadDeDescargas != null ? cantidadDeDescargas : 0
                );
    }

    public Libro (DatosLibros datosLibros, Autor autor){
        this.titulo = datosLibros.titulo();
        this.idioma = datosLibros.idiomas().isEmpty()
                ? "Idioma Desconocido"
                : datosLibros.idiomas().get(0);
        this.cantidadDeDescargas = datosLibros.cantidadDeDescargas();
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getCantidadDeDescargas() {
        return cantidadDeDescargas;
    }

    public void setCantidadDeDescargas(Double cantidadDeDescargas) {
        this.cantidadDeDescargas = cantidadDeDescargas;
    }
}
