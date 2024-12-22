package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.StaffDAO;
import com.example.piedpiperdb.Entities.Staff;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Anna R
public class StaffView {

    private StaffDAO staffDAO = new StaffDAO();
    private ObservableList<Staff> staffList;

    public void displayStaff(Stage stage) {
        BorderPane borderPane = new BorderPane();

        TableView<Staff> tableView = new TableView<>();
        TableColumn<Staff, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFirstName()));

        TableColumn<Staff, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLastName()));

        TableColumn<Staff, String> nicknameColumn = new TableColumn<>("Nickname");
        nicknameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNickname()));

        TableColumn<Staff, String> streetAdressColumn = new TableColumn<>("Street Address");
        streetAdressColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty());

        TableColumn<Staff, String> poctalCodeColumn = new TableColumn<>("Postal Code");
        poctalCodeColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty());

        TableColumn<Staff, String> cityColumn = new TableColumn<>("City");
        cityColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty());

        TableColumn<Staff, String> countryColumn = new TableColumn<>("Country");
        countryColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty());

        TableColumn<Staff, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));

        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, nicknameColumn, streetAdressColumn, poctalCodeColumn, cityColumn, countryColumn);
        //ladda data från MySQL
        loadDataFromDatabase();
        tableView.setItems(staffList);

        //Utseende
        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(10, 10, 10, 20)); //gör om

        TextField firstNameTextField = new TextField();
        firstNameTextField.setPromptText("First Name");

        TextField lastNameTextField = new TextField();
        lastNameTextField.setPromptText("Last Name");

        TextField nicknameTextField = new TextField();
        nicknameTextField.setPromptText("Nickname");

        TextField streetAdressTextField = new TextField();
        streetAdressTextField.setPromptText("Street Address");

        TextField postalCodeTextField = new TextField();
        postalCodeTextField.setPromptText("Postal Code");

        TextField cityTextField = new TextField();
        cityTextField.setPromptText("City");

        TextField countryTextField = new TextField();
        countryTextField.setPromptText("Country");

        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Email");

        Button addButton = new Button("Add Staff");
        addButton.setOnAction(e -> {
            Staff newStaff = new Staff(
                    firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    nicknameTextField.getText(),
                    streetAdressTextField.getText(),
                    postalCodeTextField.getText(),
                    cityTextField.getText(),
                    countryTextField.getText(),
                    emailTextField.getText()
            );

            if (staffDAO.savePersonal(newStaff)) {
                loadDataFromDatabase();
                tableView.setItems(staffList);
                clearForm(firstNameTextField, lastNameTextField, nicknameTextField, streetAdressTextField, postalCodeTextField, cityTextField, countryTextField, emailTextField);
            } else {
                showAlert("Error", "Failed to add new staff");
            }
        });

        formLayout.getChildren().addAll(firstNameTextField, lastNameTextField, nicknameTextField, streetAdressTextField, postalCodeTextField, cityTextField, countryTextField, emailTextField);

//        mainLayout.setCenter(tableView);
//        mainLayout.setRight(formLayout);

        Scene scene = new Scene(formLayout, 900, 600); //mainLayout som förslag
        stage.setScene(scene);
        stage.setTitle("Staff");
        stage.show();
    }
    private void loadDataFromDatabase() {
        staffList = FXCollections.observableArrayList(staffDAO.getAllStaff());
    }
    private void clearForm(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
