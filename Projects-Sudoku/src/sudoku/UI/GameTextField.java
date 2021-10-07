package sudoku.UI;

import javafx.scene.control.TextField;

public class GameTextField extends TextField {
    private final int x;
    private final int y;

    public GameTextField(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    @Override
    public void replaceText(int i, int j, String s){
        if(!s.matches("[0-9]")) {
            super.replaceText(i, j, s);
        }
    }

    @Override
    public void replaceSelection(String s){
        if(!s.matches("[0-9]")) {
            super.replaceSelection(s);
        }
    }
}
