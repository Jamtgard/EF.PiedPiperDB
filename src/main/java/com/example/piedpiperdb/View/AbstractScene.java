package com.example.piedpiperdb.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public abstract class AbstractScene {

    static Scene scene;

    static Scene getScene(Stage window) {

        /*
        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(10));
        stackPane.getStyleClass().add("backgroundTeaGreen");*/

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPadding(new Insets(10));
        anchorPane.getStyleClass().add("backgroundTeaGreen");

        VBox titelBox = new VBox();
        titelBox.setSpacing(5);
        titelBox.setPadding(new Insets(10, 10, 10, 10));
        titelBox.setLayoutY(30);
        titelBox.setLayoutX(240);

        Label titel = new Label("Piper Games");
        titel.getStyleClass().add("titel");
        titel.setAlignment(Pos.CENTER);

        Label titel2 = new Label("(Pied Piper DB)");
        titel2.getStyleClass().add("titel");
        titel2.setAlignment(Pos.CENTER);

        titelBox.getChildren().addAll(titel, titel2);

        //-----------------------------------------------------------

        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(440);
        rectangle.setWidth(180);
        rectangle.setFill(Paint.valueOf("#FFFFFF"));
        rectangle.setOpacity(0.5);
        rectangle.setY(140);
        rectangle.setX(20);

        Button button1 = new Button("Knapp 1");
        button1.getStyleClass().add("standardButton");
        button1.setMinSize(160, 30);

        Button button2 = new Button("Knapp 2");
        button2.getStyleClass().add("standardButton");
        button2.setMinSize(160, 30);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(15, 10, 10, 10));
        vbox.setMaxSize(170, 30);
        vbox.setLayoutY(140.0);
        vbox.setLayoutX(20.0);
        vbox.getChildren().addAll(button1, button2);

        //-----------------------------------------------------------

        Button userButton = new Button("UserName");
        userButton.getStyleClass().add("darkerButton");
        userButton.setMinSize(140, 30);
        userButton.setLayoutY(20.0);
        userButton.setLayoutX(533.0);

        TextField searchField = new TextField();
        searchField.getStyleClass().add("textFieldOne");
        searchField.setPromptText("Search: ");
        searchField.setMinSize(150, 30);
        searchField.setLayoutY(100.0);
        searchField.setLayoutX(485.0);

        //-----------------------------------------------------------

        Rectangle rectangle2 = new Rectangle();
        rectangle2.setHeight(440);
        rectangle2.setWidth(465);
        rectangle2.setFill(Paint.valueOf("#FFFFFF"));
        rectangle2.setOpacity(0.5);
        rectangle2.setY(140);
        rectangle2.setX(210);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(15, 10, 10, 10));
        hBox.setMaxSize(60,170);
        hBox.setLayoutY(135.0);
        hBox.setLayoutX(210.0);

        Label LabelCulumn1H = new Label("Column 1");
        LabelCulumn1H.getStyleClass().add("standardLabel");
        LabelCulumn1H.setMinSize(100, 30);
        LabelCulumn1H.setAlignment(Pos.CENTER);

        hBox.getChildren().addAll(LabelCulumn1H);

        VBox column1VBox = new VBox();
        column1VBox.setSpacing(10);
        column1VBox.setPadding(new Insets(15, 10, 10, 10));
        column1VBox.setMinSize(100,390);
        column1VBox.setLayoutY(180.0);
        column1VBox.setLayoutX(220.0);
        column1VBox.getStyleClass().add("columnV");

        //-----------------------------------------------------------

        Button back = new Button("Back");
        back.getStyleClass().add("darkerButton");
        back.setMinSize(160, 30);
        back.setLayoutY(535.0);
        back.setLayoutX(30.0);
        back.setOnAction(e->{
            HelloApplication.main(null);//Vet inte om detta fungerar
        });



        //stackPane.getChildren().addAll(rectangle, vbox);
        anchorPane.getChildren().addAll(titelBox,rectangle, vbox, userButton,searchField,rectangle2,hBox, column1VBox,back);

        scene = new Scene(anchorPane, HelloApplication.width, HelloApplication.height);
        scene.getStylesheets().add("EscortFlasher.css");

        return scene;

    }
}