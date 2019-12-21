package mainPackage.map.oasis;

import mainPackage.main.Vector2d;

import java.util.ArrayList;

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
                tile.decreaseEnergyThenMoveTenTurn(this.nextDayOperator.getMoveEnergy());
            }
        }
    }
}
