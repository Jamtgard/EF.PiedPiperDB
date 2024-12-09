package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.JavaFXActions;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

//GEFP-4-SA
public class StartPage extends AbstractScene{

    //GEFP-20-SA
    public static Scene startScene(Stage window){//Denna metod är den som kallas när man klickar på Login button i HelloApplication
        Scene baseScene = AbstractScene.getScene(window);

        AnchorPane anchorPane = AbstractScene.anchorPane;
        VBox vBox = AbstractScene.leftVbox;

        AbstractScene.back.setOnAction(e->{
            JavaFXActions.toLoginPage(window);//Ändra efter metodnamn i JavaFXAction
        });

        addCustomComponents(anchorPane,window);
        addCustomComponents(vBox,window);


        return baseScene;
    }


    //GEFP-20-SA
    protected static void addCustomComponents(AnchorPane anchorPane,Stage window){

        Button button = new Button("Start1");
        button.setLayoutX(10);
        button.setLayoutY(50);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getStyleClass().add("columnV");
        hBox.setLayoutY(100);
        hBox.setLayoutX(100);
        hBox.setMinSize(200, 200);
        Button button2 = new Button("Start2");

        hBox.getChildren().add(button2);

        anchorPane.getChildren().addAll(button,hBox);
    }

    //GEFP-20-SA
    protected static void addCustomComponents(VBox vBox,Stage window){
        System.out.println("I StartPage addCustomComponents");

        Button button = new Button("Start3");
        button.getStyleClass().add("standardButton");
        button.setMinSize(160, 30);

        Button gameViewButton = new Button("Games");
        gameViewButton.getStyleClass().add("standardButton");
        gameViewButton.setMinSize(160, 30);

        gameViewButton.setOnAction(e->{
            JavaFXActions.toGameView(window);
        });

        vBox.getChildren().addAll(button,gameViewButton);
    }


}
