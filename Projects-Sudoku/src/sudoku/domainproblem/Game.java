package sudoku.domainproblem;

import sudoku.Constants.GameState;
import sudoku.logic.SudokuUtilities;

import java.io.Serializable;

public class Game implements Serializable {
    private final GameState state;
    private final int[][] grid;
    public static final int GRID_BOUNDARY = 9;

    public Game(GameState gameState, int[][] gridState){
        this.state = gameState;
        this.grid = gridState;
    }

    public GameState getGameState() { return state; }
    public int[][] getCopyOfGridSTate(){
        return SudokuUtilities.copyToNewArray(grid);
    }
}
