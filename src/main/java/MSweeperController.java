import Interface.ControllerInterface;
import Interface.ModelInterface;
import MSweeperModel.Coord;
import MSweeperModel.GameState;
import MSweeperView.MSweeperView;

import java.awt.event.MouseEvent;
import java.util.List;

public class MSweeperController implements ControllerInterface
{
    private ModelInterface model;
    private MSweeperView view;

    private Long startTime;
    private Long endTime;
    boolean gameOver;

    MSweeperController(ModelInterface model) {
        this.model = model;
        gameOver = false;

        this.model.start();
        view = new MSweeperView(model, this);

        view.initMenu();
        view.initPanel();
        view.initFrame();
        //view.createTextBox();
        startTime  = System.currentTimeMillis();
        //model.notifyObservers(model.getState());
    }

    @Override
    public void gameControl(MouseEvent event, Coord coord) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            model.pressLeftButton(coord);
        }
        if (event.getButton() == MouseEvent.BUTTON3) {
            model.pressRightButton(coord);
        }
        if (!gameOver) {
            if (model.getState() == GameState.WINNER) {
                endTime = System.currentTimeMillis();
                model.addNewScore((endTime - startTime) / 1000);
                model.writeScores();
                view.showWinMessage((endTime - startTime) / 1000);
                gameOver = true;
            }
        }
    }

    @Override
    public void startNewGame() {
        model.start();
        gameOver = false;
        model.notifyObservers(model.getState());
        view.createTextBox();
        startTime  = System.currentTimeMillis();
    }

    @Override
    public void exitGame() {
        view.removeAll();
        System.exit(0);
    }

    @Override
    public void showScoreTable() {
        List<String> table = model.getScoreTable();
        int iter = 0;
        StringBuilder strForView = new StringBuilder();
        for (String str : table) {
            if (5 == iter) {
                break;
            }
            strForView.append(str + "\n");
            ++iter;
        }
        view.showScoreTable(strForView);
    }

    @Override
    public void showAboutMessage() {
        view.showAboutMessage(model.getAuthorsMessage());
    }

    @Override
    public void setDifficultLevel(String level) {
        if (level.equalsIgnoreCase("easy")) {
            model.setDifficultEasy();
        } else if (level.equalsIgnoreCase("medium")) {
            model.setDifficultMedium();
        }else if(level.equalsIgnoreCase("hard")){
            model.setDifficultHard();
        }
        view.setNewDifficultLevel();
        startNewGame();
    }

    @Override
    public void setNewName(String text) {
        if (text == null)
            text = "anonymous";
        model.setName(text);
    }
}
