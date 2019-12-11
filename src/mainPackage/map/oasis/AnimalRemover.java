package mainPackage.map.oasis;

import mainPackage.mapElement.animal.Animal;
import mainPackage.unused.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class AnimalRemover{
    private Oasis map;

    AnimalRemover(Oasis map){
        this.map = map;
    }

    void removeAllDeadAniamls(){
        HashMap<Vector2d, ArrayList<Animal>> copy1 = (HashMap<Vector2d, ArrayList<Animal>>) this.map.animals.clone();
        for (Map.Entry<Vector2d, ArrayList<Animal>> element : copy1.entrySet()) {
            Vector2d position = element.getKey();
            this.map.removeAnimalsWithNoEnergyAtGivenPosition(position);
        }
    }
}