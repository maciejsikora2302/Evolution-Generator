package mainPackage.map.oasis;

import mainPackage.map.AbstractWorldMap;
import mainPackage.map.MapVisualizer;
import mainPackage.mapElement.Animal;
import mainPackage.mapElement.Grass;
import mainPackage.unused.Vector2d;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class Oasis extends AbstractWorldMap {
    public HashMap<Vector2d, Grass> grassHashMap = new HashMap<>();
    public HashMap<Vector2d, Vector2d> innerOasisPositionHashMap = new HashMap<>();
    public ArrayList<Vector2d> innerOasisPositionList;
    public List<Integer> indexList;
    private final int width;
    private final int height;
    public final int plantEnergy;
    public final int maxAnimalEnergy;
    private double jungleRatio;
    private NextDayOperator nextDayOperator;


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2d proccesPositionInWrappingOasis(Vector2d position) {
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

    public boolean placeAnimal(Animal animal) {
        if (!isOccupied(animal.getPosition())) {
            ArrayList<Animal> tmp = new ArrayList<>();
            tmp.add(animal);
            animals.put(animal.getPosition(), tmp);
            return true;
        }
        return false;
    }

    public void removeAnimalFromGivenPosition(Vector2d position, Animal animal) {
        ArrayList<Animal> tmp = animals.get(position);
//        System.out.println(position);
//        System.out.println("tmp: " + tmp);
        if (tmp == null) return;
        if (tmp.size() > 0) { //powinno być zawsze prawdą
            tmp.remove(animal);
        }
        if (tmp.size() == 0) {
            animals.remove(position);
        }
    }

    public void moveAnimalToGivenPosition(Vector2d position, Animal animal) {
        if (animals.get(position) == null) {
            ArrayList<Animal> tmp = new ArrayList<>();
            tmp.add(animal);
            animals.put(position, tmp);
        } else {
            ArrayList<Animal> tmp = animals.get(position);
            tmp.add(animal);
        }
    }

    public void removeAnimalsWithNoEnergyAtGivenPosition(Vector2d position) {
        ArrayList<Animal> tmp = animals.get(position);
        ArrayList<Animal> toDelete = new ArrayList<>();
        for (Animal animal : tmp) {
            if (animal.getEnergy() <= 0) {
//                tmp.remove(animal);
                toDelete.add(animal);
            }
        }
        for (Animal animal : toDelete) {
            tmp.remove(animal);
        }

        if (tmp.size() == 0) {
            animals.remove(position);
        }
    }

    public void placeGrass(Grass grass) {
        if (!isOccupied(grass.getPosition())) {
            grassHashMap.put(grass.getPosition(), grass);
        }
    }

    public void testFunction() {
        System.out.println(isPerfectSquare(250));
    }


    public void nextDay() {
        this.nextDayOperator.makeNextDayHappen();
    }

    public int getNumberOfAnimalsAtMap() {
        int numberOfAnimals = 0;
        for (Map.Entry<Vector2d, ArrayList<Animal>> element : animals.entrySet()) {
            ArrayList<Animal> list = element.getValue();
            numberOfAnimals += list.size();
        }
        return numberOfAnimals;
    }

    public void addGrassInTheOasis() {
        //sprawdza czy losowo wybrane miejsce jest przez coś zajęte

        for (Integer index : this.indexList) {
            if (!isOccupied(this.innerOasisPositionHashMap.get(this.innerOasisPositionList.get(index)))) {
                this.placeGrass(new Grass(this.innerOasisPositionHashMap.get(this.innerOasisPositionList.get(index))));
                System.out.println(this.innerOasisPositionHashMap.get(this.innerOasisPositionList.get(index)));
                break;
            }
        }
    }

    public void addGrassOutsideOfTheOasis() {
        boolean grassCanBePlaced = false;
        int i = 0;
        while (!grassCanBePlaced) {
            int x = new Random().nextInt(this.width+1);
            int y = new Random().nextInt(this.height+1);
            Vector2d position = new Vector2d(x, y);
            if (!this.innerOasisPositionHashMap.containsKey(position)) {
                grassCanBePlaced = true;
                this.placeGrass(new Grass(position));
            }
            if (i == 1000) {
                System.out.println("Nie udalo sie położyć grassa");
                break;
            }
            i++;
        }


    }

    private boolean isPerfectSquare(double x) {
        double sr = sqrt(x);
        return ((sr - floor(sr)) == 0);
    }

    private void generateZones() {
        int numberOfFieldNeededToBeDeclaredAsInnerOasis = (int) (this.jungleRatio * this.width * this.height);
        int boundy = (int) sqrt(numberOfFieldNeededToBeDeclaredAsInnerOasis);
        Vector2d middlePointOfInnerOasis;
        Vector2d middlePointOfMap = new Vector2d(this.width/2, this.height/2);
        if (!isPerfectSquare(numberOfFieldNeededToBeDeclaredAsInnerOasis)) {
            boundy += 1;
        }
        if (this.height < boundy) {
            boundy += boundy - height;
            middlePointOfInnerOasis = new Vector2d(boundy/2,this.height/2);
        }else{
            middlePointOfInnerOasis = new Vector2d(boundy/2,boundy/2);
        }
        Vector2d moveVector = middlePointOfMap.subtract(middlePointOfInnerOasis);
        while (numberOfFieldNeededToBeDeclaredAsInnerOasis > 0) {
            for (int i = 0; i < this.height && i < boundy; i++) {
                Vector2d positionOfInnerOasis = new Vector2d(0, i);
                for (int j = 0; j < boundy; j++) {
                    this.innerOasisPositionHashMap.put(positionOfInnerOasis.add(moveVector), positionOfInnerOasis.add(moveVector));
                    positionOfInnerOasis = positionOfInnerOasis.add(new Vector2d(1, 0));
                }
            }
            numberOfFieldNeededToBeDeclaredAsInnerOasis -= 1;
        }
        List<Integer> listOfIndex = IntStream.rangeClosed(0, this.innerOasisPositionHashMap.size() - 1)
                .boxed().collect(Collectors.toList());
        Collections.shuffle(listOfIndex);
        this.indexList = listOfIndex;
        this.innerOasisPositionList = new ArrayList<>(this.innerOasisPositionHashMap.values());
    }

    @Override
    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(
                new Vector2d(0, 0),
                new Vector2d(width, height));
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (grassHashMap.get(position) != null) return grassHashMap.get(position);
        return super.objectAt(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        System.out.println("Cos chce korzystac z canMoveTo");
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if (grassHashMap.get(position) != null) return true;
        return super.isOccupied(position);
    }

    public Oasis(int width, int height, int plantEnergy, int maxAnimalEnergy, int moveEnergy, double jungleRatio) {
        if (width > height) {
            this.width = width;
            this.height = height;
        } else {
            this.height = width;
            this.width = height;
        }

        this.plantEnergy = plantEnergy;
        this.maxAnimalEnergy = maxAnimalEnergy;
        this.nextDayOperator = new NextDayOperator(this, moveEnergy);
        this.jungleRatio = jungleRatio;

        this.generateZones();
    }
}
