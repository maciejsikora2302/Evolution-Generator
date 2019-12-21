package mainPackage.main.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mainPackage.map.oasis.Oasis;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class StatisticsGeneratorToJSONFile {
    private Integer numberOfDays;
    private transient OasisParameters oasisParameters;
    private BigInteger allAnimals = new BigInteger("0");
    private BigInteger allGrass = new BigInteger("0");
    private String mostCommonGenotype;
    private BigInteger sumOfAverageAnimalEnergy = new BigInteger("0");
    private BigInteger sumOfAverageLifespan = new BigInteger("0");
    private BigDecimal sumOfAverageChildren = new BigDecimal("0");


    public StatisticsGeneratorToJSONFile(int numberOfDays, OasisParameters oasisParameters){
        this.numberOfDays = numberOfDays;
        this.oasisParameters = oasisParameters;
    }

    public void prepareStatisticsForJSONSerialization() throws IOException {
        Oasis map = new Oasis(
                this.oasisParameters.getWidth(), this.oasisParameters.getHeight(),
                this.oasisParameters.getPlantEnergy(),
                this.oasisParameters.getStartEnergy(),
                this.oasisParameters.getMoveEnergy(),
                this.oasisParameters.getJungleRatio(),
                this.oasisParameters.getNumberOfStartingAnimals(),
                this.oasisParameters.getNumberOfStartingGrass(),
                this.oasisParameters.getStartingGenes(),
                this.oasisParameters.getNumberOfGrassThatGrowsPerDay());
        for(int i=0; i<numberOfDays ;i++){
            this.allAnimals = this.allAnimals.add(BigInteger.valueOf(map.getNumberOfAnimalsAtMap()));
            this.allGrass = this.allGrass.add(BigInteger.valueOf(map.getNumberOfGrassAtMap()));
            this.sumOfAverageAnimalEnergy = this.sumOfAverageAnimalEnergy.add(BigInteger.valueOf(map.getAverageOdAnimalsEnergy()));
            this.sumOfAverageLifespan = this.sumOfAverageLifespan.add(BigInteger.valueOf(map.getAverageOfAnimalsLifespan()));
            this.sumOfAverageChildren = this.sumOfAverageChildren.add(BigDecimal.valueOf(map.getAverageOfNumberOfChildren()));
            map.nextDay();
        }
        this.mostCommonGenotype = map.getMostCommonGenotype();
        this.allAnimals = this.allAnimals.divide(BigInteger.valueOf(numberOfDays));
        this.allGrass = this.allGrass.divide(BigInteger.valueOf(numberOfDays));
        this.sumOfAverageAnimalEnergy = this.sumOfAverageAnimalEnergy.divide(BigInteger.valueOf(numberOfDays));
        this.sumOfAverageLifespan = this.sumOfAverageLifespan.divide(BigInteger.valueOf(numberOfDays));
        this.sumOfAverageChildren = this.sumOfAverageChildren.divide(BigDecimal.valueOf(numberOfDays), 6);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String pathToFileWithFilename = "src\\mainPackage\\main\\json\\" +
                "statisticsAfter" +
                numberOfDays +
                "Days.json";
        FileWriter fileWriter = new FileWriter(pathToFileWithFilename);
        gson.toJson(this, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }
}
