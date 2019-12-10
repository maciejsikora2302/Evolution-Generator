package mainPackage.unused;

import mainPackage.interfaces.IPositionChangeObserver;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundry implements IPositionChangeObserver {
    private SortedSet<Vector2d> elementsAtXAxis = new TreeSet<>(new comparatorX());
    private SortedSet<Vector2d> elementsAtYAxis = new TreeSet<>(new comparatorY());

    private class comparatorX implements Comparator<Vector2d> {
        @Override
        public int compare(Vector2d v1, Vector2d v2) {
            return v1.x != v2.x ? v1.x - v2.x : v1.y - v2.y;
        }
    }

    private class comparatorY implements Comparator<Vector2d> {
        @Override
        public int compare(Vector2d v1, Vector2d v2) {
            return v1.y != v2.y ? v1.y - v2.y : v1.x - v2.x;
        }
    }

//    public void place(IMapElement element) {
//        elementsAtXAxis.add(element.getPosition());
//        elementsAtYAxis.add(element.getPosition());
//        if(element instanceof Animal){
//            ((Animal) element).addObserver(this);
//        }
//    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        elementsAtXAxis.remove(oldPosition);
        elementsAtYAxis.remove(oldPosition);
        elementsAtXAxis.add(newPosition);
        elementsAtYAxis.add(newPosition);
    }

    public Vector2d getLowerLeft() {
        return elementsAtXAxis.first().lowerLeft(elementsAtYAxis.first());
    }

    public Vector2d getUpperRight() {
        return elementsAtXAxis.last().upperRight(elementsAtYAxis.last());
    }
}
