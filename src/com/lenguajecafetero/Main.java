package com.lenguajecafetero;

import com.lenguajecafetero.factory.CursoFactory;
import com.lenguajecafetero.factory.CursoRegularFactory;
import com.lenguajecafetero.factory.CursoIntensivoFactory;
import com.lenguajecafetero.factory.CursoPersonalizadoFactory;
import com.lenguajecafetero.factory.DatosCurso;
import com.lenguajecafetero.modelo.Curso;
import com.lenguajecafetero.modelo.enums.NivelIdioma;

public class Main {

    public static void main(String[] args) {

        CursoFactory fabricaRegular = new CursoRegularFactory();
        DatosCurso datosRegular = new DatosCurso("ING-REG-01", "Ingles Basico", "Ingles",
                "Curso regular de ingles nivel basico", 6, 150000);
        Curso cursoRegular = fabricaRegular.crearCurso(datosRegular);
        System.out.println("Curso creado: " + cursoRegular);
        System.out.println("Valor base: " + cursoRegular.calcularValorBase());

        System.out.println("------------------------------------");

        CursoFactory fabricaIntensivo = new CursoIntensivoFactory();
        DatosCurso datosIntensivo = new DatosCurso("FRA-INT-01", "Frances Intensivo", "Frances",
                "Curso intensivo de frances", 3, 200000);
        Curso cursoIntensivo = fabricaIntensivo.crearCurso(datosIntensivo);
        System.out.println("Curso creado: " + cursoIntensivo);
        System.out.println("Valor base: " + cursoIntensivo.calcularValorBase());

        System.out.println("------------------------------------");

        CursoFactory fabricaPersonalizado = new CursoPersonalizadoFactory();
        DatosCurso datosPersonalizado = new DatosCurso("POR-PER-01", "Portugues Personalizado",
                "Portugues", "Curso personalizado de portugues", 4, 180000);
        datosPersonalizado.setDatosPersonalizados(10, NivelIdioma.B1,
                "Preparacion para viaje de negocios", 25000);
        Curso cursoPersonalizado = fabricaPersonalizado.crearCurso(datosPersonalizado);
        System.out.println("Curso creado: " + cursoPersonalizado);
        System.out.println("Valor base: " + cursoPersonalizado.calcularValorBase());

        System.out.println("------------------------------------");

        Curso nuevoGrupo = cursoRegular.clonar();
        nuevoGrupo.setCodigo("ING-REG-02");
        System.out.println("Curso clonado (Prototype): " + nuevoGrupo);
        System.out.println("Es la misma instancia que el original? " + (nuevoGrupo == cursoRegular));
    }
}
