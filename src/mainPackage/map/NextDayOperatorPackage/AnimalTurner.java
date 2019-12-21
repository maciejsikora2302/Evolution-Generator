package mainPackage.map.NextDayOperatorPackage;

import mainPackage.main.Vector2d;
import mainPackage.map.oasis.tile.Tile;

import java.util.ArrayList;

public class AnimalTurner {
    private NextDayOperator nextDayOperator;

    AnimalTurner(NextDayOperator nextDayOperator){
        this.nextDayOperator = nextDayOperator;
    }

    void turnAndMoveAnimals(){
        ArrayList<Vector2d> positionList = new ArrayList<>(this.nextDayOperator.getMap().getAnimals().keySet());
        for (Vector2d position : positionList) {
            Tile tile = this.nextDayOperator.getMap().getAnimals().get(position);
            for (int i = 0; i < tile.getNumberOfAnimalsAtTile(); i++) {
                tile.decreaseEnergyThenMoveTenTurn(this.nextDayOperator.getMoveEnergy());
            }
        }
    }
}
