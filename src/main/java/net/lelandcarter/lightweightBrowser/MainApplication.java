package net.lelandcarter.lightweightBrowser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/* Created By: Leland Carter
-- Last Modified 03/09/2023
*/

public class MainApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Root node
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        //Stage properties & initialization
        stage = primaryStage;

        primaryStage.setTitle("Leland's Browser");
        primaryStage.setScene(new Scene(root, 1280, 720));

        primaryStage.show();
    }
}
