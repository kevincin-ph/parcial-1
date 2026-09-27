package com.lenguajecafetero.modelo;

/**
 * Curso regular: el valor base es simplemente el valor mensual
 * multiplicado por la duracion contratada, sin recargos adicionales.
 */
public class CursoRegular extends Curso {

    public CursoRegular(String codigo, String nombre, String idioma, String descripcion,
                         int duracionMeses, double valorMensual) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual);
    }

    @Override
    public double calcularValorBase() {
        return valorMensual * duracionMeses;
    }
}