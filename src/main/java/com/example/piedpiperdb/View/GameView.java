package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.JavaFXActions;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

//GEFP-22-SA
public class GameView extends AbstractScene{

    //GEFP-22-SA
    private static ListView gameListView;
    private static Button submit;

    //GEFP-22-SA
    public static Scene startSceneGame(Stage window){//Denna metod är den som kallas när man klickar på Login button i HelloApplication
        Scene baseScene = AbstractScene.getScene(window);

        AnchorPane anchorPane = AbstractScene.anchorPane;
        VBox vBox = AbstractScene.leftVbox;

        AbstractScene.back.setOnAction(e->{
            JavaFXActions.backToStart(window);
        });

        addCustomComponents(vBox);


        return baseScene;
    }


    //GEFP-20-SA
    protected static void addCustomComponents(AnchorPane anchorPane){


        //GEFP-22-SA
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getStyleClass().add("columnV");
        hBox.setLayoutY(140.0);
        hBox.setLayoutX(210);
        hBox.setMinSize(445, 420);
        hBox.setMaxSize(465, 440);

        hBox.getChildren().add(gameListView);

        anchorPane.getChildren().addAll(hBox);
    }

    //GEFP-20-SA
    protected static void addCustomComponents(VBox vBox){

        //GEFP-22-SA
        Button showGames = new Button("Show games");
        showGames.getStyleClass().add("standardButton");
        showGames.setMinSize(160, 30);

        submit = new Button("Choose games");
        submit.getStyleClass().add("standardButton");
        submit.setMinSize(160, 30);

        showGames.setOnAction(e->{
            gameListView = JavaFXActions.gameListView(submit);
            addCustomComponents(anchorPane);
        });


        Button addGame = new Button("Add game");
        addGame.getStyleClass().add("standardButton");
        addGame.setMinSize(160, 30);

        Button deleteGame = new Button("Delete game");
        deleteGame.getStyleClass().add("standardButton");
        deleteGame.setMinSize(160, 30);

        vBox.getChildren().addAll(showGames,addGame,deleteGame);
    }
}
