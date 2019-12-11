package mainPackage.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.google.gson.*;
import mainPackage.mapElement.animal.Animal;
import mainPackage.map.oasis.Oasis;
import mainPackage.unused.Vector2d;


public class World {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\admin\\Documents\\Studia\\Semestr III\\Programowanie obiektowe\\Evolution-Generator\\src\\mainPackage\\main\\parameters.json"));
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
            System.out.println(map.toString());
            System.out.println(map.getNumberOfAnimalsAtMap());
        }
    }
}