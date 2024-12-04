package com.example.piedpiperdb.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    Stage window;
    Scene startScene;

    private Button loginButton;

    private final Label programTitel = new Label("Hello World");


    @Override
    public void start(Stage stage) throws IOException {

        //AnchorPane anchorPane = new AnchorPane();
        StackPane stackPane = new StackPane();

        stage.setTitle("Hello!");



        Scene scene = new Scene(stackPane, 320, 240);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}