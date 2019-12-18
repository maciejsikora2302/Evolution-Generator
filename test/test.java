import com.google.gson.Gson;
import mainPackage.main.OasisParameters;
import mainPackage.map.oasis.Oasis;
import mainPackage.mapElement.animal.Animal;
import mainPackage.mapElement.animal.BabyGenesOperator;
import mainPackage.unused.Vector2d;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class test {
    private Oasis map;
    private OasisParameters parameters = new OasisParameters();


    public test() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\admin\\Documents\\Studia\\Semestr III\\Programowanie obiektowe\\Evolution-Generator\\src\\mainPackage\\main\\parameters.json"));
        Gson gson = new Gson();
        this.parameters = gson.fromJson(reader, parameters.getClass());
        this.map = new Oasis(
                parameters.getWidth(), parameters.getHeight(),
                parameters.getPlantEnergy(),
                parameters.getStartEnergy(),
                parameters.getMoveEnergy(),
                parameters.getJungleRatio(),
                parameters.getNumberOfStartingAnimals(),
                parameters.getNumberOfStartingGrass(),
                parameters.getStartingGenes(),
                parameters.getNumberOfGrassThatGrowsPerDay());
    }

    @Test
    public void testFunction() throws FileNotFoundException {
        System.out.println("testFunction| starting genes: " + this.parameters.getStartingGenes());
        Animal animal = new Animal(map, new Vector2d(2, 2), 100, parameters.getStartingGenes());
    }

    @Test
    public void geneCrafterTest(){
        Animal animal1 = new Animal(this.map, new Vector2d(1,2), 100, this.parameters.getStartingGenes());
        Animal animal2 = new Animal(this.map, new Vector2d(2,2), 100, this.parameters.getStartingGenes());

        BabyGenesOperator babyGenesOperator = new BabyGenesOperator();
        boolean eachGeneExistsInGenotype = true;
        System.out.println("geneCrafterTest| ");
        for(int j=0;j<1000;j++){
            ArrayList<Integer> babyGenes = babyGenesOperator.craftGenesForBaby(animal1,animal2);
            for(int i=0;i<8;i++){
                if (!babyGenes.contains(i)) {
                    eachGeneExistsInGenotype = false;
                    break;
                }
            }
            if(!eachGeneExistsInGenotype) break;
        }
        Assert.assertTrue(eachGeneExistsInGenotype);
    }

    @Test
    public void jungleTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Oasis testOasis = new Oasis(
                parameters.getWidth(), parameters.getHeight(),
                parameters.getPlantEnergy(),
                parameters.getStartEnergy(),
                parameters.getMoveEnergy(),
                parameters.getJungleRatio(),
                parameters.getNumberOfStartingAnimals(),
                parameters.getNumberOfStartingGrass(),
                parameters.getStartingGenes(),
                parameters.getNumberOfGrassThatGrowsPerDay());

//        Method method = testOasis.getClass().getDeclaredMethod("generateZones");
//        method.setAccessible(true);
//        method.invoke(testOasis);

    }


    //public Animal(Oasis map, Vector2d initialPosition, int energy, ArrayList<Integer> genotype) {
}
