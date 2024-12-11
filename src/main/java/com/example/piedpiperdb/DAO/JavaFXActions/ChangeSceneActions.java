package com.example.piedpiperdb.DAO.JavaFXActions;

import com.example.piedpiperdb.View.GameView;
import com.example.piedpiperdb.View.HelloApplication;
import com.example.piedpiperdb.View.StartPage;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChangeSceneActions {
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
}
