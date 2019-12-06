package mainPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.gson.*;


public class World {
    public static void main(String[] args) throws IOException {
        //TODO: operowanie na jsonie
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\admin\\Documents\\Studia\\Semestr III\\Programowanie obiektowe\\Evolution-Generator\\src\\mainPackage\\settings.json"));
        Gson gson = new Gson();
        Parameters parameters = new Parameters();
        parameters = gson.fromJson(reader,parameters.getClass());


        int width = 10;
        int height = 10;
        double jungleRatio = 0.15;
        Oasis map = new Oasis(width, height, 20, 100, 3, jungleRatio);
        int[] genes = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7};
        map.placeAnimal(new Animal(map, new Vector2d(0, 3), 100, genes));
        map.placeAnimal(new Animal(map, new Vector2d(4, 7), 100, genes));
        //TODO: losowanie pozycji tak, żeby nie postawić zwierząt na sobie
        for (int i = 0; i < 30; i++) {
            map.placeAnimal(new Animal(map, new Vector2d(new Random().nextInt(width), new Random().nextInt(height)), 100, genes));
        }

//        map.testFunction();
        int numberOfDays = 0;
        for (int i = 0; i < numberOfDays; i++) {
            map.nextDay();
            System.out.println(map.toString());
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
