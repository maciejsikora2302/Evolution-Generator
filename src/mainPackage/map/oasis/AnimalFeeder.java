package mainPackage.map.oasis;

import mainPackage.mapElement.animal.Animal;
import mainPackage.main.Vector2d;

import java.util.ArrayList;
import java.util.Map;

class AnimalFeeder {
    private final NextDayOperator nextDayOperator;

    AnimalFeeder(NextDayOperator nextDayOperator) {
        this.nextDayOperator = nextDayOperator;
    }

    void makeAnimalsEatGrassThatTheyAreStandingOnTopOf() {
        for (Map.Entry<Vector2d, ArrayList<Animal>> element : nextDayOperator.getMap().animals.entrySet()) {
            ArrayList<Animal> allAnimals = element.getValue();
            if (nextDayOperator.getMap().grassHashMap.get(allAnimals.get(0).Position) == null) continue;

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
                    animal.eat(nextDayOperator.getMap().plantEnergy / numberOfAnimalsWithHighestEnergy);
                }
            }
            nextDayOperator.getMap().grassHashMap.remove(positionWhereAnimalsAreEating);
        }
    }
}