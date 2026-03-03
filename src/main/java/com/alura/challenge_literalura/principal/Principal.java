package com.alura.challenge_literalura.principal;

import com.alura.challenge_literalura.dto.Datos;
import com.alura.challenge_literalura.dto.DatosLibros;
import com.alura.challenge_literalura.model.Autor;
import com.alura.challenge_literalura.model.Libro;
import com.alura.challenge_literalura.service.ConsumoAPI;
import com.alura.challenge_literalura.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<Libro> librosBuscados = new ArrayList<>();


    public void mostrarElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Eliga la opcion deseada:
                    1 - Buscar libro por su titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    autoresVivosEnAnio();
                    break;
                case 0:
                    System.out.println("Finalizando la aplicación...");
                    break;
                default:
                    System.out.println("Opción ingresada inválida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escriba el nombre del libro que desea buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> resultadoBusqueda = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        if (resultadoBusqueda.isPresent()) {
            Libro libro = new Libro(resultadoBusqueda.get());
            librosBuscados.add(libro);
            System.out.println("Libro encontrado: ");
            System.out.println(libro);
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void listarLibrosRegistrados() {
        if (librosBuscados.isEmpty()) {
            System.out.println("Todavia no hay libros registrados.");
        } else {
            System.out.println("Los libros registrados en este momento son: ");
            librosBuscados.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        if (librosBuscados.isEmpty()) {
            System.out.println("Todavia no hay autores registrados");
        } else {
            System.out.println("Autores registrados: ");
            librosBuscados.stream()
                    .map(Libro::getAutor)
                    .filter(Objects::nonNull)
                    .distinct()
                    .forEach(System.out::println);
        }
    }

    private void autoresVivosEnAnio() {
        System.out.println("Ingrese el año que desea buscar");
        var anio = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autoresVivos = librosBuscados.stream()
                .map(Libro::getAutor)
                .filter(Objects::nonNull)
                .filter(a -> a.getFechaDeNacimiento() != null &&
                        a.getFechaDeNacimiento() <= anio &&
                        (a.getFechaDeFallecimiento() == null ||
                                a.getFechaDeFallecimiento() >= anio)
                )
                .distinct()
                .toList();

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio);
        } else {
            System.out.println("Autores vivos en el año " + anio + ":\n");
            autoresVivos.forEach(System.out::println);
        }
    }



}
