package sudoku.logic;

import javafx.scene.Scene;
import sudoku.domainproblem.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sudoku.domainproblem.Game.GRID_BOUNDARY;

public class GameGenerator {
    public static int[][] getNewGameGrid(){
        return unsolveGame(getSolvedGame());
    }

    private static int[][] getSolvedGame(){
        Random rand = new Random(System.currentTimeMillis());
        int[][] newGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        for(int i = 1;i<=GRID_BOUNDARY;i++){
            int allocations = 0;
            int interrupt = 0;

            List<Coordinates> allocTracker = new ArrayList<>();
            int attempts = 0;

            while(allocations < GRID_BOUNDARY){
                if(interrupt > 200){
                    allocTracker.forEach(coordinates -> {
                        newGrid[coordinates.getX()][coordinates.getY()]=0;
                    });

                    interrupt = 0;
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;

                    if(attempts > 500){
                        clearArray(newGrid);
                        attempts = 0;
                        i = 1;
                    }
                }

                int x = rand.nextInt(GRID_BOUNDARY);
                int y = rand.nextInt(GRID_BOUNDARY);

                if(newGrid[x][y] == 0){
                    newGrid[x][y] = i;

                    if(Logic.sudokuIsInvalid(newGrid)){
                        newGrid[x][y] = 0;
                        interrupt++;
                    } else{
                        allocTracker.add(new Coordinates(x, y));
                        allocations++;
                    }
                }
            }
        }

        return newGrid;
    }

    private static int[][] unsolveGame(int[][] solvedGame){
        Random rand = new Random(System.currentTimeMillis());
        boolean solvable = false;
        int[][]solveableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        while(!solvable){
            SudokuUtilities.copySudokuArrayValues(solvedGame, solveableArray);
            int i = 0;

            while(i < 40){
                int x = rand.nextInt(GRID_BOUNDARY);
                int y = rand.nextInt(GRID_BOUNDARY);

                if(solveableArray[x][y] != 0){
                    solveableArray[x][y] = 0;
                    i++;
                }
            }

            int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY];
            SudokuUtilities.copySudokuArrayValues(solveableArray, toBeSolved);

            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);
        }

        return solveableArray;
    }

    private static void clearArray(int[][] grid){
        for(int i=0;i<GRID_BOUNDARY;i++){
            for(int j=0;j<GRID_BOUNDARY;j++){
                grid[i][j] = 0;

            }
        }
    }
}
