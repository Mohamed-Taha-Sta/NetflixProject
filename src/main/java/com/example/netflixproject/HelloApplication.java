package com.example.netflixproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloApplication extends Application implements Initializable {
    private static Scene scene;
    public static boolean resize=true;
    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(LoadFxml("EpisodePage"),600,600);
        stage.setResizable(resize);
        stage.setMinHeight(600);
        stage.setMinWidth(900);
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