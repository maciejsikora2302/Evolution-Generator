package mainPackage.mapElement.animal;

import mainPackage.main.Vector2d;
import mainPackage.map.oasis.Oasis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Animal {
    private Vector2d Position;
    private MapDirection Direction;
    private Oasis map;
    private Integer energy;
    private ArrayList<Integer> genotype;
    private int age;
    private int numberOfChildren = 0;
    private AnimalObserver animalObserver = null;

    public Animal(Oasis map, Vector2d initialPosition, int energy, ArrayList<Integer> genotype) {
        this.map = map;
        this.Direction = MapDirection.NORTH;
        int rand = new Random().nextInt(8);
        for (int i = 0; i < rand; i++) {
            this.Direction = this.Direction.next();
        }
        this.Position = initialPosition;
        this.energy = energy;
        this.genotype = genotype;
        this.age = 1;
    }

    public int getEnergy() {
        return this.energy;
    }

    public Vector2d getPosition() {
        return Position;
    }

    private int getTurnValue() {
        return this.genotype.get(new Random().nextInt(32));
    }

    public void moveAndTurn() {
        this.map.removeAnimalFromGivenPosition(this.Position, this);
        this.Position = this.map.wrapPosition(this.Position);
        this.Position = this.Position.add(this.Direction.toUnitVector());
        this.map.moveAnimalToGivenPosition(this.Position, this);
        this.turnAccordingToGene();
    }

    private void turnAccordingToGene() {
        int turnValue = this.getTurnValue();
        for (int i = 0; i < turnValue; i++) {
            this.Direction = this.Direction.next();
        }
    }

    public void eat(int howManyAnimalsAreEating) {
        this.energy += this.map.getPlantEnergy() / howManyAnimalsAreEating;
    }

    public void decreaseEnergyByMoveValue(int moveEnergy) {
        this.energy -= moveEnergy;
    }

    public void giveBirth() {
        this.energy -= this.energy / 4;
        this.numberOfChildren++;
    }


    ArrayList<Integer> getCopyOfGenotypeShuffled() {
        ArrayList<Integer> copyOfGenotype = new ArrayList<>(this.genotype);
        Collections.shuffle(copyOfGenotype);
        return copyOfGenotype;
    }

    public String getGenotypeAsString() {
        ArrayList<Integer> arrList = new ArrayList<>(this.genotype);
        StringBuilder sb = new StringBuilder();
        for (int i = arrList.size() - 1; i >= 0; i--) {
            int num = arrList.get(i);
            sb.append(num);
        }
        return sb.toString();
    }

    public String getStatisticalGenotypeValuesAsString() {
        HashMap<Integer, Integer> genesMap = new HashMap<>();
        for (int i = 0; i < 32; i++) {
            genesMap.merge(this.genotype.get(i), 1, Integer::sum);
        }
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Integer> values = new ArrayList<>(genesMap.values());
        for (int i = 0; i < genesMap.keySet().size(); i++) {
            stringBuilder.append("{").append(i).append("}: ").append((int) (((double) values.get(i) / 32) * 100)).append("% ");
            if (i == 3) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public AnimalObserver getObserver() {
        return this.animalObserver;
    }

    public void attachObserver(AnimalObserver animalObserver) {
        this.animalObserver = animalObserver;
        this.animalObserver.addDescendant(this);
    }

    public int getNumberOfChildren() {
        return this.numberOfChildren;
    }

    void removeObserver() {
        this.animalObserver = null;
    }

    public MapDirection getDirection() {
        return Direction;
    }

    public int getAge() {
        return this.age;
    }

    public void incrementAge() {
        this.age += 1;
    }

    public String toString() {
        for (int i = 0; i < 4; i++)
            this.Direction = this.Direction.next();
        String directionAsString = "+";
        switch (Direction) {
            case NORTH:
                directionAsString = "⇑";
                break;
            case NORTHWEST:
                directionAsString = "⇖";
                break;
            case WEST:
                directionAsString = "⇐";
                break;
            case SOUTHWEST:
                directionAsString = "⇙";
                break;
            case SOUTH:
                directionAsString = "⇓";
                break;
            case SOUTHEAST:
                directionAsString = "⇘";
                break;
            case EAST:
                directionAsString = "⇒";
                break;
            case NORTHEAST:
                directionAsString = "⇗";
                break;
        }
        for (int i = 0; i < 4; i++)
            this.Direction = this.Direction.next();
        return directionAsString;
    }
}