package mainPackage.main;

import java.util.ArrayList;
import java.util.Arrays;
public class OasisParameters {
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
    private int skipToGivenDay;
    private int numberOfGrassThatGrowsPerDay;
    private double jungleRatio;
    private int mapWindowWidth;
    private int mapWindowHeight;
    private int statsWindowWidth;

    public int getMapWindowWidth() {
        return mapWindowWidth;
    }

    public int getMapWindowHeight() {
        return mapWindowHeight;
    }

    public int getStatsWindowWidth() {
        return statsWindowWidth;
    }

    public int getSkipToGivenDay() {
        return skipToGivenDay;
    }

    public int getNumberOfGrassThatGrowsPerDay() {
        return numberOfGrassThatGrowsPerDay;
    }

    public int getNumberOfStartingGrass() {
        return numberOfStartingGrass;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Integer> getStartingGenes() {
        return startingGenes;
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public double getJungleRatio() {
        return jungleRatio;
    }

    public int getNumberOfStartingAnimals() {
        return numberOfStartingAnimals;
    }
}
