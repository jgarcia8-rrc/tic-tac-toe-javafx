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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Entry point and UI controller for the Tic Tac Toe application.
 * This class is responsible for building the JavaFX interface and
 * responding to user input. The actual game rules live in
 * {@link GameLogic}, keeping this class focused purely on the UI.
 */
public class App extends Application {

    private static final int BOARD_SIZE = GameLogic.BOARD_SIZE;

    private final GameLogic gameLogic = new GameLogic();
    private final Button[][] cellButtons = new Button[BOARD_SIZE][BOARD_SIZE];

    private Label statusLabel;
    private Button restartButton;
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
        root.setBottom(createBottomSection());

        Scene scene = new Scene(root, 450, 550);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** Loads the cat and dog icons once at startup for reuse on every move. */
    private void loadImages() {
        catImage = new Image(getClass().getResourceAsStream("/images/cat.png"));
        dogImage = new Image(getClass().getResourceAsStream("/images/dog.png"));
    }

    private Label createTitle() {
        Label title = new Label("Tic Tac Toe");
        title.getStyleClass().add("title-label");
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(0, 0, 20, 0));
        return title;
    }

    /** Builds the 3x3 grid of clickable board cells. */
    private GridPane createBoard() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button cell = new Button();
                cell.setPrefSize(100, 100);
                cell.getStyleClass().add("cell-button");

                final int r = row;
                final int c = col;
                cell.setOnAction(event -> handleCellClick(r, c));

                cellButtons[row][col] = cell;
                grid.add(cell, col, row);
            }
        }

        return grid;
    }

    /** Builds the status label and restart button shown below the board. */
    private VBox createBottomSection() {
        statusLabel = new Label("Cat's turn");
        statusLabel.getStyleClass().add("status-label");

        restartButton = new Button("New Game");
        restartButton.getStyleClass().add("restart-button");
        restartButton.setOnAction(event -> resetGame());
        restartButton.setVisible(false);
        restartButton.setManaged(false);

        VBox bottomBox = new VBox(15);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.getChildren().addAll(statusLabel, restartButton);
        VBox.setMargin(bottomBox, new Insets(20, 0, 0, 0));

        return bottomBox;
    }

    /**
     * Handles a click on a board cell: places the current player's
     * mark then checks for a win or draw.
     */
    private void handleCellClick(int row, int col) {
        if (gameOver || !gameLogic.isCellEmpty(row, col)) {
            return;
        }

        String currentPlayer = isCatTurn ? GameLogic.CAT : GameLogic.DOG;
        gameLogic.placeMark(row, col, currentPlayer);
        setCellIcon(cellButtons[row][col], currentPlayer);

        if (gameLogic.checkWinner(currentPlayer)) {
            String winnerName = currentPlayer.equals(GameLogic.CAT) ? "Cat" : "Dog";
            endGame(winnerName + " wins!");
            return;
        }

        if (gameLogic.isBoardFull()) {
            endGame("It's a draw!");
            return;
        }

        isCatTurn = !isCatTurn;
        updateStatusLabel();
    }

    /** Ends the current game: locks the board and reveals the restart button. */
    private void endGame(String message) {
        gameOver = true;
        statusLabel.setText(message);
        disableAllCells();
        restartButton.setVisible(true);
        restartButton.setManaged(true);
    }

    private void disableAllCells() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                cellButtons[row][col].setDisable(true);
            }
        }
    }

    /** Resets both the visual board and game logic for new round. */
    private void resetGame() {
        gameLogic.reset();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                cellButtons[row][col].setGraphic(null);
                cellButtons[row][col].setDisable(false);
            }
        }

        isCatTurn = true;
        gameOver = false;
        updateStatusLabel();
        restartButton.setVisible(false);
        restartButton.setManaged(false);
    }

    /** Displays the correct icon on a cell and disables it once played. */
    private void setCellIcon(Button button, String player) {
        Image image = player.equals(GameLogic.CAT) ? catImage : dogImage;

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        imageView.setPreserveRatio(true);

        button.setGraphic(imageView);
        button.setDisable(true);
    }

    private void updateStatusLabel() {
        statusLabel.setText(isCatTurn ? "Cat's turn" : "Dog's turn");
    }

    public static void main(String[] args) {
        launch(args);
    }
}