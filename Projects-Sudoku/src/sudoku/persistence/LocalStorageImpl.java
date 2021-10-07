package sudoku.persistence;

import sudoku.domainproblem.Game;
import sudoku.domainproblem.IStorage;

import java.io.*;

public class LocalStorageImpl implements IStorage {
    private static File GAME_DATA = new File(System.getProperty("user.home"),
            "gamedata.txt");
    @Override
    public void updateData(Game game) throws IOException {
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(GAME_DATA);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new IOException("Unable to access Game Data");
        }
    }

    @Override
    public Game getData() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(GAME_DATA);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try{
            Game game = (Game) objectInputStream.readObject();
            objectInputStream.close();
            return game;
        } catch(ClassNotFoundException e){
            throw new IOException("File Not Found");
        }
    }
}
