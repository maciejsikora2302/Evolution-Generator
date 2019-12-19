package mainPackage.map.oasis;

import mainPackage.mapElement.animal.Animal;

import java.util.ArrayList;
import java.util.HashMap;


class AnimalCalculator {
    private final NextDayOperator nextDayOperator;

    AnimalCalculator(NextDayOperator nextDayOperator) {
        this.nextDayOperator = nextDayOperator;
    }
    //todo calculateGenotype
    void calculateGenotype() {

        HashMap<String, Integer> domGenes = new HashMap<>();

        for (Tile tile : nextDayOperator.getMap().animals.values()) {
            ArrayList<String> genotypes = tile.getGenotypesOfAllAnimalsOnTile();
            for (String genotype: genotypes) {
                if (domGenes.get(genotype) == null) {
                    domGenes.put(genotype, 1);
                } else {
                    Integer val = domGenes.remove(genotype);
                    domGenes.put(genotype, val + 1);
                }
            }
        }

        nextDayOperator.getMap().dominatingGenotype = domGenes;
    }

    void calculateAverageOfAnimalsEnergy() {
        int sumOfEnergy = 0;
        int quantityOfAnimals = 0;
        for (Tile tile: this.nextDayOperator.getMap().animals.values()) {
            sumOfEnergy += tile.getSumOfAnimalsEnergy();
            quantityOfAnimals += tile.getNumberOfAnimalsAtTile();
        }
        if (quantityOfAnimals == 0) quantityOfAnimals = 1;
        nextDayOperator.getMap().averageOdAnimalsEnergy = sumOfEnergy / quantityOfAnimals;
    }

    void makeAnimalsOneDayOlderAndCalculateTheirMedianLifespan(NextDayOperator nextDayOperator) {
        int sumOfAge = 0;
        int quantityOfAnimals = 0;
        for (Tile tile : nextDayOperator.getMap().animals.values()) {
            sumOfAge += tile.incrementAgeOfEachAnimalAndGetSumOfTheirAge();
            quantityOfAnimals += tile.getNumberOfAnimalsAtTile();
        }
        if (quantityOfAnimals == 0) quantityOfAnimals = 1;
        nextDayOperator.getMap().averageOfAnimalsLifespan = sumOfAge / quantityOfAnimals;
    }
}