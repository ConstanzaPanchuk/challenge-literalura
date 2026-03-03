package com.alura.challenge_literalura.principal;

import com.alura.challenge_literalura.dto.Datos;
import com.alura.challenge_literalura.dto.DatosAutor;
import com.alura.challenge_literalura.dto.DatosLibros;
import com.alura.challenge_literalura.model.Autor;
import com.alura.challenge_literalura.model.Libro;
import com.alura.challenge_literalura.repository.AutorRepository;
import com.alura.challenge_literalura.repository.LibroRepository;
import com.alura.challenge_literalura.service.ConsumoAPI;
import com.alura.challenge_literalura.service.ConvierteDatos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Stream;

public class Principal {
    private static final Logger log = LoggerFactory.getLogger(Principal.class);
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<Libro> librosBuscados = new ArrayList<>();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }


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
                case 5:
                    listarLibrosPorIdioma();
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
        Datos datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibros> resultadoBusqueda = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();

        if (resultadoBusqueda.isPresent()) {
            DatosLibros datosLibros = resultadoBusqueda.get();

            Autor autor = null;
            if (!datosLibros.autores().isEmpty()) {
                DatosAutor datosAutor = datosLibros.autores().get(0);

                autor = autorRepository.findByNombre(datosAutor.nombre())
                        .orElseGet(() -> autorRepository.save(new Autor(datosAutor)));
            }

            Libro libro = new Libro(datosLibros, autor);
            libroRepository.save(libro);

            librosBuscados.add(libro);

            System.out.println("Libro encontrado y guardado en la base de datos:");
            System.out.println(libro);
            } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("Todavía no hay libros registrados en la base de datos.");
        } else {
            System.out.println("Libros registrados en la base de datos:");
            libros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Todavía no hay autores registrados en la base de datos.");
        } else {
            System.out.println("Autores registados en la base de datos:");
            autores.forEach(System.out::println);
        }
    }

    private void autoresVivosEnAnio() {
        Integer anio = null;

        while (anio == null) {
            System.out.println("Ingrese el año que desea buscar:");
            String input = teclado.nextLine();

            try {
                anio = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }

        List<Autor> autoresFallecidosTarde = autorRepository
                .findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoGreaterThanEqual(anio, anio);
        List<Autor> autoresVivos = autorRepository
                .findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoIsNull(anio);
        List<Autor> autores = Stream.concat(autoresFallecidosTarde.stream(), autoresVivos.stream())
                .distinct()
                .toList();
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio + ".");
        } else {
            System.out.println("Autores vivos en el año " + anio + ":\n");
            autores.forEach(System.out::println);
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Elige un idioma:
                1 - Español (es)
                2 - Inglés (en)
                3 - Francés (fr)
                4 - Portugués (pt)
                """);

        int opcion = teclado.nextInt();
        teclado.nextLine();

        String idioma = switch (opcion) {
            case 1 -> "es";
            case 2 -> "en";
            case 3 -> "fr";
            case 4 -> "pt";
            default -> {
                System.out.println("Opción invalida.");
                yield null;
            }
        };
        if (idioma == null) return;
        List<Libro> libros = libroRepository.findByIdiomaIgnoreCase(idioma);
        long cantidad = libros.stream().count();

        System.out.printf("""
            ----------------------------------
            Idioma: %s
            Cantidad de libros: %d
            ----------------------------------
            """, idioma, cantidad);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en este idioma.");
        } else {
            System.out.println("Lista de libros:");
            libros.forEach(System.out::println);
        }
    }

}
