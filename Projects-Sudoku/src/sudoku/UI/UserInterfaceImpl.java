package sudoku.UI;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sudoku.Constants.GameState;
import sudoku.domainproblem.Coordinates;
import sudoku.domainproblem.Game;

import java.util.HashMap;

public class UserInterfaceImpl implements IUserInterfaceContract.View,
        EventHandler<KeyEvent>{
    private final Stage stage;
    private final Group root;

    // to keep track of 81 fields
    private HashMap<Coordinates, GameTextField> textFieldCoordinates;
    private IUserInterfaceContract.EventListener listener;

    private static final double WINDOW_Y = 732;
    private static final double WINDOW_X = 668;
    private static final double BOARD_PADDING = 50;
    private static final double BOARD_X_Y = 576;

    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0, 150, 136);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224, 242, 241);
    private static final String SUDOKU = "Sudoku";

    public UserInterfaceImpl(Stage stage){
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    private void initializeUserInterface(){
        drawBackground(root);
        drawTitle(root);
        drawBoard(root);
        drawTextFields(root);
        drawGridLines(root);
        stage.show();
    }

    private void drawBackground(Group root){
         Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
         scene.setFill(WINDOW_BACKGROUND_COLOR);
         stage.setScene(scene);
    }

    private void drawTitle(Group root) {
        Text title = new Text(235, 690, SUDOKU);
        title.setFill(Color.WHITE);
        Font titleFont = new Font(43);
        title.setFont(titleFont);
        root.getChildren().add(title);
    }

    private void drawBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);

        boardBackground.setWidth(BOARD_X_Y);
        boardBackground.setHeight(BOARD_X_Y);

        boardBackground.setFill(BOARD_BACKGROUND_COLOR);

        root.getChildren().addAll(boardBackground);
    }

    private void drawTextFields(Group root) {
        final int xOrigin = 50;
        final int yOrigin = 50;

        final int xAndYDelta = 64;

        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++){
                int x = xOrigin + i * xAndYDelta;
                int y = yOrigin + j * xAndYDelta;

                GameTextField tile = new GameTextField(i, j);
                styleSudokuTitle(tile, x, y);

                tile.setOnKeyPressed(this);

                textFieldCoordinates.put(new Coordinates(i, j), tile);
                root.getChildren().add(tile);
            }
        }
    }

    private void styleSudokuTitle(GameTextField tile, int x, int y) {
        Font numberFont = new Font(32);
        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);
        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);
        tile.setBackground(Background.EMPTY);
    }

    private void drawGridLines(Group root){
        int xAndY = 114;
        int i = 0;
        while(i < 8) {
            int thickness;
            if(i == 2 || i == 5) {
                thickness = 3;
            } else {
                thickness = 2;
            }

            Rectangle verticalLine = getLine(
                    xAndY + 64 * i,
                    BOARD_PADDING,
                    BOARD_X_Y,
                    thickness
            );

            Rectangle horizontalLine = getLine(
                    BOARD_PADDING,
                    xAndY + 64 * i,
                    thickness,
                    BOARD_X_Y
            );

            root.getChildren().addAll(
                    verticalLine, horizontalLine
            );

            i++;
        }
    }

    private Rectangle getLine(double x, double y, double height, double width){
        Rectangle line = new Rectangle();

        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);
        return line;
    }

    @Override
    public void handle(KeyEvent keyEvent){
        if(keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            if(keyEvent.getText().matches("[0-9]")){
                int value = Integer.parseInt(keyEvent.getText());
                handleInput(value, keyEvent.getSource());
            } else if(keyEvent.getCode() == KeyCode.BACK_SPACE) {
                handleInput(0, keyEvent.getSource());
            } else {
                ((TextField) keyEvent.getSource()).setText("");
            }
        }
    }

    private void handleInput(int value, Object source){
        listener.onSudokuInput(
                ((GameTextField) source).getX(),
                ((GameTextField) source).getY(),
                value);
    }
    @Override
    public void setListener(IUserInterfaceContract.EventListener listener){

    }

    @Override
    public void updateSquare(int x, int y, int input){
        GameTextField tile = textFieldCoordinates.get(new Coordinates(x, y));
        String value = Integer.toString(input);
        if(value.equals("0"))
            value = "";
        tile.textProperty().setValue(value);
    }

    @Override
    public void updateBoard(Game game) {
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                TextField tile = textFieldCoordinates.get(new Coordinates(i, j));
                String value = Integer.toString(game.getCopyOfGridSTate()[i][j]);

                if(value.equals("0"))
                    value = "";

                tile.setText(value);

                if(game.getGameState() == GameState.NEW){
                    if(value.equals("")){
                        tile.setStyle("-fx-opacity: 0.8;");
                        tile.setDisable(true);
                    }
                }
            }
        }
    }

    @Override
    public void showDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if(dialog.getResult() == ButtonType.OK)
            listener.onDialogClick();
    }

    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();
    }
}
