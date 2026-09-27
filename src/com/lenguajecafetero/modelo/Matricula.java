package com.lenguajecafetero.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Matricula {

    private String id;
    private Estudiante estudiante;
    private Curso curso;
    private LocalDate fechaMatricula;
    private List<ServicioAdicional> serviciosUtilizados;
    private double descuento;
    private double valorFinal;

    private Matricula(MatriculaBuilder builder) {
        this.id = builder.id;
        this.estudiante = builder.estudiante;
        this.curso = builder.curso;
        this.fechaMatricula = builder.fechaMatricula;
        this.serviciosUtilizados = builder.serviciosUtilizados;
        this.descuento = builder.descuento;
        this.valorFinal = calcularValorFinal();
    }

    private double calcularValorFinal() {
        double valorCurso = curso.calcularValorBase();
        double valorServicios = 0;
        for (ServicioAdicional servicio : serviciosUtilizados) {
            valorServicios += servicio.getPrecio();
        }
        double subtotal = valorCurso + valorServicios;
        return subtotal - (subtotal * descuento);
    }

    public String getId() { return id; }
    public Estudiante getEstudiante() { return estudiante; }
    public Curso getCurso() { return curso; }
    public LocalDate getFechaMatricula() { return fechaMatricula; }
    public List<ServicioAdicional> getServiciosUtilizados() { return serviciosUtilizados; }
    public double getDescuento() { return descuento; }
    public double getValorFinal() { return valorFinal; }

    @Override
    public String toString() {
        return "Matricula " + id + " - " + estudiante.getNombreCompleto() + " en " + curso.getCodigo()
                + " | valor final: " + valorFinal;
    }

    public static class MatriculaBuilder {

        private String id;
        private Estudiante estudiante;
        private Curso curso;
        private LocalDate fechaMatricula;
        private List<ServicioAdicional> serviciosUtilizados = new ArrayList<>();
        private double descuento = 0;

        public MatriculaBuilder(String id, Estudiante estudiante, Curso curso) {
            this.id = id;
            this.estudiante = estudiante;
            this.curso = curso;
            this.fechaMatricula = LocalDate.now();
        }

        public MatriculaBuilder agregarServicio(ServicioAdicional servicio) {
            this.serviciosUtilizados.add(servicio);
            return this;
        }

        public MatriculaBuilder conDescuento(double descuento) {
            this.descuento = descuento;
            return this;
        }

        public MatriculaBuilder conFecha(LocalDate fecha) {
            this.fechaMatricula = fecha;
            return this;
        }

        public Matricula build() {
            return new Matricula(this);
        }
    }
}
