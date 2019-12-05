package mainPackage;

public abstract class AbstractWorldMapElement implements IMapElement {
    protected Vector2d Position;
    public Vector2d getPosition(){ return this.Position;}
}
