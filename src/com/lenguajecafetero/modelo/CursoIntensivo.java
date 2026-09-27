package com.lenguajecafetero.modelo;

/**
 * Curso intensivo: al tener mayor carga horaria semanal que un curso
 * regular, la academia le aplica un recargo fijo sobre el valor base.
 */
public class CursoIntensivo extends Curso {

    private static final double RECARGO_INTENSIVO = 0.20; // 20% adicional

    public CursoIntensivo(String codigo, String nombre, String idioma, String descripcion,
                           int duracionMeses, double valorMensual) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual);
    }

    @Override
    public double calcularValorBase() {
        double valorSinRecargo = valorMensual * duracionMeses;
        return valorSinRecargo + (valorSinRecargo * RECARGO_INTENSIVO);
    }
}