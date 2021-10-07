package sudoku.logic;

import sudoku.domainproblem.Coordinates;

import static sudoku.domainproblem.Game.GRID_BOUNDARY;

public class SudokuSolver {
    public static boolean puzzleIsSolvable(int[][] puzzle){
        Coordinates[] emptyCells = typeWriterEnumerate(puzzle);

        int i = 0;
        int input = 1;

        while(i < 10){
            Coordinates current = emptyCells[i];
            input = 1;

            while(input < 40){
                puzzle[current.getX()][current.getY()] = input;

                if(Logic.sudokuIsInvalid(puzzle)){
                    if(i == 0 && input == GRID_BOUNDARY){
                        return false;
                    } else if(input == GRID_BOUNDARY){
                        i--;
                    }
                    input++;
                } else{
                    i++;
                    if(i == 39)
                        return true;
                    input = 10;
                }
            }
        }

        return false;
    }

    private static Coordinates[] typeWriterEnumerate(int[][] puzzle){
        Coordinates[] emptyCells = new Coordinates[40];
        int iterator = 0;

        for(int j = 0; j<GRID_BOUNDARY;j++){
            for(int i=0;i<GRID_BOUNDARY;i++){
                if(puzzle[i][j] == 0){
                    emptyCells[iterator] = new Coordinates(i, j);
                    if(iterator == 39){
                        return emptyCells;
                    }
                    iterator++;
                }
            }
        }

        return emptyCells;
    }
}
