package mainPackage.interfaces;

import mainPackage.moveAndDirection.MoveDirection;
import mainPackage.unused.Vector2d;
import mainPackage.mapElement.animal.Animal;

public interface IWorldMap {
    boolean canMoveTo(Vector2d position);
    boolean place(Animal animal);
    void run(MoveDirection[] directions);
    boolean isOccupied(Vector2d position);
    Object objectAt(Vector2d position);
}
