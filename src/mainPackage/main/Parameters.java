package mainPackage.main;

import java.util.ArrayList;
import java.util.Arrays;
class oasisParameters {
    @Override
    public String toString() {
        return "Parameters{" +
                "width=" + width +
                ", height=" + height +
                ", startingGenes=" + startingGenes +
                ", startEnergy=" + startEnergy +
                ", moveEnergy=" + moveEnergy +
                ", plantEnergy=" + plantEnergy +
                ", jungleRatio=" + jungleRatio +
                '}';
    }

    private int width;
    private int height;
    private ArrayList<Integer> startingGenes;
    private int startEnergy;
    private int moveEnergy;
    private int plantEnergy;
    private int numberOfStartingAnimals;
    private int numberOfStartingGrass;

    public int getNumberOfGrassThatGrowsPerDay() {
        return numberOfGrassThatGrowsPerDay;
    }

    private int numberOfGrassThatGrowsPerDay;

    public int getNumberOfStartingGrass() {
        return numberOfStartingGrass;
    }

    private double jungleRatio;

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    ArrayList<Integer> getStartingGenes() {
        return startingGenes;
    }

    int getStartEnergy() {
        return startEnergy;
    }

    int getMoveEnergy() {
        return moveEnergy;
    }

    int getPlantEnergy() {
        return plantEnergy;
    }

    double getJungleRatio() {
        return jungleRatio;
    }

    public int getNumberOfStartingAnimals() {
        return numberOfStartingAnimals;
    }
}
