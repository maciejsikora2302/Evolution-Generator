package mainPackage.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.google.gson.*;
import mainPackage.main.Parameters;
import mainPackage.mapElement.Animal;
import mainPackage.map.oasis.Oasis;
import mainPackage.unused.Vector2d;


public class World {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\admin\\Documents\\Studia\\Semestr III\\Programowanie obiektowe\\Evolution-Generator\\src\\mainPackage\\parameters.json"));
        Gson gson = new Gson();
        Parameters parameters = new Parameters();
        parameters = gson.fromJson(reader,parameters.getClass());

        Oasis map = new Oasis(parameters.getWidth(), parameters.getHeight(),
                parameters.getPlantEnergy(),
                parameters.getStartEnergy(),
                parameters.getMoveEnergy(),
                parameters.getJungleRatio());

        for (int i = 0; i < 30; i++) {
            Vector2d placeVector = new Vector2d(
                    new Random().nextInt(parameters.getWidth()),
                    new Random().nextInt(parameters.getHeight()));
            while(map.isOccupied(placeVector)){
                placeVector = new Vector2d(
                        new Random().nextInt(parameters.getWidth()),
                        new Random().nextInt(parameters.getHeight()));
            }
            map.placeAnimal(new Animal(map,
                    placeVector,
                    parameters.getStartEnergy(),
                    parameters.getStartingGenes()
                    ));
        }

        int numberOfDays = 30;
        for (int i = 0; i < numberOfDays; i++) {
            map.nextDay();
//            System.out.println(map.toString());
            System.out.println(map.getNumberOfAnimalsAtMap());
        }



//        System.out.println(map.toString());
    }
}

//        try{
//            MoveDirection[] directions = new OptionParser().parse(args);
//        } catch (IllegalArgumentException ex){
//            System.out.println(ex.getMessage());
//            return;
//        }

//        try{
//            ArrayList<Rock> rocks = new ArrayList<>();
//            rocks.add(new Rock(new Vector2d(-4,-4)));
//            rocks.add(new Rock(new Vector2d(7,7)));
//            rocks.add(new Rock(new Vector2d(3,6)));
//            rocks.add(new Rock(new Vector2d(2,0)));
//            IWorldMap map = new UnboundedMap(rocks);
//            map.place(new Animal(map));
//            map.place(new Animal(map,new Vector2d(3,4)));
//        } catch (IllegalArgumentException ex){
//            System.out.println(ex.getMessage());
//            return;
//        }
//        MoveDirection[] directions = new OptionParser().parse(args);
//        ArrayList<Rock> rocks = new ArrayList<>();
//        rocks.add(new Rock(new Vector2d(-4,-4)));
//        rocks.add(new Rock(new Vector2d(7,7)));
//        rocks.add(new Rock(new Vector2d(3,6)));
//        rocks.add(new Rock(new Vector2d(2,0)));
//        IWorldMap map = new UnboundedMap(rocks);
//        map.place(new Animal(map));
//        map.place(new Animal(map,new Vector2d(3,4)));
