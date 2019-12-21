package mainPackage.map.oasis.tile;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainPackage.main.Updater;
import mainPackage.map.oasis.Oasis;
import mainPackage.mapElement.MapObject;
import mainPackage.mapElement.animal.Animal;
import mainPackage.main.Vector2d;
import mainPackage.mapElement.animal.AnimalObserver;

public class TileVisualizer extends StackPane {
    public static void setUpdater(Updater updater) {
        TileVisualizer.updater = updater;
    }
    private static Updater updater;


    public TileVisualizer(int width, int height, Oasis map1, int j, int i, int extraWidthVector) {

        String innerText;
        StringBuilder textWithStatisticsForTooltip = new StringBuilder();
        MapObject mapObject;
        Rectangle border = new Rectangle(width, height);


        Vector2d currentPosition = new Vector2d(j, i);
        TooltipAndRectangleCreator tooltipAndRectangleCreator = new TooltipAndRectangleCreator(this, width, height, map1, j, i, extraWidthVector, textWithStatisticsForTooltip, border, currentPosition).invoke();
        mapObject = tooltipAndRectangleCreator.getMapObject();
        Text text = tooltipAndRectangleCreator.getText();

        Tooltip statsOfTile = new Tooltip(textWithStatisticsForTooltip.toString());

        this.setOnMousePressed(eventHandler -> {
            if (eventHandler.getButton() == MouseButton.PRIMARY) {
                statsOfTile.show(this, eventHandler.getScreenX(), eventHandler.getScreenY());
            } else if (eventHandler.getButton() == MouseButton.SECONDARY && mapObject == MapObject.ANIMAL) {
                createWindowForChosenAnimal(map1, currentPosition);
            }

        });

        this.setOnMouseReleased(eventHandler -> {
            statsOfTile.hide();
        });


        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);
    }

    private void createWindowForChosenAnimal(Oasis map1, Vector2d currentPosition) {
        Tile tile = (Tile) map1.objectAt(currentPosition);
        Animal animal = tile.getAnimalWithHighestEnergy();
        animal.attachObserver(new AnimalObserver(animal));

        int windowWidth = 300;
        int windowHeight = 350;
        Stage windowToFollowChosenAnimal = new Stage();
        windowToFollowChosenAnimal.setAlwaysOnTop(true);


        Pane paneToFollowChosenAnimal = new Pane();
        paneToFollowChosenAnimal.setPrefWidth(windowWidth);
        Pane buttonPane = new Pane();
        buttonPane.setPrefWidth(windowWidth);
        buttonPane.setPrefHeight(50);
        buttonPane.setTranslateX(windowWidth/4);
        VBox paneBox = new VBox(30);
        paneBox.setAlignment(Pos.CENTER);
        paneBox.setPrefWidth(windowWidth);
        paneBox.setPrefHeight(windowHeight);

        Pane rootPaneForFollowingChosenAnimal = new Pane();


        VBox boxForText = new VBox(30);
        boxForText.setAlignment(Pos.CENTER);
        boxForText.setPrefSize(windowWidth,windowHeight-400);

        Text animalGenotype = new Text(animal.getGenotypeAsString());
        Text animalGenotypeStatistically = new Text(animal.getStatisticalGenotypeValuesAsString());
        Text numberOfChildren = new Text("Number of children: " + animal.getNumberOfChildren());
        Text numberOfDescendants = new Text("Number of descendants: " + animal.getObserver().getNumberOfDescendants());
        Text animalAge = new Text("Age: " + animal.getAge());
        Text animalEnergy = new Text("Energy: " + animal.getEnergy());
        boxForText.getChildren().addAll(
                animalGenotype,
                animalGenotypeStatistically,
                numberOfChildren,
                numberOfDescendants,
                animalAge,
                animalEnergy);

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        Button closingButton = new Button("Close and stop following");
        closingButton.setOnAction(event -> {
            animal.getObserver().detachObserverFromAnimals();
            windowToFollowChosenAnimal.hide();
            updater.stopUpdatingStatsOfSelectedAnimal();
        });
        buttons.getChildren().addAll(closingButton);


        paneToFollowChosenAnimal.getChildren().addAll(boxForText,buttons);
        buttonPane.getChildren().addAll(buttons);

        paneBox.getChildren().addAll(paneToFollowChosenAnimal,buttonPane);
        rootPaneForFollowingChosenAnimal.getChildren().addAll(paneBox);


        Scene sceneToFollowChosenAnimal = new Scene(rootPaneForFollowingChosenAnimal,windowWidth,windowHeight);
        updater.startUpdatingStatsOfSelectedAnimal(paneToFollowChosenAnimal);

        windowToFollowChosenAnimal.setTitle("Following Animal");
        windowToFollowChosenAnimal.setScene(sceneToFollowChosenAnimal);
        windowToFollowChosenAnimal.show();
    }

}
