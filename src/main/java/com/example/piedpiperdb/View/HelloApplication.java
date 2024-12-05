package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.MatchDAO;
import com.example.piedpiperdb.Entities.Match;
import com.example.piedpiperdb.Entities.MatchType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // GEFP-9-AWS små testat
/*        MatchDAO dao = new MatchDAO();
        Match match = new Match(MatchType.PLAYER_VS_PLAYER);
        dao.saveMatch(match);*/

       /* kommenterar ut denna för tillfället, bara jobbig popup när man testar XD
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();*/
    }

    public static void main(String[] args) {
        launch();
    }
}