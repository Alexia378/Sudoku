package sudoku.logic;

import sudoku.domainproblem.Game;

public class SudokuUtilities {
    public static void copySudokuArrayValues(int[][] oldArray, int[][] newArray){
        for(int i = 0; i < Game.GRID_BOUNDARY; i++){
            for(int j = 0; j < Game.GRID_BOUNDARY; j++){
                newArray[i][j] = oldArray[i][j];
            }
        }
    }

    public static int[][] copyToNewArray(int[][] oldArray){
        int[][] newArray = new int[Game.GRID_BOUNDARY][Game.GRID_BOUNDARY];

        for(int i=0;i<Game.GRID_BOUNDARY;i++){
            for(int j = 0;j < Game.GRID_BOUNDARY;j++){
                newArray[i][j] = oldArray[i][j];
            }
        }

        return newArray;
    }
}
