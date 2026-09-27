package com.lenguajecafetero.modelo;

import com.lenguajecafetero.modelo.enums.NivelIdioma;

/**
 * Curso personalizado: ademas de los datos comunes de un curso,
 * requiere la cantidad de sesiones con profesor, el nivel de
 * referencia que busca alcanzar el estudiante y sus objetivos.
 *
 * Su valor base suma el costo mensual normal mas el costo de las
 * sesiones individuales con el profesor asignado.
 */
public class CursoPersonalizado extends Curso {

    private int cantidadSesiones;
    private NivelIdioma nivelReferencia;
    private String objetivos;
    private double tarifaPorSesionProfesor;

    public CursoPersonalizado(String codigo, String nombre, String idioma, String descripcion,
                               int duracionMeses, double valorMensual, int cantidadSesiones,
                               NivelIdioma nivelReferencia, String objetivos,
                               double tarifaPorSesionProfesor) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual);
        this.cantidadSesiones = cantidadSesiones;
        this.nivelReferencia = nivelReferencia;
        this.objetivos = objetivos;
        this.tarifaPorSesionProfesor = tarifaPorSesionProfesor;
    }

    @Override
    public double calcularValorBase() {
        double valorMensualidades = valorMensual * duracionMeses;
        double costoSesiones = cantidadSesiones * tarifaPorSesionProfesor;
        return valorMensualidades + costoSesiones;
    }

    public int getCantidadSesiones() {
        return cantidadSesiones;
    }

    public NivelIdioma getNivelReferencia() {
        return nivelReferencia;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public double getTarifaPorSesionProfesor() {
        return tarifaPorSesionProfesor;
    }
}