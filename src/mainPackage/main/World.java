package mainPackage.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainPackage.mapElement.Grass;
import mainPackage.mapElement.animal.Animal;
import mainPackage.map.oasis.Oasis;
import mainPackage.unused.Vector2d;


public class World extends Application {

    private Oasis map;
    private Pane root = new Pane();


    private int windowWidth = 600;
    private int windowHeight = 600;

    private Parent createContent() {

        root.setPrefSize(windowWidth, windowHeight);

        new AnimationTimer() {
            @Override
            public void handle(long now){
                root.getChildren().clear();
                onUpdate();
            }
        }.start();


        return root;
    }

    private class Tile extends StackPane {
        public Tile(int width, int height, String innerText) {
            Rectangle border = new Rectangle(width, height);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            Text text = new Text(innerText);

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);
        }
    }

    private void onUpdate(){
        this.map.nextDay();


        int mapWidth = this.map.getWidth();
        int mapHeight = this.map.getHeight();

        int tileWidth = windowWidth/mapWidth;
        int tileHeight = windowHeight/mapHeight;


        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                //filling board with tiles

                String innerText;
                Vector2d currentPosition = new Vector2d(j,i);
                if(this.map.isOccupied(currentPosition)){
                    Object object = this.map.objectAt(currentPosition);
                    if (object != null) {
                        if (object instanceof ArrayList && ((ArrayList) object).size()>1 ){
                            innerText = "⚤"; //இ, ∰, ⛧
                        }else if(object instanceof ArrayList && ((ArrayList) object).size() == 1){
                            innerText = ((ArrayList) object).get(0).toString();
                        }else if(object instanceof Grass){
                            innerText = "Ӂ ";
                        }else {
                            innerText = object.toString();
                        }
                    } else {
                        innerText = " ";
                    }
                }else{
                    innerText = " ";
                }


                Tile tile = new Tile(tileWidth, tileHeight, innerText);
                tile.setTranslateX(j * tileWidth);
                tile.setTranslateY(i * tileHeight);

                root.getChildren().add(tile);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\admin\\Documents\\Studia\\Semestr III\\Programowanie obiektowe\\Evolution-Generator\\src\\mainPackage\\main\\parameters.json"));
        Gson gson = new Gson();
        oasisParameters parameters = new oasisParameters();
        parameters = gson.fromJson(reader, parameters.getClass());

        Oasis map = new Oasis(parameters.getWidth(), parameters.getHeight(),
                parameters.getPlantEnergy(),
                parameters.getStartEnergy(),
                parameters.getMoveEnergy(),
                parameters.getJungleRatio(),
                parameters.getNumberOfStartingAnimals(),
                parameters.getNumberOfStartingGrass(),
                parameters.getStartingGenes());
        this.map = map;

        primaryStage.setTitle("Main Window");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();

    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}