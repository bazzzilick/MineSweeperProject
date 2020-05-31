package Interface;

import MSweeperModel.Coord;

import java.awt.event.MouseEvent;

public interface ControllerInterface
{
    public void gameControl(MouseEvent event, Coord coord);
    public void startNewGame();
    public void exitGame();
    public void showScoreTable();
    public void showAboutMessage();
    public void setDifficultLevel(String level);
    public void setNewName(String text);
}
