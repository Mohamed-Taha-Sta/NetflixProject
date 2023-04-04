package com.example.netflixproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Scene scene;
    public static boolean resize=true;
    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(LoadFxml("LoginPage"),600,600);
        stage.setResizable(resize);
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

}