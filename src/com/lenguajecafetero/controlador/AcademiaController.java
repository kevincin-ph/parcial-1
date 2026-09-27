package com.lenguajecafetero.controlador;

import com.lenguajecafetero.factory.CursoFactory;
import com.lenguajecafetero.factory.CursoIntensivoFactory;
import com.lenguajecafetero.factory.CursoPersonalizadoFactory;
import com.lenguajecafetero.factory.CursoRegularFactory;
import com.lenguajecafetero.factory.DatosCurso;
import com.lenguajecafetero.modelo.Academia;
import com.lenguajecafetero.modelo.AsignacionProfesor;
import com.lenguajecafetero.modelo.Curso;
import com.lenguajecafetero.modelo.CursoPersonalizado;
import com.lenguajecafetero.modelo.Estudiante;
import com.lenguajecafetero.modelo.Matricula;
import com.lenguajecafetero.modelo.Profesor;
import com.lenguajecafetero.modelo.ServicioAdicional;
import com.lenguajecafetero.modelo.enums.NivelIdioma;

import java.time.LocalDate;
import java.util.List;

public class AcademiaController {

    private final Academia academia;

    public AcademiaController() {
        academia = Academia.getInstancia();
        cargarDatosDemostracion();
    }

    public Estudiante registrarEstudiante(String nombre, String documento, String telefono,
                                         String correo, int edad) {
        validarTexto(nombre, "El nombre del estudiante es obligatorio.");
        validarTexto(documento, "El documento es obligatorio.");
        validarTexto(telefono, "El teléfono es obligatorio.");
        validarTexto(correo, "El correo es obligatorio.");
        if (edad < 1 || edad > 120) {
            throw new IllegalArgumentException("La edad debe estar entre 1 y 120 años.");
        }
        if (academia.buscarEstudiantePorDocumento(documento.trim()) != null) {
            throw new IllegalArgumentException("Ya existe un estudiante con ese documento.");
        }
        Estudiante estudiante = new Estudiante(nombre.trim(), documento.trim(), telefono.trim(),
                correo.trim(), edad);
        academia.registrarEstudiante(estudiante);
        return estudiante;
    }

    public Curso crearCurso(String tipo, String codigo, String nombre, String idioma,
                            String descripcion, int duracion, double valorMensual,
                            int sesiones, NivelIdioma nivel, String objetivos,
                            double tarifaSesion) {
        validarTexto(codigo, "El código del curso es obligatorio.");
        validarTexto(nombre, "El nombre del curso es obligatorio.");
        validarTexto(idioma, "El idioma es obligatorio.");
        if (duracion < 1 || valorMensual < 0) {
            throw new IllegalArgumentException("La duración debe ser positiva y el valor no puede ser negativo.");
        }
        String codigoNormalizado = codigo.trim();
        boolean duplicado = academia.getCursos().stream()
                .anyMatch(curso -> curso.getCodigo().equalsIgnoreCase(codigoNormalizado));
        if (duplicado) {
            throw new IllegalArgumentException("Ya existe un curso con ese código.");
        }

        CursoFactory fabrica = switch (tipo) {
            case "Intensivo" -> new CursoIntensivoFactory();
            case "Personalizado" -> new CursoPersonalizadoFactory();
            default -> new CursoRegularFactory();
        };
        DatosCurso datos = new DatosCurso(codigoNormalizado, nombre.trim(), idioma.trim(),
                descripcion == null ? "" : descripcion.trim(), duracion, valorMensual);
        if (fabrica instanceof CursoPersonalizadoFactory) {
            if (sesiones < 1 || nivel == null || tarifaSesion < 0) {
                throw new IllegalArgumentException("Completa sesiones, nivel y tarifa del curso personalizado.");
            }
            datos.setDatosPersonalizados(sesiones, nivel, objetivos == null ? "" : objetivos.trim(), tarifaSesion);
        }
        Curso curso = fabrica.crearCurso(datos);
        academia.registrarCurso(curso);
        return curso;
    }

    public Profesor registrarProfesor(String identificacion, String nombre, String idioma,
                                      String telefono, double tarifa) {
        validarTexto(identificacion, "La identificación del profesor es obligatoria.");
        validarTexto(nombre, "El nombre del profesor es obligatorio.");
        validarTexto(idioma, "El idioma del profesor es obligatorio.");
        validarTexto(telefono, "El teléfono del profesor es obligatorio.");
        if (tarifa < 0) {
            throw new IllegalArgumentException("La tarifa no puede ser negativa.");
        }
        boolean duplicado = academia.getProfesores().stream()
                .anyMatch(profesor -> profesor.getIdentificacion().equalsIgnoreCase(identificacion.trim()));
        if (duplicado) {
            throw new IllegalArgumentException("Ya existe un profesor con esa identificación.");
        }
        Profesor profesor = new Profesor(identificacion.trim(), nombre.trim(), idioma.trim(),
                telefono.trim(), tarifa);
        academia.registrarProfesor(profesor);
        return profesor;
    }

    public Matricula crearMatricula(Estudiante estudiante, Curso curso,
                                    ServicioAdicional servicio, double descuento) {
        if (estudiante == null || curso == null) {
            throw new IllegalArgumentException("Selecciona un estudiante y un curso.");
        }
        if (descuento < 0 || descuento > 1) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 % y 100 %.");
        }
        String id = String.format("MAT-%03d", academia.getMatriculas().size() + 1);
        Matricula.MatriculaBuilder builder = new Matricula.MatriculaBuilder(id, estudiante, curso)
                .conDescuento(descuento);
        if (servicio != null) {
            builder.agregarServicio(servicio);
        }
        Matricula matricula = builder.build();
        academia.registrarMatricula(matricula);
        return matricula;
    }

    public AsignacionProfesor asignarProfesor(Estudiante estudiante, CursoPersonalizado curso,
                                              Profesor profesor) {
        if (estudiante == null || curso == null || profesor == null) {
            throw new IllegalArgumentException("Selecciona estudiante, curso personalizado y profesor.");
        }
        AsignacionProfesor asignacion = new AsignacionProfesor(estudiante, curso, profesor);
        academia.registrarAsignacion(asignacion);
        return asignacion;
    }

    public List<Estudiante> estudiantes() { return List.copyOf(academia.getEstudiantes()); }
    public List<Curso> cursos() { return List.copyOf(academia.getCursos()); }
    public List<Profesor> profesores() { return List.copyOf(academia.getProfesores()); }
    public List<ServicioAdicional> servicios() { return List.copyOf(academia.getServicios()); }
    public List<Matricula> matriculas() { return List.copyOf(academia.getMatriculas()); }
    public List<AsignacionProfesor> asignaciones() { return List.copyOf(academia.getAsignaciones()); }
    public List<CursoPersonalizado> cursosPersonalizados() {
        return academia.getCursos().stream()
                .filter(CursoPersonalizado.class::isInstance)
                .map(CursoPersonalizado.class::cast)
                .toList();
    }
    public double ingresos() {
        return academia.calcularIngresos(LocalDate.now().withDayOfMonth(1), LocalDate.now());
    }
    public int cantidadEstudiantes() { return academia.getEstudiantes().size(); }
    public int cantidadCursos() { return academia.getCursos().size(); }
    public int cantidadMatriculas() { return academia.getMatriculas().size(); }

    private void cargarDatosDemostracion() {
        if (!academia.getEstudiantes().isEmpty()) {
            return;
        }
        Estudiante estudiante = new Estudiante("Maria Fernanda Lopez", "1093456789",
                "3001234567", "maria.lopez@email.com", 22);
        Profesor profesor = new Profesor("P-001", "Carlos Ramirez", "Portugues",
                "3109876543", 30000);
        ServicioAdicional servicio = new ServicioAdicional("SERV-01", "Tutoria de refuerzo",
                "Sesion individual de refuerzo con un profesor", 40000);
        academia.configurarDatos("LenguajeCafetero", "900123456-1", "Calle 10 # 20-30",
                "6067654321", "contacto@lenguajecafetero.com", "www.lenguajecafetero.com");
        academia.registrarEstudiante(estudiante);
        academia.registrarProfesor(profesor);
        academia.registrarServicioAdicional(servicio);
        Curso regular = new CursoRegularFactory().crearCurso(new DatosCurso("ING-REG-01",
                "Ingles Basico", "Ingles", "Nivel inicial", 6, 150000));
        Curso intensivo = new CursoIntensivoFactory().crearCurso(new DatosCurso("FRA-INT-01",
                "Frances Intensivo", "Frances", "Programa intensivo", 3, 200000));
        DatosCurso datosPersonalizados = new DatosCurso("POR-PER-01", "Portugues Personalizado",
                "Portugues", "Plan individual de aprendizaje", 4, 180000);
        datosPersonalizados.setDatosPersonalizados(10, NivelIdioma.B1,
                "Preparacion para viaje de negocios", 25000);
        Curso personalizado = new CursoPersonalizadoFactory().crearCurso(datosPersonalizados);
        academia.registrarCurso(regular);
        academia.registrarCurso(intensivo);
        academia.registrarCurso(personalizado);
        academia.registrarMatricula(new Matricula.MatriculaBuilder("MAT-001", estudiante, regular)
                .agregarServicio(servicio).conDescuento(0.10).build());
        academia.registrarAsignacion(new AsignacionProfesor(estudiante,
                (CursoPersonalizado) personalizado, profesor));
    }

    private static void validarTexto(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
    }
}
