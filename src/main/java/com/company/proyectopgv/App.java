package com.company.proyectopgv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private final int MIN_HEIGHT = 730;
     private final int MIN_WIDTH = 1290;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), MIN_WIDTH, MIN_HEIGHT);
        stage.setScene(scene);
        
        // Configurar el listener para la altura y lo limito al final de altura mínima
         stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() < MIN_HEIGHT) {
                stage.setHeight(MIN_HEIGHT);
            }
        });

        // Configurar el listener para la anchura y lo limito al final de anchura mínima
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() < MIN_WIDTH) {
                stage.setWidth(MIN_WIDTH);
            }
        });

        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}