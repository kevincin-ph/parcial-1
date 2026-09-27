package com.lenguajecafetero.factory;

import com.lenguajecafetero.modelo.enums.NivelIdioma;

/**
 * Agrupa los datos que puede necesitar cualquier tipo de curso.
 * Se usa como parametro de entrada para las fabricas, asi cada
 * fabrica concreta solo toma de aqui los campos que realmente
 * necesita para construir su tipo de curso.
 */
public class DatosCurso {

    private String codigo;
    private String nombre;
    private String idioma;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;

    // Campos que solo aplican a un curso personalizado
    private int cantidadSesiones;
    private NivelIdioma nivelReferencia;
    private String objetivos;
    private double tarifaPorSesionProfesor;

    public DatosCurso(String codigo, String nombre, String idioma, String descripcion,
                       int duracionMeses, double valorMensual) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.idioma = idioma;
        this.descripcion = descripcion;
        this.duracionMeses = duracionMeses;
        this.valorMensual = valorMensual;
    }

    public void setDatosPersonalizados(int cantidadSesiones, NivelIdioma nivelReferencia,
                                        String objetivos, double tarifaPorSesionProfesor) {
        this.cantidadSesiones = cantidadSesiones;
        this.nivelReferencia = nivelReferencia;
        this.objetivos = objetivos;
        this.tarifaPorSesionProfesor = tarifaPorSesionProfesor;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getIdioma() { return idioma; }
    public String getDescripcion() { return descripcion; }
    public int getDuracionMeses() { return duracionMeses; }
    public double getValorMensual() { return valorMensual; }
    public int getCantidadSesiones() { return cantidadSesiones; }
    public NivelIdioma getNivelReferencia() { return nivelReferencia; }
    public String getObjetivos() { return objetivos; }
    public double getTarifaPorSesionProfesor() { return tarifaPorSesionProfesor; }
}