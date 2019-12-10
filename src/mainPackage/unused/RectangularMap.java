package mainPackage.unused;

import mainPackage.map.AbstractWorldMap;
import mainPackage.map.MapVisualizer;

public class RectangularMap extends AbstractWorldMap {
    private int width;
    private int height;
//    private List<Animal> animals = new ArrayList<>();


    public RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (!isOccupied(position))
            return position.x < this.width && position.x >= 0
                    &&
                    position.y < this.height && position.y >= 0;
        return false;
    }



    @Override
    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(
                new Vector2d(0, 0),
                new Vector2d(this.width, this.height));
    }
}
