package com.jamiegarcia.tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Entry point for the Tic Tac Toe application.
 * This class just launches the JavaFX runtime for now —
 * the real UI will be built in Milestone 2.
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label placeholder = new Label("Tic Tac Toe - Setup Complete");
        StackPane root = new StackPane(placeholder);

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}