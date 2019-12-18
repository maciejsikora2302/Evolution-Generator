package mainPackage.interfaces;

import mainPackage.main.Vector2d;
import mainPackage.mapElement.animal.Animal;

public interface IWorldMap {
    boolean place(Animal animal);

    boolean isOccupied(Vector2d position);
    Object objectAt(Vector2d position);
}
