package mainPackage;

public interface IWorldMap {
    boolean canMoveTo(Vector2d position);
    boolean place(Animal animal);
    void run(MoveDirection[] directions);
    boolean isOccupied(Vector2d position);
    Object objectAt(Vector2d position);
}
