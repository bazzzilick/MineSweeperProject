import MSweeperModel.MSweeperModel;

public class MineSweeper
{
    public static void main(String[] args) {
        MSweeperModel model = new MSweeperModel();
        MSweeperController controller = new MSweeperController(model);
    }
}
