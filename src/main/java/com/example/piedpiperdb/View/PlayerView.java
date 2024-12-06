package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.DAO.PlayerDAO;
import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//GEFP-19-AA
public class PlayerView extends AbstractScene{

    private static PlayerDAO playerDAO = new PlayerDAO();
    private static GameDAO gameDAO = new GameDAO();

    public static Scene playerScene(Stage window){
        Scene baseScene = AbstractScene.getScene(window); // Skapar en sen från mallen i abstract Scene

        AnchorPane anchorPane = AbstractScene.anchorPane;
        VBox vBox = AbstractScene.leftVbox;


/*        HelloApplication helloApp = new HelloApplication();
        AbstractScene.back.setOnAction(e->{
            try {
                helloApp.start(window);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });*/

        //addCustomComponents(anchorPane);
        addCustomComponents(vBox);



        AnchorPane rootPane = (AnchorPane) baseScene.getRoot();

        return baseScene;
    }

    private static void addCustomComponents(VBox vBox){
/*            List<Game> games = gameDAO.getAllGames();
            List<CheckBox> checkBoxes = new ArrayList<>();


            for (Game game : games) {
                String name = game.getGame_name();
                int gameId = game.getGame_id();
                CheckBox checkBox  = new CheckBox(name + ", GameID: " + gameId );
                checkBoxes.add(checkBox);
            }*/

            ObservableList<String> options = FXCollections.observableArrayList(
                    "Players from all games",
                    "Game 1",
                    "Game 2"
            );
            List<String> selectedOptions = new ArrayList<>();
            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.setPromptText("Select players from... ");
            VBox checkboxBoxContainer = new VBox();
            options.forEach(option -> {
                CheckBox checkBox = new CheckBox(option);
                checkBox.setOnAction(event -> {
                    if (checkBox.isSelected()) {
                        selectedOptions.add(option);
                    } else {
                        selectedOptions.remove(option);
                    }
                });
                comboBox.setPromptText("Selected: " + option);
            });

            Label selected = new Label();
            comboBox.setOnAction(event -> {
                selected.setText("Selected games: " + String.join(", " , selectedOptions));
            });





/*            allGames.setOnAction(actionEvent -> {
                boolean allGamesSelected = allGames.isSelected();
                for (CheckBox checkBox : checkBoxes) {
                    checkBox.setSelected(allGamesSelected);
                }

            });*/

            Button getPlayersFromSelectedgamesButton = new Button("Show selected players");
            getPlayersFromSelectedgamesButton.getStyleClass().add("standardButton");
            getPlayersFromSelectedgamesButton.setMinSize(160, 30);
/*            getPlayersFromSelectedgamesButton.setOnAction(e -> {
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


            });*/

            Button addNewPlayerButton = new Button("Add new player");
            addNewPlayerButton.getStyleClass().add("standardButton");
            addNewPlayerButton.setMinSize(160, 30);
/*            addNewPlayerButton.setOnAction(e -> {

            });*/

            Button updatePlayerByIdButton = new Button("Update player by ID");
            updatePlayerByIdButton.getStyleClass().add("standardButton");
            updatePlayerByIdButton.setMinSize(160, 30);
/*            updatePlayerByIdButton.setOnAction(e -> {

            });*/

            Button deletePlayerByIdButton = new Button("Delete player by ID");
            deletePlayerByIdButton.getStyleClass().add("standardButton");
            deletePlayerByIdButton.setMinSize(160, 30);
 /*           deletePlayerByIdButton.setOnAction(e -> {

            });*/

            vBox.getChildren().addAll(comboBox, checkboxBoxContainer, selected,getPlayersFromSelectedgamesButton, addNewPlayerButton, updatePlayerByIdButton, deletePlayerByIdButton );


    }

    protected static void addCustomComponents(AnchorPane anchorPane){

    }
}
