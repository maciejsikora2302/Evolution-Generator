package mainPackage.main;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mainPackage.map.oasis.TileVisualizer;
import mainPackage.mapElement.animal.Animal;
import mainPackage.mapElement.animal.AnimalObserver;

public class Updater {
    private final World world;
    private int mapWidth;
    private int mapHeight;
    private int tileWidth;
    private int tileHeight;
    private Pane updatePane = null;
    private boolean updateStatsOfSelectedAnimal = false;

    public void startUpdatingStatsOfSelectedAnimal(Pane updatePane){
        this.updateStatsOfSelectedAnimal = true;
        this.updatePane = updatePane;
    }

    public void stopUpdatingStatsOfSelectedAnimal(){
        this.updateStatsOfSelectedAnimal = false;
        this.updatePane = null;
    }

    private void updateStatsOfSelectedAnimal(){
        this.updatePane.getChildren().clear();
        Animal animal = AnimalObserver.chosenAnimal;
        int windowWidth = 300;
        int windowHeight = 350;

        VBox boxForText = new VBox(30);
        boxForText.setAlignment(Pos.CENTER);
        boxForText.setPrefSize(windowWidth,windowHeight-400);

        Text animalGenotype = new Text(animal.getGenotypeAsString());
        Text animalGenotypeStatistically = new Text(animal.getStatisticalGenotypeValuesAsString());
        Text numberOfChildren = new Text("Number of children: " + animal.getNumberOfChildren());
        Text numberOfDescendants = new Text("Number of descendants: " + animal.getObserver().getNumberOfDescendants());
        Text animalEnergy = new Text("Energy: " + animal.getEnergy());
        Text animalAge;
        if(animal.getEnergy() > 0 ){
            animalAge = new Text("Age: " + animal.getAge());
        }else{
            animalAge = new Text("Died at age: " + animal.getAge());
            this.updateStatsOfSelectedAnimal = false;
        }
        boxForText.getChildren().addAll(animalGenotype,
                animalGenotypeStatistically,
                numberOfChildren,
                numberOfDescendants,
                animalAge,
                animalEnergy
        );
        this.updatePane.getChildren().addAll(boxForText);
    }

    public Updater(World world) {
        this.world = world;
    }

    void statsUpdate() {
        world.getTextWithStatisticsPane().getChildren().clear();

        Text animalsOnMap = new Text("Animals currently alive: " + world.getMap1().getNumberOfAnimalsAtMap());// + " numberOfGenotypes: " + map2.getNumberOfGenotypes());
        Text grassOnMap = new Text("Current amount of grass: " + world.getMap1().getNumberOfGrassAtMap());
        Text mostCommonGenotype = new Text("Most common genotype: " + world.getMap1().getMostCommonGenotype());
        Text numberOfAnimalsWithMostCommonGenotype = new Text("Number of animals with most common genotype: " + world.getMap1().getMostCommonGenotypeQuantity());
        Text averageOfAnimalsEnergy = new Text("Average of animals energy: " + world.getMap1().getAverageOdAnimalsEnergy());
        Text averageOfAnimalLifespan = new Text("Average of animals lifespan: " + world.getMap1().getAverageOfAnimalsLifespan());
        Text averageOfAnimalChildren = new Text("Average of animals children: " + world.getMap1().getAverageOfNumberOfChildren());
        Text currentDay = new Text("Current day: " + world.getMap1().getDay());

        VBox statisticsBox = new VBox(20);
        statisticsBox.setPrefWidth(world.getStatisticsWidth());
        statisticsBox.setPrefHeight(world.getStatisticsInnerWidowHeight());

        statisticsBox.setAlignment(Pos.CENTER);
        statisticsBox.getChildren().addAll(
                animalsOnMap,
                grassOnMap,
                mostCommonGenotype,
                numberOfAnimalsWithMostCommonGenotype,
                averageOfAnimalsEnergy,
                averageOfAnimalLifespan,
                averageOfAnimalChildren,
                currentDay
        );

        world.getTextWithStatisticsPane().getChildren().add(statisticsBox);
    }

    void onUpdate() {
        world.getMap1().nextDay();

        world.getMapPane().getChildren().clear();

        statsUpdate();
        if(updateStatsOfSelectedAnimal){
            updateStatsOfSelectedAnimal();
        }



        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                //filling board with tiles

                TileVisualizer tile = new TileVisualizer(tileWidth, tileHeight, world.getMap1(), j, i, world.getStatisticsWidth());

                world.getMapPane().getChildren().add(tile);
            }
        }
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }
}