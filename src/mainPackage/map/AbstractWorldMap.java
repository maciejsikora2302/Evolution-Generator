package mainPackage.map;

import mainPackage.interfaces.IPositionChangeObserver;
import mainPackage.interfaces.IWorldMap;
import mainPackage.mapElement.animal.Animal;
import mainPackage.moveAndDirection.MoveDirection;
import mainPackage.unused.Vector2d;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    //HashMap<Vector2d, Animal> animals = new HashMap<>();
    public HashMap<Vector2d, ArrayList<Animal>> animals = new HashMap<>();

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
//        Animal animal = animals.get(oldPosition);
//        ArrayList<Animal> animalsAtPosition = animals.get(oldPosition);
//        animalsAtPosition.remove(oldPosition);
//        animals.put(newPosition, animal);
    }

    public boolean place(Animal animal) {
        if (!isOccupied(animal.getPosition())) {
            ArrayList<Animal> tmp = new ArrayList<>();
            tmp.add(animal);
            animals.put(animal.getPosition(), tmp);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.get(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return animals.get(position);
    }

    public void run(MoveDirection[] directions) {
        int i = 0, numberOfAnimals = animals.size();
        //ArrayList<Animal> animalsArray = new ArrayList<>(animals.values());
        for (MoveDirection direction : directions) {
//            System.out.println(direction);
//            System.out.println(this.toString());
//            Vector2d oldPosition = animalsArray.get(i).getPosition();
//            animalsArray.get(i).move(direction);
//            if(!animalsArray.get(i).getPosition().equals(oldPosition)){
//                animals.remove(oldPosition);
//                animals.put(animalsArray.get(i).getPosition(),animalsArray.get(i));
//            }

            i += 1;
            if (i == numberOfAnimals) i = 0;
        }
    }
}
