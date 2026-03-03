package com.alura.challenge_literalura.model;

import com.alura.challenge_literalura.dto.DatosLibros;


public class Libro {
    private String titulo;
    private Autor autor;
    private String idioma;
    private Double cantidadDeDescargas;

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", idioma='" + idioma + '\'' +
                ", cantidadDeDescargas=" + cantidadDeDescargas +
                '}';
    }

    public Libro (DatosLibros datosLibros){
        this.titulo = datosLibros.titulo();
        this.autor = datosLibros.autores().isEmpty()
                ? null
                : new Autor(datosLibros.autores().get(0));
        this.idioma = datosLibros.idiomas().isEmpty()
                ? "Idioma Desconocido"
                :datosLibros.idiomas().get(0);
        this.cantidadDeDescargas = datosLibros.cantidadDeDescargas();
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
