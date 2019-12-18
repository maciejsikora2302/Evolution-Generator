package mainPackage.mapElement;

import mainPackage.main.Vector2d;

public class Grass extends AbstractWorldMapElement {

    @Override
    public String toString() {
        return "* ";
    }

    public Grass(Vector2d position){
        this.Position = position;
    }
}