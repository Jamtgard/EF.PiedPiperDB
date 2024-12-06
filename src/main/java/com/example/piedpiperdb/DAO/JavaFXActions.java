package com.example.piedpiperdb.DAO;

import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.View.AbstractScene;
import com.example.piedpiperdb.View.GameView;
import com.example.piedpiperdb.View.HelloApplication;
import com.example.piedpiperdb.View.StartPage;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

//GEFP-22-SA
public class JavaFXActions {

    //GEFP-22-SA
    public static void backToLogin(Stage window) {
        HelloApplication helloApp = new HelloApplication();
        window.setTitle("Login");
        try {
            helloApp.start(window);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    //GEFP-22-SA
    public static void backToStart(Stage window){
        window.setTitle("Start page");
        window.setScene(StartPage.startScene(window));
    }

    //GEFP-22-SA
    public static void toGameView(Stage window){
        window.setTitle("Games");
        window.setScene(GameView.startSceneGame(window));
    }

    //GEFP-22-SA
    public static ObservableList<String> getGames(){
        GameDAO gameDAO = new GameDAO();
        ObservableList<Game> games = FXCollections.observableArrayList(gameDAO.getAllGames());

        ObservableList<String> gameName = FXCollections.observableArrayList();
        for(Game game : games){
            gameName.add(game.getGame_name());
        }

        return gameName;
    }

    //GEFP-22-SA
    public static ListView gameListView(Button submit){
        ListView gameListView = new ListView();
        gameListView.getItems().addAll(getGames());
        gameListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        gameListView.setMinSize(445,420);

        submitClick(submit, gameListView);

        return gameListView;
    }

    //GEFP-22-SA
    public static void submitClick(Button submit, ListView gameListView){
        VBox vBox = AbstractScene.leftVbox;

        vBox.getChildren().clear();
        vBox.getChildren().addAll(submit);

        submit.setOnAction(event -> {
            String message ="";
            ObservableList<String> games;
            games = gameListView.getSelectionModel().getSelectedItems();

            for(String g : games){
                message += g + "\n";
            }
            System.out.println(message);
        });

        AbstractScene.back.setOnAction(event -> {
            toGameView(HelloApplication.window);
        });
    }

}
