package mainPackage.map.oasis;

import mainPackage.mapElement.Grass;
import mainPackage.mapElement.animal.Animal;
import mainPackage.main.Vector2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TilePlaceholder {
    ArrayList<Animal> animals;
    Grass grass;
    Vector2d position;

    public TilePlaceholder(Vector2d position, Animal animal){
        this.position = position;
        this.animals = new ArrayList<>();
        this.addAnimal(animal);
    }

    public void addGrass(Grass grass){
        this.grass = grass;
    }

    public void addAnimal(Animal animal){
        this.animals.add(animal);
        this.animals.sort(new SortByEnergy());
    }

    public void removeAnimal(Animal animal){
        this.animals.remove(animal);
        this.animals.sort(new SortByEnergy());
    }

    public int getNumberOfAnimalsAtTile(){
        return this.animals.size();
    }

    static class SortByEnergy implements Comparator<Animal>{

        @Override
        public int compare(Animal animal, Animal t1) {
            return t1.getEnergy() - animal.getEnergy();
        }
    }
}

