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
    public void testArrayHashing(){
        ArrayList<Integer> genotype = new ArrayList<>(this.parameters.getStartingGenes());
//        map.addGenotypeToHashMap();
        genotype.sort(Integer::compareTo);
        System.out.println(genotype);
    }

    @Test
    public void tileTests(){
        Animal animal100 = new Animal(this.map, new Vector2d(1,2), 100, this.parameters.getStartingGenes());
        Animal animal70 = new Animal(this.map, new Vector2d(2,2), 70, this.parameters.getStartingGenes());
        Animal animal48 = new Animal(this.map, new Vector2d(4,2), 48, this.parameters.getStartingGenes());
        Animal animal25 = new Animal(this.map, new Vector2d(10,2), 25, this.parameters.getStartingGenes());
        Animal animalN12 = new Animal(this.map, new Vector2d(20,2), -12, this.parameters.getStartingGenes());
        Animal animal0 = new Animal(this.map, new Vector2d(70,2), 0, this.parameters.getStartingGenes());

        Tile testTile = new Tile(animal25);
        testTile.addAnimal(animal70);
        testTile.addAnimal(animal48);
        testTile.addAnimal(animal100);
        testTile.addAnimal(animalN12);
        testTile.addAnimal(animal0);

        Assert.assertEquals("Asserting if top Animal has 100 energy has failed",
                testTile.getAnimalWithHighestEnergy().getEnergy(), animal100.getEnergy());
        Assert.assertEquals("Asserting if second highest animal has 70 energy",
                testTile.getAnimalWithSecondHighestEnergy().getEnergy(), animal70.getEnergy());

        testTile.removeAnimal(animal100);
        Assert.assertNotEquals("Removing failed. Removed animal with highest energy",
                testTile.getAnimalWithSecondHighestEnergy().getEnergy(), animal100.getEnergy());

        testTile.removeAnimalsWithNoEnergyAndGetTheirGenotypes();
        Assert.assertTrue("Removing all animals with noe energy failed", testTile.getAnimalWithLowestEnergy().getEnergy() > 0);


        System.out.println(testTile);


    }


    //public Animal(Oasis map, Vector2d initialPosition, int energy, ArrayList<Integer> genotype) {
}
