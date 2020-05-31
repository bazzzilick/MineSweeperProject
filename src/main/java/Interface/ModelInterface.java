package Interface;

import MSweeperModel.Box;
import MSweeperModel.Coord;
import MSweeperModel.GameState;
import MSweeperModel.Ranges;

import java.util.List;

public interface ModelInterface
{
    public void registerObserver(ModelObserver obs);
    public void removeObserver(ModelObserver obs);
    public void notifyObservers(GameState state);

    public Box getBox(Coord coord);
    public GameState getState();
    public int getCols();
    public int getRows();

    public void setDifficultEasy();
    public void setDifficultMedium();
    public void setDifficultHard();

    public void setOpenedToCloseBoxesAroundNumber(Coord coord);
    public void pressLeftButton (Coord coord);
    public void pressRightButton(Coord coord);

    public void start();

    public List<String> getScoreTable();
    public void addNewScore(Long time);
    public void writeScores();
    public void setName(String text);

    public String getAuthorsMessage();
}
