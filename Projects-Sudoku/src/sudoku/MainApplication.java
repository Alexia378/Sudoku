package sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sudoku.UI.IUserInterfaceContract;
import sudoku.UI.UserInterfaceImpl;
import sudoku.logic.SudokuBuildLogic;

import java.io.IOException;

public class MainApplication extends Application {
    private IUserInterfaceContract.View uiImplementation;

    @Override
    public void start(Stage primaryStage) throws Exception{
        uiImplementation = new UserInterfaceImpl(primaryStage);
        try{
            SudokuBuildLogic.build(uiImplementation);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
