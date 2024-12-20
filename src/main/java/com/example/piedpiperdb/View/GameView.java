package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.JavaFXActions.ChangeSceneAction;
import com.example.piedpiperdb.DAO.JavaFXActions.GameActions;
import com.example.piedpiperdb.Entities.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//GEFP-22-SA
public class GameView extends AbstractScene{

    //GEFP-22-SA
    private static ListView<String> gameListView;
    private static ListView<String> gameListViewDelete;//GEFP-25-SA
    private static AnchorPane anchorPaneAction;
    private static Stage stage;
    private static VBox vBoxAllGames;
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
        vBoxAllGames = new VBox();//GEFP-25-SA
        vBoxAllGames.setSpacing(10);
        vBoxAllGames.setAlignment(Pos.CENTER);

        Label allGames = new Label();
        allGames.setText("All games");
        allGames.getStyleClass().add("titel");

        gameListView = new ListView();
        GameActions.gameListView(gameListView);
        gameListView.getStyleClass().add("list-cell");


        Label buttonLabel = new Label();
        buttonLabel.setText("Hold ctrl or shift to \nselect more than one game");
        buttonLabel.getStyleClass().add("standardLabelNoBorder");


        //GEFP-26-SA
        Button showPlayers = new Button("Show players");
        showPlayers.getStyleClass().add("standardButton");
        showPlayers.setMinSize(160, 30);
        showPlayers.setOnAction(e->{
            GameActions.getPlayerForGame(gameListView);
        });

        Button showMatches = new Button("Show matches");
        showMatches.getStyleClass().add("standardButton");
        showMatches.setMinSize(160, 30);
        showMatches.setOnAction(e->{
            GameActions.getMatchesForGame(gameListView);
        });

        //SA
        vBoxAllGames.getChildren().addAll(allGames, gameListView,buttonLabel,showPlayers,showMatches);

        anchorPaneAction = new AnchorPane();

        anchorPaneAction.getChildren().addAll(vBoxAllGames);

        anchorPaneAction.setLayoutX(middleOfStage);
        anchorPaneAction.setLayoutY(150);

        anchorPane.getChildren().addAll(anchorPaneAction);
    }

    //GEFP-20-SA
    protected static void addCustomComponents(VBox vBox){

        //GEFP-22-SA

        addCustomComponents(anchorPane);

        //--------------------------------------------------------------

        //Show games
        //GEFP-22-SA
        Button showGames = new Button("Show games");
        showGames.getStyleClass().add("standardButton");
        showGames.setMinSize(160, 30);
        showGames.setOnAction(e->{
            GameActions.updateGameListView(gameListView);
            clearAnchorpane(vBoxAllGames);
        });

        //--------------------------------------------------------------------

        //Add game
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
                //GEFP-34-SA
                GameActions.updateInput(newGameInput);
                clearAnchorpane(addGameBox);
            });
        });


//------------------------------------------------------------------

        //Update game
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
            GameActions.updateChoiceBoxTextField(choiceBox);
            clearAnchorpane(updateGameBox);

            submitUpdate.setOnAction(ev->{
                GameActions.updateGame(updateGameInput,choiceBox);
                GameActions.gameListView(gameListView);
                //GEFP-34-SA
                GameActions.updateChoiceBoxTextField(choiceBox);
                GameActions.updateInput(updateGameInput);
                clearAnchorpane(updateGameBox);
            });

        });

        //------------------------------------------------------------------

        //Delete game
        Button deleteGame = new Button("Delete game");
        deleteGame.getStyleClass().add("standardButton");
        deleteGame.setMinSize(160, 30);

        VBox vBoxDelete = new VBox();
        vBoxDelete.setSpacing(10);
        vBoxDelete.setAlignment(Pos.CENTER);

        Label deleteGameLabel = new Label("Delete game");
        deleteGameLabel.getStyleClass().add("titel");

        gameListViewDelete = new ListView();//GEFP-25-SA
        GameActions.gameListView(gameListViewDelete);
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
            GameActions.updateGameListView(gameListViewDelete);
            clearAnchorpane(vBoxDelete);

            deleteGameButton.setOnAction(ev->{
                GameActions.deleteGame(gameListViewDelete);
                //GEFP-34-SA
                GameActions.updateGameListView(gameListViewDelete);
                clearAnchorpane(vBoxDelete);
            });
        });



        //----------------------------------------------------------------

        //Update/delete with id
        //GEFP-34-SA
        VBox showGamesBox = new VBox();
        showGamesBox.setSpacing(10);
        showGamesBox.setPadding(new Insets(10,0,0,0));
        showGamesBox.setAlignment(Pos.CENTER);

        TableView<Game> tableViewGame = new TableView<>();

        TableColumn<Game,String>gameId = new TableColumn<>("Id");
        gameId.setCellValueFactory(new PropertyValueFactory<>("gameId"));
        gameId.setMinWidth(60);

        TableColumn<Game,String>gameName = new TableColumn<>("Game Name");
        gameName.setCellValueFactory(new PropertyValueFactory<>("gameName"));
        gameName.setMinWidth(200);

        tableViewGame.getItems().addAll(GameActions.getGames());
        tableViewGame.getColumns().addAll(gameId,gameName);
        tableViewGame.getSortOrder().add(gameId);

        TextField gameIdDeleteInput = new TextField();
        gameIdDeleteInput.getStyleClass().add("textFieldOne");
        gameIdDeleteInput.setPromptText("Id");

        Button deleteById = new Button("Delete");
        deleteById.getStyleClass().add("standardButton");
        deleteById.setMinSize(70, 30);
        deleteById.setOnAction(e->{
            GameActions.deleteGameById(gameIdDeleteInput);
            GameActions.updateTableView(tableViewGame,gameId);
            GameActions.updateInput(gameIdDeleteInput);
            clearAnchorpane(showGamesBox);
        });

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10,10,10,10));
        hbox.getChildren().addAll(gameIdDeleteInput,deleteById);

        TextField gameIdUpdateInput = new TextField();
        gameIdUpdateInput.getStyleClass().add("textFieldOne");
        gameIdUpdateInput.setPromptText("Id");

        TextField newNameInput = new TextField();
        newNameInput.getStyleClass().add("textFieldOne");
        newNameInput.setPromptText("New name");

        Button updateById = new Button("Update");
        updateById.getStyleClass().add("standardButton");
        updateById.setMinSize(70, 30);
        updateById.setOnAction(e->{
            GameActions.updateGame(gameIdUpdateInput,newNameInput);
            GameActions.updateTableView(tableViewGame,gameId);
            GameActions.updateInput(newNameInput);
            GameActions.updateInput(gameIdUpdateInput);
            clearAnchorpane(showGamesBox);
        });

        VBox textFieldsVBox = new VBox();
        textFieldsVBox.setSpacing(10);

        textFieldsVBox.getChildren().addAll(gameIdUpdateInput,newNameInput);

        HBox updateGameHBox = new HBox();
        updateGameHBox.setSpacing(10);
        updateGameHBox.setPadding(new Insets(0,10,10,10));
        updateGameHBox.getChildren().addAll(textFieldsVBox,updateById);


        showGamesBox.getChildren().addAll(tableViewGame,hbox,updateGameHBox);

        Button updateDeleteId = new Button("Update/delete with id");
        updateDeleteId.getStyleClass().add("standardButton");
        updateDeleteId.setMinSize(160, 30);
        updateDeleteId.setOnAction(e->{
            GameActions.updateTableView(tableViewGame,gameId);
            clearAnchorpane(showGamesBox);
        });


        //--------------------------------------------------------------

        //Buttons vBox getChildren
        //GEFP-22-SA
        vBox.getChildren().addAll(showGames,addGame,updateGame,deleteGame,updateDeleteId);
    }

    //GEFP-26-SA
    public static void clearAnchorpane (VBox vBox){
        anchorPaneAction.getChildren().clear();
        anchorPaneAction.getChildren().addAll(vBox);
    }


}
