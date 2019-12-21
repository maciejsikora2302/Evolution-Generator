package mainPackage.map.NextDayOperatorPackage;

import mainPackage.map.oasis.tile.Tile;
import mainPackage.main.Vector2d;

import java.util.ArrayList;

public class AnimalFeeder {
    private final NextDayOperator nextDayOperator;

    AnimalFeeder(NextDayOperator nextDayOperator) {
        this.nextDayOperator = nextDayOperator;
    }

    void makeAnimalsEatGrassThatTheyAreStandingOnTopOf() {

        ArrayList<Vector2d> grassPositionList = new ArrayList<>(this.nextDayOperator.getMap().getGrasses().keySet());

        for(Vector2d position: grassPositionList){
            Tile tile = this.nextDayOperator.getMap().getAnimals().get(position);
            if(tile == null) continue;
            tile.animalsWithHighestEnergyEat();
            this.nextDayOperator.getMap().getGrasses().remove(position);
        }
    }
}