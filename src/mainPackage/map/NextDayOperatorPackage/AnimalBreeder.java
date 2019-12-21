package mainPackage.map.NextDayOperatorPackage;

import mainPackage.map.oasis.tile.Tile;
import mainPackage.mapElement.animal.Animal;
import mainPackage.main.Vector2d;

import java.util.*;

public class AnimalBreeder {
    private final NextDayOperator nextDayOperator;

    AnimalBreeder(NextDayOperator nextDayOperator) {
        this.nextDayOperator = nextDayOperator;
    }

    void breedValidAnimals() {
        ArrayList<Vector2d> positionList = new ArrayList<>(this.nextDayOperator.getMap().getAnimals().keySet());

        for (Vector2d position : positionList) {
            Tile tile = this.nextDayOperator.getMap().getAnimals().get(position);
            if (tile.getNumberOfAnimalsAtTile() == 1) continue;


            Animal firstAnimal = tile.getAnimalWithHighestEnergy();
            Animal secondAnimal = tile.getAnimalWithSecondHighestEnergy();

            if (firstAnimal.getEnergy() < (nextDayOperator.getMap().getStartAnimalEnergy() / 2) ||
                    secondAnimal.getEnergy() < (nextDayOperator.getMap().getStartAnimalEnergy() / 2)) continue;

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
                    babyPosition = nextDayOperator.getMap().wrapPosition(babyPosition);
                    babyAnimalSuccessfullyPlaced = true;
                }
                if (i == 100) {
                    System.out.println("New cute Animal was not strong enough to survive :( | [There were no space for it]");
                    break;
                }
            }

            int babyEnergy = (firstAnimal.getEnergy() / 4) + (secondAnimal.getEnergy() / 4);
            firstAnimal.giveBirth();
            secondAnimal.giveBirth();


            ArrayList<Integer> babyGenes = nextDayOperator.getBabyGenesOperator().craftGenesForBaby(firstAnimal, secondAnimal);

            Animal baby = new Animal(nextDayOperator.getMap(), babyPosition, babyEnergy, babyGenes);
            if (firstAnimal.getObserver() != null) baby.attachObserver(firstAnimal.getObserver());
            else if (secondAnimal.getObserver() != null) baby.attachObserver(secondAnimal.getObserver());

            if (babyAnimalSuccessfullyPlaced)
                nextDayOperator.getMap().placeAnimal(baby);
        }
    }
}