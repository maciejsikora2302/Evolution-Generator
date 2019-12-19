import com.google.gson.Gson;
import mainPackage.main.OasisParameters;
import mainPackage.map.oasis.Oasis;
import mainPackage.map.oasis.Tile;
import mainPackage.mapElement.animal.Animal;
import mainPackage.mapElement.animal.BabyGenesOperator;
import mainPackage.main.Vector2d;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class testTile {
//    private Oasis map;
//    private OasisParameters parameters = new OasisParameters();


    public testTile() throws FileNotFoundException {
//        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\admin\\Documents\\Studia\\Semestr III\\Programowanie obiektowe\\Evolution-Generator\\src\\mainPackage\\main\\parameters.json"));
//        Gson gson = new Gson();
//        this.parameters = gson.fromJson(reader, parameters.getClass());
//        this.map = new Oasis(
//                parameters.getWidth(), parameters.getHeight(),
//                parameters.getPlantEnergy(),
//                parameters.getStartEnergy(),
//                parameters.getMoveEnergy(),
//                parameters.getJungleRatio(),
//                parameters.getNumberOfStartingAnimals(),
//                parameters.getNumberOfStartingGrass(),
//                parameters.getStartingGenes(),
//                parameters.getNumberOfGrassThatGrowsPerDay());
    }

        @Test
    public void tileTests(){
//        Animal animal1 = new Animal(this.map, new Vector2d(1,2), 100, this.parameters.getStartingGenes());
//        Animal animal2 = new Animal(this.map, new Vector2d(2,2), 100, this.parameters.getStartingGenes());

//        Tile testTile = new Tile(animal1);
//        testTile.addAnimal(animal2);
//
//        System.out.println(testTile);

        ArrayList<Integer> testList = new ArrayList<>();
        testList.add(86);
        testList.add(35);
        testList.add(255);
        testList.add(123);
        testList.add(10);
        testList.add(34);
            System.out.println(testList);
        for(int i=0;i<testList.size();i++){
            if(testList.get(i) == 35){
                testList.remove(i);
            }
            if(testList.get(i) == 123){
                testList.remove(i);
            }
        }
            System.out.println(testList);
    }


    //public Animal(Oasis map, Vector2d initialPosition, int energy, ArrayList<Integer> genotype) {
}
