package com.jamiegarcia.tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Entry point for the Tic Tac Toe application.
 * Builds the main window: title at top, 3x3 board in the center,
 * and a status label at the bottom.
 */
public class App extends Application {

    // Size of the board - Tic Tac Toe is always 3x3
    private static final int BOARD_SIZE = 3;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        root.setTop(createTitle());
        root.setCenter(createBoard());
        root.setBottom(createStatusLabel());

        Scene scene = new Scene(root, 450, 500);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates the title label shown at the top of the window.
     */
    private Label createTitle() {
        Label title = new Label("Tic Tac Toe");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(0, 0, 20, 0));
        return title;
    }

    /**
     * Creates the 3x3 grid of buttons representing the board.
     * Buttons are empty placeholders for now - click handling
     * and X/O logic come in Milestone 3.
     */
    private GridPane createBoard() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button cell = new Button("");
                cell.setPrefSize(100, 100);
                cell.setStyle("-fx-font-size: 24px;");
                grid.add(cell, col, row);
            }
        }

        return grid;
    }

    /**
     * Creates the status label shown at the bottom of the window.
     * This will later display whose turn it is, or the winner/draw message.
     */
    private Label createStatusLabel() {
        Label status = new Label("Player X's turn");
        status.setStyle("-fx-font-size: 18px;");
        BorderPane.setAlignment(status, Pos.CENTER);
        BorderPane.setMargin(status, new Insets(20, 0, 0, 0));
        return status;
    }

    public static void main(String[] args) {
        launch(args);
    }
}