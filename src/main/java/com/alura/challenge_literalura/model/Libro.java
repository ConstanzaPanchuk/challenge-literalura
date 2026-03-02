package com.alura.challenge_literalura.model;

import com.alura.challenge_literalura.dto.DatosLibros;

import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

public class Libro {
    private String titulo;
    private String autores;
    private String idiomas;
    private Double cantidadDeDescargas;

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autores='" + autores + '\'' +
                ", idiomas='" + idiomas + '\'' +
                ", cantidadDeDescargas=" + cantidadDeDescargas +
                '}';
    }

    public Libro (DatosLibros datosLibros){
        this.titulo = datosLibros.titulo();
        this.autores = datosLibros.autores().stream()
                .map(a -> a.nombre())
                .collect(Collectors.joining(", "));
        this.idiomas = String.join(", ", datosLibros.idiomas());
        this.cantidadDeDescargas = getCantidadDeDescargas();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getCantidadDeDescargas() {
        return cantidadDeDescargas;
    }

    public void setCantidadDeDescargas(Double cantidadDeDescargas) {
        this.cantidadDeDescargas = cantidadDeDescargas;
    }
}
