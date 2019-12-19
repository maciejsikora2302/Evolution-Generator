package mainPackage.map.oasis;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import mainPackage.map.MapObject;
import mainPackage.mapElement.Grass;
import mainPackage.mapElement.animal.Animal;
import mainPackage.main.Vector2d;

import java.util.ArrayList;

public class TileVisualizer extends StackPane {
    public TileVisualizer(int width, int height, Oasis map1, int j, int i) {

        String innerText;
        MapObject mapObject;

        Vector2d currentPosition = new Vector2d(j, i);
        if (map1.isOccupied(currentPosition)) {
            Object object = map1.objectAt(currentPosition);
            if (object != null) {
                if (object instanceof Tile && ((Tile) object).getNumberOfAnimalsAtTile() > 1) {
                    innerText = "⚤"; //இ, ∰, ⛧
                    mapObject = MapObject.MULTIPLEANIMALS;
                } else if (object instanceof Tile && ((Tile) object).getNumberOfAnimalsAtTile() == 1) {
                    innerText = ((Tile) object).getAnimalWithHighestEnergy().toString();
                    mapObject = MapObject.ANIMAL;
                } else if (object instanceof Grass) {
                    innerText = "Ӂ ";
                    mapObject = MapObject.GRASS;
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

        Rectangle border = new Rectangle(width, height);

        border.setStroke(null);

        Text text = new Text(innerText);

        switch (mapObject){
            case EMPTY:
                if(map1.doesInnerOasisExistsAtGivenPosition(currentPosition)){
                    border.setFill(Color.LIGHTGREEN);
                }else{
                    border.setFill(Color.LIGHTGRAY);
                }
                break;
            case GRASS:
                border.setFill(Color.GREEN);
                break;
            case ANIMAL:
                border.setFill(Color.ORANGE);
//                Animal animal = map1.animals.get(currentPosition);
                break;
            case MULTIPLEANIMALS:
                border.setFill(Color.RED);
                break;
        }

        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);
    }
}
