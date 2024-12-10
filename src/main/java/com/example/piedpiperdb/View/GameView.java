package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.JavaFXActions;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//GEFP-22-SA
public class GameView extends AbstractScene{

    //GEFP-22-SA
    private static Button submit;
    private static ListView gameListView;
    private static AnchorPane addStuff;
    private static Stage stage;

    //GEFP-22-SA
    public static Scene startSceneGame(Stage window){//Denna metod är den som kallas när man klickar på Login button i HelloApplication
        Scene baseScene = AbstractScene.getScene(window);
        stage = window;

        AnchorPane anchorPane = AbstractScene.anchorPane;
        VBox vBox = AbstractScene.leftVbox;

        AbstractScene.back.setOnAction(e->{
            JavaFXActions.toStartPage(window);
        });

        addCustomComponents(vBox);


        return baseScene;
    }


    //GEFP-20-SA
    protected static void addCustomComponents(AnchorPane anchorPane){


        //GEFP-22-SA

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setMinSize(445, 300);
        vBox.setMaxSize(465, 400);

        Label buttonLabel = new Label();
        buttonLabel.setText("Hold ctrl or shift to select more than one game");
        buttonLabel.getStyleClass().add("standardLabel");
        buttonLabel.setAlignment(Pos.CENTER);

        gameListView = new ListView();
        gameListView = JavaFXActions.gameListView(gameListView);
        gameListView.setMinSize(445,300);
        gameListView.getStyleClass().add("standardButton");

        vBox.getChildren().addAll(buttonLabel,gameListView);

        addStuff = new AnchorPane();
        addStuff.setLayoutY(150);
        addStuff.setLayoutX(220);
        addStuff.setMinSize(400, 400);
        addStuff.getStyleClass().add("columnV");


        addStuff.getChildren().addAll(vBox);

        anchorPane.getChildren().addAll(addStuff);
    }

    //GEFP-20-SA
    protected static void addCustomComponents(VBox vBox){

        //GEFP-22-SA

        addCustomComponents(anchorPane);

        VBox addGameBox = new VBox();
        addGameBox.setSpacing(10);
        addGameBox.setPadding(new Insets(10,10,10,10));
        addGameBox.setAlignment(Pos.CENTER);

        Label addGameLabel = new Label("Add a new game");
        addGameLabel.getStyleClass().add("titel");

        TextField newGameInput = new TextField();
        newGameInput.setPromptText("Game Name");
        newGameInput.getStyleClass().add("textFieldOne");
        newGameInput.setMinSize(150, 30);

        Button submitAdd = new Button("Submit");
        submitAdd.getStyleClass().add("standardButton");
        submitAdd.setMinSize(160, 30);

        addGameBox.getChildren().addAll(addGameLabel,newGameInput,submitAdd);


        Button addGame = new Button("Add game");
        addGame.getStyleClass().add("standardButton");
        addGame.setMinSize(160, 30);

        addGame.setOnAction(e->{
            clearAnchorpane(addGameBox);
            AbstractScene.back.setOnAction(event-> JavaFXActions.toGameView(stage));

            submitAdd.setOnAction(ev->{
                JavaFXActions.addGame(newGameInput);
                JavaFXActions.toGameView(stage);
            });
        });

//------------------------------------------------------------------

        Button deleteGame = new Button("Delete game");
        deleteGame.getStyleClass().add("standardButton");
        deleteGame.setMinSize(160, 30);
        deleteGame.setOnAction(e->{
            JavaFXActions.deleteGame(gameListView);
            JavaFXActions.toGameView(stage);
        });


//------------------------------------------------------------------

        VBox updateGameBox = new VBox();
        updateGameBox.setSpacing(10);
        updateGameBox.setPadding(new Insets(10,10,10,10));
        updateGameBox.setAlignment(Pos.CENTER);

        TextField updateGameInput = new TextField();
        updateGameInput.setPromptText("New name");
        updateGameInput.getStyleClass().add("textFieldOne");

        Button updateGame = new Button("Update game");
        updateGame.getStyleClass().add("standardButton");
        updateGame.setMinSize(160, 30);


        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(JavaFXActions.getGamesString());
        choiceBox.setValue(choiceBox.getItems().get(0));
        choiceBox.setMinSize(150, 30);
        choiceBox.getStyleClass().add("standardButton");

        Label chooseToUpdate = new Label("Choose game to update");
        chooseToUpdate.getStyleClass().add("titel");

        Button submitUpdate = new Button("Submit");
        submitUpdate.getStyleClass().add("standardButton");
        submitUpdate.setMinSize(160, 30);

        updateGameBox.getChildren().addAll(chooseToUpdate,choiceBox,updateGameInput,submitUpdate);

        updateGame.setOnAction(e->{
            clearAnchorpane(updateGameBox);

            submitUpdate.setOnAction(ev->{
                JavaFXActions.updateGame(updateGameInput,choiceBox);
                JavaFXActions.gameListView(gameListView);
                JavaFXActions.toGameView(stage);
            });

        });

        vBox.getChildren().addAll(addGame,deleteGame,updateGame);
    }

    public static void clearAnchorpane (VBox vBox){
        addStuff.getChildren().clear();
        addStuff.getChildren().addAll(vBox);
    }
}
