package mainPackage.map.oasis;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import mainPackage.main.Vector2d;
import mainPackage.map.MapObject;
import mainPackage.mapElement.Grass;
import mainPackage.mapElement.animal.Animal;
import mainPackage.mapElement.animal.AnimalObserver;
import org.stjs.javascript.dom.Col;

class TooltipAndRectangleCreator {
    private TileVisualizer tileVisualizer;
    private int width;
    private int height;
    private Oasis map1;
    private int j;
    private int i;
    private int extraWidthVector;
    private StringBuilder textWithStatisticsForTooltip;
    private Rectangle border;
    private Vector2d currentPosition;
    private MapObject mapObject;
    private Text text;

    public TooltipAndRectangleCreator(TileVisualizer tileVisualizer, int width, int height, Oasis map1, int j, int i, int extraWidthVector, StringBuilder textWithStatisticsForTooltip, Rectangle border, Vector2d currentPosition) {
        this.tileVisualizer = tileVisualizer;
        this.width = width;
        this.height = height;
        this.map1 = map1;
        this.j = j;
        this.i = i;
        this.extraWidthVector = extraWidthVector;
        this.textWithStatisticsForTooltip = textWithStatisticsForTooltip;
        this.border = border;
        this.currentPosition = currentPosition;
    }

    public MapObject getMapObject() {
        return mapObject;
    }

    public Text getText() {
        return text;
    }

    public TooltipAndRectangleCreator invoke() {
        String innerText;
        if (map1.isOccupied(currentPosition)) {
            Object object = map1.objectAt(currentPosition);
            if (object != null) {
                if (object instanceof Tile && ((Tile) object).getNumberOfAnimalsAtTile() > 1) {
                    innerText = "⚤"; //இ, ∰, ⛧
                    border.setFill(Color.PINK);
                    textWithStatisticsForTooltip.append("On this tile there are multiple animals (").
                            append(((Tile) object).getNumberOfAnimalsAtTile()).append(") and are preparing to give birth to new cute animal\n").
                            append("Properties of the strongest animal:\n").
                            append("-> Genotype: ").append(((Tile) object).getAnimalWithHighestEnergy().getGenotypeAsString()).append("\n").
                            append("-> Energy: ").append(((Tile) object).getAnimalWithHighestEnergy().getEnergy()).append("\n").
                            append("Properties of second strongest animal:\n").
                            append("-> Genotype: ").append(((Tile) object).getAnimalWithSecondHighestEnergy().getGenotypeAsString()).append("\n").
                            append("-> Energy: ").append(((Tile) object).getAnimalWithSecondHighestEnergy().getEnergy()).append("\n");
                    mapObject = MapObject.MULTIPLEANIMALS;
                } else if (object instanceof Tile && ((Tile) object).getNumberOfAnimalsAtTile() == 1) {
                    innerText = ((Tile) object).getAnimalWithHighestEnergy().toString();
                    Animal animal = ((Tile) object).getAnimalWithHighestEnergy();
                    textWithStatisticsForTooltip.append("Animal properties:\n");
                    textWithStatisticsForTooltip.append("Genotype: ").append(animal.getGenotypeAsString()).append("\n");
                    textWithStatisticsForTooltip.append("Energy: ").append(animal.getEnergy()).append("\n");
                    textWithStatisticsForTooltip.append("Direction: ").append(animal.getDirection()).append("\n");
                    textWithStatisticsForTooltip.append("Age: ").append(animal.getAge()).append("\n");
                    textWithStatisticsForTooltip.append("Number of children: ").append(animal.getNumberOfChildren()).append("\n");
                    textWithStatisticsForTooltip.append("Currently being observed: ");
                    if(animal.getObserver() == null){
                        textWithStatisticsForTooltip.append("no\n");
                    }else{
                        textWithStatisticsForTooltip.append("yes\n");
                    }
                    int animalEnergy = animal.getEnergy();
                    if(animalEnergy<=0){
                        border.setFill(Color.BLACK);
                        innerText = "\uD83D\uDC80";
                    }else if(animalEnergy <= 255){
                        border.setFill(Color.rgb(255,animalEnergy,0));
                    }else if(animalEnergy <= 510){
                        border.setFill(Color.rgb(255-(animalEnergy-255),255,0));
                    }else {
                        border.setFill(Color.rgb(0,255,0));
                    }
                    if(animal == AnimalObserver.chosenAnimal){
                        border.setFill(Color.BLUE);
                    }

                    mapObject = MapObject.ANIMAL;
                } else if (object instanceof Grass) {
                    innerText = "Ӂ ";
                    textWithStatisticsForTooltip.append("Well. This is grass. \nAnimals eat this.");
                    mapObject = MapObject.GRASS;
                    border.setFill(Color.GREEN);
                } else {
                    innerText = object.toString();
                    mapObject = MapObject.EMPTY;
                }
            } else {
                innerText = " ";
                mapObject = MapObject.EMPTY;
            }
        } else {
            innerText = " ";
            mapObject = MapObject.EMPTY;
        }

        border.setStroke(null);
        text = new Text(innerText);
        if(innerText == "\uD83D\uDC80"){
            text.setFill(Color.WHITE);
        }
        if (mapObject == MapObject.EMPTY) {
            if (map1.doesInnerOasisExistsAtGivenPosition(currentPosition)) {
                textWithStatisticsForTooltip.append("This tile belongs to jungle");
                border.setFill(Color.LIGHTGREEN);
            } else {
                textWithStatisticsForTooltip.append("This is the most boring tile\nanimals can move here");
                border.setFill(Color.LIGHTGRAY);
            }
        }

        tileVisualizer.setTranslateX(j * width + extraWidthVector);
        tileVisualizer.setTranslateY(i * height);
        return this;
    }
}
