package com.alura.challenge_literalura.repository;

import com.alura.challenge_literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);
    List<Autor> findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoGreaterThanEqual(
            Integer anioNacimiento, Integer anioFallecimiento
    );

    List<Autor> findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoIsNull(
            Integer anio
    );

//@Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :anio AND (a.fechaDeFallecimiento IS NULL OR a.fechaDeFallecimiento >= :anio)")
//List<Autor> autoresVivosEnAnio(@Param("anio") Integer anio);
}
