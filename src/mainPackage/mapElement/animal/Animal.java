package mainPackage.mapElement.animal;


import mainPackage.mapElement.AbstractWorldMapElement;
import mainPackage.moveAndDirection.MapDirection;
import mainPackage.main.Vector2d;
import mainPackage.map.oasis.Oasis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Animal extends AbstractWorldMapElement {
    private MapDirection Direction;
    private Oasis map;
    private Integer energy;
    private ArrayList<Integer> genotype;
    private int age;

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
//        this.addGenotypeToMap();
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

    public void move() {
        this.map.removeAnimalFromGivenPosition(this.Position, this);
        this.Position = this.map.proccessPositionInWrappingOasis(this.Position);
        this.Position = this.Position.add(this.Direction.toUnitVector());
        this.map.moveAnimalToGivenPosition(this.Position, this);
    }

    public void turnAccordingToGene() {
        int turnValue = this.getTurnValue();
        for (int i = 0; i < turnValue; i++) {
            this.Direction = this.Direction.next();
        }
    }

    public void eat(int energy) {
        this.energy += energy;
    }

    public void decreaseEnergyByMoveValue(int moveEnergy) {
        this.energy -= moveEnergy;
    }

    public void giveBirth() {
        this.energy -= this.energy / 4;
    }

    public ArrayList<Integer> getGenotype() {
        return this.genotype;
    }

    public ArrayList<Integer> getCopyOfGenotypeShuffled(){
        ArrayList<Integer> copyOfGenotype = new ArrayList<>(this.genotype);
        Collections.shuffle(copyOfGenotype);
        return copyOfGenotype;
    }

    public String getGenotypeAsString() {
        ArrayList<Integer> arrList = this.genotype;
        StringBuilder sb = new StringBuilder();
        for (int i = arrList.size() - 1; i >= 0; i--) {
            int num = arrList.get(i);
            sb.append(num);
        }
        return sb.toString();
    }

    public int getAge() {
        return this.age;
    }

    public void incrementAge() {
        this.age += 1;
    }

    public String toString() {
        switch (Direction) {
            case NORTH:
                return "N ";
            case NORTHWEST:
                return "NW";
            case WEST:
                return "W ";
            case SOUTHWEST:
                return "SW";
            case SOUTH:
                return "S ";
            case SOUTHEAST:
                return "SE";
            case EAST:
                return "E ";
            case NORTHEAST:
                return "NE";
        }
        return "+";
    }


}


//    private LinkedList<IPositionChangeObserver> observers = new LinkedList<>();
//
//    public void addObserver(IPositionChangeObserver observer){
//        observers.add(observer);
//    }
//    public void removeObserver(IPositionChangeObserver observer){
//        observers.remove(observer);
//    }
//    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
//        for(IPositionChangeObserver observer: observers){
//            observer.positionChanged(oldPosition, newPosition);
//        }
//    }
//private void addGenotypeToMap(){
//        String result = this.getGenotypeAsString();
//
//        System.out.print("Creating animal. Genotype: " + result + " ");
//
//        if(this.map.dominatingGenotype.get(result) == null){
//            this.map.dominatingGenotype.put(result,1);
//            System.out.println(1);
//        }else{
//            Integer val = this.map.dominatingGenotype.remove(result);
//            System.out.println(val + 1);
//            this.map.dominatingGenotype.put(result,val+1);
//        }
//    }