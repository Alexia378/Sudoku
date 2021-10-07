package sudoku.logic;

import sudoku.UI.IUserInterfaceContract;
import sudoku.domainproblem.Game;
import sudoku.domainproblem.IStorage;
import sudoku.persistence.LocalStorageImpl;

import java.io.IOException;

public class SudokuBuildLogic {
    public static void build(IUserInterfaceContract.View ui) throws IOException {
        Game initialState;
        IStorage storage = new LocalStorageImpl();

        try{
            initialState = storage.getData();
        } catch(IOException e){
            initialState = Logic.getNewGame();
            storage.updateData(initialState);
        }

        IUserInterfaceContract.EventListener uiLogic =
                new ControlLogic(storage, ui);

        ui.setListener(uiLogic);
        ui.updateBoard(initialState);
    }
}
