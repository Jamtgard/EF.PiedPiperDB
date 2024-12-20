package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.JavaFXActions.ChangeSceneAction;
import com.example.piedpiperdb.DAO.JavaFXActions.GameActions;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//GEFP-4-SA
public class StartPage extends AbstractScene{

    //GEFP-20-SA
    public static Scene startScene(Stage window){//Denna metod är den som kallas när man klickar på Login button i HelloApplication
        Scene baseScene = AbstractScene.getScene(window);

        AnchorPane anchorPane = AbstractScene.anchorPane;
        VBox vBox = AbstractScene.leftVbox;

        HelloApplication helloApp = new HelloApplication();
        AbstractScene.back.setOnAction(e->{
            ChangeSceneAction.toLoginPage(window);//GEFP-22-SA
        });

        addCustomComponents(anchorPane,window);
        addCustomComponents(vBox,window);

        baseScene.getStylesheets().add("EscortFlasher.css");



        return baseScene;
    }


    //GEFP-20-SA
    protected static void addCustomComponents(AnchorPane anchorPane,Stage window){
        //GEFP-43-SA, tog bort example hBox o knappar
    }

    //GEFP-20-SA
    protected static void addCustomComponents(VBox vBox,Stage window){
        System.out.println("I StartPage addCustomComponents");

        Button gameViewButton = new Button("Games");
        gameViewButton.getStyleClass().add("standardButton");
        gameViewButton.setMinSize(160, 30);

        gameViewButton.setOnAction(e->{
            ChangeSceneAction.toGameView(window);//GEFP-22-SA
        });

        //GEFP-23-AWS
        Button matchViewButton = new Button("Matches");
        matchViewButton.getStyleClass().add("standardButton");
        matchViewButton.setMinSize(160, 30);
        matchViewButton.setOnAction(e->{
            ChangeSceneAction.toMatchView(window);
        });

        //GEFP-19-AA
        Button playerViewButton= new Button("Players");
        playerViewButton.getStyleClass().add("standardButton");
        playerViewButton.setMinSize(160, 30);

        playerViewButton.setOnAction(e->{
            ChangeSceneAction.toPlayerView(window);
        });

        //GEFP-27-SJ
        Button teamViewButton = new Button("Teams");
        teamViewButton.getStyleClass().add("standardButton");
        teamViewButton.setMinSize(160, 30);

        teamViewButton.setOnAction(e->{
            ChangeSceneAction.toTeamView(window);
        });

        vBox.getChildren().addAll(gameViewButton,matchViewButton, playerViewButton, teamViewButton);
    }



}
