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

public class App extends Application {

    private static final int BOARD_SIZE = 3;
    private static final String CAT = "CAT";
    private static final String DOG = "DOG";

    private final String[][] board = new String[BOARD_SIZE][BOARD_SIZE];
    private final Button[][] cellButtons = new Button[BOARD_SIZE][BOARD_SIZE];

    private Label statusLabel;
    private boolean isCatTurn = true;
    private boolean gameOver = false;

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

    private GridPane createBoard() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button cell = new Button();
                cell.setPrefSize(100, 100);

                final int r = row;
                final int c = col;
                cell.setOnAction(event -> handleCellClick(r, c));

                cellButtons[row][col] = cell;
                grid.add(cell, col, row);
            }
        }

        return grid;
    }

    private void handleCellClick(int row, int col) {
        if (gameOver || board[row][col] != null) {
            return;
        }

        String currentPlayer = isCatTurn ? CAT : DOG;
        board[row][col] = currentPlayer;
        setCellIcon(cellButtons[row][col], currentPlayer);

        if (checkWinner(currentPlayer)) {
            String winnerName = currentPlayer.equals(CAT) ? "Cat" : "Dog";
            endGame(winnerName + " wins!");
            return;
        }

        if (isBoardFull()) {
            endGame("It's a draw!");
            return;
        }

        isCatTurn = !isCatTurn;
        updateStatusLabel();
    }

    private boolean checkWinner(String player) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (player.equals(board[row][0])
                    && player.equals(board[row][1])
                    && player.equals(board[row][2])) {
                return true;
            }
        }

        for (int col = 0; col < BOARD_SIZE; col++) {
            if (player.equals(board[0][col])
                    && player.equals(board[1][col])
                    && player.equals(board[2][col])) {
                return true;
            }
        }

        if (player.equals(board[0][0])
                && player.equals(board[1][1])
                && player.equals(board[2][2])) {
            return true;
        }

        if (player.equals(board[0][2])
                && player.equals(board[1][1])
                && player.equals(board[2][0])) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private void endGame(String message) {
        gameOver = true;
        statusLabel.setText(message);
        disableAllCells();
    }

    private void disableAllCells() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                cellButtons[row][col].setDisable(true);
            }
        }
    }

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