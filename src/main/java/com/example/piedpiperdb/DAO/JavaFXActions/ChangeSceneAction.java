package com.example.piedpiperdb.DAO.JavaFXActions;

import com.example.piedpiperdb.View.GameView;
import com.example.piedpiperdb.View.HelloApplication;
import com.example.piedpiperdb.View.PlayerView;
import com.example.piedpiperdb.View.StartPage;
import javafx.scene.Scene;
import javafx.stage.Stage;

//GEFP-22-SA
public class ChangeSceneAction {

    //GEFP-22-SA
    public static void toLoginPage(Stage window) {
        Scene start = HelloApplication.getStartScene();
        window.setTitle("Login");
        window.setScene(start);
    }

    //GEFP-22-SA
    public static void toStartPage(Stage window){
        window.setTitle("Start page");
        window.setScene(StartPage.startScene(window));
    }

    //GEFP-22-SA
    public static void toGameView(Stage window){
        window.setTitle("Games");
        window.setScene(GameView.startSceneGame(window));
    }

    //GEFP-19-AA
    public static void toPlayerView(Stage window){
        window.setTitle("Players");
        window.setScene(PlayerView.playerScene(window));
    }
}
