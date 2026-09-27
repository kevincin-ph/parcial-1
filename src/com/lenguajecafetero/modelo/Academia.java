package com.lenguajecafetero.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Academia {

    private static Academia instancia;

    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String email;
    private String paginaWeb;

    private List<Estudiante> estudiantes;
    private List<Curso> cursos;
    private List<Profesor> profesores;
    private List<ServicioAdicional> servicios;
    private List<Matricula> matriculas;

    private Academia() {
        this.estudiantes = new ArrayList<>();
        this.cursos = new ArrayList<>();
        this.profesores = new ArrayList<>();
        this.servicios = new ArrayList<>();
        this.matriculas = new ArrayList<>();
    }

    public static Academia getInstancia() {
        if (instancia == null) {
            instancia = new Academia();
        }
        return instancia;
    }

    public void configurarDatos(String nombreComercial, String nit, String direccion,
                                 String telefono, String email, String paginaWeb) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.paginaWeb = paginaWeb;
    }

    public void registrarEstudiante(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    public void registrarCurso(Curso curso) {
        cursos.add(curso);
    }

    public void registrarProfesor(Profesor profesor) {
        profesores.add(profesor);
    }

    public void registrarServicioAdicional(ServicioAdicional servicio) {
        servicios.add(servicio);
    }

    public void registrarMatricula(Matricula matricula) {
        matriculas.add(matricula);
    }

    public Estudiante buscarEstudiantePorDocumento(String documento) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getDocumentoIdentidad().equals(documento)) {
                return estudiante;
            }
        }
        return null;
    }

    public double calcularIngresos(LocalDate fechaInicio, LocalDate fechaFin) {
        double total = 0;
        for (Matricula matricula : matriculas) {
            LocalDate fecha = matricula.getFechaMatricula();
            boolean dentroDelRango = !fecha.isBefore(fechaInicio) && !fecha.isAfter(fechaFin);
            if (dentroDelRango) {
                total += matricula.getValorFinal();
            }
        }
        return total;
    }

    public String getNombreComercial() { return nombreComercial; }
    public List<Estudiante> getEstudiantes() { return estudiantes; }
    public List<Curso> getCursos() { return cursos; }
    public List<Profesor> getProfesores() { return profesores; }
    public List<ServicioAdicional> getServicios() { return servicios; }
    public List<Matricula> getMatriculas() { return matriculas; }
}
