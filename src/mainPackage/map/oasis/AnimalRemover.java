package mainPackage.map.oasis;

import mainPackage.mapElement.animal.Animal;
import mainPackage.unused.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class AnimalRemover{
    private NextDayOperator nextDayOperator;

    AnimalRemover(NextDayOperator nextDayOperator){
        this.nextDayOperator = nextDayOperator;
    }

    void removeAllDeadAniamls(){
        HashMap<Vector2d, ArrayList<Animal>> copy1 = (HashMap<Vector2d, ArrayList<Animal>>) this.nextDayOperator.getMap().animals.clone();
        for (Map.Entry<Vector2d, ArrayList<Animal>> element : copy1.entrySet()) {
            Vector2d position = element.getKey();
            this.nextDayOperator.getMap().removeAnimalsWithNoEnergyAtGivenPosition(position);
        }
    }
}