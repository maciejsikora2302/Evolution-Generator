package mainPackage;


import java.util.LinkedList;
import java.util.Random;

public class Animal extends AbstractWorldMapElement {
    private MapDirection Direction;
    private Oasis map;
    private int energy;
    private int[] moveGen;

    public int getEnergy() {
        return this.energy;
    }

    public Vector2d getPosition() {
        return Position;
    }

    public MapDirection getDirection() {
        return Direction;
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

    private int getTurnValue() {
        return this.moveGen[new Random().nextInt(32)];
    }

    public int compareTo(Animal animal) {
        return (Integer.compare(this.getEnergy(), animal.getEnergy()));
    }

    public void move(MoveDirection direction) {
        this.map.removeAnimalFromGivenPosition(this.Position, this);
        this.Position = this.map.proccesPositionInWrappingOasis(this.Position);
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
        if (this.energy > this.map.maxAnimalEnergy) {
            this.energy = this.map.maxAnimalEnergy;
        }
    }

    public void decreaseEnergyByMoveValue(int moveEnergy) {
        this.energy -= moveEnergy;
    }

    public void giveBirth() {
        this.energy -= this.energy / 4;
    }


    //TODO:getMoveGen powinno być usunięte po stworzeniu generowania genów
    public int[] getMoveGen() {
        return moveGen;
    }

    public Animal(Oasis map, Vector2d initialPosition, int energy, int[] moveGen) {
        this.map = map;
        this.Direction = MapDirection.NORTH;
        int rand = new Random().nextInt(8);
        for (int i = 0; i < rand; i++) {
            this.Direction = this.Direction.next();
        }
        this.Position = initialPosition;
        this.energy = energy;
        this.moveGen = moveGen;
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