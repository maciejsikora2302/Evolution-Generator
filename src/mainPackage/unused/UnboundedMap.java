package mainPackage.unused;

import mainPackage.map.MapVisualizer;
import mainPackage.mapElement.animal.Animal;
import mainPackage.map.AbstractWorldMap;

import java.util.List;

public class UnboundedMap extends AbstractWorldMap {
    private int xMax=0,xMin=0,yMax=0,yMin=0;
    private List<Rock> rocks;
    private MapBoundry boundry = new MapBoundry();

    public UnboundedMap(List<Rock> rocks){
        this.rocks = rocks;
        boolean firstIteration = true;
        for(Rock rock: rocks){
            if(firstIteration){
                this.xMax = rock.getPosition().x;
                this.xMin = rock.getPosition().x;
                this.yMax = rock.getPosition().y;
                this.yMin = rock.getPosition().y;
                firstIteration = false;
            }else{
                if(rock.getPosition().x > this.xMax) this.xMax = rock.getPosition().x;
                if(rock.getPosition().x < this.xMin) this.xMin = rock.getPosition().x;
                if(rock.getPosition().y > this.yMax) this.yMax = rock.getPosition().y;
                if(rock.getPosition().y < this.yMin) this.yMin = rock.getPosition().y;
            }
//            boundry.place(rock);
        }
    }


    @Override
    public boolean place(Animal animal) {
        if (!isOccupied(animal.getPosition())) {
            if(animal.getPosition().x > this.xMax) this.xMax = animal.getPosition().x;
            if(animal.getPosition().x < this.xMin) this.xMin = animal.getPosition().x;
            if(animal.getPosition().y > this.yMax) this.yMax = animal.getPosition().y;
            if(animal.getPosition().y < this.yMin) this.yMin = animal.getPosition().y;
//            boundry.place(animal);
            //animals.put(animal.getPosition(),animal);
            return true;
        }
        throw new IllegalArgumentException("Position: " + animal.getPosition().toString() + " is already occupied.");
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for(Rock rock: rocks){
            if(rock.getPosition().equals(position))
                return true;
        }
        return super.isOccupied(position);
    }

    @Override
    public Object objectAt(Vector2d position) {
        for(Rock rock: rocks){
            if(rock.getPosition().equals(position)){
                return rock;
            }
        }
        return super.objectAt(position);
    }

    @Override
    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(
                new Vector2d(this.xMin, this.yMin),
                new Vector2d(this.xMax, this.yMax));
    }
}
