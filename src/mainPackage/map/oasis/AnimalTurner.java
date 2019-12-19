package mainPackage.map.oasis;

import mainPackage.mapElement.animal.Animal;
import mainPackage.main.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnimalTurner {
    private NextDayOperator nextDayOperator;

    AnimalTurner(NextDayOperator nextDayOperator){
        this.nextDayOperator = nextDayOperator;
    }

    void turnAndMoveAnimals(){
        ArrayList<Vector2d> positionList = new ArrayList<>(this.nextDayOperator.getMap().animals.keySet());
        for (Vector2d position : positionList) {
            Tile tile = this.nextDayOperator.getMap().animals.get(position);
            for (int i = 0; i < tile.getNumberOfAnimalsAtTile(); i++) {
                tile.turnAllAnimalsAndDecreaseTheirEnergy(this.nextDayOperator.getMoveEnergy());
                tile.moveAllAnimals();
            }
        }
    }
}
