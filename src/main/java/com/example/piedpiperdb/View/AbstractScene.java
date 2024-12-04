package com.example.piedpiperdb.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AbstractScene {

    static Scene scene;

    static Scene getScene(Stage window) {

        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(10));
        stackPane.getStyleClass().add("backgroundTeaGreen");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPadding(new Insets(10));
        anchorPane.getStyleClass().add("backgroundTeaGreen");

        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(500);
        rectangle.setWidth(200);
        rectangle.setFill(Paint.valueOf("#FFFFFF"));
        rectangle.setOpacity(0.5);
        rectangle.setY(70);
        rectangle.setX(20);

        Button button1 = new Button("Knapp 1");
        button1.getStyleClass().add("standardButton");
        button1.setMinSize(180, 30);

        Button button2 = new Button("Knapp 2");
        button2.getStyleClass().add("standardButton");
        button2.setMinSize(180, 30);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setMaxSize(190, 30);
        vbox.setLayoutY(80.0);
        vbox.setLayoutX(20.0);
        vbox.getChildren().addAll(button1, button2);

        stackPane.getChildren().addAll(rectangle, vbox);
        anchorPane.getChildren().addAll(rectangle, vbox);

        scene = new Scene(anchorPane, HelloApplication.width, HelloApplication.height);
        scene.getStylesheets().add("EscortFlasher.css");

        return scene;

    }
}