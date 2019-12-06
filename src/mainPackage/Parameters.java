package mainPackage;

import java.util.ArrayList;
import java.util.Arrays;

public class Parameters {
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
    private double jungleRatio;

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

}
