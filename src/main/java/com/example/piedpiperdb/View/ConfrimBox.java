package com.example.piedpiperdb.View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//GEFP-16-AA
public class ConfrimBox {
    private static boolean answer;

    public static boolean display(String title, String message){
        Stage window = new Stage();

        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);
        window.setMinHeight(200);

        Label label = new Label(message + "\n\n\n\n");

        Button yes = new Button("Yes");
        yes.getStyleClass().add("standardButton");
        Button no = new Button("No");
        no.getStyleClass().add("standardButton");

        yes.setOnAction(e -> {
            answer = true;
            window.close();
        });
        no.setOnAction(e -> {
            answer = false;
            window.close();
        });

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(yes, no);

        VBox layout = new VBox(label, buttonLayout);
        layout.getStyleClass().add("backgroundTeaGreen");
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("EscortFlasher.css");
        window.setScene(scene);
        window.showAndWait();

        return answer;
    };

}
