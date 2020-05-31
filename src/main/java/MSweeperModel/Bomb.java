package MSweeperModel;

public class Bomb
{
    private Matrix bombmap;
    private int totalbombs;

    Bomb(int totalbombs) {
        this.totalbombs = totalbombs;
        fixBombsCount();
    }

    void init(){
        bombmap = new Matrix(Box.ZERO);
        for(int i = 0; i < totalbombs; i++) {
            setBomb();
        }
    }

    Box get(Coord coord){
        return bombmap.get(coord);
    }

    /*private void setBomb() {
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if (bombmap.get(coord).ordinal() != Box.BOMB.ordinal()) {
                bombmap.set(coord, Box.BOMB);
                //for (Coord around : Ranges.getCoordsAround(coord))
                    //bombmap.set(around, Box.NUM1);
                break;
            }
        }
    }*/

    private void fixBombsCount () {
        int MAXBOMBS = Ranges.getSize().x * Ranges.getSize().y / 2;
        if (totalbombs > MAXBOMBS)
            totalbombs = MAXBOMBS;
    }

    private void setBomb () {
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if (bombmap.get(coord) == Box.BOMB) { continue;}
            bombmap.set(coord, Box.BOMB);
            incNumbersAroundBomb(coord);
            break;
        }
    }

    private void incNumbersAroundBomb(Coord coord){
        for (Coord around : Ranges.getCoordsAround(coord)) {
            if (bombmap.get(around) != Box.BOMB) {
                bombmap.set(around, bombmap.get(around).getNextNumberBox());
            }
        }
    }

    public int getTotalBombs() {
        return totalbombs;
    }
}
