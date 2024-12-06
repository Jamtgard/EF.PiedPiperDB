package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.DAO.PlayerDAO;
import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

//GEFP-19-AA
public class PlayerView extends AbstractScene{

    private static PlayerDAO playerDAO = new PlayerDAO();
    private static GameDAO gameDAO = new GameDAO();

    public static Scene playerScene(Stage window){
        Scene baseScene = AbstractScene.getScene(window); // Skapar en sen från mallen i abstract Scene


        List<Game> games = gameDAO.getAllGames();
        List<CheckBox> checkBoxes = new ArrayList<>();


        for (Game game : games) {
           String name = game.getGame_name();
           int gameId = game.getGame_id();
           CheckBox checkBox  = new CheckBox(name + ", GameID: " + gameId );
           checkBoxes.add(checkBox);
        }

        CheckBox allGames = new CheckBox("All Games");

        allGames.setOnAction(actionEvent -> {
            boolean allGamesSelected = allGames.isSelected();
            for (CheckBox checkBox : checkBoxes) {
                checkBox.setSelected(allGamesSelected);
            }

        });

        Button allPlayersButton = new Button("Show players from selected games");
        allPlayersButton.setLayoutX(100);
        allPlayersButton.setLayoutY(100);
        allPlayersButton.setOnAction(e -> {
            if (allGames.isSelected()) {
                List<Player> players = playerDAO.getAllPlayers();
                ObservableList<Player> observablePlayers = FXCollections.observableArrayList(players);
                //tableView.setItems(observablePlayers); // Behöver skapa en anslutning till tableView eller likanande i den abstrakta scenen
            } else {
                List<Integer> ids = new ArrayList<>();
                for (CheckBox checkBox : checkBoxes) {
                    if (checkBox.isSelected()) {
                        String text = checkBox.getText();
                        int lastSpaceIndex = text.lastIndexOf(" ");
                        String stringId = text.substring(lastSpaceIndex + 1);
                        int id = Integer.parseInt(stringId);
                        ids.add(id);
                    }
                }
                List<Player> players = playerDAO.getAllPlayersFromSelectedGame(ids);
            }


        });

        Button addNewPlayerButton = new Button("Add new player");
        addNewPlayerButton.setLayoutX(100);
        addNewPlayerButton.setLayoutY(100);
        addNewPlayerButton.setOnAction(e -> {

       });

        Button updatePlayerByIdButton = new Button("Update player by ID");
        updatePlayerByIdButton.setLayoutX(100);
        updatePlayerByIdButton.setLayoutY(100);
        updatePlayerByIdButton.setOnAction(e -> {

        });

        Button deletePlayerByIdButton = new Button("Delete player by ID");
        deletePlayerByIdButton.setLayoutX(100);
        deletePlayerByIdButton.setLayoutY(100);
        deletePlayerByIdButton.setOnAction(e -> {

        });


        AnchorPane rootPane = (AnchorPane) baseScene.getRoot();
       //rootPane.getChildren.addAll(allGames,checkBoxes,addNewPlayerButton, updatePlayerByIdButton, deletePlayerByIdButton);


        return baseScene;
    }
}
