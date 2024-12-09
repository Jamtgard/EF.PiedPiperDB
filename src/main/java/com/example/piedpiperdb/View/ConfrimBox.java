package com.example.piedpiperdb.View;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

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
        layout.getStyleClass().add("standardLabel");
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("EscortFlasher.css");
        window.setScene(scene);
        window.showAndWait();

        return answer;
    };

    //GEFP-19-AA
    public static List<String> displayCheckBoxOptions(String title,  List<CheckBox> checkBoxes) {

        List<String> selectedOptions = new ArrayList<>();

        Stage window = new Stage();

        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(350);
        window.setMinHeight(400);

        Label label = new Label("Please select one or several options");

        checkBoxes.forEach(checkBox -> {
                checkBox.setOnAction(event -> {
                    if (checkBox.isSelected()) {
                        selectedOptions.add(checkBox.getText());
                    } else {
                        selectedOptions.remove(checkBox.getText());
                    }
                });
            });

        Button confirm = new Button("Confirm options");
        confirm.getStyleClass().add("standardButton");
        confirm.setOnAction(e -> {
            window.close();
        });

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().add(confirm);

        VBox layout = new VBox(10);
        layout.getStyleClass().add("backgroundTeaGreen");
        layout.getStyleClass().add("standardLabel");
        layout.setAlignment(Pos.TOP_LEFT);
        layout.getChildren().add(label);
        layout.getChildren().addAll(checkBoxes);
        layout.getChildren().add(buttonLayout);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("EscortFlasher.css");
        window.setScene(scene);
        window.showAndWait();


        return selectedOptions;

    }

}
