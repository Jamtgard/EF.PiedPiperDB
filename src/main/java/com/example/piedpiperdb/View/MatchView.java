package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.DAO.JavaFXActions.ChangeSceneAction;
import com.example.piedpiperdb.DAO.JavaFXActions.MatchActions;
import com.example.piedpiperdb.DAO.MatchDAO;
import com.example.piedpiperdb.DAO.PlayerDAO;
import com.example.piedpiperdb.DAO.TeamDAO;
import com.example.piedpiperdb.Entities.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

import static com.example.piedpiperdb.DAO.JavaFXActions.MatchActions.addMatch;


//GEFP-32-AWS

public class MatchView extends AbstractScene {

    private static ListView matchListView;
    private static MatchDAO matchDAO = new MatchDAO();
    private static GameDAO gameDAO = new GameDAO();
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
        Button addMatchButton = createAddMatch();

        Button uppdateMatchButton = createUppdateMatch();
        Button deleteMatch = createDeleteMatch();

        vBox.getChildren().addAll(getAllMatchesButton, addMatchButton, uppdateMatchButton , deleteMatch);
    }

    private static Button createGetAllMatches(){
        Button getAllMatches = new Button("Show Matches");
        getAllMatches.getStyleClass().add("standardButton");
        getAllMatches.setMinSize(160,30);

        getAllMatches.setOnAction(actionEvent -> {
            List<Match> matches = MatchActions.getAllMatches();

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

            // hämtar info om lag o spelare ifrån databas
            TeamDAO teamDAO = new TeamDAO();
            List<Team> allTeams = teamDAO.getAllTeams();
            PlayerDAO playerDAO = new PlayerDAO();
            List<Player> allPlayers = playerDAO.getAllPlayers();

            // vbox för formuläret
            VBox addMatchBox = new VBox();
            addMatchBox.setSpacing(10);
            addMatchBox.setPadding(new Insets(10,10,10,10));
            addMatchBox.setAlignment(Pos.CENTER);

            Label addMatchLabel = new Label("Add Match");
            addMatchLabel.getStyleClass().add("titel");

            // för at välja matchTYp
            ComboBox<MatchType> matchTypeComboBox = new ComboBox<>();
            matchTypeComboBox.getItems().addAll(MatchType.values());
            matchTypeComboBox.setPromptText("Select Match Type");

            // för att välja spel
            ComboBox<Game> gameComboBox = new ComboBox<>();
            gameComboBox.setPromptText("Select Game");

            // hämtar ifrån data basen
            GameDAO gameDAO = new GameDAO();
            List<Game> games = gameDAO.getAllGames();
            gameComboBox.getItems().addAll(games);

            // anpassar hur spelen visas i listan
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

            // för att välja datum
            DatePicker matchDatePicker = new DatePicker(LocalDate.now());
            matchDatePicker.setPromptText("Match Date");
            matchDatePicker.getStyleClass().add("textFieldOne");

            // dynamiskt val av lag eller spelare
            Label participantLabel = new Label();
            participantLabel.setVisible(false);

            ComboBox<String> firstParticipantComboBox = new ComboBox<>();
            firstParticipantComboBox.setPromptText("First Participant");
            firstParticipantComboBox.setVisible(false);

            ComboBox<String> secondParticipantComboBox = new ComboBox<>();
            secondParticipantComboBox.setPromptText("Second Participant");
            secondParticipantComboBox.setVisible(false);

            // dynamisk logik beroende på pvp elle tvt
            matchTypeComboBox.setOnAction(event->{
                MatchType selectedMatchType = matchTypeComboBox.getValue();
                // döljer knapparna
                firstParticipantComboBox.getItems().clear();
                secondParticipantComboBox.getItems().clear();

                firstParticipantComboBox.setVisible(false);
                secondParticipantComboBox.setVisible(false);
                participantLabel.setVisible(false);

                if(selectedMatchType == MatchType.TEAM_VS_TEAM){
                    participantLabel.setText("Select Teams");

                    firstParticipantComboBox.getItems().addAll(
                            allTeams.stream().map(Team::getTeamName).toList()
                    );
                    secondParticipantComboBox.getItems().addAll(
                            allTeams.stream().map(Team::getTeamName).toList()
                    );
                    // visar knapparna
                    firstParticipantComboBox.setVisible(true);
                    secondParticipantComboBox.setVisible(true);
                    participantLabel.setVisible(true);

                    firstParticipantComboBox.setValue("Team 1");
                    secondParticipantComboBox.setValue("Team 2");

                }else if (selectedMatchType == MatchType.PLAYER_VS_PLAYER) {
                    participantLabel.setText("Select Players");
                    firstParticipantComboBox.getItems().addAll(
                            allPlayers.stream().map(Player::getNickname).toList()
                    );
                    secondParticipantComboBox.getItems().addAll(
                            allPlayers.stream().map(Player::getNickname).toList()
                    );

                    // visar knapparna
                    firstParticipantComboBox.setVisible(true);
                    secondParticipantComboBox.setVisible(true);
                    participantLabel.setVisible(true);

                    firstParticipantComboBox.setValue("Player 1");
                    secondParticipantComboBox.setValue("Player 2");
                }
                    // döljer så man inte kan välja 2 av samma spelare/lag
                    firstParticipantComboBox.setCellFactory(param -> new ListCell<>(){
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if(empty || item == null){
                                setText(null);
                                setDisable(false);
                            }else{
                                if(item.equals(secondParticipantComboBox.getValue())){
                                    setText("----------");
                                    setDisable(true);
                                } else{
                                    setText(item);
                                    setDisable(false);
                                }
                            }
                        }
                    });
                    // döljer så man inte kan välja 2 av samma spelare/lag
                    secondParticipantComboBox.setCellFactory(param -> new ListCell<>(){
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if(empty || item == null){
                                setText(null);
                                setDisable(false);
                            } else {
                                if(item.equals(firstParticipantComboBox.getValue())){
                                    setText("----------");
                                    setDisable(true);
                                } else {
                                    setText(item);
                                    setDisable(false);
                                }
                            }
                        }
                    });
                    firstParticipantComboBox.setOnAction(event1 -> secondParticipantComboBox.setButtonCell(secondParticipantComboBox.getButtonCell()));
                    secondParticipantComboBox.setOnAction(event2 -> firstParticipantComboBox.setButtonCell(firstParticipantComboBox.getButtonCell()));
            });

            // submit knapp
            Button submitButton = new Button("Submit");
            submitButton.getStyleClass().add("standardButton");
            submitButton.setMinSize(160,30);

            addMatchBox.getChildren().addAll(addMatchLabel,matchTypeComboBox,  gameComboBox, matchDatePicker,firstParticipantComboBox,secondParticipantComboBox, submitButton);

            VBox resultBox = createResultBox();
            resultBox.getChildren().add(addMatchBox);
            AbstractScene.anchorPane.getChildren().add(resultBox);

            /*//skapar scene och visar formulär
            Scene addMatchScene = new Scene(addMatchBox, 400, 300);
            Stage addMatchStage = new Stage();
            addMatchStage.setTitle("Add Match");
            addMatchStage.setScene(addMatchScene);
            addMatchStage.show();*/

            // lägger till matchen
            submitButton.setOnAction(event -> {
                Game selectedGame = gameComboBox.getValue();
                MatchType selectedMatchType = matchTypeComboBox.getValue();
                LocalDate matchDate = matchDatePicker.getValue();
                String firstParticipant = firstParticipantComboBox.getValue();
                String secondParticipant = secondParticipantComboBox.getValue();

                String matchName = firstParticipant + " vs " + secondParticipant;

                if(selectedMatchType == null){
                    System.out.println("Select Match Type");
                    return;
                }

                if (firstParticipant == null || secondParticipant == null){
                    System.out.println("Select both participants");
                    return;
                }

                if(selectedGame == null){
                    System.out.println("Please select a game");
                    return;
                }

                if(matchDate == null){
                    System.out.println("Please select a match date");
                    return;
                }

                addMatch(matchName, selectedMatchType, matchDate, selectedGame);
                System.out.println("Match added successfully");
                /*addMatchStage.close();*/
                resultBox.getChildren().clear();
                showMatchTable(AbstractScene.anchorPane, MatchActions.getAllMatches());
                updateMatchList();

            });
        });
        return addMatch;
    }

    private static Button createDeleteMatch(){
        Button deleteMatch = new Button("Delete Match");
        deleteMatch.getStyleClass().add("standardButton");
        deleteMatch.setMinSize(160,30);
        deleteMatch.setOnAction(actionEvent -> {
            VBox deleteMatchBox = new VBox();
            deleteMatchBox.setSpacing(10);
            deleteMatchBox.setPadding(new Insets(10,10,10,10));
            deleteMatchBox.setAlignment(Pos.CENTER);

            Label deleteMatchLabel = new Label("Delete Match");
            deleteMatchLabel.getStyleClass().add("titel");

            ComboBox<Match>matchComboBox = new ComboBox<>();
            matchComboBox.setPromptText("Select Match to Delete");
            matchComboBox.getItems().addAll(MatchActions.getAllMatches());

            matchComboBox.setCellFactory(param -> new ListCell<>(){
                @Override
                protected void updateItem(Match match, boolean empty) {
                    super.updateItem(match, empty);
                    setText(empty || match == null ? null : match.getMatchName());
                }
            });

            matchComboBox.setButtonCell(new ListCell<>(){
                @Override
                protected void updateItem(Match match, boolean empty) {
                    super.updateItem(match, empty);
                    setText(empty || match == null ? null : match.getMatchName());
                }
            });

            Button confirmDeletButton = new Button("Delete Match");
            confirmDeletButton.getStyleClass().add("standardButton");
            confirmDeletButton.setMinSize(160,30);

            deleteMatchBox.getChildren().addAll(deleteMatchLabel, matchComboBox, confirmDeletButton);

            VBox resultBox = createResultBox();
            resultBox.getChildren().add(deleteMatchBox);
            AbstractScene.anchorPane.getChildren().add(resultBox);

            confirmDeletButton.setOnAction(event -> {
               Match selectedMatch = matchComboBox.getValue();

               if(selectedMatch == null){
                   System.out.println("Select Match to delete.");
                   return;
               }
               boolean success = MatchActions.deleteMatchById(selectedMatch.getMatchId());
               if (success) {
                   System.out.println("Match deleted successfully from database.");
               } else {
                   System.out.println("Match could not be deleted from database.");
               }
               MatchActions.deleteMatchById(selectedMatch.getMatchId());

               updateMatchList();
               resultBox.getChildren().clear();
               showMatchTable(AbstractScene.anchorPane, MatchActions.getAllMatches());

            });

        });
        return deleteMatch;
    }

    private static Button createUppdateMatch(){
        Button updateMatch = new Button("Update Match");
        updateMatch.getStyleClass().add("standardButton");
        updateMatch.setMinSize(160,30);

        updateMatch.setOnAction(actionEvent -> {
            // uppdaterings formulär

            VBox selectMatchBox = new VBox();
            selectMatchBox.setSpacing(10);
            selectMatchBox.setPadding(new Insets(10,10,10,10));
            selectMatchBox.setAlignment(Pos.CENTER);


            Label selectMatchLabel = new Label("Update Match");
            selectMatchLabel.getStyleClass().add("titel");

            ComboBox<Match> matchComboBox = new ComboBox<>();
            matchComboBox.setPromptText("Select Match");
            matchComboBox.getItems().addAll(MatchActions.getAllMatches());

            matchComboBox.setCellFactory(param -> new ListCell<>(){
                @Override
                protected void updateItem(Match match, boolean empty) {
                    super.updateItem(match, empty);
                    setText(empty || match == null ? null : match.getMatchName());
                }
            });

            matchComboBox.setButtonCell(new ListCell<>(){
                @Override
                protected void updateItem(Match match, boolean empty) {
                    super.updateItem(match, empty);
                    setText(empty || match == null ? null : match.getMatchName());
                }
            });

            // för att gå vidare till updateforumlär
            Button proceedButton = new Button("Next");
            proceedButton.getStyleClass().add("standardButton");
            proceedButton.setMinSize(160,30);

            VBox resultBox = createResultBox();
            selectMatchBox.getChildren().addAll(selectMatchLabel,matchComboBox,proceedButton);
            resultBox.getChildren().addAll(selectMatchBox);
            AbstractScene.anchorPane.getChildren().add(resultBox);

            proceedButton.setOnAction(event -> {
                Match selectedMatch = matchComboBox.getValue();

                if(selectedMatch == null){
                    System.out.println("No match selected.");
                    return;
                }
                // updateformulär
                VBox updateForm = new VBox();
                updateForm.setSpacing(10);
                updateForm.setPadding(new Insets(10,10,10,10));
                updateForm.setAlignment(Pos.CENTER);

                Label updateLabel = new Label("Update Match");
                updateLabel.getStyleClass().add("titel");

                TextField matchNameField = new TextField(selectedMatch.getMatchName());
                matchNameField.setPromptText("Match Name");
                matchNameField.setMaxWidth(400);
                matchNameField.setAlignment(Pos.CENTER);

                ComboBox<MatchType> matchTypeComboBox = new ComboBox<>();
                matchTypeComboBox.getItems().addAll(MatchType.values());
                matchTypeComboBox.setValue(selectedMatch.getMatchType());

                // hämtar spel ifrån databas
                ComboBox<Game> gameComboBox = new ComboBox<>();
                gameComboBox.setPromptText(selectedMatch.getGameName());
                gameComboBox.getItems().addAll(gameDAO.getAllGames());

                gameComboBox.setCellFactory(param -> new ListCell<>(){
                    @Override
                    protected void updateItem(Game game, boolean empty) {
                        super.updateItem(game, empty);
                        setText(empty || game == null ? null : game.getGameName());
                    }
                });

                gameComboBox.setButtonCell(new ListCell<>(){
                    @Override
                    protected void updateItem(Game game, boolean empty) {
                        super.updateItem(game, empty);
                        setText(empty || game == null ? null : game.getGameName());
                    }
                });

                DatePicker matchDatePicker = new DatePicker(selectedMatch.getMatchDate());

                TextField resultField = new TextField(selectedMatch.getMatchResult());
                resultField. setPromptText("Match Result");
                resultField.setMaxWidth(200);
                resultField.setAlignment(Pos.CENTER);

                Button updateButton = new Button("Update Match");
                updateButton.getStyleClass().add("standardButton");
                updateButton.setMinSize(160,30);

                updateForm.getChildren().addAll(updateLabel, matchNameField, matchTypeComboBox, gameComboBox, matchDatePicker, resultField, updateButton);

                resultBox.getChildren().clear();
                resultBox.getChildren().add(updateForm);

                //updaterar och validerar
                updateButton.setOnAction(updateEvent ->{
                    String updateName = matchNameField.getText();
                    MatchType updateMatchType = matchTypeComboBox.getValue();
                    Game updateGame = gameComboBox.getValue();
                    LocalDate updateMatchDate = matchDatePicker.getValue();
                    String updateResult = resultField.getText();

                    if(updateName == null || updateName.isEmpty()){
                        System.out.println("Match Name is required.");
                        return;
                    }
                    if(updateMatchType == null){
                        System.out.println("Match Type is required.");
                        return;
                    }
                    if(updateGame == null){
                        System.out.println("Game is required.");
                        return;
                    }
                    if (updateMatchDate == null){
                        System.out.println("Match Date is required.");
                    }

                    /*selectedMatch.setMatchName(updateName);
                    selectedMatch.setMatchType(updateMatchType);
                    selectedMatch.setGameId(updateGame);
                    selectedMatch.setMatchDate(updateMatchDate);
                    selectedMatch.setMatchResult(updateResult);*/

                    MatchActions.updateMatch(selectedMatch, updateName, updateMatchType, updateMatchDate, updateGame, updateResult);
                    updateMatchList();

                    resultBox.getChildren().clear();
                    showMatchTable(AbstractScene.anchorPane, MatchActions.getAllMatches());


                });
            });
        });
        return updateMatch;
    }

    private static void updateMatchList(){
         // hämtar uppdaterad lista med matcher
        List<Match> matches = matchDAO.getAllMatches();
        matchListView.getItems().clear();
        matchListView.getItems().addAll(matches);
    }

    public static void showMatchTable(AnchorPane anchorPane, List<Match> matches){
        // visar tabel
        formContainer = createResultBox();
        formContainer.getStyleClass().add("textFieldOne");
        TableView<Match> table = createMatchTable(matches);
        VBox.setVgrow(table, Priority.ALWAYS);
        formContainer.getChildren().addAll(table);

        anchorPane.getChildren().add(formContainer);
    }

    public static TableView<Match> createMatchTable(List<Match> matches){
        // Skapar tabell
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
