package mainPackage;

import java.util.*;

public class Oasis extends AbstractWorldMap {
    private HashMap<Vector2d, Grass> grassHashMap = new HashMap<>();
    private int width;
    private int height;
    private int jungleRatio;
    private int plantEnergy;
    private int moveEnergy;
    public int maxAnimalEnergy;

    //TODO: przerobić canMoveTo powinno uwzględniać, że zwierak wychodząć poza boundry pojawia siępo drugiej stronie mapy
    // ewentualnie zmienić funkcję move

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.x <= width && position.x >= 0 &&
                position.y <= height && position.y >= 0;
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

    public boolean placeGrass(Grass grass) {
        if (!isOccupied(grass.getPosition())) {
            grassHashMap.put(grass.getPosition(), grass);
            return true;
        }
        return false;
    }

    public void testFunction() {
        System.out.println(new Random().nextInt(33));
    }

    public void nextDay() {
        //usunięcie martwych zwierząt
        HashMap<Vector2d, ArrayList<Animal>> copy1 = (HashMap<Vector2d, ArrayList<Animal>>) animals.clone();
        for (Map.Entry<Vector2d, ArrayList<Animal>> element : copy1.entrySet()) {
            Vector2d position = element.getKey();
            this.removeAnimalsWithNoEnergyAtGivenPosition(position);
        }
//        System.out.println("Usunięto marte zwierzaki");
        //PO KOLEI DLA KAŻDEGO ZWIERZAKA

        //skręt, przemieszczenie
        HashMap<Vector2d, ArrayList<Animal>> copy2 = (HashMap<Vector2d, ArrayList<Animal>>) animals.clone();
        //ArrayList<Vector2d> keys = (ArrayList<Vector2d>) animals.keySet();
        for (Map.Entry<Vector2d, ArrayList<Animal>> element : copy2.entrySet()) {
            Vector2d position = element.getKey();
            ArrayList<Animal> allAnimalsAtPosition = animals.get(position);
            for (int i = 0; i < allAnimalsAtPosition.size(); i++) {
                allAnimalsAtPosition.get(i).turnAccordingToGene();
                allAnimalsAtPosition.get(i).decreaseEnergyByMoveValue(this.moveEnergy);
                allAnimalsAtPosition.get(i).move(MoveDirection.FORWARD);

            }
        }
//        System.out.println("Zwierzaki powiny się przekręcić i przemieścić");


        //jedzenie
        for (Map.Entry<Vector2d, ArrayList<Animal>> element : animals.entrySet()) {
            ArrayList<Animal> allAnimals = element.getValue();
            if (grassHashMap.get(allAnimals.get(0).Position) == null) continue;

            int numberOfAnimalsWithHighestEnergy = 0;
            int highestEnergy = -1;
            Vector2d positionWhereAnimalsAreEating = allAnimals.get(0).getPosition();
            for (Animal animal : allAnimals) {
                if (animal.getEnergy() == highestEnergy) {
                    numberOfAnimalsWithHighestEnergy++;
                }
                if (animal.getEnergy() > highestEnergy) {
                    highestEnergy = animal.getEnergy();
                    numberOfAnimalsWithHighestEnergy = 1;
                }
            }
            for (Animal animal : allAnimals) {
                if (animal.getEnergy() == highestEnergy) {
                    animal.eat(this.plantEnergy / numberOfAnimalsWithHighestEnergy);
                }
            }
            grassHashMap.remove(positionWhereAnimalsAreEating);
        }
//        System.out.println("Zwierzaki powinny zjeść co mogły");

        HashMap<Vector2d, ArrayList<Animal>> copy3 = (HashMap<Vector2d, ArrayList<Animal>>) animals.clone();
        //rozmnażanie
        for (Map.Entry<Vector2d, ArrayList<Animal>> element : copy3.entrySet()) {
            ArrayList<Animal> allAnimals = element.getValue();
            if (allAnimals.size() == 1) continue;

            if (allAnimals.size() == 2) {
                boolean babyAnimalSuccessfullyPlaced = false;
                Animal firstAnimal = allAnimals.get(0);
                Animal secondAnimal = allAnimals.get(1);

                if (firstAnimal.getEnergy() < (maxAnimalEnergy / 2) ||
                        secondAnimal.getEnergy() < (maxAnimalEnergy / 2)) continue;

                //wyznaczanie pozycji dziecka
                Vector2d babyPosition = firstAnimal.getPosition();

                int i = 0;
                while (!babyAnimalSuccessfullyPlaced) {
                    i++;
                    int x = new Random().nextInt(3) - 1;
                    int y = new Random().nextInt(3) - 1;
                    Vector2d additionVector = new Vector2d(x, y);
                    babyPosition = babyPosition.add(additionVector);
                    if (this.isOccupied(babyPosition) || !this.canMoveTo(babyPosition)) {
                        babyPosition = babyPosition.subtract(additionVector);
                    } else {
                        babyAnimalSuccessfullyPlaced = true;
                    }
                    if (i == 100){
                        System.out.println("Nie udało się stworzyć dziecka");
                        break;
                    }
                }

                //wyznaczaie energii dziecka
                int babyEnergy = (firstAnimal.getEnergy() / 4) + (secondAnimal.getEnergy() / 4);
                firstAnimal.giveBirth();
                secondAnimal.giveBirth();

                System.out.println("Stworzono nowe dziecko o parametrach: " + babyPosition.x + " " + babyPosition.y + " " + babyEnergy);
                //TODO: generowanie nowych genów dziecka, obecnie kopiuje geny rodzica
                Animal baby = new Animal(this, babyPosition, babyEnergy, firstAnimal.getMoveGen());
                if (babyAnimalSuccessfullyPlaced)
                    System.out.println("BABY PLACED");
                this.placeAnimal(baby);
            } else {
                System.out.println("duzo zwierzakow na jednym miejscu");
            }
        }
//        System.out.println("Zwierzaki powinne się rozmożyć");

        //dodanie nowych roślin na mapie
        //TODO:dodawania z podziałem na oaze w środku i resztę, obecnie jest dwie randomowe pozycje
        for (int i = 0; i < 2; i++) {
            boolean canPlaceGrass = false;
            Vector2d pos = null;
            while (!canPlaceGrass) {
                Grass grass = new Grass(
                        new Vector2d(
                                new Random().nextInt(this.width),
                                new Random().nextInt(this.height)));
                canPlaceGrass = this.placeGrass(grass);
                pos = grass.Position;
            }
//            System.out.println(pos);
        }
    }

    public int getNumberOfAnimalsAtMap() {
        int numberOfAnimals = 0;
        for (Map.Entry<Vector2d, ArrayList<Animal>> element : animals.entrySet()) {
            ArrayList<Animal> list = element.getValue();
            numberOfAnimals += list.size();
        }
        return numberOfAnimals;
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
    public boolean isOccupied(Vector2d position) {
        if (grassHashMap.get(position) != null) return true;
        return super.isOccupied(position);
    }

    public Oasis(int width, int height, int plantEnergy, int maxAnimalEnergy, int moveEnergy) {
        this.width = width;
        this.height = height;
        this.plantEnergy = plantEnergy;
        this.maxAnimalEnergy = maxAnimalEnergy;
        this.moveEnergy = moveEnergy;
    }
}
