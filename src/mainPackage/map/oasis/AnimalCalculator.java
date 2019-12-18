package mainPackage.map.oasis;

import mainPackage.mapElement.animal.Animal;

import java.util.ArrayList;
import java.util.HashMap;


class AnimalCalculator {
    private final NextDayOperator nextDayOperator;

    AnimalCalculator(NextDayOperator nextDayOperator) {
        this.nextDayOperator = nextDayOperator;
    }

    void calculateGenotype() {

        HashMap<String, Integer> domGenes = new HashMap<String, Integer>();

        for (ArrayList<Animal> animalArrayList : nextDayOperator.getMap().animals.values()) {
            for (Animal animal : animalArrayList) {
                String result = animal.getGenotypeAsString();
                if (domGenes.get(result) == null) {
                    domGenes.put(result, 1);
                } else {
                    Integer val = domGenes.remove(result);
                    domGenes.put(result, val + 1);
                }
            }
        }

        nextDayOperator.getMap().dominatingGenotype = domGenes;
    }

    void calculateAverageOfAnimalsEnergy() {
        int sumOfEnergy = 0;
        int quantityOfAnimals = 0;
        for (ArrayList<Animal> animalArrayList : nextDayOperator.getMap().animals.values()) {
            for (Animal animal : animalArrayList) {
                sumOfEnergy += animal.getEnergy();
            }
            quantityOfAnimals += animalArrayList.size();
        }
        if (quantityOfAnimals == 0) quantityOfAnimals = 1;
        nextDayOperator.getMap().averageOdAnimalsEnergy = sumOfEnergy / quantityOfAnimals;
    }

    void makeAnimalsOneDayOlderAndCalculateTheirMedianLifespan(NextDayOperator nextDayOperator) {
        int sumOfAge = 0;
        int quantityOfAnimals = 0;
        for (ArrayList<Animal> animalArrayList : nextDayOperator.getMap().animals.values()) {
            for (Animal animal : animalArrayList) {
                animal.incrementAge();
                sumOfAge += animal.getAge();
            }
            quantityOfAnimals += animalArrayList.size();
        }
        if (quantityOfAnimals == 0) quantityOfAnimals = 1;
        nextDayOperator.getMap().averageOfAnimalsLifespan = sumOfAge / quantityOfAnimals;
    }
}