package mainPackage;

import java.lang.reflect.Array;
import java.util.Arrays;

public class OptionParser {
    public MoveDirection[] parse(String[] inputDirectionsInString) {
        MoveDirection[] directions = new MoveDirection[inputDirectionsInString.length];
        int correctDirections = 0;
        for (int i=0;i<inputDirectionsInString.length;i++) {
            switch (inputDirectionsInString[i]){
                case "f":
                    directions[correctDirections]=MoveDirection.FORWARD;
                    correctDirections+=1;
                    break;
                case "b":
                    directions[correctDirections]=MoveDirection.BACKWARD;
                    correctDirections+=1;
                    break;
                case "r":
                    directions[correctDirections]=MoveDirection.RIGHT;
                    correctDirections+=1;
                    break;
                case "l":
                    directions[correctDirections]=MoveDirection.LEFT;
                    correctDirections+=1;
                    break;
                default:
                    throw new IllegalArgumentException(inputDirectionsInString[i] + " is not legal move specification");
            }
        }
        return Arrays.copyOfRange(directions,0, correctDirections);
    }
}
