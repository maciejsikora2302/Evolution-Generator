package mainPackage;

import java.util.ArrayList;
import java.util.Random;

public class World {
    public static void main(String[] args) {
        int width = 10;
        int height = 10;
        Oasis map = new Oasis(width, height, 20, 100, 2);
        int[] genes = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7};
        map.placeAnimal(new Animal(map, new Vector2d(0, 3), 100, genes));
        map.placeAnimal(new Animal(map, new Vector2d(4, 7), 100, genes));//TODO: losowanie pozycja
        for (int i = 0; i < 30; i++) {
            map.placeAnimal(new Animal(map, new Vector2d(new Random().nextInt(width), new Random().nextInt(height)), 100, genes));
        }

//        map.testFunction();
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
