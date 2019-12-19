package mainPackage.map.oasis;

import mainPackage.mapElement.animal.Animal;
import mainPackage.main.Vector2d;

import java.util.*;

class AnimalBreeder {
    private final NextDayOperator nextDayOperator;

    AnimalBreeder(NextDayOperator nextDayOperator) {
        this.nextDayOperator = nextDayOperator;
    }

    void breedValidAnimals() {
        ArrayList<Vector2d> positionList = new ArrayList<>(this.nextDayOperator.getMap().animals.keySet());

        for (Vector2d position: positionList) {
            Tile tile = this.nextDayOperator.getMap().animals.get(position);
            if (tile.getNumberOfAnimalsAtTile() == 1) continue;


            Animal firstAnimal = tile.getAnimalWithHighestEnergy();
            Animal secondAnimal = tile.getAnimalWithSecondHighestEnergy();

            if (firstAnimal.getEnergy() < (nextDayOperator.getMap().startAnimalEnergy / 2) ||
                    secondAnimal.getEnergy() < (nextDayOperator.getMap().startAnimalEnergy / 2)) continue;

            //wyznaczanie pozycji dziecka
            boolean babyAnimalSuccessfullyPlaced = false;
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
                    babyPosition = nextDayOperator.getMap().proccessPositionInWrappingOasis(babyPosition);
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


            Animal baby = new Animal(nextDayOperator.getMap(), babyPosition, babyEnergy, babyGenes);
            if (babyAnimalSuccessfullyPlaced)
                nextDayOperator.getMap().placeAnimal(baby);
        }
    }
}