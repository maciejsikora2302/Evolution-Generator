package mainPackage.mapElement;

import mainPackage.interfaces.IMapElement;
import mainPackage.main.Vector2d;

public abstract class AbstractWorldMapElement implements IMapElement {
    public Vector2d Position;
    public Vector2d getPosition(){ return this.Position;}
}
