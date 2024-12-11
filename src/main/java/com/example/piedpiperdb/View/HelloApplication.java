package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.*;
import com.example.piedpiperdb.DAO.JavaFXActions.ChangeSceneAction;
import com.example.piedpiperdb.DAO.JavaFXActions.GameActions;
import com.example.piedpiperdb.Entities.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    //GEFP-5-SA
    public static Stage window;
    public static Scene startScene;

    private Button loginButton;

    final static int height = 800;//600
    final static int width = 1500;//700

    private final Label programTitel = new Label("Piper Games");


    @Override
    public void start(Stage stage) throws IOException {

        //GEFP-18-SA testar
        PlayerDAO playerDAO = new PlayerDAO();
        MatchDAO matchDAO = new MatchDAO();
        GameDAO gameDAO = new GameDAO();
        TeamDAO teamDAO = new TeamDAO();

        Player player = new Player();
        Match match = new Match();
        Game game = new Game();
        Team team = new Team();



        /*
        System.out.println("Antal players är: "+playerDAO.getAllPlayers().size());
        System.out.println("Antal matcher är: "+matchDAO.getAllMatches().size());
        System.out.println("Antal spel är: "+gameDAO.getAllGames().size());
        System.out.println("Antal lag är: "+teamDAO.getAllTeams().size());*/



        // GEFP-9-AWS små testat
/*
        MatchDAO dao = new MatchDAO();
        Match match = new Match(MatchType.PLAYER_VS_PLAYER);
        dao.saveMatch(match);*/

        //GEFP-5-SA
        window = stage;
        window.setResizable(false);
        StackPane stackPane = new StackPane();
        stackPane.getStyleClass().add("backgroundTeaGreen");

        window.setTitle("Start page");


        Rectangle greenBackground = new Rectangle(width, height);
        greenBackground.setHeight(height-100);//500, GEFP-25-SA, ändra storlek så den följer efter Stage storleken
        greenBackground.setWidth(width-100);//600, GEFP-25-SA, ändra storlek så den följer efter Stage storleken
        greenBackground.setFill(Paint.valueOf("#FFFFFF"));
        greenBackground.setOpacity(0.5);
        greenBackground.setY(height/2);
        greenBackground.setX(width/2);

        programTitel.getStyleClass().add("titel");
        loginButton = new Button("Login");
        loginButton.getStyleClass().add("standardButton");

        //GEFP-4-SA
        loginButton.setOnAction(e -> {
            ChangeSceneAction.toStartPage(window);//GEFP-22-SA
        });

        Label userName = new Label("Username");
        userName.getStyleClass().add("standardLabel");

        TextField userNameField = new TextField();
        userNameField.getStyleClass().add("textFieldOne");
        userNameField.setPromptText("Username");

        Label password = new Label("Password");
        password.getStyleClass().add("standardLabel");

        TextField passwordField = new TextField();
        passwordField.getStyleClass().add("textFieldOne");
        passwordField.setPromptText("Password");

        HBox hBox1 = new HBox();
        hBox1.setSpacing(10);
        hBox1.setPadding(new Insets(10, 10, 10, 10));
        hBox1.setAlignment(Pos.CENTER);

        hBox1.getChildren().addAll(userName, userNameField);

        HBox hBox2 = new HBox();
        hBox2.setSpacing(10);
        hBox2.setPadding(new Insets(10, 10, 10, 10));
        hBox2.setAlignment(Pos.CENTER);

        hBox2.getChildren().addAll(password, passwordField);

        //GEFP-5-SA
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(programTitel,hBox1,hBox2, loginButton);

        stackPane.getChildren().addAll(greenBackground,vBox);

        startScene = new Scene(stackPane, width, height);
        startScene.getStylesheets().add("EscortFlasher.css");
        stage.setScene(startScene);

        //GEFP-16-AA
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        //SA
        window.show();
    }

    //GEFP-16-AA
    private void closeProgram() {
        boolean answer = ConfrimBox.display("Exit", "Are you sure you want to exit?");

        if (answer) {
            System.out.println("Exit program");
            Platform.exit();
        } else {
            System.out.println("Program continues");
        }

    }



    public static void main(String[] args) {
        launch();
    }

    public static Scene getStartScene() {return startScene;}

}