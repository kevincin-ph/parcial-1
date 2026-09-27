package com.lenguajecafetero.modelo;

import java.time.LocalDate;

public class Estudiante {

    private String nombreCompleto;
    private String documentoIdentidad;
    private String telefono;
    private String correo;
    private int edad;
    private LocalDate fechaRegistro;

    public Estudiante(String nombreCompleto, String documentoIdentidad, String telefono,
                       String correo, int edad) {
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.telefono = telefono;
        this.correo = correo;
        this.edad = edad;
        this.fechaRegistro = LocalDate.now();
    }

    public String getNombreCompleto() { return nombreCompleto; }
    public String getDocumentoIdentidad() { return documentoIdentidad; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public int getEdad() { return edad; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }

    @Override
    public String toString() {
        return documentoIdentidad + " - " + nombreCompleto + " (" + edad + " anios)";
    }
}
