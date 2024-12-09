package com.example.piedpiperdb.View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MatchView extends AbstractScene {

    private static ListView matchListView;
    private static Button submit;

    public static Scene startSceneMatch(Stage window){
        Scene baseScene = AbstractScene.getScene(window);
        AnchorPane anchorPane = AbstractScene.anchorPane;
        VBox vBox = AbstractScene.leftVbox;

        AbstractScene.back.setOnAction(event -> {

        });


        return baseScene;
    }



}
