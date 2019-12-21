package mainPackage.mapElement;

import mainPackage.main.Vector2d;

public class Grass {
    private Vector2d Position;

    public Vector2d getPosition() {
        return Position;
    }

    public String toString() {
        return "* ";
    }

    public Grass(Vector2d position) {
        this.Position = position;
    }
}