package com.alura.challenge_literalura.repository;

import com.alura.challenge_literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByIdiomaIgnoreCase(String idioma);
;
//@Query("SELECT COUNT(l) FROM Libro l WHERE LOWER(l.idioma) = LOWER(:idioma)")
//long contarLibrosPorIdioma(@Param("idioma") String idioma);
}
