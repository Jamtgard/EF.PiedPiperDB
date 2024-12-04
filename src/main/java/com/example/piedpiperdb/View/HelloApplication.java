package com.example.piedpiperdb.View;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    Stage window;
    Scene startScene;

    private Button loginButton;

    private final int height = 600;
    private final int width = 700;

    private final Label programTitel = new Label("Hello World");


    @Override
    public void start(Stage stage) throws IOException {

        window = stage;
        window.setResizable(false);
        //AnchorPane anchorPane = new AnchorPane();
        StackPane stackPane = new StackPane();

        stage.setTitle("Start page");


        Rectangle greenBackground = new Rectangle(width, height);
        greenBackground.setHeight(400);
        greenBackground.setWidth(500);
        greenBackground.setFill(Paint.valueOf("#D0E3C4"));

        programTitel.getStyleClass().add("titel");
        loginButton = new Button("Login");
        loginButton.getStyleClass().add("standardButton");


        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getStyleClass().add("backgroundOlivine");

        vBox.getChildren().addAll(programTitel, loginButton);


        stackPane.getChildren().addAll(greenBackground, vBox);



        startScene = new Scene(stackPane, width, height);
        startScene.getStylesheets().add("EscortFlasher.css");
        stage.setScene(startScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}