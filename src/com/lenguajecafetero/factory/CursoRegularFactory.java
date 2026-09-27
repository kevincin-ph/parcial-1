package com.lenguajecafetero.factory;

import com.lenguajecafetero.modelo.Curso;
import com.lenguajecafetero.modelo.CursoRegular;

public class CursoRegularFactory extends CursoFactory {

    @Override
    public Curso crearCurso(DatosCurso datos) {
        return new CursoRegular(
                datos.getCodigo(),
                datos.getNombre(),
                datos.getIdioma(),
                datos.getDescripcion(),
                datos.getDuracionMeses(),
                datos.getValorMensual()
        );
    }
}
