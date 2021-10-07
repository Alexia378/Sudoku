package sudoku.domainproblem;

import java.io.IOException;

public interface IStorage {
    void updateData(Game game) throws IOException;
    Game getData() throws IOException;
}
