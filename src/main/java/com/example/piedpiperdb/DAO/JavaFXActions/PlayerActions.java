package com.example.piedpiperdb.DAO.JavaFXActions;

import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.DAO.MatchDAO;
import com.example.piedpiperdb.DAO.PlayerDAO;
import com.example.piedpiperdb.DAO.TeamDAO;
import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Player;
import com.example.piedpiperdb.Entities.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;


//GEFP-31-AA
public class PlayerActions {
    private static PlayerDAO playerDAO = new PlayerDAO();
    private static GameDAO gameDAO = new GameDAO();
    private static TeamDAO teamDAO = new TeamDAO();
    private static MatchDAO matchDAO = new MatchDAO();

    public static VBox getTableViewAllPlayers(VBox vBox) {
        List<Player> players = playerDAO.getAllPlayers();
        System.out.println(players.size());
        return showTable(vBox, players );
    }

    public static VBox getTableViewSelectedPlayers(VBox vBox, List<String> selections) {
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
        return showTable(vBox, players );
    }

    public static List<CheckBox> gameCheckBoxes(){
        List<Game> games = gameDAO.getAllGames();
        List<CheckBox> checkBoxes = new ArrayList<>();

        for (Game game : games) {
            String name = game.getGameName();
            int gameId = game.getGameId();
            System.out.println(gameId);
            CheckBox checkBox  = new CheckBox(name + ", GameID: " + gameId );
            checkBoxes.add(checkBox);
        }
        return checkBoxes;
    }

    public static VBox showTable(VBox vBox, List<Player> players){
        vBox.getStyleClass().add("textFieldOne");
        TableView<Player> table = createPlayerTable(players); //Skapa tableView
        table.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);
        vBox.getChildren().addAll(table);

        return vBox;
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

        TableColumn<Player, String> match = new TableColumn<>("Match");
        match.setCellValueFactory(new PropertyValueFactory<>("matchInfo"));

        table.getColumns().addAll(player_id, nickname, fullName, address, country, email, game_name, team_name, match);
        table.setItems(observableList);
        return table;
    }

    public static List<Game> getAllGames() {
        return gameDAO.getAllGames();
    }


    public static List<Team> getTeamsByGame(int gameId) {
        return teamDAO.getTeamsByGame(List.of(gameId));
    }

    public static void savePlayer(Player player) {
        playerDAO.savePlayer(player);
    }

    public static Player createPlayerFromFields(String firstName, String lastName, String nickname, String streetAddress,
                                                String zipCode, String city, String country, String email,
                                                String selectedGameValue, String selectedTeamValue) {
        Player player = new Player(firstName, lastName, nickname, streetAddress, zipCode, city, country, email);

        if (selectedGameValue != null && !selectedGameValue.isEmpty()) {
            int gameId = Integer.parseInt(selectedGameValue.split(",")[0].trim());
            Game selectedGame = gameDAO.getGameById(gameId);
            player.setGameId(selectedGame);
        }

        if (selectedTeamValue != null && !selectedTeamValue.isEmpty()) {
            int teamId = Integer.parseInt(selectedTeamValue.split(",")[0].trim());
            Team selectedTeam = teamDAO.getTeamById(teamId);
            player.setTeamId(selectedTeam);
        }

        return player;
    }

    public static boolean areFieldsEmpty(String firstName, String lastName, String nickname, String email) {
        return firstName.isEmpty() || lastName.isEmpty() || nickname.isEmpty() || email.isEmpty();
    }

    // Kontrollera om nickname är unikt
    public static boolean isNicknameUnique(String nickname) {
        return playerDAO.isNicknameUnique(nickname);
    }

    // Kontrollera om e-mail är unik
    public static boolean isEmailUnique(String email) {
        return playerDAO.isEmailUnique(email);
    }

    public static Player getPlayerById(int playerId) {
        return playerDAO.getPlayer(playerId);
    }

    public static void updatePlayer(Player player) {
        playerDAO.updatePlayer(player);
    }

    public static boolean deletePlayerById(int playerId) {
        return playerDAO.deletePlayerById(playerId);
    }
}
