package com.example.piedpiperdb.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

//GEFP-4-SA
public abstract class AbstractScene {

    static Scene scene;

    //GEFP-20-SA
    public static VBox leftVbox;
    public static Button back;
    //private static HBox columnNames;
    public static AnchorPane anchorPane;

    static Scene getScene(Stage window) {

        //GEFP-4-SA
        anchorPane = new AnchorPane();
        anchorPane.setPadding(new Insets(10));
        anchorPane.getStyleClass().add("backgroundTeaGreen");

        //GEFP-20-SA
        anchorPane.getChildren().addAll(
                createTitleBox(),
                createPlaceForButtons(),
                createRectagleForActions(),
                createBackButton(window),
                createSearchField(),
                createUserButton(),
                leftVbox
        );

        addCustomComponents(anchorPane);

        //GEFP-4-SA
        scene = new Scene(anchorPane, HelloApplication.width, HelloApplication.height);
        scene.getStylesheets().add("EscortFlasher.css");

        return scene;


        //-----------------------------------------------------------
        /*
        //GEFP-4-SA
        Button button1 = new Button("Knapp 1");
        button1.getStyleClass().add("standardButton");
        button1.setMinSize(160, 30);

        columnNames = new HBox();
        columnNames.setSpacing(10);
        columnNames.setPadding(new Insets(15, 10, 10, 10));
        columnNames.setMaxSize(60,170);
        columnNames.setLayoutY(135.0);
        columnNames.setLayoutX(210.0);

        Label LabelCulumn1H = new Label("Column 1");
        LabelCulumn1H.getStyleClass().add("standardLabel");
        LabelCulumn1H.setMinSize(100, 30);
        LabelCulumn1H.setAlignment(Pos.CENTER);

        columnNames.getChildren().addAll(LabelCulumn1H);

        VBox column1VBox = new VBox();
        column1VBox.setSpacing(10);
        column1VBox.setPadding(new Insets(15, 10, 10, 10));
        column1VBox.setMinSize(100,390);
        column1VBox.setLayoutY(180.0);
        column1VBox.setLayoutX(220.0);
        column1VBox.getStyleClass().add("columnV");*/

    }

    //GEFP-20-SA
    protected static void addCustomComponents(AnchorPane anchorPane){
        //Lägg in tex egna knappar
    }

    //GEFP-20-SA
    private static Node createTitleBox(){
        //GEFP-4-SA
        VBox titelBox = new VBox();
        titelBox.setSpacing(5);
        titelBox.setPadding(new Insets(10, 10, 10, 10));
        titelBox.setLayoutY(30);
        titelBox.setLayoutX(((double) HelloApplication.width /2) - 110);//240, GEFP-25-SA, ändra storlek så den följer efter Stage storleken
        //700/2=350

        Label titel = new Label("Piper Games");
        titel.getStyleClass().add("titel");

        Label titel2 = new Label("(Pied Piper DB)");
        titel2.getStyleClass().add("titel");

        titelBox.getChildren().addAll(titel, titel2);
        titelBox.setAlignment(Pos.CENTER);//GEFP-26-SA ändra så vBoxen är Pos.CENTER så labels i den är centrerade över varandra
        return titelBox;
    }
    //GEFP-20-SA
    private static Node createPlaceForButtons(){
        //GEFP-4-SA
        Rectangle rectangleBGButtons = new Rectangle();
        rectangleBGButtons.setHeight(HelloApplication.height-160);//GEFP-25-SA, ändra storlek så den följer efter Stage storleken
        rectangleBGButtons.setWidth(180);
        rectangleBGButtons.setFill(Paint.valueOf("#FFFFFF"));
        rectangleBGButtons.setOpacity(0.5);
        rectangleBGButtons.setY(140);
        rectangleBGButtons.setX(20);

        leftVbox = new VBox();
        leftVbox.setSpacing(10);
        leftVbox.setPadding(new Insets(15, 10, 10, 10));
        leftVbox.setMinSize(160,20);
        leftVbox.setMaxSize(170, 30);
        leftVbox.setLayoutY(140.0);
        leftVbox.setLayoutX(20.0);

        return rectangleBGButtons;

    }
    //GEFP-20-SA
    private static Node createRectagleForActions(){
        //GEFP-4-SA
        Rectangle rectangleBGForAction = new Rectangle();
        rectangleBGForAction.setHeight(HelloApplication.height-160);//440, GEFP-25-SA, ändra storlek så den följer efter Stage storleken
        rectangleBGForAction.setWidth(HelloApplication.width-235);//465, GEFP-25-SA, ändra storlek så den följer efter Stage storleken
        rectangleBGForAction.setFill(Paint.valueOf("#FFFFFF"));
        rectangleBGForAction.setOpacity(0.5);
        rectangleBGForAction.setY(140);
        rectangleBGForAction.setX(210);

        return rectangleBGForAction;
    }
    //GEFP-20-SA
    private static Node createBackButton(Stage window){
        //GEFP-4-SA
        HelloApplication helloApp = new HelloApplication();
        back = new Button("Back");
        back.getStyleClass().add("darkerButton");
        back.setMinSize(160, 30);
        back.setLayoutY(HelloApplication.height-65);//535.0, GEFP-25-SA, ändra storlek så den följer efter Stage storleken
        back.setLayoutX(30.0);

        return back;
    }
    //GEFP-20-SA
    private static Node createSearchField(){
        //GEFP-4-SA
        TextField searchField = new TextField();
        searchField.getStyleClass().add("textFieldOne");
        searchField.setPromptText("Search: ");
        searchField.setMinSize(150, 30);
        searchField.setLayoutY(100.0);
        searchField.setLayoutX(HelloApplication.width-215);//485.0, GEFP-25-SA, ändra storlek så den följer efter Stage storleken

        return searchField;
    }
    //GEFP-20-SA
    private static Node createUserButton(){
        //GEFP-4-SA
        Button userButton = new Button("UserName");
        userButton.getStyleClass().add("darkerButton");
        userButton.setMinSize(140, 30);
        userButton.setLayoutY(20.0);
        userButton.setLayoutX(HelloApplication.width-167);//533.0, GEFP-25-SA, ändra storlek så den följer efter Stage storleken

        return userButton;
    }
}