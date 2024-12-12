package com.example.piedpiperdb.View;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

//GEFP-16-AA
public class ConfirmBox {
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
            checkBoxes.forEach(checkBox -> checkBox.setSelected(false));
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

    public static void playersOfGame(String game, String players){
        Stage window = new Stage();
        window.setTitle("Player of "+game);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(300);
        window.setMinHeight(250);

        Label gameLabel = new Label(game);
        gameLabel.getStyleClass().add("titel");

        VBox layout = new VBox();
        layout.setSpacing(5);
        layout.setAlignment(Pos.CENTER);

        Label label = new Label("Players");
        label.getStyleClass().add("standardLabel");

        Label playersLabel = new Label(players);
        playersLabel.getStyleClass().add("standardLabelNoBorder");
        playersLabel.setLineSpacing(5);

        layout.getChildren().addAll(label, playersLabel);

        Button ok = new Button("Ok");
        ok.getStyleClass().add("standardButton");
        ok.setOnAction(e -> {window.close();});

        VBox vbox = new VBox();
        vbox.setSpacing(25);
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("backgroundTeaGreen");
        vbox.getChildren().addAll(gameLabel, layout,ok);

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add("EscortFlasher.css");
        window.setScene(scene);
        window.showAndWait();
    }
}
