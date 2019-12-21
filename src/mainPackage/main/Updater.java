package mainPackage.main;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mainPackage.map.oasis.Oasis;
import mainPackage.map.oasis.tile.TileVisualizer;
import mainPackage.mapElement.animal.Animal;
import mainPackage.mapElement.animal.AnimalObserver;

public class Updater {
    private final World world;
    private int mapWidth;
    private int mapHeight;
    private int tileWidth;
    private int tileHeight;
    private Pane updatePane = null;
    private boolean canUpdateStatsOfSelectedAnimal = false;

    Updater(World world) {
        this.world = world;
    }

    void onUpdate(Oasis map, Pane mapPane, Pane textStatisticsPane) {
        map.nextDay();

        mapPane.getChildren().clear();

        statsUpdate(textStatisticsPane, map);
        if (canUpdateStatsOfSelectedAnimal) {
            updateStatsOfSelectedAnimal();
        }


        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                TileVisualizer tile = new TileVisualizer(tileWidth, tileHeight, map, j, i, world.getStatisticsWidth());
                mapPane.getChildren().add(tile);
            }
        }
    }

    private void statsUpdate(Pane statsPane, Oasis map) {
        statsPane.getChildren().clear();

        Text animalsOnMap = new Text("Animals currently alive: " + map.getNumberOfAnimalsAtMap());
        Text grassOnMap = new Text("Current amount of grass: " + map.getNumberOfGrassAtMap());
        Text mostCommonGenotype = new Text("Most common genotype: " + map.getMostCommonGenotype());
        Text numberOfAnimalsWithMostCommonGenotype = new Text("Number of animals with most common genotype: " + map.getMostCommonGenotypeQuantity());
        Text averageOfAnimalsEnergy = new Text("Average of animals energy: " + map.getAverageOdAnimalsEnergy());
        Text averageOfAnimalLifespan = new Text("Average of animals lifespan: " + map.getAverageOfAnimalsLifespan());
        Text averageOfAnimalChildren = new Text("Average of animals children: " + map.getAverageOfNumberOfChildren());
        Text currentDay = new Text("Current day: " + map.getDay());

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


        statsPane.getChildren().add(statisticsBox);
    }

    public void startUpdatingStatsOfSelectedAnimal(Pane updatePane) {
        this.canUpdateStatsOfSelectedAnimal = true;
        this.updatePane = updatePane;
    }

    public void stopUpdatingStatsOfSelectedAnimal() {
        this.canUpdateStatsOfSelectedAnimal = false;
        this.updatePane = null;
    }

    private void updateStatsOfSelectedAnimal() {
        this.updatePane.getChildren().clear();
        Animal animal = AnimalObserver.chosenAnimal;
        int windowWidth = 300;
        int windowHeight = 350;

        VBox boxForText = new VBox(30);
        boxForText.setAlignment(Pos.CENTER);
        boxForText.setPrefSize(windowWidth, windowHeight - 400);

        Text animalGenotype = new Text(animal.getGenotypeAsString());
        Text animalGenotypeStatistically = new Text(animal.getStatisticalGenotypeValuesAsString());
        Text numberOfChildren = new Text("Number of children: " + animal.getNumberOfChildren());
        Text numberOfDescendants = new Text("Number of descendants: " + animal.getObserver().getNumberOfDescendants());
        Text animalEnergy = new Text("Energy: " + animal.getEnergy());
        Text animalAge;
        if (animal.getEnergy() > 0) {
            animalAge = new Text("Age: " + animal.getAge());
        } else {
            animalAge = new Text("Died at age: " + animal.getAge());
            this.canUpdateStatsOfSelectedAnimal = false;
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

    void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    int getTileWidth() {
        return tileWidth;
    }

    int getTileHeight() {
        return tileHeight;
    }

    int getMapWidth() {
        return mapWidth;
    }

    int getMapHeight() {
        return mapHeight;
    }
}