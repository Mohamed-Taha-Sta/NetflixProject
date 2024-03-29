package com.example.netflixproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloApplication extends Application implements Initializable {
    private static Scene scene;
    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(LoadFxml("LoginPage"),1280,720);
        stage.setResizable(false);
        stage.setMinWidth(1280);
        stage.setMinHeight(720);
        Image icon = new Image(new File("src/main/resources/Images/Design/Copy_of_Blue_Geometric_Party_Movie_Night_Invitation-removebg-preview.png").toURI().toString() );
        stage.getIcons().add(icon);
        stage.setTitle("StreamScape");
        stage.setScene(scene);
        stage.show();

    }
    public static void SetRoot(String fxml) throws  Exception{
        scene.setRoot(LoadFxml(fxml));

    }
    private static Parent LoadFxml(String fxml)throws IOException{
        FXMLLoader fxmlLoader =new FXMLLoader(HelloApplication.class.getResource(fxml+".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {

        launch();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



}