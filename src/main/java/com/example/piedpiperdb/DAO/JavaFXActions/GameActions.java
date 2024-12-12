package com.example.piedpiperdb.DAO.JavaFXActions;

import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.DAO.MatchDAO;
import com.example.piedpiperdb.DAO.PlayerDAO;
import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Match;
import com.example.piedpiperdb.Entities.Player;
import com.example.piedpiperdb.View.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//GEFP-22-SA
public class GameActions {

    //GEFP-22-SA
    private static GameDAO gameDAO = new GameDAO();

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
        if(gameListView.getSelectionModel().getSelectedItems().size() == gameListView.getItems().size()) {
            AlertBox.displayAlertBox("Delete game","You can't delete all games");
        }else {

            Set<Integer> gameById = stringToId(gameListView);

            for (Integer gameId : gameById) {
                System.out.println(gameId);
            }


            for (Integer gameId : gameById) {
                System.out.println("Deleting game with id " + gameId);
                gameDAO.updatePlayersBeforeDelete(gameId);
                gameDAO.updateMatchesBeforeDelete(gameId);
                gameDAO.updateTeamsBeforeDelete(gameId);
                gameDAO.deleteGameById(gameId);
            }
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

        //GEFP-26-SA, la till if-sats för om textfield är tomt
        if(textField.getText().isEmpty() || textField.getText().replaceAll("\\s+","").equals("")){
            AlertBox.displayAlertBox("Update game","You can't update \""+choiceBox.getValue()+"\" to an empty game name");
        } else {
            String selectedGame = choiceBox.getValue();
            System.out.println(selectedGame);

            String newGameName = textField.getText();
            System.out.println(newGameName);

            ObservableList<Game> game = getGames();

            for (int i = 0; i < game.size(); i++) {
                if (game.get(i).getGameName().equals(selectedGame)) {
                    Game oldGame = game.get(i);
                    System.out.println(oldGame.getGameName());
                    gameDAO.updateGame(oldGame, newGameName);
                }
            }

        }
    }

    public static List<Player> getAllPlayers(){
        PlayerDAO playerDAO = new PlayerDAO();
        ObservableList<Player>players = FXCollections.observableArrayList(playerDAO.getAllPlayers());

        /*for(Game game : gameDAO.getAllGames()){
            players.add(game.getPlayers().get(0));
        }*/
        return players;
    }
    public static List<Match> getAllMatches(){
        MatchDAO matchDAO = new MatchDAO();
        ObservableList<Match>matches = FXCollections.observableArrayList(matchDAO.getAllMatches());
        /*for(Game game : gameDAO.getAllGames()){
            matches.add(game.getMatches().get(0));
        }*/
        return matches;
    }


}
