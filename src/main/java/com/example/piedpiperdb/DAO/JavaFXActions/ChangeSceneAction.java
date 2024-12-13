package com.example.piedpiperdb.DAO.JavaFXActions;

import com.example.piedpiperdb.View.*;
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
    //GEFP-23-AWS
    public static void toMatchView(Stage window){
        window.setTitle("Matches");
        window.setScene(MatchView.startSceneMatch(window));
    }

    //GEFP-27-SJ
    public static void toTeamView(Stage window){
        window.setTitle("Teams");
        window.setScene(TeamView.startTeamScene(window));
    }

}
