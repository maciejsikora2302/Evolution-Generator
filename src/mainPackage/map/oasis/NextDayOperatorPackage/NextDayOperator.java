package mainPackage.map.oasis.NextDayOperatorPackage;

import mainPackage.map.oasis.Oasis;
import mainPackage.mapElement.animal.BabyGenesOperator;

public class NextDayOperator {
    private final AnimalBreeder animalBreeder = new AnimalBreeder(this);
    private final AnimalRemover animalRemover = new AnimalRemover(this);
    private final AnimalTurner animalTurner = new AnimalTurner(this);
    private final AnimalFeeder animalFeeder = new AnimalFeeder(this);
    private final AnimalCalculator animalCalculator = new AnimalCalculator(this);
    private Oasis map;
    private int moveEnergy;
    private BabyGenesOperator babyGenesOperator = new BabyGenesOperator();


    public void makeNextDayHappen() {
        animalRemover.removeAllDeadAnimals();
        animalTurner.turnAndMoveAnimals();
        animalFeeder.makeAnimalsEatGrassThatTheyAreStandingOnTopOf();
        animalBreeder.breedValidAnimals();
        for (int i = 0; i < this.map.getNumberOfGrassThatGrowsPerDay() / 2; i++) {
            this.atTheEndOfTheDayNewGrassGrows();
        }
        animalCalculator.calculateGenotype();
        animalCalculator.calculateAverageOfAnimalsEnergy();
        animalCalculator.makeAnimalsOneDayOlderAndCalculateTheirMedianLifespan(this);
    }

    public void atTheEndOfTheDayNewGrassGrows() {
        this.map.addGrassInTheOasis();
        this.map.addGrassOutsideOfTheOasis();
    }

    public NextDayOperator(Oasis map, int moveEnergy) {
        this.map = map;
        this.moveEnergy = moveEnergy;
    }

    BabyGenesOperator getBabyGenesOperator() {
        return this.babyGenesOperator;
    }

    public Oasis getMap() {
        return this.map;
    }

    int getMoveEnergy(){
        return this.moveEnergy;
    }
}
