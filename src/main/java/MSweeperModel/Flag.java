package MSweeperModel;

class Flag
{
    private Matrix flagmap;
    private int countOfClosed;

    void init () {
        flagmap = new Matrix(Box.CLOSED);
        countOfClosed = Ranges.getSize().x * Ranges.getSize().y;
    }

    Box get (Coord coord) {
        return flagmap.get(coord);
    }

    void setOpenedToBox(Coord coord) {
        flagmap.set(coord, Box.OPENED);
        countOfClosed--;
    }

    void toggleFlagedToBox (Coord coord) {
        switch (flagmap.get(coord)) {
            case FLAGED : setClosedToBox(coord); break;
            case CLOSED : setFlagedToBox(coord); break;
        }
    }

    void setClosedToBox (Coord coord) {
        flagmap.set(coord, Box.CLOSED);
    }

    private void setFlagedToBox(Coord coord) {
        flagmap.set(coord, Box.FLAGED);
    }


    int getCountOfClosedBoxes() {
        return countOfClosed;
    }

    void setBombedToBox(Coord coord) {
        flagmap.set(coord, Box.BOMBED);
    }

    void setOpenedToClosedBombBox(Coord coord) {
        if (flagmap.get(coord) == Box.CLOSED)
            flagmap.set(coord, Box.OPENED);
    }

    void setNobombToFlagedSafeBox(Coord coord) {
        if (flagmap.get(coord) == Box.FLAGED)
            flagmap.set(coord, Box.NOBOMB);
    }

    int getCountOfFlagedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord))
            if (flagmap.get(around) == Box.FLAGED)
                count++;
        return count;
    }


}

