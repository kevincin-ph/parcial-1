package com.lenguajecafetero.modelo;

import com.lenguajecafetero.modelo.enums.EstadoCurso;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un curso ofrecido por la academia. Es la clase base para
 * los tres tipos de curso que maneja el negocio: regular, intensivo y
 * personalizado.
 *
 * Cada subtipo decide como se calcula su valor base, por lo que
 * calcularValorBase() queda como responsabilidad de cada subclase
 * (principio de responsabilidad unica: esta clase no sabe como se
 * cobra cada tipo de curso, solo conoce los datos comunes).
 */
public abstract class Curso implements Cloneable {

    protected String codigo;
    protected String nombre;
    protected String idioma;
    protected String descripcion;
    protected int duracionMeses;
    protected double valorMensual;
    protected EstadoCurso estado;
    protected List<String> beneficios;

    protected Curso(String codigo, String nombre, String idioma, String descripcion,
                     int duracionMeses, double valorMensual) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.idioma = idioma;
        this.descripcion = descripcion;
        this.duracionMeses = duracionMeses;
        this.valorMensual = valorMensual;
        this.estado = EstadoCurso.ACTIVO;
        this.beneficios = new ArrayList<>();
    }

    public abstract double calcularValorBase();

    public void agregarBeneficio(String beneficio) {
        beneficios.add(beneficio);
    }

    /**
     * Implementacion del patron Prototype: permite crear un curso nuevo
     * a partir de una plantilla existente (por ejemplo, abrir un grupo
     * nuevo de un curso que ya esta configurado), sin tener que volver
     * a construir todo desde cero.
     */
    public Curso clonar() {
        try {
            Curso copia = (Curso) super.clone();
            // la lista de beneficios se copia aparte para que el
            // clon no comparta la misma lista en memoria que el original
            copia.beneficios = new ArrayList<>(this.beneficios);
            return copia;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("No se pudo clonar el curso " + codigo, e);
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdioma() {
        return idioma;
    }

    public EstadoCurso getEstado() {
        return estado;
    }

    public void setEstado(EstadoCurso estado) {
        this.estado = estado;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public double getValorMensual() {
        return valorMensual;
    }

    public List<String> getBeneficios() {
        return beneficios;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + idioma + ", " + estado + ")";
    }
}