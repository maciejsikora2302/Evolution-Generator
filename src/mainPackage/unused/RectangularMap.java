package mainPackage.unused;

import mainPackage.map.AbstractWorldMap;
import mainPackage.map.MapVisualizer;
import mainPackage.main.Vector2d;

public class RectangularMap extends AbstractWorldMap {
    private int width;
    private int height;
//    private List<Animal> animals = new ArrayList<>();


    public RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;
    }


    @Override
    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(
                new Vector2d(0, 0),
                new Vector2d(this.width, this.height));
    }
}
