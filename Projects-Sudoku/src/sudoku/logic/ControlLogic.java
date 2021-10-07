package sudoku.logic;

import javafx.scene.control.Control;
import sudoku.Constants.GameState;
import sudoku.Constants.Messages;
import sudoku.UI.IUserInterfaceContract;
import sudoku.domainproblem.Game;
import sudoku.domainproblem.IStorage;

import java.io.IOException;
import java.util.logging.Logger;

public class ControlLogic implements IUserInterfaceContract.EventListener {
    private IStorage storage;
    private IUserInterfaceContract.View view;

    public ControlLogic(IStorage storage, IUserInterfaceContract.View view){
        this.storage = storage;
        this.view = view;
    }

    @Override
    public void onSudokuInput(int x, int y, int input) {
        try{
            Game data = storage.getData();
            int[][] newGridState = data.getCopyOfGridSTate();
            newGridState[x][y] = input;

            data = new Game(Logic.checkForCompletion(newGridState), newGridState);
            storage.updateData(data);

            view.updateSquare(x, y, input);
            if(data.getGameState() == GameState.COMPLETE)
                view.showDialog(Messages.GAME_COMPLETE);
        } catch(IOException e){
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }

    @Override
    public void onDialogClick() {
        try{
            storage.updateData(Logic.getNewGame());
            view.updateBoard(storage.getData());
        } catch(IOException e){
            view.showError(Messages.ERROR);
        }
    }
}
