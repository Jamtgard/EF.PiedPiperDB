package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.DAO.JavaFXActions.ChangeSceneAction;
import com.example.piedpiperdb.DAO.MatchDAO;
import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Match;
import com.example.piedpiperdb.Entities.MatchType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

import static com.example.piedpiperdb.DAO.JavaFXActions.MatchActions.addMatch;


public class MatchView extends AbstractScene {

    private static ListView matchListView;
    private static MatchDAO matchDAO = new MatchDAO();
    private static Stage stage;
    private static VBox formContainer;

    public static Scene startSceneMatch(Stage window){
        Scene baseScene = AbstractScene.getScene(window);
        stage = window;

        AnchorPane anchorPane = AbstractScene.anchorPane;
        VBox vBox = AbstractScene.leftVbox;

        addCustomComponents(vBox);

        AbstractScene.back.setOnAction(actionEvent -> {
            ChangeSceneAction.toStartPage(window);
        });


        return baseScene;
    }


    private static void addCustomComponents(VBox vBox){
        matchListView = new ListView<>();
        matchListView.setPrefHeight(300);

        updateMatchList();

        Button getAllMatchesButton = createGetAllMatches();
        Button addMatchPvPButton = createAddMatch();

        Button uppdateMatchButton = createUppdateMatch();
        Button deleteMatch = createDeleteMatch();

        vBox.getChildren().addAll(getAllMatchesButton, addMatchPvPButton, uppdateMatchButton , deleteMatch);
    }

    private static Button createGetAllMatches(){
        Button getAllMatches = new Button("Show Matches");
        getAllMatches.getStyleClass().add("standardButton");
        getAllMatches.setMinSize(160,30);
        getAllMatches.setOnAction(actionEvent -> {
            List<Match> matches = matchDAO.getAllMatches();
            showMatchTable(AbstractScene.anchorPane, matches);
            updateMatchList();
                });
        return getAllMatches;
    }

    private static Button createAddMatch(){
        Button addMatch = new Button("Add Match");
        addMatch.getStyleClass().add("standardButton");
        addMatch.setMinSize(160,30);

        addMatch.setOnAction(actionEvent -> {
            // vbox för formuläret
            VBox addMatchBoxPvp = new VBox();
            addMatchBoxPvp.setSpacing(10);
            addMatchBoxPvp.setPadding(new Insets(10,10,10,10));
            addMatchBoxPvp.setAlignment(Pos.CENTER);

            Label addMatchLabel = new Label("Add Match");
            addMatchLabel.getStyleClass().add("titel");

            // för att välja namn
            TextField matchNameInput = new TextField();
            matchNameInput.setPromptText("Match Name");
            matchNameInput.getStyleClass().add("textFieldOne");
            matchNameInput.setMinSize(150,30);

            // för att välja spel
            ComboBox<Game> gameComboBox = new ComboBox<>();
            gameComboBox.setPromptText("Select Game");

            GameDAO gameDAO = new GameDAO();
            List<Game> games = gameDAO.getAllGames();
            gameComboBox.getItems().addAll(games);
            // anpassad cellfabrik för att visa spelens namn
            gameComboBox.setCellFactory(param -> new ListCell<>(){
                @Override
                protected void updateItem(Game game, boolean empty) {
                    super.updateItem(game, empty);
                    setText(empty || game == null ? null : game.getGameName());
                }
            });

            gameComboBox.setButtonCell(new ListCell<>(){
                @Override
                protected void updateItem(Game game, boolean empty){
                    super.updateItem(game, empty);
                    setText(empty || game == null ? null : game.getGameName());
                }
            });


            // för at välja matchTYp
            ComboBox<MatchType> matchTypeComboBox = new ComboBox<>();
            matchTypeComboBox.getItems().addAll(MatchType.values());
            matchTypeComboBox.setPromptText("Select Match Type");

            // för att välja datum
            DatePicker matchDatePicker = new DatePicker(LocalDate.now());
            matchDatePicker.setPromptText("Match Date");
            matchDatePicker.getStyleClass().add("textFieldOne");

            // submit knapp
            Button submitButton = new Button("Submit");
            submitButton.getStyleClass().add("standardButton");
            submitButton.setMinSize(160,30);

            addMatchBoxPvp.getChildren().addAll(addMatchLabel, matchNameInput, gameComboBox, matchDatePicker, matchTypeComboBox, submitButton);

            Scene addMatchScene = new Scene(addMatchBoxPvp, 400, 300);
            Stage addMatchStage = new Stage();
            addMatchStage.setTitle("Add Match");
            addMatchStage.setScene(addMatchScene);
            addMatchStage.show();

            submitButton.setOnAction(event -> {
                String matchName = matchNameInput.getText();
                Game selectedGame = gameComboBox.getValue();
                MatchType selectedMatchType = matchTypeComboBox.getValue();
                LocalDate matchDate = matchDatePicker.getValue();


                if(matchName == null || matchName.trim().isEmpty()){
                    System.out.println("Match name cannot be empty");
                    return;
                }
                if(selectedGame == null){
                    System.out.println("Please select a game");
                    return;
                }

                if(selectedMatchType == null){
                    System.out.println("Please select a match type");
                    return;
                }

                if(matchDate == null){
                    System.out.println("Please select a match date");
                    return;
                }

                addMatch(matchName, selectedMatchType, matchDate, selectedGame);
                System.out.println("Match added successfully");
                addMatchStage.close();
                updateMatchList();
            });
        });
        return addMatch;
    }


/*    private static Button createAddMatchTvT(){
        Button addMatch = new Button("Add Match (TvT)");
        addMatch.getStyleClass().add("standardButton");
        addMatch.setMinSize(160,30);
        addMatch.setOnAction(actionEvent -> {
            Match newMatch = new Match(MatchType.TEAM_VS_TEAM);
            newMatch.setMatchName("New Match");
            newMatch.setMatchDate(LocalDate.now());
            boolean success = matchDAO.saveMatch(newMatch);
            if(success){
                System.out.println("Match added successfully");
                updateMatchList();
            }else {
                System.out.println("Failed to save match");
            }
        });
        return addMatch;
    }*/

    private static Button createDeleteMatch(){
        Button deleteMatch = new Button("Delete Selected Match");
        deleteMatch.getStyleClass().add("standardButton");
        deleteMatch.setMinSize(160,30);
        deleteMatch.setOnAction(actionEvent -> {
            Match selectedMatch = (Match) matchListView.getSelectionModel().getSelectedItem();
            if(selectedMatch != null){
                boolean success = matchDAO.deleteMatchById(selectedMatch.getMatchId());
                if(success){
                    System.out.println("Match deleted successfully");
                    updateMatchList();
                } else {
                    System.out.println("Failed to delete match");
                }
            } else {
                System.out.println("No match selected.");
            }
        });
        return deleteMatch;
    }

    private static Button createUppdateMatch(){
        Button updateMatch = new Button("Update Match");
        updateMatch.getStyleClass().add("standardButton");
        updateMatch.setMinSize(160,30);
        updateMatch.setOnAction(actionEvent -> {
        });
        return updateMatch;
    }

    private static void updateMatchList(){
        List<Match> matches = matchDAO.getAllMatches();
        matchListView.getItems().clear();
        matchListView.getItems().addAll(matches);
    }

    public static void showMatchTable(AnchorPane anchorPane, List<Match> matches){
        formContainer = createResultBox();
        formContainer.getStyleClass().add("textFieldOne");
        TableView<Match> table = createMatchTable(matches);
        formContainer.getChildren().addAll(table);

        anchorPane.getChildren().add(formContainer);
    }

    public static TableView<Match> createMatchTable(List<Match> matches){
        ObservableList<Match> observableList = FXCollections.observableList(matches);
        TableView<Match> table = new TableView<>();

        TableColumn<Match, Integer> matchIdColumn = new TableColumn<>("Match ID");
        matchIdColumn.setCellValueFactory(new PropertyValueFactory<>("matchId"));

        TableColumn<Match, String> matchNameColumn = new TableColumn<>("Match Name");
        matchNameColumn.setCellValueFactory(new PropertyValueFactory<>("MatchName"));

        TableColumn<Match, String> matchTypeColumn = new TableColumn<>("Match Type");
        matchTypeColumn.setCellValueFactory(new PropertyValueFactory<>("MatchType"));

        TableColumn<Match, Game> gameColumn = new TableColumn<>("Game");
        gameColumn.setCellValueFactory(new PropertyValueFactory<>("GameName"));

        TableColumn<Match, String> matchDateColumn = new TableColumn<>("Match Date");
        matchDateColumn.setCellValueFactory(new PropertyValueFactory<>("MatchDate"));

        TableColumn<Match, String> matchResultColumn = new TableColumn<>("Result");
        matchResultColumn.setCellValueFactory(new PropertyValueFactory<>("MatchResult"));


        table.getColumns().addAll(matchIdColumn, matchNameColumn, matchTypeColumn, gameColumn, matchDateColumn, matchResultColumn);
        table.setItems(observableList);

        return table;
    }

    public static VBox createResultBox(){
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));

        vBox.setSpacing(10);
        vBox.getStyleClass().add("backgroundTeaGreen");
        AnchorPane.setTopAnchor(vBox,150.0);
        AnchorPane.setLeftAnchor(vBox,220.0);
        AnchorPane.setRightAnchor(vBox,30.0);
        AnchorPane.setBottomAnchor(vBox,30.0);
        return vBox;
    }



}
