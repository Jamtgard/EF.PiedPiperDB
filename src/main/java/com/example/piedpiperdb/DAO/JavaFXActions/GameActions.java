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

    //CRUD

    //Create
    //GEFP-22-SA
    public static void addGame(TextField textField) {
        //GEFP-34-SA
        if(textField.getText().isEmpty() || textField.getText().replaceAll("\\s+", "").isEmpty()){
            AlertBox.displayAlertBox("Add game","You can't add an empty game name");
        } else {
            String gameNameLowerCase = textField.getText().toLowerCase();

            boolean gameExists = false;
            for(String game : getGamesString()){
                if(game.toLowerCase().contains(gameNameLowerCase)){
                    gameExists = true;
                    break;
                }
            }

            if(gameExists){
                AlertBox.displayAlertBox("Add game","This game name is already in use");
                return;
            }

            //GEFP-22-SA
            String gameName = textField.getText();
            Game newGame = new Game(firstLetterCap(gameName));
            gameDAO.saveGame(newGame);
        }

    }

    //Read
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
    public static void getPlayerForGame(ListView gameListView) {
        Set<Integer> gameById = stringToId(gameListView);//Får in alla id på valda spel

        if(gameById.isEmpty()){
            System.out.println("No game selected");
        }else {
            for (Integer game : gameById) {

                Game selectedGame = gameDAO.getGameById(game);

                String gameName = selectedGame.getGameName();

                if (selectedGame.getPlayers().isEmpty()) {
                    ConfirmBox.noPlayersOrMatchesOfGame(gameName, "No players","Players");

                } else {
                    ObservableList<Player>players = FXCollections.observableArrayList(selectedGame.getPlayers());
                    String playersCount = String.valueOf(players.size());
                    ConfirmBox.playersOfGame(gameName,players,playersCount);
                }
            }
        }
    }
    //GEFP-26-SA
    public static void getMatchesForGame(ListView gameListView) {
        Set<Integer> gameById = stringToId(gameListView);//Får in alla id på valda spel

        if(gameById.isEmpty()){
            System.out.println("No game selected");
        }else {
            for (Integer game : gameById) {

                Game selectedGame = gameDAO.getGameById(game);

                String gameName = selectedGame.getGameName();

                if (selectedGame.getMatches().isEmpty()) {
                    ConfirmBox.noPlayersOrMatchesOfGame(gameName, "No matches","Matches");

                } else {
                    ObservableList<Match>matches = FXCollections.observableArrayList(selectedGame.getMatches());
                    String matchesCount = String.valueOf(matches.size());
                    ConfirmBox.matchesOfGame(gameName,matches,matchesCount);
                }
            }
        }
    }

    //Update
    //GEFP-34-SA
    public static boolean gameExits(TextField textField) {
        String gameName = textField.getText().toLowerCase();

        boolean gameExists = false;
        for(String game : getGamesString()){
            if(game.toLowerCase().contains(gameName)){
                gameExists = true;
                break;
            }
        }
        if(gameExists){
            AlertBox.displayAlertBox("Update game","This game name is already in use");
            return true;
        }
        return false;
    }

    //GEFP-39-SA
    public static String firstLetterCap(String gameName){
        String caps = gameName.toUpperCase();
        String withCap = caps.charAt(0) + gameName.substring(1);
        System.out.println(withCap);
        return withCap;
    }

    //GEFP-22-SA
    public static void updateGame(TextField textField,ChoiceBox<String> choiceBox) {
        //GEFP-26-SA, la till if-sats för om textfield är tomt
        if(textField.getText().isEmpty() || textField.getText().replaceAll("\\s+","").equals("")){
            AlertBox.displayAlertBox("Update game","You can't update \""+choiceBox.getValue()+"\" to an empty game name");
            return;
        } else {//GEFP-34-SA, la till om man försöker uppdatera till ett spelnamn som redan finns
            if(GameActions.gameExits(textField)){
                return;
            }
        }

        String selectedGame = choiceBox.getValue();
        System.out.println(selectedGame);

        String newGameName = textField.getText();
        System.out.println(newGameName);

        ObservableList<Game> game = getGames();

        for (int i = 0; i < game.size(); i++) {
            if (game.get(i).getGameName().equals(selectedGame)) {
                Game oldGame = game.get(i);
                System.out.println(oldGame.getGameName());
                gameDAO.updateGame(oldGame, firstLetterCap(newGameName));
            }
        }


    }
    //GEFP-34-SA
    public static void updateGame(TextField input,TextField newName) {
        if(input.getText().isEmpty() || input.getText().replaceAll("\\s+","").equals("") ||newName.getText().isEmpty() || newName.getText().replaceAll("\\s+","").equals("")){
            AlertBox.displayAlertBox("Update game","No id or name in text field");

        }else {
            try{
                int gameId = Integer.parseInt(input.getText().trim());

                if(gameDAO.getGameById(gameId) != null){
                    System.out.println("Updating game with id " + gameId);

                    if(GameActions.gameExits(newName)){
                        return;
                    }

                    gameDAO.updateGame(gameDAO.getGameById(gameId), newName.getText());

                }else {
                    AlertBox.displayAlertBox("Update game","No game with that id");
                }

            }catch (NumberFormatException e){
                AlertBox.displayAlertBox("Update game","Not an id");
            }

        }

    }
    //GEFP-34-SA
    public static void updateGameListView(ListView<String> gameListViewDelete){
        gameListViewDelete.getItems().clear();
        gameListViewDelete.getItems().addAll(GameActions.getGamesString());
        gameListViewDelete.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    public static void updateChoiceBoxTextField(ChoiceBox<String> choiceBox) {
        choiceBox.getItems().clear();
        choiceBox.getItems().addAll(GameActions.getGamesString());
        choiceBox.setValue(choiceBox.getItems().get(0));
    }
    public static void updateTableView(TableView<Game>tableViewGame, TableColumn<Game,String>gameId){
        tableViewGame.getItems().clear();
        tableViewGame.getItems().addAll(GameActions.getGames());
        tableViewGame.getSortOrder().add(gameId);
    }
    public static void updateInput(TextField input){
        input.clear();
    }


    //Delete
    //GEFP-34-SA
    public static void updateAndDelete(int gameId){
        gameDAO.updatePlayersBeforeDelete(gameId);
        gameDAO.updateMatchesBeforeDelete(gameId);
        gameDAO.updateTeamsBeforeDelete(gameId);
        gameDAO.deleteGameById(gameId);
    }
    //GEFP-22-SA
    public static void deleteGame(ListView<String> gameListView) {
        if(gameListView.getSelectionModel().getSelectedItems().size() == gameListView.getItems().size()) {
            AlertBox.displayAlertBox("Delete game","You can't delete all games");
        }else {

            Set<Integer> gameById = stringToId(gameListView);

            for (Integer gameId : gameById) {
                System.out.println("Deleting game with id " + gameId);
                updateAndDelete(gameId);
            }
        }

    }
    //GEFP-34-SA
    public static void deleteGameById(TextField input){
        if(input.getText().isEmpty() || input.getText().replaceAll("\\s+", "").isEmpty()){
            AlertBox.displayAlertBox("Delete game","No game selected");
        }else {
            try{
                int gameId = Integer.parseInt(input.getText().trim());

                if(gameDAO.getGameById(gameId) != null){
                    System.out.println("Deleting game with id " + gameId);
                    updateAndDelete(gameId);

                }else {
                    AlertBox.displayAlertBox("Delete game","No game with that id");
                }

            }catch (NumberFormatException e){
                AlertBox.displayAlertBox("Delete game","Not an id");
            }

        }
    }
}
