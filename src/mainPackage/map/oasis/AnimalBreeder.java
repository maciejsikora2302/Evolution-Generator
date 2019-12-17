package mainPackage.map.oasis;

import mainPackage.mapElement.animal.Animal;
import mainPackage.unused.Vector2d;

import java.util.*;

class AnimalBreeder {
    private final NextDayOperator nextDayOperator;

    AnimalBreeder(NextDayOperator nextDayOperator) {
        this.nextDayOperator = nextDayOperator;
    }

    void breedValidAnimals() {
        HashMap<Vector2d, ArrayList<Animal>> copy3 = (HashMap<Vector2d, ArrayList<Animal>>) nextDayOperator.getMap().animals.clone();
        for (Map.Entry<Vector2d, ArrayList<Animal>> element : copy3.entrySet()) {
            ArrayList<Animal> allAnimals = element.getValue();
            if (allAnimals.size() == 1) continue;

            allAnimals.sort(Comparator.comparingInt(Animal::getEnergy).reversed());

            boolean babyAnimalSuccessfullyPlaced = false;
            Animal firstAnimal = allAnimals.get(0);
            Animal secondAnimal = allAnimals.get(1);

            if (firstAnimal.getEnergy() < (nextDayOperator.getMap().startAnimalEnergy / 2) ||
                    secondAnimal.getEnergy() < (nextDayOperator.getMap().startAnimalEnergy / 2)) continue;

            //wyznaczanie pozycji dziecka
            Vector2d babyPosition = firstAnimal.getPosition();

            int i = 0;
            while (!babyAnimalSuccessfullyPlaced) {
                i++;
                int x = new Random().nextInt(3) - 1;
                int y = new Random().nextInt(3) - 1;
                Vector2d additionVector = new Vector2d(x, y);
                babyPosition = babyPosition.add(additionVector);
                if (nextDayOperator.getMap().isOccupied(babyPosition)) {
                    babyPosition = babyPosition.subtract(additionVector);
                } else {
                    babyPosition = nextDayOperator.getMap().proccesPositionInWrappingOasis(babyPosition);
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


            ArrayList<Integer> babyGenes = nextDayOperator.getBabyGenesOperator().craftGenesForBaby(firstAnimal, secondAnimal);
            Collections.sort(babyGenes);

            Animal baby = new Animal(nextDayOperator.getMap(), babyPosition, babyEnergy, babyGenes);
            if (babyAnimalSuccessfullyPlaced)
                nextDayOperator.getMap().placeAnimal(baby);

        }
    }
}