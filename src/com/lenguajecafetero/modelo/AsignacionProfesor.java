package com.lenguajecafetero.modelo;

import java.time.LocalDate;

public class AsignacionProfesor {

    private Estudiante estudiante;
    private CursoPersonalizado curso;
    private Profesor profesor;
    private LocalDate fechaAsignacion;

    public AsignacionProfesor(Estudiante estudiante, CursoPersonalizado curso, Profesor profesor) {
        this.estudiante = estudiante;
        this.curso = curso;
        this.profesor = profesor;
        this.fechaAsignacion = LocalDate.now();
    }

    public Estudiante getEstudiante() { return estudiante; }
    public CursoPersonalizado getCurso() { return curso; }
    public Profesor getProfesor() { return profesor; }
    public LocalDate getFechaAsignacion() { return fechaAsignacion; }

    @Override
    public String toString() {
        return "Asignacion: " + estudiante.getNombreCompleto() + " - curso " + curso.getCodigo()
                + " - profesor " + profesor.getNombre();
    }
}
