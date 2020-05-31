package MSweeperModel;

import Interface.ModelInterface;
import Interface.ModelObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class MSweeperModel implements ModelInterface
{
    private ArrayList<ModelObserver> modelObservers;

    private int COLS = 9;
    private int ROWS = 9;
    private int BOMBS = 10;

    private Bomb bomb;
    private Flag flag;
    private GameState state;

    private String playerName = "anonymous";
    private ScoreWorker scoreTable;

    public MSweeperModel() {

        modelObservers = new ArrayList<>();

        Ranges.setSize(new Coord(COLS, ROWS));
        bomb = new Bomb(BOMBS);
        flag = new Flag();

        scoreTable = new ScoreWorker();
    }

    private void reset() {
        Ranges.setSize(new Coord(COLS, ROWS));
        bomb = new Bomb(BOMBS);
    }

    @Override
    public void registerObserver(ModelObserver observer) {
        modelObservers.add(observer);
    }

    @Override
    public void removeObserver(ModelObserver observer) {
        modelObservers.remove(observer);
    }

    @Override
    public void notifyObservers(GameState state) {
        for(ModelObserver subscriber : modelObservers)
            subscriber.update(state);
    }

    @Override
    public void start() {
        reset();
        bomb.init();
        flag.init();
        state = GameState.PLAYED;
    }

    @Override
    public List<String> getScoreTable(){
        return scoreTable.getScoreTable();
    }

    @Override
    public void addNewScore(Long time){
        scoreTable.addNewScore(time);
    }

    @Override
    public void writeScores(){
        scoreTable.writeScoreTableToFile();
    }

    @Override
    public void setName(String name) {
        scoreTable.setNamePlayer(name);
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public int getCols() {
        return COLS;
    }

    @Override
    public int getRows() {
        return ROWS;
    }

    @Override
    public Box getBox(Coord coord) {
        if (flag.get(coord) == Box.OPENED)
            return bomb.get(coord);
        else
            return flag.get(coord);
    }

    private void openBox (Coord coord) {
        switch (flag.get(coord)) {
            case OPENED : setOpenedToCloseBoxesAroundNumber(coord); return;
            case FLAGED : return;
            case CLOSED :
                switch (bomb.get(coord)) {
                    case ZERO : openBoxesAround(coord); return;
                    case BOMB : openBombs(coord); return;
                    default   : flag.setOpenedToBox(coord); return;
                }
        }

    }

    public void setOpenedToCloseBoxesAroundNumber(Coord coord) {
        if (bomb.get(coord) != Box.BOMB)
            if (flag.getCountOfFlagedBoxesAround(coord) == bomb.get(coord).getNumber())
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (flag.get(around) == Box.CLOSED)
                        openBox(around);
    }

    @Override
    public void setDifficultEasy() {
        COLS = 9;
        ROWS = 9;
        BOMBS = 10;
        reset();
    }

    @Override
    public void setDifficultMedium() {
        COLS = 18;
        ROWS = 18;
        BOMBS = 25;
        reset();
    }

    @Override
    public void setDifficultHard() {
        COLS = 27;
        ROWS = 27;
        BOMBS = 45;
        reset();
    }

    private void openBombs(Coord bombed) {
        state = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coord coord : Ranges.getAllCoords()) {
            if (bomb.get(coord) == Box.BOMB)
                flag.setOpenedToClosedBombBox(coord);
            else
                flag.setNobombToFlagedSafeBox(coord);
        }
    }

    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord))
            openBox(around);
    }

    public void pressLeftButton (Coord coord) {
        if (gameOver())
            return;
        openBox(coord);
        checkWinner();
        notifyObservers(state);
        //flag.setOpenedToBox(coord);
    }

    public void pressRightButton(Coord coord) {
        if (gameOver())
            return;
        flag.toggleFlagedToBox(coord);
        notifyObservers(state);
    }

    private boolean gameOver() {
        if (state == GameState.PLAYED)
            return false;
        return true;
    }

    private void checkWinner () {
        if (state == GameState.PLAYED)
            if (flag.getCountOfClosedBoxes() == bomb.getTotalBombs())
                state = GameState.WINNER;
    }

    @Override
    public String getAuthorsMessage() {
        String message = "This game was made in a super short time, \n" +
                "as all deadlines were already burning.\n" +
                "All rights reserved\n" +
                "@bazzzilick 2019-2020";
        return message;
    }
}

