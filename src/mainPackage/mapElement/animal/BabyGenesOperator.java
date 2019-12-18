package mainPackage.mapElement.animal;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BabyGenesOperator {

    public BabyGenesOperator() {
        List<Integer> randomGenesOrder = IntStream.rangeClosed(0, 32).boxed().collect(Collectors.toList());
        Collections.shuffle(randomGenesOrder);
    }

    public ArrayList<Integer> craftGenesForBaby(Animal firstParent, Animal secondParent) {
        HashMap<Integer, Integer> genesMap = getGeneMapOfBothParentsCombinedGenesInRandomOrder(firstParent, secondParent);
        addMissingGenesToMap(genesMap);
        return createArrayListOfGenesForBabyAccordingToGeneMap(genesMap);
    }

    private HashMap<Integer, Integer> getGeneMapOfBothParentsCombinedGenesInRandomOrder(Animal firstParent, Animal secondParent) {
        Random randomIntGenerator = new Random();
        ArrayList<Integer> firstParentGenesShuffled = firstParent.getCopyOfGenotypeShuffled();
        ArrayList<Integer> secondParentGenesShuffled = secondParent.getCopyOfGenotypeShuffled();

        HashMap<Integer, Integer> genesMap = new HashMap<>();
        for (int i = 0; i < 32; i++) {
            if (randomIntGenerator.nextInt() % 2 == 0) {
                genesMap.merge(firstParentGenesShuffled.get(i), 1, Integer::sum);
            } else {
                genesMap.merge(secondParentGenesShuffled.get(i), 1, Integer::sum);
            }
        }
        return genesMap;
    }

    private void addMissingGenesToMap(HashMap<Integer, Integer> genesMap) {
        Random random = new Random();
        ArrayList<Integer> keySet = new ArrayList<>(genesMap.keySet());

        for (Integer i = 0; i < 8; i++) {
            Integer genCount = genesMap.get(i);
            if (genCount == null) {
                int randomGene = keySet.get(random.nextInt(keySet.size()));
                Integer randomGeneValue = genesMap.get(randomGene);

                if (randomGeneValue != 1) {
                    genesMap.remove(randomGene);
                    genesMap.put(i, 1);
                    genesMap.put(randomGene, randomGeneValue - 1);
                } else {

                    while (randomGeneValue == 1) {
                        randomGene = keySet.get(random.nextInt(keySet.size()));
                        randomGeneValue = genesMap.get(randomGene);
                    }
                    genesMap.remove(randomGene);
                    genesMap.put(i, 1);
                    genesMap.put(randomGene, randomGeneValue - 1);

                }

            }
        }
    }

    private ArrayList<Integer> createArrayListOfGenesForBabyAccordingToGeneMap(HashMap<Integer, Integer> genesMap) {
        ArrayList<Integer> babyGenes = new ArrayList<>();
        for(Integer gene:genesMap.keySet()){
            Integer geneCount =genesMap.get(gene);
            for(int i=0; i <geneCount;i++){
                babyGenes.add(gene);
            }
        }
        return babyGenes;
    }
}
