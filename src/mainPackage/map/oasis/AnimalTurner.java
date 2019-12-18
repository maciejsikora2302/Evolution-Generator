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
        HashMap<Vector2d, ArrayList<Animal>> copy2 = (HashMap<Vector2d, ArrayList<Animal>>) this.nextDayOperator.getMap().animals.clone();
        //ArrayList<Vector2d> keys = (ArrayList<Vector2d>) animals.keySet();
        for (Map.Entry<Vector2d, ArrayList<Animal>> element : copy2.entrySet()) {
            Vector2d position = element.getKey();
            ArrayList<Animal> allAnimalsAtPosition = this.nextDayOperator.getMap().animals.get(position);
            for (int i = 0; i < allAnimalsAtPosition.size(); i++) {
                allAnimalsAtPosition.get(i).turnAccordingToGene();
                allAnimalsAtPosition.get(i).decreaseEnergyByMoveValue(this.nextDayOperator.getMoveEnergy());
                allAnimalsAtPosition.get(i).move();
            }
        }
    }
}
