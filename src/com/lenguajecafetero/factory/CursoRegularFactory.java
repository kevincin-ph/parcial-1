package com.lenguajecafetero.factory;

import com.lenguajecafetero.modelo.Curso;

/**
 * Patron Factory Method.
 *
 * En vez de que el resto de la aplicacion decida con un if/else o un
 * switch que clase de Curso instanciar, cada tipo de curso tiene su
 * propia fabrica. Si en el futuro la academia agrega un cuarto tipo
 * de curso, basta con crear una nueva fabrica sin tocar el resto del
 * sistema (principio abierto/cerrado).
 */
public abstract class CursoFactory {
    public abstract Curso crearCurso(DatosCurso datos);
}