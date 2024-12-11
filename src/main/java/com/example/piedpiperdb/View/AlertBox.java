package com.example.piedpiperdb.View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//GEFP-24-SJ
public class AlertBox {
//    private static boolean answer = false;

    //GEFP-24-SJ Version 1
    /*
    public static void displayAlertBox(String title, String message) {
        Stage window = new Stage();

        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setMinWidth(250);
        window.setMinHeight(200);

        Label label = new Label(message + "\n\n\n\n");

        Button okButton = new Button("OK");
        okButton.getStyleClass().add("standardButton");
        okButton.setOnAction(e -> window.close());

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(okButton);

        VBox layout = new VBox(label, buttonLayout);
        layout.getStyleClass().add("backgroundTeaGreen");
        layout.getStyleClass().add("standardLabel");
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("EscortFlasher.css");
        window.setScene(scene);
        window.showAndWait();

    }

     */

    //GEFP-21-SJ version 2  - (Att gå till en tidigare branch är förrvirrande, men gör detta i samband med TeamView)
    public static void displayAlertBox(String title, String message) {

        // Test-Stängar för att se Scaling-effekt
         /*
        message = "Kort meddelande";
        message = "Absolut ett längre meddelande";
        message = "Är detta det längsta meddelandet? vad tror du min vän?";
        message = "Nej, jag tror att det 3e meddelandet är kortare än det 4e och absolut kortare än det 5e... skulle aldrig växa sig lika stor som t.ex ett 6e meddelande och jag tror inte att nån någonsin skulle kunna räkna längen på ett 7e.";
          */

        Region spacer = new Region();
        spacer.setPrefHeight(40);

        int charWidth = 7; // - Om vi ändrar i CSS med fonts/storlek så kommer vi behöva ändra detta.
        int spacingToEdge = 50;
        double calculatedWidth = message.length() * charWidth + spacingToEdge;
        double width = Math.min(500, Math.max(250, calculatedWidth));

        Stage window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);

        Label label = new Label(message);
        label.setWrapText(true);
        label.setMaxWidth(width - spacingToEdge);
        label.setAlignment(Pos.CENTER);

        VBox tempLayout = new VBox(label);
        tempLayout.getStyleClass().add("backgroundTeaGreen");
        tempLayout.getStyleClass().add("standardLabel");
        tempLayout.layout();

        Button okButton = new Button("OK");
        okButton.getStyleClass().add("standardButton");
        okButton.setOnAction(e -> window.close());

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(okButton);

        VBox layout = new VBox(label, spacer, buttonLayout);
        layout.getStyleClass().add("backgroundTeaGreen");
        layout.getStyleClass().add("standardLabel");
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, width, 200); // Standardhöjd
        scene.getStylesheets().add("EscortFlasher.css");
        window.setScene(scene);
        window.showAndWait();
    }
}
