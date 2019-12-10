package mainPackage.unused;

import mainPackage.mapElement.AbstractWorldMapElement;

public class Rock extends AbstractWorldMapElement {
    public Rock(Vector2d position){
        this.Position = position;
    }
    public String toString(){
        return "s";
    }
}
