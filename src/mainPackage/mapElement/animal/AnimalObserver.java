package mainPackage.mapElement.animal;

import java.util.HashSet;

public class AnimalObserver {
    private HashSet<Animal> descendants = new HashSet<>();
    public static Animal chosenAnimal;

    public AnimalObserver(Animal animal) {
        this.pointAtChosenAnimal(animal);
    }

    public void addDescendant(Animal animal) {
        this.descendants.add(animal);
    }

    public int getNumberOfDescendants() {
        return this.descendants.size();
    }

    public int getNumberOfChildren() {
        return chosenAnimal.getNumberOfChildren();
    }

    public void pointAtChosenAnimal(Animal animal) {
        chosenAnimal = animal;
        chosenAnimal.attachObserver(this);
    }

    public void detachObserverFromAnimals() {
        for (Animal animal : descendants) {
            animal.removeObserver();
        }
        chosenAnimal.removeObserver();
    }
}
