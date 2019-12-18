package mainPackage.interfaces;

import mainPackage.moveAndDirection.MoveDirection;
import mainPackage.unused.Vector2d;
import mainPackage.mapElement.animal.Animal;

public interface IWorldMap {
    boolean place(Animal animal);

    boolean isOccupied(Vector2d position);
    Object objectAt(Vector2d position);
}
