package metier;

import java.util.*;

public class PyRat {

    private List<Point> fromagesMap;
    private HashSet<Point> fromagesSet;
    private Map<Point, List<Point>> labyMap;
    private Map<Point,HashSet<Point>> labySet;
    /* Méthode appelée une seule fois permettant d'effectuer des traitements "lourds" afin d'augmenter la performace de la méthode turn. */
    public void preprocessing(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        fromagesMap = fromages;
        fromagesSet = initializeFromagesSet();
        labyMap = new HashMap<Point, List<Point>>();
        labyMap = laby;
        labySet = new HashMap<Point,HashSet<Point>>();
        labySet = initializeLabySet();
    }

    private HashSet<Point> initializeFromagesSet(){
        HashSet<Point> set = new HashSet<>();
        set.addAll(fromagesMap);
        return set;
    }

    private HashMap<Point,HashSet<Point>> initializeLabySet(){
        HashMap<Point,HashSet<Point>> map = new HashMap<Point,HashSet<Point>>();
        Set<Point> points = labyMap.keySet();
        for (Point point:points) {
            HashSet<Point> pointSet = new HashSet<Point>();
            pointSet.addAll(labyMap.get(point));
            map.put(point,pointSet);
        }
        return map;
    }

    private HashMap<Point,Point> routage(Point de){
        HashMap<Point,Point> routage = new HashMap<Point,Point>();
        routage.put(de,null);
        HashSet<Point> marquage = new  HashSet<Point>();
        return routageRec(de,routage,marquage);
    }

    private HashMap<Point,Point> routageRec(Point de, HashMap<Point,Point> routage, HashSet<Point> marquage){
        marquage.add(de);
        for (Point point: labyMap.get(de)) {
            if(!marquage.contains(point)){
                routage.put(point,de);
                routageRec(point,routage,marquage);
            }
        }
        return routage;
    }

    /* Méthode de test appelant les différentes fonctionnalités à développer.
        @param laby - Map<Point, List<Point>> contenant tout le labyrinthe, c'est-à-dire la liste des Points, et les Points en relation (passages existants)
        @param labyWidth, labyHeight - largeur et hauteur du labyrinthe
        @param position - Point contenant la position actuelle du joueur
        @param fromages - List<Point> contenant la liste de tous les Points contenant un fromage. */
    public void turn(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        Point pt1 = new Point(2,1);
        Point pt2 = new Point(3,1);
        System.out.println((fromageIci(pt1) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt1);
        System.out.println((fromageIci_EnOrdreConstant(pt2) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt2);
        System.out.println((passagePossible(pt1, pt2) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt2);
        System.out.println((passagePossible_EnOrdreConstant(pt1, pt2) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt2);
        System.out.println("Liste des points inatteignables depuis la position " + position + " : " + pointsInatteignables(position));
    }

    /* Regarde dans la liste des fromages s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci(Point pos) {
        return fromagesMap.contains(pos);
    }

    /* Regarde de manière performante (accès en ordre constant) s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci_EnOrdreConstant(Point pos) {
        return fromagesSet.contains(pos);
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a ».
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible(Point de, Point a) {
        return labyMap.get(de).contains(a);
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a »,
        mais sans devoir parcourir la liste des Points se trouvant dans la Map !
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible_EnOrdreConstant(Point de, Point a) {
        return labySet.get(de).contains(a);
    }

    /* Retourne la liste des points qui ne peuvent pas être atteints depuis la position « pos ».
        @return la liste des points qui ne peuvent pas être atteints depuis la position « pos ». */
    private List<Point> pointsInatteignables(Point pos) {
        HashMap<Point,Point> routage = routage(pos);
        List<Point> inatteignables = new ArrayList<Point>();
        for (Point point:labyMap.keySet()) {
            if(!routage.containsKey(point)){
                inatteignables.add(point);
            }
        }
        return inatteignables;
    }
}