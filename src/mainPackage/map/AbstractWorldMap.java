package mainPackage.map;

import mainPackage.interfaces.IPositionChangeObserver;
import mainPackage.interfaces.IWorldMap;
import mainPackage.mapElement.animal.Animal;
import mainPackage.moveAndDirection.MoveDirection;
import mainPackage.unused.Vector2d;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap{
    public HashMap<Vector2d, ArrayList<Animal>> animals = new HashMap<>();

    public boolean place(Animal animal) {
        if (!isOccupied(animal.getPosition())) {
            ArrayList<Animal> tmp = new ArrayList<>();
            tmp.add(animal);
            animals.put(animal.getPosition(), tmp);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.get(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return animals.get(position);
    }

}
