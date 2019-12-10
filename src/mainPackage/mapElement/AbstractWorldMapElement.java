package mainPackage.mapElement;

import mainPackage.unused.Vector2d;
import mainPackage.interfaces.IMapElement;

public abstract class AbstractWorldMapElement implements IMapElement {
    public Vector2d Position;
    public Vector2d getPosition(){ return this.Position;}
}
