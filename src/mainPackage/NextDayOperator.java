package mainPackage;

import java.util.*;

import static java.util.Comparator.comparingInt;

class NextDayOperator {
    private Oasis map;
    private int moveEnergy;
    private BabyGenesOperator babyGenesOperator = new BabyGenesOperator();



    void makeNextDayHappen(){
        this.removeAllDeadAniamls();
        this.turnAndMoveAnimals();
        this.makeAnimalsEatGrassThatTheyAreStandingOnTopOf();
        this.breedValidAnimals();
        this.atTheEndOfTheDayNewGrassGrows();
    }

    private void removeAllDeadAniamls(){
        HashMap<Vector2d, ArrayList<Animal>> copy1 = (HashMap<Vector2d, ArrayList<Animal>>) this.map.animals.clone();
        for (Map.Entry<Vector2d, ArrayList<Animal>> element : copy1.entrySet()) {
            Vector2d position = element.getKey();
            this.map.removeAnimalsWithNoEnergyAtGivenPosition(position);
        }
    }

    private void turnAndMoveAnimals(){
        HashMap<Vector2d, ArrayList<Animal>> copy2 = (HashMap<Vector2d, ArrayList<Animal>>) this.map.animals.clone();
        //ArrayList<Vector2d> keys = (ArrayList<Vector2d>) animals.keySet();
        for (Map.Entry<Vector2d, ArrayList<Animal>> element : copy2.entrySet()) {
            Vector2d position = element.getKey();
            ArrayList<Animal> allAnimalsAtPosition = this.map.animals.get(position);
            for (int i = 0; i < allAnimalsAtPosition.size(); i++) {
                if(allAnimalsAtPosition.get(i) == null){
                    System.out.println("null lol");
                }
                allAnimalsAtPosition.get(i).turnAccordingToGene();
                allAnimalsAtPosition.get(i).decreaseEnergyByMoveValue(this.moveEnergy);
                allAnimalsAtPosition.get(i).move(MoveDirection.FORWARD);

            }
        }
    }

    private void makeAnimalsEatGrassThatTheyAreStandingOnTopOf(){
        for (Map.Entry<Vector2d, ArrayList<Animal>> element : this.map.animals.entrySet()) {
            ArrayList<Animal> allAnimals = element.getValue();
            if (this.map.grassHashMap.get(allAnimals.get(0).Position) == null) continue;

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
                    animal.eat(this.map.plantEnergy / numberOfAnimalsWithHighestEnergy);
                }
            }
            this.map.grassHashMap.remove(positionWhereAnimalsAreEating);
        }
    }

    private void breedValidAnimals(){
        HashMap<Vector2d, ArrayList<Animal>> copy3 = (HashMap<Vector2d, ArrayList<Animal>>) this.map.animals.clone();
        for (Map.Entry<Vector2d, ArrayList<Animal>> element : copy3.entrySet()) {
            ArrayList<Animal> allAnimals = element.getValue();
            if (allAnimals.size() == 1) continue;


            allAnimals.sort(comparingInt(Animal::getEnergy).reversed());

            boolean babyAnimalSuccessfullyPlaced = false;
            Animal firstAnimal = allAnimals.get(0);
            Animal secondAnimal = allAnimals.get(1);

            if (firstAnimal.getEnergy() < (this.map.maxAnimalEnergy / 2) ||
                    secondAnimal.getEnergy() < (this.map.maxAnimalEnergy / 2)) continue;

            //wyznaczanie pozycji dziecka
            Vector2d babyPosition = firstAnimal.getPosition();

            int i = 0;
            while (!babyAnimalSuccessfullyPlaced) {
                i++;
                int x = new Random().nextInt(3) - 1;
                int y = new Random().nextInt(3) - 1;
                Vector2d additionVector = new Vector2d(x, y);
                babyPosition = babyPosition.add(additionVector);
                if (this.map.isOccupied(babyPosition)) {
                    babyPosition = babyPosition.subtract(additionVector);
                } else {
                    babyPosition = this.map.proccesPositionInWrappingOasis(babyPosition);
                    babyAnimalSuccessfullyPlaced = true;
                }
                if (i == 100) {
                    System.out.println("Nie udało się stworzyć dziecka");
                    break;
                }
            }

            int babyEnergy = (firstAnimal.getEnergy() / 4) + (secondAnimal.getEnergy() / 4);
            firstAnimal.giveBirth();
            secondAnimal.giveBirth();



            //TODO: generowanie nowych genów dziecka, obecnie kopiuje geny rodzica

            ArrayList<Integer> babyGenes = babyGenesOperator.craftGenesForBaby(firstAnimal,secondAnimal);


            Animal baby = new Animal(this.map, babyPosition, babyEnergy, babyGenes);
            if (babyAnimalSuccessfullyPlaced)
                this.map.placeAnimal(baby);

        }
    }

    private void atTheEndOfTheDayNewGrassGrows(){
//        for (int i = 0; i < 2; i++) {
//            boolean canPlaceGrass = false;
//            while (!canPlaceGrass) {
//                Grass grass = new Grass(
//                        new Vector2d(
//                                new Random().nextInt(this.map.getWidth()),
//                                new Random().nextInt(this.map.getHeight())));
//                canPlaceGrass = this.map.placeGrass(grass);
//            }
//        }
        this.map.addGrassInTheOasis();
        this.map.addGrassOutsideOfTheOasis();
    }

    NextDayOperator(Oasis map, int moveEnergy){
        this.map = map;
        this.moveEnergy = moveEnergy;

    }
}
