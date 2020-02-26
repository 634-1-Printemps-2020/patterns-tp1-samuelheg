import metier.Point;
import metier.PyRat;
import outils.Labyrinthe;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main (String[] args) {
        PyRat pyrat = new PyRat();
        final Map<Point, List<Point>> laby = Labyrinthe.getLaby();
        final int labyWidth = Labyrinthe.getLabyWidth();
        final int labyHeight = Labyrinthe.getLabyHeight();
        Point position = Labyrinthe.getPosition();
        List<Point> fromages = Labyrinthe.getFromages();

        pyrat.preprocessing(laby, labyWidth, labyHeight, position, fromages);
        pyrat.turn(laby, labyWidth, labyHeight, position, fromages);

    }
}