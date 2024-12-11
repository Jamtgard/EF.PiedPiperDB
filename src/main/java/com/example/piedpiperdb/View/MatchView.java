package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.MatchDAO;
import com.example.piedpiperdb.Entities.Match;
import com.example.piedpiperdb.Entities.MatchType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class MatchView extends AbstractScene {
    private static ListView matchListView;
    private static Button submit;
    private static MatchDAO matchDAO = new MatchDAO();

    public static Scene matchScene(Stage window){
        Scene baseScene = AbstractScene.getScene(window);
        AnchorPane anchorPane = AbstractScene.anchorPane;
        VBox vBox = AbstractScene.leftVbox;

        addCustomComponents(vBox);

        AbstractScene.back.setOnAction(actionEvent -> {
            window.setScene(baseScene);
        });

        anchorPane.getChildren().add(vBox);
        return new Scene(anchorPane);
    }


    private static void addCustomComponents(VBox vBox){
/*
    List<Match> matches = MatchDAO.getAllMatches();
        List<CheckBox> checkBoxes = new ArrayList<>();

        for (Match match : matches) {
            String name = match.getMatchName();
            int matchId = match.getId();
            Enum type = match.getMatchType();
            CheckBox checkBox = new CheckBox(name + " MatchId: " + matchId + " MatchType: " + type);
            checkBoxes.add(checkBox);
        }

        Button getAllMatches = new Button("Get All Matches");
        getAllMatches.getStyleClass().add("standardButton");
        getAllMatches.setMinSize(160,30);
        getAllMatches.setOnAction(event -> {
            List<Match> allMatches = matchDAO.getAllMatches();
            System.out.println(matches.size());

        });

*/
        matchListView = new ListView<>();
        matchListView.setPrefHeight(300);

        updateMatchList();

        Button getAllMatchesButton = createGetAllMatches();
        Button addMatchPvPButton = createAddMatchPvP();
        Button addMatchTvTButton = createAddMatchTvT();
        Button deleteMatch = createDeleteMatch();

        vBox.getChildren().addAll(getAllMatchesButton, addMatchPvPButton, addMatchTvTButton, deleteMatch);
    }

    private static Button createGetAllMatches(){
        Button getAllMatches = new Button("Get All Matches");
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

    private static Button createDeleteMatch(){
        Button deleteMatch = new Button("Delete Selected Match");
        deleteMatch.getStyleClass().add("standardButton");
        deleteMatch.setMinSize(160,30);
        deleteMatch.setOnAction(actionEvent -> {
            Match selectedMatch = (Match) matchListView.getSelectionModel().getSelectedItem();
            if(selectedMatch != null){
                boolean success = matchDAO.deleteMatchById(selectedMatch.getId());
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

    private static void updateMatchList(){
        List<Match> matches = matchDAO.getAllMatches();
        matchListView.getItems().clear();
        matchListView.getItems().addAll(matches);
    }

}
