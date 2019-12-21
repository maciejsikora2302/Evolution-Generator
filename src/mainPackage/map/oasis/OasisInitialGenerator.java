package mainPackage.map.oasis;

import mainPackage.main.Vector2d;
import mainPackage.mapElement.animal.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class OasisInitialGenerator {
    private final Oasis oasis;

    OasisInitialGenerator(Oasis oasis) {
        this.oasis = oasis;
    }

    private boolean isPerfectSquare(double x) {
        double sr = Math.sqrt(x);
        return ((sr - Math.floor(sr)) == 0);
    }//todo extract initial generator to other class

    void generateZones() {
        int numberOfFieldNeededToBeDeclaredAsInnerOasis = (int) (oasis.getJungleRatio() * oasis.getWidth() * oasis.getHeight());
        int boundry = (int) Math.sqrt(numberOfFieldNeededToBeDeclaredAsInnerOasis);

        Vector2d middlePointOfInnerOasis;
        Vector2d middlePointOfMap = new Vector2d(oasis.getWidth() / 2, oasis.getHeight() / 2);
        if (!isPerfectSquare(numberOfFieldNeededToBeDeclaredAsInnerOasis)) {
            boundry += 1;
        }
        if (oasis.getHeight() < boundry) {
            boundry += boundry - oasis.getHeight();
            middlePointOfInnerOasis = new Vector2d(boundry / 2, oasis.getHeight() / 2);
        } else {
            middlePointOfInnerOasis = new Vector2d(boundry / 2, boundry / 2);
        }
        Vector2d moveVector = middlePointOfMap.subtract(middlePointOfInnerOasis);
        while (numberOfFieldNeededToBeDeclaredAsInnerOasis > 0) {
            for (int i = 0; i < oasis.getHeight() && i < boundry; i++) {
                Vector2d positionOfInnerOasis = new Vector2d(0, i);
                for (int j = 0; j < boundry; j++) {
                    oasis.getInnerOasisPositionHashMap().put(positionOfInnerOasis.add(moveVector), positionOfInnerOasis.add(moveVector));
                    positionOfInnerOasis = positionOfInnerOasis.add(new Vector2d(1, 0));
                }
            }
            numberOfFieldNeededToBeDeclaredAsInnerOasis -= 1;
        }
        List<Integer> listOfIndex = IntStream.rangeClosed(0, oasis.getInnerOasisPositionHashMap().size() - 1)
                .boxed().collect(Collectors.toList());
        Collections.shuffle(listOfIndex);
        oasis.setIndexList(listOfIndex);
        oasis.setInnerOasisPositionList(new ArrayList<>(oasis.getInnerOasisPositionHashMap().values()));
    }

    void placeInitialAnimals(int numberOfInitialAnimals, ArrayList<Integer> startingGenes) {
        for (int i = 0; i < numberOfInitialAnimals; i++) {
            Vector2d placeVector = new Vector2d(
                    new Random().nextInt(oasis.getWidth()),
                    new Random().nextInt(oasis.getHeight()));
            while (oasis.isOccupied(placeVector)) {
                placeVector = new Vector2d(
                        new Random().nextInt(oasis.getWidth()),
                        new Random().nextInt(oasis.getHeight()));
            }
            oasis.placeAnimal(new Animal(oasis, placeVector, oasis.getStartAnimalEnergy(), startingGenes));
        }
    }

    void placeInitialGrass(int numberOfInitialGrass) {
        for (int i = 0; i < numberOfInitialGrass; i++) {
            oasis.getNextDayOperator().atTheEndOfTheDayNewGrassGrows();
        }
    }
}