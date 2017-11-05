package Oka.controler;

import Oka.entities.Entity;
import Oka.model.*;
import Oka.model.plot.Plot;
import Oka.utils.Logger;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GameBoard {
    //region==========ATTRIBUTES===========
    private static GameBoard ourInstance = new GameBoard();
    private HashMap<Point, Cell> grid = new HashMap<>();
    private ArrayList<Point> availableSlots = new ArrayList<>();

    private Set<Irrigation> irrigation = new HashSet<>();
    //endregion

    //region==========CONSTRUCTORS=========
    private GameBoard() {
        availableSlots.add(new Point());

        addCell(new Pond());
    }
    //endregion

    //region==========GETTER/SETTER========
    public static GameBoard getInstance() {
        return ourInstance;
    }

    public HashMap<Point, Cell> getGrid() {
        return grid;
    }

    public void setGrid(HashMap<Point, Cell> grid) {
        this.grid = grid;
    }

    /**
     * should return all the possible slots where a tile may be layed
     * todo : implement
     *
     * @return ArrayList
     */
    public ArrayList<Point> getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(ArrayList<Point> availableSlots) {
        this.availableSlots = availableSlots;
    }

    public Set<Irrigation> getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(Set<Irrigation> irrigation) {
        this.irrigation = irrigation;
    }
    //endregion

    //region==========METHODS==============
    public void addCell(Cell cell) {
        assertNotOnGrid(cell.getCoords());
        assertIsAvailable(cell.getCoords());

        grid.put(cell.getCoords(), cell);
        availableSlots.remove(cell.getCoords());

        refreshAvailableSlots(cell);
    }

    public void addIrrigation(Point point1, Point point2) throws InvalidArgumentException {
        if (!canPlaceIrigation(point1, point2))
            throw new InvalidArgumentException(new String[]{"The two specified points must be a valid position for a channel"});
        if (point1.equals(new Point()) || point2.equals(new Point())) return;


        Irrigation irg = new Irrigation(point1, point2);

        if (this.irrigation.contains(irg)) Logger.printLine("Déjà irrigué");

        else this.irrigation.add(irg);

        if (grid.get(point1) instanceof Plot) {
            ((Plot) grid.get(point1)).setIsIrrigated(true);
        }
        if (grid.get(point2) instanceof Plot) {
            ((Plot) grid.get(point2)).setIsIrrigated(true);
        }


    }

    public boolean canPlaceIrigation(Point point1, Point point2) {
        if (verifIrrigation(point1, point2)) return false;
        ArrayList<Cell> neightBoors = getCommonNeighboors(point1, point2);
        Boolean can = neightBoors.contains(grid.get(new Point()));

        for (Cell neightboor :
                neightBoors) {
            can |= verifIrrigation(neightboor.getCoords(), point1);
            can |= verifIrrigation(neightboor.getCoords(), point2);
        }
        return can;
    }

    public boolean verifIrrigation(Point point1, Point point2) {
        if (point1.equals(new Point()) || point2.equals(new Point())) return false;

        Irrigation irg = new Irrigation(point1, point2);

        return this.irrigation.contains(irg);

    }

    public ArrayList<Point> getEveryNeighboors(Point point) {
        Vector[] vectors = new Vector[6];
        vectors[0] = new Vector(Enums.Axis.x, 1);
        vectors[1] = new Vector(Enums.Axis.x, -1);
        vectors[2] = new Vector(Enums.Axis.y, 1);
        vectors[3] = new Vector(Enums.Axis.y, -1);
        vectors[4] = new Vector(Enums.Axis.z, 1);
        vectors[5] = new Vector(Enums.Axis.z, -1);

        ArrayList<Point> neightbours = new ArrayList<>();

        for (Vector v : vectors) {
            neightbours.add(v.applyVector(point));
        }

        return neightbours;
    }

    public ArrayList<Cell> getExistingNeighboors(Point point) {
        Vector[] vectors = new Vector[6];
        vectors[0] = new Vector(Enums.Axis.x, 1);
        vectors[1] = new Vector(Enums.Axis.x, -1);
        vectors[2] = new Vector(Enums.Axis.y, 1);
        vectors[3] = new Vector(Enums.Axis.y, -1);
        vectors[4] = new Vector(Enums.Axis.z, 1);
        vectors[5] = new Vector(Enums.Axis.z, -1);

        ArrayList<Cell> neightbours = new ArrayList<>();

        for (Vector v : vectors) {
            if (grid.containsKey(v.applyVector(point))) neightbours.add(grid.get(v.applyVector(point)));
        }

        return neightbours;
    }

    public ArrayList<Cell> getCommonNeighboors(Point point1, Point point2) {
        ArrayList<Cell> neighboorspoint1 = getExistingNeighboors(point1);
        ArrayList<Cell> neighboorspoint2 = getExistingNeighboors(point2);
        ArrayList<Cell> neighboorsintersection = new ArrayList<>();
        for (Cell cell : neighboorspoint1) {
            if (neighboorspoint2.contains(cell)) {
                neighboorsintersection.add(cell);
            }
        }
        return neighboorsintersection;
    }

    public boolean isConnected(Point point1, Point point2, ArrayList<Point[]> visited) {

        if (verifIrrigation(point1, point2)) {
            visited.add(new Point[]{point1, point2});
            visited.add(new Point[]{point2, point1});
        } else {
            return false;
        }
        ArrayList<Cell> commonNeighboors = getCommonNeighboors(point1, point2);
        Point n1 = commonNeighboors.size() > 0 ? commonNeighboors.get(0).getCoords() : null;
        Point n2 = commonNeighboors.size() > 1 ? commonNeighboors.get(1).getCoords() : null;
        int pd1 = distanceToPond(point1);
        int pd2 = distanceToPond(point2);
        int pdn1 = n1 != null ? distanceToPond(n1) : 0;
        int pdn2 = n2 != null ? distanceToPond(n2) : 0;

        ArrayList<Point[]> tab2 = new ArrayList<>();

        if (visited.contains(new Point[]{n1, n2}))
            return false;

        if (n1 != null && n1.equals(new Point())) return true;

        if (n2 != null && n2.equals(new Point())) return true;

        if (pd1 > pd2) {
            if (pdn1 > pdn2) {
                if (n1 != null) {
                    tab2.add(0, new Point[]{point1, n1});
                    tab2.add(2, new Point[]{point2, n1});
                }
                if (n2 != null) {
                    tab2.add(1, new Point[]{point1, n2});
                    tab2.add(3, new Point[]{point2, n2});
                }
             /*   tab[0] = new Point[]{point1, n1};
                tab[1] = new Point[]{point1, n2};
                tab[2] = new Point[]{point2, n1};
                tab[3] = new Point[]{point2, n2}; */
            } else {
                if (n2 != null) {
                    tab2.add(0, new Point[]{point1, n2});
                    tab2.add(2, new Point[]{point2, n2});
                }
                if (n1 != null) {
                    tab2.add(1, new Point[]{point1, n1});
                    tab2.add(3, new Point[]{point2, n1});
                }
               /* tab[0] = new Point[]{point1, n2};
                tab[1] = new Point[]{point1, n1};
                tab[2] = new Point[]{point2, n2};
                tab[3] = new Point[]{point2, n1}; */
            }
        } else {
            if (pdn1 > pdn2) {
                if (n1 != null) {
                    tab2.add(0, new Point[]{point2, n1});
                    tab2.add(2, new Point[]{point1, n1});
                }
                if (n2 != null) {
                    tab2.add(1, new Point[]{point2, n2});
                    tab2.add(3, new Point[]{point1, n2});
                }
             /*   tab[0] = new Point[]{point2, n1};
                tab[1] = new Point[]{point2, n2};
                tab[2] = new Point[]{point1, n1};
                tab[3] = new Point[]{point1, n2};*/
            } else {
                if (n2 != null) {
                    tab2.add(0, new Point[]{point2, n2});
                    tab2.add(2, new Point[]{point1, n2});
                }
                if (n1 != null) {
                    tab2.add(1, new Point[]{point2, n1});
                    tab2.add(3, new Point[]{point1, n1});
                }
              /*  tab[0] = new Point[]{point2, n2};
                tab[1] = new Point[]{point2, n1};
                tab[2] = new Point[]{point1, n2};
                tab[3] = new Point[]{point1, n1};*/
            }
        }

        for (Point[] minitab : tab2) {
            if (isConnected(minitab[0], minitab[1], visited)) {
                return true;
            }

        }

        return false;
    }

    int distanceToPond(Point point1) {
        int z = -point1.x - point1.y;
        return Math.max(Math.abs(point1.x), Math.max(Math.abs(point1.y), Math.abs(z)));
    }


    private void assertNotOnGrid(Point point) {
        if (point == null) throw new IllegalArgumentException("Point is null");
        if (grid.containsKey(point)) throw new IllegalArgumentException("The cell is already on the grid");
    }

    private void assertIsAvailable(Point point) throws IllegalArgumentException {
        if (point == null) throw new IllegalArgumentException("Point is null");
        if (!availableSlots.contains(point)) throw new IllegalArgumentException("The slot is not available");
    }

    private void refreshAvailableSlots(Cell cell) {
        for (Point point : getEveryNeighboors(cell.getCoords())) {
            if (!grid.containsKey(point) && !availableSlots.contains(point)) availableSlots.add(point);
        }
    }

    public boolean moveEntity(Entity entity, Point point) {
        if (entity == null) throw new IllegalArgumentException("Entity is null");
        if (point == null) throw new IllegalArgumentException("Point is null");
        if (!grid.containsKey(point)) throw new IllegalArgumentException("The cell is not on the grid");

        return Vector.areAligned(entity.getCoords(), point) && entity.move(point);
    }

    /**
     * Computes the available irigation slots
     *
     * @return Set of available irrigations
     */
    public Set<Irrigation> getAvailableChannelSlots() {

        Set<Irrigation> availableIrrigations = new HashSet<Irrigation>();

        ArrayList<Cell> neightboors;

        neightboors = getExistingNeighboors(new Point());

        neightboors.forEach(cell -> {
            ArrayList<Cell> touching = getCommonNeighboors(cell.getCoords(), new Point());
            touching.stream().filter(t -> !verifIrrigation(cell.getCoords(), t.getCoords())).forEach(t -> availableIrrigations.add(new Irrigation(t.getCoords(), cell.getCoords())));
        });

        for (Irrigation irg :
                irrigation) {

            neightboors = getCommonNeighboors(irg.getPlot1().getCoords(), irg.getPlot2().getCoords());

            neightboors.stream()
                    .filter(cell -> !cell.getCoords().equals(new Point()))
                    .forEach(plot -> {

                        if (!verifIrrigation(plot.getCoords(), irg.getPlot1().getCoords()))
                            availableIrrigations.add(new Irrigation(plot.getCoords(), irg.getPlot1().getCoords()));

                        if (!verifIrrigation(plot.getCoords(), irg.getPlot2().getCoords()))
                            availableIrrigations.add(new Irrigation(plot.getCoords(), irg.getPlot2().getCoords()));

                    });
        }
        availableIrrigations.removeAll(irrigation);
        return availableIrrigations;
    }
    //endregion
}
