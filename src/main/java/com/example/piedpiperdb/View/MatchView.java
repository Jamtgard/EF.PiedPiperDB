package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.JavaFXActions.ChangeSceneAction;
import com.example.piedpiperdb.DAO.MatchDAO;
import com.example.piedpiperdb.Entities.Match;
import com.example.piedpiperdb.Entities.MatchType;
import jakarta.persistence.Table;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;



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
        Button addMatchPvPButton = createAddMatchPvP();
        Button addMatchTvTButton = createAddMatchTvT();
        Button uppdateMatchButton = createUppdateMatch();
        Button deleteMatch = createDeleteMatch();

        vBox.getChildren().addAll(getAllMatchesButton, addMatchPvPButton, addMatchTvTButton,uppdateMatchButton , deleteMatch);
    }

    private static Button createGetAllMatches(){
        Button getAllMatches = new Button("Show Matches");
        getAllMatches.getStyleClass().add("standardButton");
        getAllMatches.setMinSize(160,30);
        getAllMatches.setOnAction(actionEvent -> updateMatchList());
        return getAllMatches;
    }

    private static Button createAddMatchPvP(){
        Button addMatch = new Button("Add Match (PvP");
        addMatch.getStyleClass().add("standardButton");
        addMatch.setMinSize(160,30);
        addMatch.setOnAction(actionEvent -> {
            Match newMatch = new Match(MatchType.PLAYER_VS_PLAYER);
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
    }
    private static Button createAddMatchTvT(){
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
        formContainer = new createResultBox();
        formContainer.getStyleClass().add("textFieldOne");
        TableView<Match> table = createMatchTable(matches);
        formContainer.getChildren().addAll(table);

        anchorPane.getChildren().add(formContainer);
    }

    public static TableView<Match> createMatchTable(List<Match> matches){
        ObservableList<Match> observableList = FXCollections.observableList(matches);
        TableView<Match> table = new TableView<>();

        TableColumn<Match, Integer> matchIdColumn = new TableColumn<>("Match ID");
        matchIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Match, String> matchNameColumn = new TableColumn<>("Match Name");
        matchNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Match, String> matchDateColumn = new TableColumn<>("Match Date");
        matchDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Match, String> matchTypeColumn = new TableColumn<>("Match Type");
        matchTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));


        table.getColumns().addAll(matchIdColumn, matchNameColumn, matchDateColumn, matchTypeColumn);
        table.setItems(observableList);

        return table;
    }



}
