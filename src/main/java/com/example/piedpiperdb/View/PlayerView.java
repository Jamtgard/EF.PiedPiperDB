package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.DAO.PlayerDAO;
import com.example.piedpiperdb.Entities.Game;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
            List<Game> games = gameDAO.getAllGames();
            List<CheckBox> checkBoxes = new ArrayList<>();


            for (Game game : games) {
                String name = game.getGame_name();
                int gameId = game.getGame_id();
                CheckBox checkBox  = new CheckBox(name + ", GameID: " + gameId );
                checkBoxes.add(checkBox);
            }

            Button getAllPlayers = new Button("Show selected players");
            getAllPlayers.getStyleClass().add("standardButton");
            getAllPlayers.setMinSize(160, 30);


            Button selectedPlayers = new Button("Show players from \nselected game or games");
            selectedPlayers.getStyleClass().add("standardButton");
            selectedPlayers.setMinSize(160, 50);
            selectedPlayers.setOnAction(actionEvent -> {
                List<String> selections = ConfrimBox.displayCheckBoxOptions("Select game or games", checkBoxes);

            });


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

            vBox.getChildren().addAll(getAllPlayers, selectedPlayers, addNewPlayerButton, updatePlayerByIdButton, deletePlayerByIdButton );


    }

    protected static void addCustomComponents(AnchorPane anchorPane){

    }
}
