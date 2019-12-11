package mainPackage.mapElement.animal;

import mainPackage.mapElement.animal.Animal;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BabyGenesOperator {

    public BabyGenesOperator() {
        List<Integer> randomGenesOrder = IntStream.rangeClosed(0, 32).boxed().collect(Collectors.toList());
        Collections.shuffle(randomGenesOrder);
    }

    public ArrayList<Integer> craftGenesForBaby(Animal firstParent, Animal secondParent) {
        int firstSeparator = new Random().nextInt(32);
        int secondSeparator = new Random().nextInt(32);
        while (firstSeparator == secondSeparator) {
            secondSeparator = new Random().nextInt(32);
        }
        if (firstSeparator > secondSeparator) {
            int p = firstSeparator;
            firstSeparator = secondSeparator;
            secondSeparator = p;
        }


        return this.combineGenes(firstParent, secondParent, firstSeparator, secondSeparator);
    }

    private ArrayList<Integer> combineGenes(Animal firstParent, Animal secondParent, int firstSeparator, int secondSeparator) {
        ArrayList<Integer> babyGenes = new ArrayList<>();
//        System.out.println("babyGenes: " + babyGenes);
        int combinationSequence = new Random().nextInt(3);
        ArrayList<Integer> firstParentGenes = firstParent.getMoveGen();
        ArrayList<Integer> secondParentGenes = (ArrayList<Integer>) secondParent.getMoveGen().clone();
        Collections.shuffle(firstParentGenes);
        Collections.shuffle(secondParentGenes);
        switch (combinationSequence) {
            case 0:
                for (int i = 0; i < secondSeparator; i++) {
                    babyGenes.add(firstParentGenes.get(i));
                }
                for (int i = secondSeparator; i < 32; i++) {
                    babyGenes.add(secondParentGenes.get(i));
                }
                break;
            case 1:
                for (int i = firstSeparator; i < 32; i++) {
                    babyGenes.add(firstParentGenes.get(i));
                }
                for (int i = 0; i < firstSeparator; i++) {
                    babyGenes.add(secondParentGenes.get(i));
                }
                break;
            case 2:
                for (int i = 0; i < firstSeparator; i++) {
                    babyGenes.add(firstParentGenes.get(i));
                }
                for (int i = firstSeparator; i < secondSeparator; i++) {
                    babyGenes.add(secondParentGenes.get(i));
                }
                for (int i = secondSeparator; i < 32; i++) {
                    babyGenes.add(firstParentGenes.get(i));
                }
                break;
        }



        for(int i =0; i<8;i++){
            babyGenes.remove(this.mostCommon(babyGenes));
        }
        for(int i=0;i<8;i++){
            babyGenes.add(i);
        }



        Collections.sort(babyGenes);

//        System.out.println(babyGenes);
        return babyGenes;
    }

    private Integer mostCommon(ArrayList<Integer> list) {
        Map<Integer, Integer> map = new HashMap<>();

        for (Integer t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<Integer, Integer> max = null;

        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getKey();
    }
}
