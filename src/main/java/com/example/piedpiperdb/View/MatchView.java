package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.DAO.JavaFXActions.ChangeSceneAction;
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

                   /* TeamDAO teamDAO = new TeamDAO();
                    List<Team> teams = teamDAO.getAllTeams();
                    for(Team team : teams){
                        firstParticipantComboBox.getItems().add(team.getTeamName());
                        secondParticipantComboBox.getItems().add(team.getTeamName());
                    }*/
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

            //skapar scene och visar formulär
            Scene addMatchScene = new Scene(addMatchBox, 400, 300);
            Stage addMatchStage = new Stage();
            addMatchStage.setTitle("Add Match");
            addMatchStage.setScene(addMatchScene);
            addMatchStage.show();

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
                addMatchStage.close();
                updateMatchList();
            });
        });
        return addMatch;
    }

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
