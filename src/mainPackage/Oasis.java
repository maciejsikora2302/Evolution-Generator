package mainPackage;

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
//        System.out.println("Usunięto marte zwierzaki");
        //PO KOLEI DLA KAŻDEGO ZWIERZAKA

        //skręt, przemieszczenie

//        System.out.println("Zwierzaki powiny się przekręcić i przemieścić");


        //jedzenie
//        for (Map.Entry<Vector2d, ArrayList<Animal>> element : animals.entrySet()) {
//            ArrayList<Animal> allAnimals = element.getValue();
//            if (grassHashMap.get(allAnimals.get(0).Position) == null) continue;
//
//            int numberOfAnimalsWithHighestEnergy = 0;
//            int highestEnergy = -1;
//            Vector2d positionWhereAnimalsAreEating = allAnimals.get(0).getPosition();
//            for (Animal animal : allAnimals) {
//                if (animal.getEnergy() == highestEnergy) {
//                    numberOfAnimalsWithHighestEnergy++;
//                }
//                if (animal.getEnergy() > highestEnergy) {
//                    highestEnergy = animal.getEnergy();
//                    numberOfAnimalsWithHighestEnergy = 1;
//                }
//            }
//            for (Animal animal : allAnimals) {
//                if (animal.getEnergy() == highestEnergy) {
//                    animal.eat(this.plantEnergy / numberOfAnimalsWithHighestEnergy);
//                }
//            }
//            grassHashMap.remove(positionWhereAnimalsAreEating);
//        }
//        System.out.println("Zwierzaki powinny zjeść co mogły");

//        HashMap<Vector2d, ArrayList<Animal>> copy3 = (HashMap<Vector2d, ArrayList<Animal>>) animals.clone();
//        //rozmnażanie
//        for (Map.Entry<Vector2d, ArrayList<Animal>> element : copy3.entrySet()) {
//            ArrayList<Animal> allAnimals = element.getValue();
//            if (allAnimals.size() == 1) continue;
//
//            if (allAnimals.size() == 2) {
//                boolean babyAnimalSuccessfullyPlaced = false;
//                Animal firstAnimal = allAnimals.get(0);
//                Animal secondAnimal = allAnimals.get(1);
//
//                if (firstAnimal.getEnergy() < (maxAnimalEnergy / 2) ||
//                        secondAnimal.getEnergy() < (maxAnimalEnergy / 2)) continue;
//
//                //wyznaczanie pozycji dziecka
//                Vector2d babyPosition = firstAnimal.getPosition();
//
//                int i = 0;
//                while (!babyAnimalSuccessfullyPlaced) {
//                    i++;
//                    int x = new Random().nextInt(3) - 1;
//                    int y = new Random().nextInt(3) - 1;
//                    Vector2d additionVector = new Vector2d(x, y);
//                    babyPosition = babyPosition.add(additionVector);
//                    if (this.isOccupied(babyPosition)) {
//                        babyPosition = babyPosition.subtract(additionVector);
//                    } else {
//                        babyPosition = this.proccesPositionInWrappingOasis(babyPosition);
//                        babyAnimalSuccessfullyPlaced = true;
//                    }
//                    if (i == 100) {
//                        System.out.println("Nie udało się stworzyć dziecka");
//                        break;
//                    }
//                }
//
//                //wyznaczaie energii dziecka
//                int babyEnergy = (firstAnimal.getEnergy() / 4) + (secondAnimal.getEnergy() / 4);
//                firstAnimal.giveBirth();
//                secondAnimal.giveBirth();
//
//                System.out.println("Stworzono nowe dziecko o parametrach: " + babyPosition.x + " " + babyPosition.y + " " + babyEnergy);
//
//                Animal baby = new Animal(this, babyPosition, babyEnergy, firstAnimal.getMoveGen());
//                if (babyAnimalSuccessfullyPlaced)
//                    System.out.println("BABY PLACED");
//                this.placeAnimal(baby);
//            } else {
//                System.out.println("duzo zwierzakow na jednym miejscu");
//            }
//        }
//        System.out.println("Zwierzaki powinne się rozmożyć");

        //dodanie nowych roślin na mapie
//        for (int i = 0; i < 2; i++) {
//            boolean canPlaceGrass = false;
//            while (!canPlaceGrass) {
//                Grass grass = new Grass(
//                        new Vector2d(
//                                new Random().nextInt(this.width),
//                                new Random().nextInt(this.height)));
//                canPlaceGrass = this.placeGrass(grass);
//            }
//        }
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
