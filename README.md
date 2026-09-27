# Lenguaje Cafetero

Aplicacion de gestion academica con patrones Factory Method, Prototype, Builder y Singleton. La interfaz de escritorio esta organizada en Modelo-Vista-Controlador (MVC) y utiliza JavaFX.

## Requisitos

- JDK 21 o superior
- Maven 3.8 o superior

Maven descarga JavaFX automaticamente; no es necesario instalar JavaFX en el JDK.

## Ejecutar la interfaz JavaFX

Desde la raiz del proyecto:

```sh
mvn javafx:run
```

La aplicacion inicia con datos de demostracion y permite registrar estudiantes, crear cursos regulares/intensivos/personalizados, generar matriculas con servicios y descuentos, registrar profesores y asignarlos a cursos personalizados. El resumen muestra el volumen de registros e ingresos del mes.

## Arquitectura

- `modelo`: entidades y reglas de negocio de la academia.
- `controlador`: validacion y coordinacion entre la vista y el modelo.
- `vista`: aplicacion JavaFX y componentes de interfaz.
- `src/main/resources`: estilos visuales de JavaFX.

La entrada de consola original `com.lenguajecafetero.Main` se conserva. La entrada grafica configurada en Maven es `com.lenguajecafetero.vista.AcademiaApplication`.

## Compilar y probar

```sh
mvn test
```
