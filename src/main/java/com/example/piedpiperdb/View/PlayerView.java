package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.DAO.PlayerDAO;
import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.hibernate.internal.util.ExceptionHelper.getRootCause;

//GEFP-19-AA
public class PlayerView extends AbstractScene{

    private static PlayerDAO playerDAO = new PlayerDAO();
    private static GameDAO gameDAO = new GameDAO();

    public static Scene playerScene(Stage window){
        Scene baseScene = AbstractScene.getScene(window); // Skapar en sen från mallen i abstract Scene

        AnchorPane anchorPane = AbstractScene.anchorPane;
        VBox vBox = AbstractScene.leftVbox;


/*        HelloApplication helloApp = new HelloApplication();
        AbstractScene.back.setOnAction(e->{
            try {
                helloApp.start(window);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });*/

        //addCustomComponents(anchorPane);
        addCustomComponents(vBox);



        AnchorPane rootPane = (AnchorPane) baseScene.getRoot();

        return baseScene;
    }

    private static void addCustomComponents(VBox vBox){
            List<Game> games = gameDAO.getAllGames();
            List<CheckBox> checkBoxes = new ArrayList<>();


            for (Game game : games) {
                String name = game.getGame_name();
                int gameId = game.getGame_id();
                CheckBox checkBox  = new CheckBox(name + ", GameID: " + gameId );
                checkBoxes.add(checkBox);
            }

            Button getAllPlayers = new Button("Show all players");
            getAllPlayers.getStyleClass().add("standardButton");
            getAllPlayers.setMinSize(160, 30);
            getAllPlayers.setOnAction(event -> {
                List<Player> players = playerDAO.getAllPlayers();
                System.out.println(players.size());
                showTable(anchorPane, players );
            });


            Button selectedPlayers = new Button("Show players from \nselected game or games");
            selectedPlayers.getStyleClass().add("standardButton");
            selectedPlayers.setMinSize(160, 50);
            selectedPlayers.setOnAction(actionEvent -> {
                List<String> selections = ConfirmBox.displayCheckBoxOptions("Select game or games", checkBoxes);
                List<Integer> ids = new ArrayList<>();
                for (String selection : selections) {
                    try {
                        String[] parts = selection.split(" ");

                        String lastPart = parts[parts.length - 1];
                        int id = Integer.parseInt(lastPart);
                        ids.add(id);
                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Could not parse id: " + selection);
                    }
                    System.out.println("IDs to query: " + ids);
                }
                List<Player> players = playerDAO.getAllPlayersFromSelectedGame(ids);
                System.out.println(players.size());
                showTable(anchorPane, players );
            });


            Button addNewPlayerButton = new Button("Add new player");
            addNewPlayerButton.getStyleClass().add("standardButton");
            addNewPlayerButton.setMinSize(160, 30);
            addNewPlayerButton.setOnAction(e -> {
                showAddPlayerForm(anchorPane);
            });

            Button updatePlayerByIdButton = new Button("Update player by ID");
            updatePlayerByIdButton.getStyleClass().add("standardButton");
            updatePlayerByIdButton.setMinSize(160, 30);
            updatePlayerByIdButton.setOnAction(e -> {
                showUpdatePlayerForm(anchorPane);
            });

            Button deletePlayerByIdButton = new Button("Delete player by ID");
            deletePlayerByIdButton.getStyleClass().add("standardButton");
            deletePlayerByIdButton.setMinSize(160, 30);
 /*           deletePlayerByIdButton.setOnAction(e -> {

            });*/

            vBox.getChildren().addAll(getAllPlayers, selectedPlayers, addNewPlayerButton, updatePlayerByIdButton, deletePlayerByIdButton );


    }

    public static void showTable(AnchorPane anchorPane, List<Player> players){
        TableView<Player> table = createPlayerTable(players); //Skapa tableView

        AnchorPane.setTopAnchor(table, 150.0);
        AnchorPane.setLeftAnchor(table, 220.0);
        AnchorPane.setRightAnchor(table, 30.0);
        AnchorPane.setBottomAnchor(table, 30.0);

        anchorPane.getStyleClass().add("backgroundTeaGreen");
        anchorPane.getStyleClass().add("standardLabel");
        anchorPane.getStyleClass().add("columnV");

        anchorPane.getChildren().addAll(table);

    }

    private static TableView<Player> createPlayerTable(List<Player> players){
        ObservableList<Player> observableList = FXCollections.observableList(players);

        TableView<Player> table = new TableView<>();

        TableColumn<Player, Integer> player_id = new TableColumn<>("Player ID");
        player_id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Player, String> nickname = new TableColumn<>("Nickname");
        nickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));

        TableColumn<Player, String> fullName = new TableColumn<>("Name");
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<Player, String> address = new TableColumn<>("Address");
        address.setCellValueFactory(new PropertyValueFactory<>("fullAddress"));

        TableColumn<Player, String> country = new TableColumn<>("Country");
        country.setCellValueFactory(new PropertyValueFactory<>("country"));

        TableColumn<Player, String> email = new TableColumn<>("E-mail");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Player, String> game_name = new TableColumn<>("Game");
        game_name.setCellValueFactory(new PropertyValueFactory<>("gameName"));

        TableColumn<Player, String> team_name = new TableColumn<>("Team");
        team_name.setCellValueFactory(new PropertyValueFactory<>("teamName"));

        //TO DO

        table.getColumns().addAll(player_id, nickname, fullName, address, country, email, game_name, team_name);
        table.setItems(observableList);
        return table;
    }

    private static void showAddPlayerForm(AnchorPane anchorPane){
        VBox formContainer = new VBox();
        formContainer.setPadding(new Insets(20));
        formContainer.setSpacing(10);
        formContainer.getStyleClass().add("backgroundTeaGreen");
        AnchorPane.setTopAnchor(formContainer, 150.0);
        AnchorPane.setLeftAnchor(formContainer, 220.0);
        AnchorPane.setRightAnchor(formContainer, 30.0);
        AnchorPane.setBottomAnchor(formContainer, 30.0);


        HBox fistNameBox = new HBox(5);
        Label fistName = new Label(" First Name* ");
        fistName.getStyleClass().add("standardLabel");
        TextField fistNamefield = new TextField();
        fistNamefield.getStyleClass().add("textFieldOne");
        fistNamefield.setPromptText("First name");
        fistNameBox.getChildren().addAll(fistName, fistNamefield);

        HBox lastNameBox = new HBox(5);
        Label lastName = new Label(" Last Name* ");
        lastName.getStyleClass().add("standardLabel");
        TextField lastNameField = new TextField();
        lastNameField.getStyleClass().add("textFieldOne");
        lastNameField.setPromptText("Last name");
        lastNameBox.getChildren().addAll(lastName, lastNameField);

        HBox nicknameBox = new HBox(5);
        Label nickname = new Label(" Nickname* ");
        nickname.getStyleClass().add("standardLabel");
        TextField nicknameField = new TextField();
        nicknameField.getStyleClass().add("textFieldOne");
        nicknameField.setPromptText("Nickname");
        nicknameBox.getChildren().addAll(nickname, nicknameField);

        HBox addressBox = new HBox(5);
        Label streetAddress = new Label(" Street Address ");
        streetAddress.getStyleClass().add("standardLabel");
        TextField streetAddressField = new TextField();
        streetAddressField.getStyleClass().add("textFieldOne");
        streetAddressField.setPromptText("Street address");
        addressBox.getChildren().addAll(streetAddress, streetAddressField);

        HBox zipBox = new HBox(5);
        Label zipCode = new Label(" Zip Code ");
        zipCode.getStyleClass().add("standardLabel");
        TextField zipCodeField = new TextField();
        zipCodeField.getStyleClass().add("textFieldOne");
        zipCodeField.setPromptText("Zip code");
        zipBox.getChildren().addAll(zipCode, zipCodeField);

        HBox cityBox = new HBox(5);
        Label city = new Label(" City ");
        city.getStyleClass().add("standardLabel");
        TextField cityField = new TextField();
        cityField.getStyleClass().add("textFieldOne");
        cityField.setPromptText("City");
        cityBox.getChildren().addAll(city, cityField);


        HBox countryBox = new HBox(5);
        Label country = new Label(" Country ");
        country.getStyleClass().add("standardLabel");
        TextField countryField = new TextField();
        countryField.getStyleClass().add("textFieldOne");
        countryField.setPromptText("Country");
        countryBox.getChildren().addAll(country, countryField);

        HBox emailBox = new HBox(5);
        Label email = new Label(" E-mail ");
        email.getStyleClass().add("standardLabel");
        TextField emailField = new TextField();
        emailField.getStyleClass().add("textFieldOne");
        emailField.setPromptText("E-mail");
        emailBox.getChildren().addAll(email, emailField);

        formContainer.getChildren().addAll(fistNameBox, lastNameBox, nicknameBox, addressBox,zipBox,cityBox, countryBox, emailBox);

        Button saveButton = new Button("Save Player");
        saveButton.getStyleClass().add("standardButton");
        saveButton.setMinSize(160, 30);
        saveButton.setOnAction(event -> {
            Player player = null;
            if(fistNamefield.getText().isEmpty() || lastNameField.getText().isEmpty() || nicknameField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please fill in all mandatory fields. Fields marked with *.");
                alert.show();
            } else {
                //Hitta annan lösning!
                try {
                    player = new Player(fistNamefield.getText(), lastNameField.getText(), nicknameField.getText(), streetAddressField.getText(), zipCodeField.getText(), cityField.getText(), countryField.getText(), emailField.getText());
                    Label labelSaved = new Label(" Player saved to database! ");
                    labelSaved.getStyleClass().add("standardLabel");
                    formContainer.getChildren().add(labelSaved);

                } catch (Exception e) {
                        e.printStackTrace();

                }
            }

            PlayerDAO newPlayer = new PlayerDAO();
            newPlayer.savePlayer(player);
            System.out.println(player.toString());

        });


        formContainer.getChildren().add(saveButton);

        anchorPane.getChildren().add(formContainer);

    }

    private static void showUpdatePlayerForm(AnchorPane anchorPane){
        VBox getIdBox = new VBox();
        getIdBox.setPadding(new Insets(20));
        getIdBox.setSpacing(10);
        getIdBox.getStyleClass().add("backgroundTeaGreen");
        AnchorPane.setTopAnchor(getIdBox, 150.0);
        AnchorPane.setLeftAnchor(getIdBox, 220.0);
        AnchorPane.setRightAnchor(getIdBox, 30.0);
        AnchorPane.setBottomAnchor(getIdBox, 30.0);

        HBox playerIdBox = new HBox(5);
        Label playerId = new Label("Enter Player ID: ");
        playerId.getStyleClass().add("standardLabel");
        TextField playerInfield = new TextField();
        playerInfield.getStyleClass().add("textFieldOne");
        playerInfield.setPromptText("Player ID");
        playerIdBox.getChildren().addAll(playerId, playerInfield);

        VBox formContainer = new VBox(5);
        formContainer.setPadding(new Insets(20));
        formContainer.getStyleClass().add("backgroundTeaGreen");
        AnchorPane.setTopAnchor(formContainer, 250.0);
        AnchorPane.setLeftAnchor(formContainer, 220.0);
        AnchorPane.setRightAnchor(formContainer, 30.0);
        AnchorPane.setBottomAnchor(formContainer, 30.0);

        Label labelNoPlayerId = new Label(" No player found! Enter a different ID (only numbers allowed). ");
        labelNoPlayerId.getStyleClass().add("standardLabel");

        Button getButton = new Button("Get Player from database");
        getButton.getStyleClass().add("standardButton");
        getButton.setOnAction(event -> {
            try {
            Player playerToUpdate = playerDAO.getPlayer(Integer.parseInt(playerInfield.getText()));

                if (playerToUpdate == null) {
                    formContainer.getChildren().add(labelNoPlayerId);
                    return;
                } else {
                    if (!formContainer.getChildren().isEmpty()) {
                        formContainer.getChildren().clear();
                    }
                    System.out.println(playerToUpdate.toString());
                    HBox fistNameBox = new HBox(5);
                    Label fistName = new Label(" First Name* ");
                    fistName.getStyleClass().add("standardLabel");
                    TextField fistNamefield = new TextField();
                    fistNamefield.getStyleClass().add("textFieldOne");
                    fistNamefield.setText(playerToUpdate.getFirstName());
                    fistNameBox.getChildren().addAll(fistName, fistNamefield);

                    HBox lastNameBox = new HBox(5);
                    Label lastName = new Label(" Last Name* ");
                    lastName.getStyleClass().add("standardLabel");
                    TextField lastNameField = new TextField();
                    lastNameField.getStyleClass().add("textFieldOne");
                    lastNameField.setText(playerToUpdate.getLastName());
                    lastNameBox.getChildren().addAll(lastName, lastNameField);

                    HBox nicknameBox = new HBox(5);
                    Label nickname = new Label(" Nickname* ");
                    nickname.getStyleClass().add("standardLabel");
                    TextField nicknameField = new TextField();
                    nicknameField.getStyleClass().add("textFieldOne");
                    nicknameField.setText(playerToUpdate.getNickname());
                    nicknameBox.getChildren().addAll(nickname, nicknameField);

                    HBox addressBox = new HBox(5);
                    Label streetAddress = new Label(" Street Address ");
                    streetAddress.getStyleClass().add("standardLabel");
                    TextField streetAddressField = new TextField();
                    streetAddressField.getStyleClass().add("textFieldOne");
                    streetAddressField.setText(playerToUpdate.getStreetAddress());
                    addressBox.getChildren().addAll(streetAddress, streetAddressField);

                    HBox zipBox = new HBox(5);
                    Label zipCode = new Label(" Zip Code ");
                    zipCode.getStyleClass().add("standardLabel");
                    TextField zipCodeField = new TextField();
                    zipCodeField.getStyleClass().add("textFieldOne");
                    zipCodeField.setText(playerToUpdate.getZipCode());
                    zipBox.getChildren().addAll(zipCode, zipCodeField);

                    HBox cityBox = new HBox(5);
                    Label city = new Label(" City ");
                    city.getStyleClass().add("standardLabel");
                    TextField cityField = new TextField();
                    cityField.getStyleClass().add("textFieldOne");
                    cityField.setText(playerToUpdate.getCity());
                    cityBox.getChildren().addAll(city, cityField);


                    HBox countryBox = new HBox(5);
                    Label country = new Label(" Country ");
                    country.getStyleClass().add("standardLabel");
                    TextField countryField = new TextField();
                    countryField.getStyleClass().add("textFieldOne");
                    countryField.setText(playerToUpdate.getCountry());
                    countryBox.getChildren().addAll(country, countryField);

                    HBox emailBox = new HBox(5);
                    Label email = new Label(" E-mail ");
                    email.getStyleClass().add("standardLabel");
                    TextField emailField = new TextField();
                    emailField.getStyleClass().add("textFieldOne");
                    emailField.setText(playerToUpdate.getEmail());
                    emailBox.getChildren().addAll(email, emailField);

                    Button updateButton = new Button("Update Player");
                    updateButton.getStyleClass().add("standardButton");
                    updateButton.setOnAction(e -> {
                        Player updatePlayer = new Player(fistNamefield.getText(), lastNameField.getText(), nicknameField.getText(), streetAddressField.getText(), zipCodeField.getText(), cityField.getText(), countryField.getText(), emailField.getText());
                        playerDAO.updatePlayer(updatePlayer);
                        Label labelSaved = new Label(" Player saved and updated in the database! ");
                        labelSaved.getStyleClass().add("standardLabel");
                        formContainer.getChildren().add(labelSaved);
                    });


                    formContainer.getChildren().addAll(fistNameBox, lastNameBox, nicknameBox, addressBox, cityBox, countryBox, emailBox, updateButton);

                }
            } catch (Exception e){
                formContainer.getChildren().add(labelNoPlayerId);
            }
        });


        getIdBox.getChildren().addAll(playerIdBox, getButton);

        anchorPane.getChildren().addAll(getIdBox, formContainer);
    }

    protected static void addCustomComponents(AnchorPane anchorPane, List<Player> players){


    }
}
