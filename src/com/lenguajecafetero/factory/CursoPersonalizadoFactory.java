package com.lenguajecafetero.factory;

import com.lenguajecafetero.modelo.Curso;
import com.lenguajecafetero.modelo.CursoPersonalizado;

public class CursoPersonalizadoFactory extends CursoFactory {

    @Override
    public Curso crearCurso(DatosCurso datos) {
        return new CursoPersonalizado(
                datos.getCodigo(),
                datos.getNombre(),
                datos.getIdioma(),
                datos.getDescripcion(),
                datos.getDuracionMeses(),
                datos.getValorMensual(),
                datos.getCantidadSesiones(),
                datos.getNivelReferencia(),
                datos.getObjetivos(),
                datos.getTarifaPorSesionProfesor()
        );
    }
}