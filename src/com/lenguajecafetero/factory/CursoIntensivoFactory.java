package com.lenguajecafetero.factory;

import com.lenguajecafetero.modelo.Curso;
import com.lenguajecafetero.modelo.CursoIntensivo;

public class CursoIntensivoFactory extends CursoFactory {

    @Override
    public Curso crearCurso(DatosCurso datos) {
        return new CursoIntensivo(
                datos.getCodigo(),
                datos.getNombre(),
                datos.getIdioma(),
                datos.getDescripcion(),
                datos.getDuracionMeses(),
                datos.getValorMensual()
        );
    }
}