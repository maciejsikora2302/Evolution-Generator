package mainPackage.main;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.google.gson.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainPackage.map.MapObject;
import mainPackage.map.oasis.Tile;
import mainPackage.mapElement.Grass;
import mainPackage.map.oasis.Oasis;
import mainPackage.unused.Vector2d;


public class World extends Application {

    private Oasis map1;
    private Oasis map2;
    private Pane root = new Pane();

    private Pane statsRootPane = new Pane();
    private Pane statsPane = new Pane();
    private Pane buttonsPane = new Pane();

    private int windowWidth = 500;
    private int windowHeight = 500;

    private int statisticsInnerWidowHeight = windowHeight / 2;
    private int statisticsButtonsInnerWidowHeight = windowHeight / 2;
    private int statisticsWidth = 250;

    private AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            root.getChildren().clear();
            onUpdate();
        }
    };

    private Parent createContent() {
        root.setPrefSize(windowWidth, windowHeight);
        onUpdate();
        return root;
    }

    private Parent createStats(){
        statsRootPane.setPrefSize(statisticsWidth,windowHeight);

        Text animalsOnMap = new Text("Animals currenty alive on map: " + map1.getNumberOfAnimalsAtMap());

        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(e -> {
            animationTimer.stop();
        });
        Button resumeButton = new Button("Resume");
        resumeButton.setOnAction(e -> {
            animationTimer.start();
        });

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(pauseButton,resumeButton);
        buttons.setAlignment(Pos.CENTER);

        VBox statisticsBox = new VBox(20);
        statisticsBox.setPrefWidth(statisticsWidth);
        statisticsBox.setPrefHeight(statisticsInnerWidowHeight);

        HBox buttonsBox = new HBox(10);
        buttonsBox.setPrefWidth(statisticsWidth);
        buttonsBox.setPrefHeight(statisticsButtonsInnerWidowHeight);


        statisticsBox.setAlignment(Pos.CENTER);
        statisticsBox.getChildren().addAll(animalsOnMap);

        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(pauseButton, resumeButton);

        statsPane.getChildren().add(statisticsBox);
        buttonsPane.getChildren().add(buttonsBox);
        buttonsPane.setTranslateY(statisticsInnerWidowHeight);

        statsRootPane.getChildren().addAll(statsPane, buttonsPane);

        return statsRootPane;
    }

    private void statsUpdate(){
        statsPane.getChildren().clear();

        Text animalsOnMap = new Text("Animals currenty alive on map: " + map1.getNumberOfAnimalsAtMap());


        VBox statisticsBox = new VBox(20);
        statisticsBox.setPrefWidth(statisticsWidth);
        statisticsBox.setPrefHeight(statisticsInnerWidowHeight);

        statisticsBox.setAlignment(Pos.CENTER);
        statisticsBox.getChildren().addAll(animalsOnMap);

        statsPane.getChildren().add(statisticsBox);


    }

    private void onUpdate(){
        this.map1.nextDay();

        statsUpdate();

        int mapWidth = this.map1.getWidth();
        int mapHeight = this.map1.getHeight();

        int tileWidth = windowWidth/mapWidth;
        int tileHeight = windowHeight/mapHeight;

        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                //filling board with tiles

                String innerText;
                MapObject mapObject;

                Vector2d currentPosition = new Vector2d(j,i);
                if(this.map1.isOccupied(currentPosition)){
                    Object object = this.map1.objectAt(currentPosition);
                    if (object != null) {
                        if (object instanceof ArrayList && ((ArrayList) object).size()>1 ){
                            innerText = "⚤"; //இ, ∰, ⛧
                            mapObject = MapObject.MULTIPLEANIMALS;
                        }else if(object instanceof ArrayList && ((ArrayList) object).size() == 1){
                            innerText = ((ArrayList) object).get(0).toString();
                            mapObject = MapObject.ANIMAL;
                        }else if(object instanceof Grass){
                            innerText = "Ӂ ";
                            mapObject = MapObject.GRASS;
                        }else {
                            innerText = object.toString();
                            mapObject = MapObject.EMPTY;
                        }
                    } else {
                        innerText = " ";
                        mapObject = MapObject.EMPTY;
                    }
                }else{
                    innerText = " ";
                    mapObject = MapObject.EMPTY;
                }


                Tile tile = new Tile(tileWidth, tileHeight, innerText, mapObject);
                tile.setTranslateX(j * tileWidth);
                tile.setTranslateY(i * tileHeight);

                root.getChildren().add(tile);
            }
        }
    }
    //============================================

    //============================================

    @Override
    public void start(Stage primaryStage) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\admin\\Documents\\Studia\\Semestr III\\Programowanie obiektowe\\Evolution-Generator\\src\\mainPackage\\main\\parameters.json"));
        Gson gson = new Gson();
        oasisParameters parameters = new oasisParameters();
        parameters = gson.fromJson(reader, parameters.getClass());

        this.map1 = new Oasis(
                parameters.getWidth(), parameters.getHeight(),
                parameters.getPlantEnergy(),
                parameters.getStartEnergy(),
                parameters.getMoveEnergy(),
                parameters.getJungleRatio(),
                parameters.getNumberOfStartingAnimals(),
                parameters.getNumberOfStartingGrass(),
                parameters.getStartingGenes(),
                parameters.getNumberOfGrassThatGrowsPerDay());





//        BufferedReader reader2 = new BufferedReader(new FileReader("C:\\Users\\admin\\Documents\\Studia\\Semestr III\\Programowanie obiektowe\\Evolution-Generator\\src\\mainPackage\\main\\parametersSecondMap.json"));
//        Gson gson2 = new Gson();
//        oasisParameters parametersSecondMap = new oasisParameters();
//        parametersSecondMap = gson2.fromJson(reader2, parametersSecondMap.getClass());
//        this.map2 = new Oasis(
//                parametersSecondMap.getWidth(), parametersSecondMap.getHeight(),
//                parametersSecondMap.getPlantEnergy(),
//                parametersSecondMap.getStartEnergy(),
//                parametersSecondMap.getMoveEnergy(),
//                parametersSecondMap.getJungleRatio(),
//                parametersSecondMap.getNumberOfStartingAnimals(),
//                parametersSecondMap.getNumberOfStartingGrass(),
//                parametersSecondMap.getStartingGenes(),
//                parametersSecondMap.getNumberOfGrassThatGrowsPerDay());
//
//
//        Stage secondaryStage = new Stage();
//        secondaryStage.setTitle("Second Map");
//        secondaryStage.setScene(new Scene(createContent()));



        primaryStage.setTitle("First Map");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int dividerX = 3;
        int dividerY = 2;

        primaryStage.setX(screenSize.getWidth() / dividerX);
        primaryStage.setY(screenSize.getHeight() / dividerY - windowHeight / 2);


        Stage statStage = new Stage();
        statStage.setTitle("Statstics");
        statStage.setScene(new Scene(createStats()));
        statStage.show();



        statStage.setX(screenSize.getWidth() / dividerX - statisticsWidth - 5);
        statStage.setY(screenSize.getHeight() / dividerY - windowHeight / 2);
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}