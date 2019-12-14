package mainPackage.map.oasis;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import mainPackage.map.MapObject;

public class Tile extends StackPane {
    public Tile(int width, int height, String innerText, MapObject mapObject) {
        Rectangle border = new Rectangle(width, height);
        border.setFill(null);
        border.setStroke(Color.BLACK);

        Text text = new Text(innerText);

        switch (mapObject){
            case EMPTY:
                border.setFill(Color.LIGHTGRAY);
                break;
            case GRASS:
                border.setFill(Color.GREEN);
                break;
            case ANIMAL:
                border.setFill(Color.ORANGE);
                break;
            case MULTIPLEANIMALS:
                border.setFill(Color.RED);
                break;
        }

        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);
    }
}
