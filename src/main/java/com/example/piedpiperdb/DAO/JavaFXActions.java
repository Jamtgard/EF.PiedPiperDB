package com.example.piedpiperdb.DAO;

import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.View.AbstractScene;
import com.example.piedpiperdb.View.GameView;
import com.example.piedpiperdb.View.HelloApplication;
import com.example.piedpiperdb.View.StartPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

//GEFP-22-SA
public class JavaFXActions {

    private static ListView gameListViewAction;
    private static GameDAO gameDAO = new GameDAO();

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

    //GEFP-22-SA
    public static ObservableList<Game> getGames(){
        ObservableList<Game> games = FXCollections.observableArrayList(gameDAO.getAllGames());
        return games;
    }

    //GEFP-22-SA
    public static ObservableList<String> getGamesString(){
        ObservableList<Game> games = getGames();

        ObservableList<String> gameName = FXCollections.observableArrayList();
        for(Game game : games){
            gameName.add(game.getGameName());
        }

        return gameName;
    }


    //GEFP-22-SA
    public static ListView gameListView(ListView gameListView){
        gameListView.getItems().addAll(getGamesString());
        gameListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
    //GEFP-22-SA
    public static Set<Integer> stringToId(ListView gameListView){
        ObservableList<Game> games = getGames();
        ObservableList<String> gameNames;
        gameNames = gameListView.getSelectionModel().getSelectedItems();
        Set<Integer> gameById = new HashSet<>();

        for(String name : gameNames){
            for(Game game : games){
                if(game.getGameName().equals(name)){
                    gameById.add(game.getGameId());
                }
            }

        }
        return gameById;
    }

    //GEFP-22-SA
    public static void deleteGame(ListView gameListView) {
        Set<Integer>gameById = stringToId(gameListView);

        for(Integer gameId : gameById){
            System.out.println(gameId);
        }


        for(Integer gameId : gameById){
            System.out.println("Deleting game with id " + gameId);
            System.out.println("a-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            //gameDAO.updatePlayersTeamIdBeforeDelete(gameId);
            gameDAO.deleteGameById(gameId);
        }


    }
    //GEFP-22-SA
    public static void addGame(TextField textField) {
        String gameName = textField.getText();
        Game newGame = new Game(gameName);
        gameDAO.saveGame(newGame);

    }
    //GEFP-22-SA
    public static void updateGame(TextField textField,ChoiceBox<String> choiceBox) {

        String selectedGame = choiceBox.getValue();
        System.out.println(selectedGame);
        System.out.println("j------------------------------------------------------------------------------------------------");

        String newGameName = textField.getText();
        System.out.println(newGameName);
        System.out.println("l----------------------------------------------------------------------------------------------------------------------");

        ObservableList<Game> game = getGames();

        for (int i = 0; i < game.size(); i++) {
            if(game.get(i).getGameName().equals(selectedGame)){
                Game oldGame = game.get(i);
                System.out.println(oldGame.getGameName());
                System.out.println("k-----------------------------------------------------------------------------------------------------------------");
                gameDAO.updateGame(oldGame, newGameName);
            }
        }


        /*
        Game oldGame = gameDAO.getGameById(5);
        System.out.println("e---------------------------------------------------------------------------------------------------------------");

        gameDAO.updateGame(oldGame, newGameName);*/



    }


}
