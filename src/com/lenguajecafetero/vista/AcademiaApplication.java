package com.lenguajecafetero.vista;

import com.lenguajecafetero.controlador.AcademiaController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AcademiaApplication extends Application {

    @Override
    public void start(Stage stage) {
        AcademiaController controller = new AcademiaController();
        AcademiaView view = new AcademiaView(controller);
        Scene scene = new Scene(view, 1280, 820);
        scene.getStylesheets().add(getClass().getResource("academia.css").toExternalForm());
        stage.setTitle("Lenguaje Cafetero | Gestión académica");
        stage.setMinWidth(1040);
        stage.setMinHeight(700);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
