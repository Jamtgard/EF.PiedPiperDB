package com.example.piedpiperdb.View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//GEFP-24-SJ
public class AlertBox {
//    private static boolean answer = false;

    public static void displayAlertBox(String title, String message) {
        Stage window = new Stage();

        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setMinWidth(250);
        window.setMinHeight(200);

        Label label = new Label(message);

        Button okButton = new Button("OK");
        okButton.getStyleClass().add("standardButton");
        okButton.setOnAction(e -> window.close());

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(okButton);

        VBox layout = new VBox(label, buttonLayout);
        layout.getStyleClass().add("standardLayout");
        layout.getStyleClass().add("standardLabel");
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("EscortFlasher.css");
        window.setScene(scene);
        window.showAndWait();

    }
}
