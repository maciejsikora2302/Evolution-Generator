package mainPackage.map.oasis;

import mainPackage.mapElement.Grass;
import mainPackage.mapElement.animal.Animal;
import mainPackage.main.Vector2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Tile {
    private ArrayList<Animal> animals;

    public Tile(Animal animal) {
        this.animals = new ArrayList<>();
        this.addAnimal(animal);
    }

    public void addAnimal(Animal animal) {
        this.animals.add(animal);
        this.animals.sort(new SortByEnergy());
    }

    public void removeAnimal(Animal animal) {
        this.animals.remove(animal);
        if (this.animals.size() == 0) return;
        this.animals.sort(new SortByEnergy());
    }

    public ArrayList<String > removeAnimalsWithNoEnergyAndGetTheirGenotypes() {
        ArrayList<String> genotypes = new ArrayList<>();
        for (int i = animals.size() - 1; i >= 0; i--) {
            if (animals.get(i).getEnergy() <= 0) {
                genotypes.add(animals.remove(i).getGenotypeAsString());
            } else {
                break;
            }
        }
        return genotypes;
    }

    public ArrayList<String> getGenotypesOfAllAnimalsOnTile(){
        ArrayList<String> genotypes = new ArrayList<>();
        for(Animal animal:animals){
            genotypes.add(animal.getGenotypeAsString());
        }
        return genotypes;
    }

    public void turnAllAnimalsAndDecreaseTheirEnergy(int moveEnergy) {
        for (Animal animal : animals) {
            animal.turnAccordingToGene();
            animal.decreaseEnergyByMoveValue(moveEnergy);
        }
    }

    public void moveAllAnimals() {
        for (int i = 0; i < animals.size(); i++) {
            animals.get(i).move();
        }
    }

    public void animalsWithHighestEnergyEat() {
        int numberOfAnimalsWithHighestEnergy = 1;
        for (int i = 1; i < animals.size(); i++) {
            if(animals.get(i).getEnergy() == animals.get(i-1).getEnergy()){
                numberOfAnimalsWithHighestEnergy++;
            }
        }
        for(int i=0; i<numberOfAnimalsWithHighestEnergy;i++){
            animals.get(i).eat(numberOfAnimalsWithHighestEnergy);
        }
    }

    public Animal getAnimalWithHighestEnergy(){
        return animals.get(0);
    }

    public Animal getAnimalWithSecondHighestEnergy(){
        if(animals.size() > 1){
            return animals.get(1);
        }else{
            System.out.println("I just tried to return animal with second highest energy but there was only one animal");
            return null;
        }
    }

    public Animal getAnimalWithLowestEnergy(){
        return this.animals.get(this.animals.size()-1);
    }

    public int getNumberOfAnimalsAtTile() {
        return this.animals.size();
    }

    public int getSumOfAnimalsEnergy(){
        int sum = 0;
        for(Animal animal:animals){
            sum+=animal.getEnergy();
        }
        return sum;
    }

    public int getSumOfAnimalChildren(){
        int numberOfChildren = 0;
        for(Animal animal:animals){
            numberOfChildren += animal.getNumberOfChildren();
        }
        return numberOfChildren;
    }

    public int incrementAgeOfEachAnimalAndGetSumOfTheirAge(){
        int sum = 0;
        for(Animal animal:animals){
            animal.incrementAge();
            sum += animal.getAge();
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("I'm containing: ");
        for(int i=0;i<animals.size();i++){
            result.append(animals.get(i).getEnergy());
            result.append(" ");
        }
        return result.toString();
    }

    static class SortByEnergy implements Comparator<Animal> {

        @Override
        public int compare(Animal animal, Animal t1) {
            return t1.getEnergy() - animal.getEnergy();
        }
    }
}

