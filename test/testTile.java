import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class testTile {

        @Test
    public void tileTests(){
        ArrayList<Integer> testList = new ArrayList<>();
        testList.add(86);
        testList.add(35);
        testList.add(255);
        testList.add(123);
        testList.add(10);
        testList.add(34);
            System.out.println(testList);
        for(int i=0;i<testList.size();i++){
            if(testList.get(i) == 35){
                testList.remove(i);
            }
            if(testList.get(i) == 123){
                testList.remove(i);
            }
        }
            System.out.println(testList);
    }
}
