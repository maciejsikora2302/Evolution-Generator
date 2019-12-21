package mainPackage.map.NextDayOperatorPackage;

import mainPackage.map.oasis.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;


class AnimalCalculator {
    private final NextDayOperator nextDayOperator;

    AnimalCalculator(NextDayOperator nextDayOperator) {
        this.nextDayOperator = nextDayOperator;
    }

    void calculateGenotype() {

        HashMap<String, Integer> domGenes = new HashMap<>();

        for (Tile tile : nextDayOperator.getMap().getAnimals().values()) {
            ArrayList<String> genotypes = tile.getGenotypesOfAllAnimalsOnTile();
            for (String genotype : genotypes) {
                if (domGenes.get(genotype) == null) {
                    domGenes.put(genotype, 1);
                } else {
                    Integer val = domGenes.remove(genotype);
                    domGenes.put(genotype, val + 1);
                }
            }
        }

        nextDayOperator.getMap().setDominatingGenotype(domGenes);
    }

    void calculateAverageOfAnimalsEnergy() {
        int sumOfEnergy = 0;
        int quantityOfAnimals = 0;
        for (Tile tile : this.nextDayOperator.getMap().getAnimals().values()) {
            sumOfEnergy += tile.getSumOfAnimalsEnergy();
            quantityOfAnimals += tile.getNumberOfAnimalsAtTile();
        }
        if (quantityOfAnimals == 0) quantityOfAnimals = 1;
        nextDayOperator.getMap().setAverageOdAnimalsEnergy(sumOfEnergy / quantityOfAnimals);
    }

    void makeAnimalsOneDayOlderAndCalculateTheirMedianLifespan(NextDayOperator nextDayOperator) {
        int sumOfAge = 0;
        int quantityOfAnimals = 0;
        for (Tile tile : nextDayOperator.getMap().getAnimals().values()) {
            sumOfAge += tile.incrementAgeOfEachAnimalAndGetSumOfTheirAge();
            quantityOfAnimals += tile.getNumberOfAnimalsAtTile();
        }
        if (quantityOfAnimals == 0) quantityOfAnimals = 1;
        nextDayOperator.getMap().setAverageOfAnimalsLifespan(sumOfAge / quantityOfAnimals);
    }
}