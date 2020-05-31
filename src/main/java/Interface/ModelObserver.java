package Interface;

import MSweeperModel.GameState;

public interface ModelObserver
{
    public void update(GameState state);
}
