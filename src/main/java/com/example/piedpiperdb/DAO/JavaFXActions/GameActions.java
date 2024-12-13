package com.example.piedpiperdb.DAO.JavaFXActions;

import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Match;
import com.example.piedpiperdb.Entities.Player;
import com.example.piedpiperdb.View.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.*;

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
    //GEFP-26-SA
/*
    public static ObservableList<Game> stringToGame(ObservableList<String>gameObservableList){
        //Får in string med valda spel
        //Vill få ut de spel som är valda, inte bara string, utan objektet
        ObservableList<Game> games = getGames();

        ObservableList<Game> gamesToSendBack = FXCollections.observableArrayList();

        Set<Integer> gameById = new HashSet<>();
        System.out.println("w-----------------------------------------------------------------------------------------");
        for(String name : gameObservableList){
            for(Game game : games){
                if(game.getGameName().equals(name)){
                    System.out.println("Valt spel: "+name);
                    gameById.add(game.getGameId());
                }
            }

        }

        for(Integer gameId : gameById){
            gamesToSendBack.add(gameDAO.getGameById(gameId));
            System.out.println("Valt spel: "+gameId);
        }

        return gamesToSendBack;
    }
*/

    //GEFP-22-SA
    public static void deleteGame(ListView gameListView) {
        if(gameListView.getSelectionModel().getSelectedItems().size() == gameListView.getItems().size()) {
            AlertBox.displayAlertBox("Delete game","You can't delete all games");
        }else {

            Set<Integer> gameById = stringToId(gameListView);

            System.out.println("p-----------------------------------------------------------------------------------------------");
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

    //GEFP-26-SA
    public static void getPlayerForGame(ListView gameListView) {
        Set<Integer> gameById = stringToId(gameListView);//Får in alla id på valda spel

        System.out.println("y-------------------------------------------------------");
        if(gameById.isEmpty()){
            System.out.println("No game selected");
        }else {
            for (Integer game : gameById) {

                Game selectedGame = gameDAO.getGameById(game);

                String gameName = selectedGame.getGameName();

                if (selectedGame.getPlayers().isEmpty()) {
                    ConfirmBox.noPlayersOrMathesOfGame(gameName, "No players","Players");

                } else {
                    ObservableList<Player>players = FXCollections.observableArrayList(selectedGame.getPlayers());
                    ConfirmBox.playersOfGame(gameName,players);
                }
            }
        }
    }
    //GEFP-26-SA
    public static void getMatchesForGame(ListView gameListView) {
        Set<Integer> gameById = stringToId(gameListView);//Får in alla id på valda spel

        System.out.println("y-------------------------------------------------------");
        if(gameById.isEmpty()){
            System.out.println("No game selected");
        }else {
            for (Integer game : gameById) {

                Game selectedGame = gameDAO.getGameById(game);

                String gameName = selectedGame.getGameName();

                if (selectedGame.getMatches().isEmpty()) {
                    ConfirmBox.noPlayersOrMathesOfGame(gameName, "No matches","Matches");

                } else {
                    ObservableList<Match>matches = FXCollections.observableArrayList(selectedGame.getMatches());
                    ConfirmBox.matchesOfGame(gameName,matches);
                }
            }
        }
    }

}
