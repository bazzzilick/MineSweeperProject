package MSweeperModel;

import java.util.ArrayList;
import java.util.Random;

public class Ranges
{
    private static Coord size;
    private static ArrayList<Coord> allCoords;
    private static Random random = new Random();

    public Ranges() {}

    static void setSize(Coord _size) {
        size = _size;
        allCoords = new ArrayList();

        for(int y = 0; y < size.y; ++y) {
            for(int x = 0; x < size.x; ++x) {
                allCoords.add(new Coord(x, y));
            }
        }
    }

    public static Coord getSize() {
        return size;
    }

    public static ArrayList<Coord> getAllCoords() {
        return allCoords;
    }

    static boolean inRange(Coord coord) {
        return coord.x >= 0 && coord.x < size.x && coord.y >= 0 && coord.y < size.y;
    }

    static Coord getRandomCoord() {
        return new Coord(random.nextInt(size.x), random.nextInt(size.y));
    }

    static ArrayList<Coord> getCoordsAround(Coord coord) {
        ArrayList<Coord> list = new ArrayList();

        for(int i = coord.x - 1; i <= coord.x + 1; ++i) {
            for(int j = coord.y - 1; j <= coord.y + 1; ++j) {
                Coord around;
                if (inRange(around = new Coord(i, j)) && !around.equals(coord)) {
                    list.add(around);
                }
            }
        }

        return list;
    }
}
