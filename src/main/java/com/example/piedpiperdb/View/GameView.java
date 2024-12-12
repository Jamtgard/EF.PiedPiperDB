package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.JavaFXActions.ChangeSceneAction;
import com.example.piedpiperdb.DAO.JavaFXActions.GameActions;
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
    private static ListView gameListView;
    private static ListView gameListViewDelete;//GEFP-25-SA
    private static AnchorPane anchorPaneAction;
    private static Stage stage;
    private static double middleOfStage = (HelloApplication.width/2) -110;//GEFP-25-SA, ändra storlek så den följer efter Stage storleken

    //GEFP-22-SA
    public static Scene startSceneGame(Stage window){//Denna metod är den som kallas när man klickar på Login button i HelloApplication
        Scene baseScene = AbstractScene.getScene(window);
        stage = window;


        AnchorPane anchorPane = AbstractScene.anchorPane;
        VBox vBox = AbstractScene.leftVbox;

        AbstractScene.back.setOnAction(e->{
            ChangeSceneAction.toStartPage(window);
        });

        addCustomComponents(vBox);


        return baseScene;
    }


    //GEFP-20-SA
    protected static void addCustomComponents(AnchorPane anchorPane){


        //GEFP-22-SA
        VBox listView = new VBox();//GEFP-25-SA
        listView.setSpacing(10);
        listView.setAlignment(Pos.CENTER);

        Label allGames = new Label();
        allGames.setText("All games");
        allGames.getStyleClass().add("titel");

        gameListView = new ListView();
        gameListView = GameActions.gameListView(gameListView);
        gameListView.getStyleClass().add("list-cell");

        listView.getChildren().addAll(allGames, gameListView);

        anchorPaneAction = new AnchorPane();

        anchorPaneAction.getChildren().addAll(listView);

        anchorPaneAction.setLayoutX(middleOfStage);
        anchorPaneAction.setLayoutY(150);

        anchorPane.getChildren().addAll(anchorPaneAction);
    }

    //GEFP-20-SA
    protected static void addCustomComponents(VBox vBox){

        //GEFP-22-SA

        addCustomComponents(anchorPane);
        //--------------------------------------------------------------------

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

            submitAdd.setOnAction(ev->{
                GameActions.addGame(newGameInput);
                ChangeSceneAction.toGameView(stage);
            });
        });

//------------------------------------------------------------------

        Button deleteGame = new Button("Delete game");
        deleteGame.getStyleClass().add("standardButton");
        deleteGame.setMinSize(160, 30);

        VBox vBoxDelete = new VBox();
        vBoxDelete.setSpacing(10);
        vBoxDelete.setAlignment(Pos.CENTER);

        Label deleteGameLabel = new Label("Delete game");
        deleteGameLabel.getStyleClass().add("titel");

        gameListViewDelete = new ListView();//GEFP-25-SA
        gameListViewDelete = GameActions.gameListView(gameListViewDelete);
        gameListViewDelete.getStyleClass().add("list-cell");

        Label buttonLabel = new Label();
        buttonLabel.setText("Hold ctrl or shift to \nselect more than one game");
        buttonLabel.getStyleClass().add("standardLabelNoBorder");

        Button deleteGameButton = new Button();//GEFP-25-SA
        deleteGameButton.setText("Delete");
        deleteGameButton.getStyleClass().add("standardButton");
        deleteGameButton.setMinSize(160, 30);

        vBoxDelete.getChildren().addAll(deleteGameLabel,gameListViewDelete,buttonLabel,deleteGameButton);

        deleteGame.setOnAction(e->{
            clearAnchorpane(vBoxDelete);

            deleteGameButton.setOnAction(ev->{
                GameActions.deleteGame(gameListViewDelete);
                ChangeSceneAction.toGameView(stage);
            });
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
        choiceBox.getItems().addAll(GameActions.getGamesString());
        choiceBox.setValue(choiceBox.getItems().get(0));
        choiceBox.setMinSize(190, 30);
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
                GameActions.updateGame(updateGameInput,choiceBox);
                GameActions.gameListView(gameListView);
                ChangeSceneAction.toGameView(stage);
            });

        });

        //--------------------------------------------------------------

        Button showGames = new Button("Show games");
        showGames.getStyleClass().add("standardButton");
        showGames.setMinSize(160, 30);
        showGames.setOnAction(e->{
            ChangeSceneAction.toGameView(stage);
        });

        vBox.getChildren().addAll(showGames,addGame,updateGame,deleteGame);
    }

    public static void clearAnchorpane (VBox vBox){
        anchorPaneAction.getChildren().clear();
        anchorPaneAction.getChildren().addAll(vBox);
    }
}
