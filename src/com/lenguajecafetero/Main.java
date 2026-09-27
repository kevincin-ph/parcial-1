package com.lenguajecafetero;

import com.lenguajecafetero.factory.CursoFactory;
import com.lenguajecafetero.factory.CursoRegularFactory;
import com.lenguajecafetero.factory.CursoIntensivoFactory;
import com.lenguajecafetero.factory.CursoPersonalizadoFactory;
import com.lenguajecafetero.factory.DatosCurso;
import com.lenguajecafetero.modelo.Curso;
import com.lenguajecafetero.modelo.Estudiante;
import com.lenguajecafetero.modelo.Profesor;
import com.lenguajecafetero.modelo.ServicioAdicional;
import com.lenguajecafetero.modelo.Matricula;
import com.lenguajecafetero.modelo.Academia;
import com.lenguajecafetero.modelo.enums.NivelIdioma;

import java.time.LocalDate;

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

        System.out.println("------------------------------------");

        Estudiante estudiante = new Estudiante("Maria Fernanda Lopez", "1093456789",
                "3001234567", "maria.lopez@email.com", 22);
        System.out.println("Estudiante registrado: " + estudiante);

        Profesor profesor = new Profesor("P-001", "Carlos Ramirez", "Portugues",
                "3109876543", 30000);
        System.out.println("Profesor registrado: " + profesor);

        ServicioAdicional servicio = new ServicioAdicional("SERV-01", "Tutoria de refuerzo",
                "Sesion individual de refuerzo con un profesor", 40000);
        System.out.println("Servicio adicional registrado: " + servicio);

        System.out.println("------------------------------------");

        Academia academia = Academia.getInstancia();
        academia.configurarDatos("LenguajeCafetero", "900123456-1", "Calle 10 # 20-30",
                "6067654321", "contacto@lenguajecafetero.com", "www.lenguajecafetero.com");

        academia.registrarEstudiante(estudiante);
        academia.registrarCurso(cursoRegular);
        academia.registrarProfesor(profesor);
        academia.registrarServicioAdicional(servicio);

        Academia otraReferencia = Academia.getInstancia();
        System.out.println("Es la misma instancia de Academia? " + (academia == otraReferencia));
        System.out.println("Nombre comercial: " + academia.getNombreComercial());

        System.out.println("------------------------------------");

        Matricula matricula = new Matricula.MatriculaBuilder("MAT-001", estudiante, cursoRegular)
                .agregarServicio(servicio)
                .conDescuento(0.10)
                .build();
        academia.registrarMatricula(matricula);
        System.out.println(matricula);

        System.out.println("------------------------------------");

        Estudiante encontrado = academia.buscarEstudiantePorDocumento("1093456789");
        System.out.println("Estudiante encontrado: " + encontrado);

        double ingresos = academia.calcularIngresos(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        System.out.println("Ingresos del periodo consultado: " + ingresos);
    }
}
