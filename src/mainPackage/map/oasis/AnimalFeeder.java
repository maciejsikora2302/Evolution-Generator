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

        ArrayList<Vector2d> grassPositionList = new ArrayList<>(this.nextDayOperator.getMap().grasses.keySet());

        for(Vector2d position: grassPositionList){
            Tile tile = this.nextDayOperator.getMap().animals.get(position);
            if(tile == null) continue;
            tile.animalsWithHighestEnergyEat();
            this.nextDayOperator.getMap().grasses.remove(position);
        }
    }
}