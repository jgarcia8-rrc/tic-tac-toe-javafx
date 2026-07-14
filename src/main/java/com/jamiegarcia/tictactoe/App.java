package com.jamiegarcia.tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Entry point for the Tic Tac Toe application.
 * Builds the main window and handles player moves (Cat vs Dog).
 */
public class App extends Application {

    private static final int BOARD_SIZE = 3;
    private static final String CAT = "CAT";
    private static final String DOG = "DOG";

    // Tracks what's placed in each cell: null = empty, otherwise CAT or DOG.
    // We'll use this same board in Milestone 4 to check for winners.
    private final String[][] board = new String[BOARD_SIZE][BOARD_SIZE];

    // Keep references to the buttons so we can update their icons on click
    private final Button[][] cellButtons = new Button[BOARD_SIZE][BOARD_SIZE];

    private Label statusLabel;
    private boolean isCatTurn = true; // Cat always moves first

    private Image catImage;
    private Image dogImage;

    @Override
    public void start(Stage primaryStage) {
        loadImages();

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
     * Loads the cat and dog images once at startup so we don't
     * re-read the files from disk on every single click.
     */
    private void loadImages() {
        catImage = new Image(getClass().getResourceAsStream("/images/cat.png"));
        dogImage = new Image(getClass().getResourceAsStream("/images/dog.png"));
    }

    private Label createTitle() {
        Label title = new Label("Tic Tac Toe");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(0, 0, 20, 0));
        return title;
    }

    /**
     * Builds the 3x3 grid of clickable cells.
     */
    private GridPane createBoard() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button cell = new Button();
                cell.setPrefSize(100, 100);

                // row/col must be captured in final local variables
                // to safely use them inside the click handler below
                final int r = row;
                final int c = col;
                cell.setOnAction(event -> handleCellClick(r, c));

                cellButtons[row][col] = cell;
                grid.add(cell, col, row);
            }
        }

        return grid;
    }

    /**
     * Handles a click on a board cell: ignores it if the cell is
     * already taken, otherwise places the current player's icon
     * and switches turns.
     */
    private void handleCellClick(int row, int col) {
        if (board[row][col] != null) {
            return; // Cell already occupied - do nothing
        }

        String currentPlayer = isCatTurn ? CAT : DOG;
        board[row][col] = currentPlayer;

        setCellIcon(cellButtons[row][col], currentPlayer);

        isCatTurn = !isCatTurn;
        updateStatusLabel();
    }

    /**
     * Displays the correct icon (cat or dog) on the given button
     * and disables it so it can't be clicked again.
     */
    private void setCellIcon(Button button, String player) {
        Image image = player.equals(CAT) ? catImage : dogImage;

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        imageView.setPreserveRatio(true);

        button.setGraphic(imageView);
        button.setDisable(true);
    }

    private Label createStatusLabel() {
        statusLabel = new Label("Cat's turn");
        statusLabel.setStyle("-fx-font-size: 18px;");
        BorderPane.setAlignment(statusLabel, Pos.CENTER);
        BorderPane.setMargin(statusLabel, new Insets(20, 0, 0, 0));
        return statusLabel;
    }

    private void updateStatusLabel() {
        statusLabel.setText(isCatTurn ? "Cat's turn" : "Dog's turn");
    }

    public static void main(String[] args) {
        launch(args);
    }
}