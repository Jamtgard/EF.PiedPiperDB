package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.MatchDAO;
import com.example.piedpiperdb.Entities.Match;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

        AbstractScene.back.setOnAction(event -> {

        });


        return baseScene;
    }


    private static void addCustomComponents(VBox vBox){
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


    }


}
