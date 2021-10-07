package sudoku.logic;

import sudoku.Constants.GameState;
import sudoku.Constants.Rows;
import sudoku.domainproblem.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static sudoku.domainproblem.Game.GRID_BOUNDARY;

public class Logic {
    public static Game getNewGame(){
        return new Game(GameState.NEW,
                GameGenerator.getNewGameGrid());
    }

    public static GameState checkForCompletion(int[][] grid){
        if(sudokuIsInvalid(grid))
            return GameState.ACTIVE;
        if(tilesAreNotFilled(grid))
            return GameState.ACTIVE;
        return GameState.COMPLETE;
    }

    public static boolean sudokuIsInvalid(int[][] grid){
        if(rowsAreInvalid(grid))
            return true;
        if(columnsAreInvalid(grid))
            return true;
        if(squaresAreInvalid(grid))
            return true;
        else return false;
    }

    private static boolean rowsAreInvalid(int[][] grid){
        for(int j = 0; j < GRID_BOUNDARY; j++){
            List<Integer> row = new ArrayList<>();
            for(int i = 0; i < GRID_BOUNDARY; i++){
                row.add(grid[i][j]);
            }

            if(collectionHasRepeats(row))return true;
        }

        return false;
    }

    private static boolean columnsAreInvalid(int[][] grid){
        for(int i=0;i<GRID_BOUNDARY;i++){
            List<Integer> row = new ArrayList<>();
            for(int j = 0;j<GRID_BOUNDARY;j++){
                row.add(grid[i][j]);
            }

            if(collectionHasRepeats(row))return true;
        }

        return false;
    }

    private static boolean squaresAreInvalid(int[][] grid){
        if(rowOfSquaresIsInvalid(Rows.TOP, grid))
            return true;
        if(rowOfSquaresIsInvalid(Rows.MIDDLE, grid))
            return true;
        if(rowOfSquaresIsInvalid(Rows.BOTTOM, grid))
            return true;
        return false;
    }

    private static boolean rowOfSquaresIsInvalid(Rows value, int[][] grid){
        switch (value) {
            case TOP:
                if(squareIsInvalid(0, 0, grid))
                    return true;
                if(squareIsInvalid(0, 3, grid))
                    return true;
                if(squareIsInvalid(0, 6, grid))
                    return true;
                return false;
            case MIDDLE:
                if(squareIsInvalid(3, 0, grid))
                    return true;
                if(squareIsInvalid(3, 3, grid))
                    return true;
                if(squareIsInvalid(3, 6, grid))
                    return true;
                return false;
            case BOTTOM:
                if(squareIsInvalid(6, 0, grid))
                    return true;
                if(squareIsInvalid(6, 3, grid))
                    return true;
                if(squareIsInvalid(6, 6, grid))
                    return true;
                return false;
            default:
                return false;
        }
    }

    private static boolean squareIsInvalid(int i, int j, int[][] grid){
        int yEnd = j + 3;
        int xEnd = i + 3;
        List<Integer> square = new ArrayList<>();

        while(j < yEnd){
            while(i < xEnd) {
                square.add(grid[i][j]);
                i++;
            }
            i-=3;
            j++;
        }

        if(collectionHasRepeats(square))
            return true;
        return false;
    }

    private static boolean collectionHasRepeats(List<Integer> collection){
        for(int i = 1;i<=GRID_BOUNDARY;i++){
            if(Collections.frequency(collection, i) > 1)
                return true;
        }

        return false;
    }

    public static boolean tilesAreNotFilled(int[][] grid){
        for(int i=0;i<GRID_BOUNDARY;i++){
            for(int j=0;j<GRID_BOUNDARY;j++){
                if(grid[i][j] == 0){
                    return true;
                }
            }
        }

        return false;
    }
}
