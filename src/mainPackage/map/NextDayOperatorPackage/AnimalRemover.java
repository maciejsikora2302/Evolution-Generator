package mainPackage.map.NextDayOperatorPackage;

import mainPackage.main.Vector2d;

import java.util.ArrayList;

class AnimalRemover {
    private NextDayOperator nextDayOperator;

    AnimalRemover(NextDayOperator nextDayOperator) {
        this.nextDayOperator = nextDayOperator;
    }

    void removeAllDeadAnimals() {
        ArrayList<Vector2d> positionList = new ArrayList<>(this.nextDayOperator.getMap().getAnimals().keySet());
        for (Vector2d position : positionList) {
            this.nextDayOperator.getMap().removeAnimalsWithNoEnergyAtGivenPosition(position);
        }
    }
}