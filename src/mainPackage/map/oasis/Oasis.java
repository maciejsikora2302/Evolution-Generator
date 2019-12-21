package mainPackage.map.oasis;

import mainPackage.map.NextDayOperatorPackage.NextDayOperator;
import mainPackage.map.oasis.tile.Tile;
import mainPackage.mapElement.animal.Animal;
import mainPackage.mapElement.Grass;
import mainPackage.main.Vector2d;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Oasis {
    private final int plantEnergy;
    final int startAnimalEnergy;
    final int numberOfGrassThatGrowsPerDay;
    int averageOdAnimalsEnergy;
    int averageOfAnimalsLifespan;
    HashMap<Vector2d, Tile> animals = new HashMap<>();
    HashMap<Vector2d, Grass> grasses = new HashMap<>();
    HashMap<String, Integer> dominatingGenotype = new HashMap<>();
    private HashMap<Vector2d, Vector2d> innerOasisPositionHashMap = new HashMap<>();
    private ArrayList<Vector2d> innerOasisPositionList;
    private List<Integer> indexList;
    private final int width;
    private final int height;
    private final double jungleRatio;
    private final NextDayOperator nextDayOperator;
    private int day = 0;

    public void setAverageOdAnimalsEnergy(int averageOdAnimalsEnergy) {
        this.averageOdAnimalsEnergy = averageOdAnimalsEnergy;
    }

    public void setAverageOfAnimalsLifespan(int averageOfAnimalsLifespan) {
        this.averageOfAnimalsLifespan = averageOfAnimalsLifespan;
    }

    public void setAnimals(HashMap<Vector2d, Tile> animals) {
        this.animals = animals;
    }

    public void setGrasses(HashMap<Vector2d, Grass> grasses) {
        this.grasses = grasses;
    }

    public void setDominatingGenotype(HashMap<String, Integer> dominatingGenotype) {
        this.dominatingGenotype = dominatingGenotype;
    }

    public void addGenotypeToHashMap(String genotype) {
        dominatingGenotype.merge(genotype, 1, Integer::sum);
    }

    public void removeGenotypeFromHashMap(String genotype) {
        Integer numberOfSameGenotype = dominatingGenotype.remove(genotype);
        if (numberOfSameGenotype == null) {
            System.out.println("Tried to remove genotype that didn't exist");
        } else if (numberOfSameGenotype == 1) {
            return;
        } else {
            dominatingGenotype.put(genotype, numberOfSameGenotype - 1);
        }

    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public void nextDay() {
        this.nextDayOperator.makeNextDayHappen();
        this.incrementDay();
    }

    public int getDay() {
        return this.day;
    }

    public HashMap<Vector2d, Vector2d> getInnerOasisPositionHashMap() {
        return innerOasisPositionHashMap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2d wrapPosition(@NotNull Vector2d position) {
        if (position.x < 0) {
            position = new Vector2d(this.width, position.y);
        }
        if (position.x > this.width) {
            position = new Vector2d(0, position.y);
        }
        if (position.y < 0) {
            position = new Vector2d(position.x, this.height);
        }
        if (position.y > this.height) {
            position = new Vector2d(position.x, 0);
        }

        return position;
    }

    public void removeAnimalFromGivenPosition(Vector2d position, Animal animal) {
        Tile tile = animals.get(position);
        if (tile == null) {
            System.out.println("Something tried to remove Animals from position where it didn't existed");
            return;
        }
        if (tile.getNumberOfAnimalsAtTile() > 0) {
            tile.removeAnimal(animal);
        }
        if (tile.getNumberOfAnimalsAtTile() == 0) {
            animals.remove(position);
        }
    }

    public void moveAnimalToGivenPosition(Vector2d position, Animal animal) {
        if (animals.get(position) == null) {
            Tile tile = new Tile(animal);
            animals.put(position, tile);
        } else {
            animals.get(position).addAnimal(animal);
        }
    }

    public int getNumberOfAnimalsAtMap() {
        int numberOfAnimals = 0;
        for (Tile tile : animals.values()) {
            numberOfAnimals += tile.getNumberOfAnimalsAtTile();
        }
        return numberOfAnimals;
    }

    public float getAverageOfNumberOfChildren() {
        int sumOfChildren = 0;
        for (Tile tile : animals.values()) {
            sumOfChildren += tile.getSumOfAnimalChildren();
        }
        return ((float) sumOfChildren / this.getNumberOfAnimalsAtMap());
    }

    public int getNumberOfGrassAtMap() {
        return this.grasses.size();
    }

    public String getMostCommonGenotype() {
        String genotype = "brak";
        Integer maxNumber = 0;
        for (String gene : dominatingGenotype.keySet()) {
            Integer val = dominatingGenotype.get(gene);
            if (val > maxNumber) {
                genotype = gene;
                maxNumber = val;
            }
        }
        return genotype;
    }

    public int getMostCommonGenotypeQuantity() {
        Integer maxNumber = 0;
        for (Integer val : dominatingGenotype.values()) {
            if (val > maxNumber) {
                maxNumber = val;
            }
        }
        return maxNumber;
    }

    public int getAverageOdAnimalsEnergy() {
        return this.averageOdAnimalsEnergy;
    }

    public int getAverageOfAnimalsLifespan() {
        return this.averageOfAnimalsLifespan;
    }

    public void placeAnimal(@NotNull Animal animal) {
        if (!isOccupied(animal.getPosition())) {
            Tile tile = new Tile(animal);
            this.animals.put(animal.getPosition(), tile);
        }
    }

    public void removeAnimalsWithNoEnergyAtGivenPosition(Vector2d position) {
        Tile tile = animals.get(position);
        tile.removeAnimalsWithNoEnergyAndGetTheirGenotypes();
        if (tile.getNumberOfAnimalsAtTile() == 0) {
            animals.remove(position);
        }
    }

    public void addGrassInTheOasis() {
        for (Integer potentialPositionIndex : this.indexList) {
            if (!isOccupied(this.innerOasisPositionHashMap.get(this.innerOasisPositionList.get(potentialPositionIndex)))) {
                this.placeGrass(new Grass(this.innerOasisPositionHashMap.get(this.innerOasisPositionList.get(potentialPositionIndex))));
                break;
            }
        }
        if (day % 500 == 0) {
            Collections.shuffle(this.getInnerOasisPositionList());
        }
    }

    public void addGrassOutsideOfTheOasis() {
        boolean grassCanBePlaced = false;
        int i = 0;
        while (!grassCanBePlaced) {
            int x = new Random().nextInt(this.width + 1);
            int y = new Random().nextInt(this.height + 1);
            Vector2d position = new Vector2d(x, y);
            if (!this.innerOasisPositionHashMap.containsKey(position)) {
                grassCanBePlaced = true;
                this.placeGrass(new Grass(position));
            }
            if (i == 1000) {
                System.out.println("Grass could not be placed to do too much of grasses already being on map");
                break;
            }
            i++;
        }
    }

    public boolean doesInnerOasisExistsAtGivenPosition(Vector2d position) {
        return innerOasisPositionHashMap.containsKey(position);
    }

    private void placeGrass(@NotNull Grass grass) {
        if (!isOccupied(grass.getPosition())) {
            grasses.put(grass.getPosition(), grass);
        }
    }

    private void incrementDay() {
        this.day++;
    }

    public Object objectAt(Vector2d position) {
        if (grasses.get(position) != null) return grasses.get(position);
        return animals.get(position);
    }

    public boolean isOccupied(Vector2d position) {
        if (grasses.get(position) != null) return true;
        return animals.get(position) != null;
    }

    public Oasis(int width, int height, int plantEnergy, int maxAnimalEnergy, int moveEnergy,
                 double jungleRatio, int numberOnInitialAnimals, int numberOfInitialGrass,
                 ArrayList<Integer> startingGenes, int numberOfGrassThatGrowsPerDay) {
        if (width > height) {
            this.width = width;
            this.height = height;
        } else {
            this.height = width;
            this.width = height;
        }

        this.plantEnergy = plantEnergy;
        this.startAnimalEnergy = maxAnimalEnergy;
        this.nextDayOperator = new NextDayOperator(this, moveEnergy);
        this.jungleRatio = jungleRatio;
        this.numberOfGrassThatGrowsPerDay = numberOfGrassThatGrowsPerDay;
        OasisInitialGenerator oasisInitialGenerator = new OasisInitialGenerator(this);
        oasisInitialGenerator.generateZones();
        oasisInitialGenerator.placeInitialAnimals(numberOnInitialAnimals, startingGenes);
        oasisInitialGenerator.placeInitialGrass(numberOfInitialGrass);
        this.averageOdAnimalsEnergy = maxAnimalEnergy;
    }

    public NextDayOperator getNextDayOperator() {
        return nextDayOperator;
    }

    public double getJungleRatio() {
        return jungleRatio;
    }

    public ArrayList<Vector2d> getInnerOasisPositionList() {
        return innerOasisPositionList;
    }

    public List<Integer> getIndexList() {
        return indexList;
    }

    public int getStartAnimalEnergy() {
        return startAnimalEnergy;
    }

    public void setInnerOasisPositionList(ArrayList<Vector2d> innerOasisPositionList) {
        this.innerOasisPositionList = innerOasisPositionList;
    }

    public void setIndexList(List<Integer> indexList) {
        this.indexList = indexList;
    }

    public int getNumberOfGrassThatGrowsPerDay() {
        return numberOfGrassThatGrowsPerDay;
    }

    public HashMap<Vector2d, Tile> getAnimals() {
        return animals;
    }

    public HashMap<Vector2d, Grass> getGrasses() {
        return grasses;
    }

    public HashMap<String, Integer> getDominatingGenotype() {
        return dominatingGenotype;
    }
}
