package mainPackage.main;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import mainPackage.main.json.OasisParameters;
import mainPackage.map.oasis.Oasis;
import mainPackage.map.oasis.tile.TileVisualizer;


public class World extends Application {
    private final Updater updater = new Updater(this);
    private final WindowsCreator windowsCreator = new WindowsCreator(this);
    private OasisParameters oasisParameters = new OasisParameters();
    private Oasis map1;
    private Pane root = new Pane();
        private Pane mapPane = new Pane();
        private Pane statisticsPane = new Pane();
            private Pane textWithStatisticsPane = new Pane();
            private Pane buttonsPane = new Pane();
    private Oasis map2;
    private Pane secondRoot = new Pane();
        private Pane secondMapPane = new Pane();
        private Pane secondsStatisticsPane = new Pane();
            private Pane secondTextWithStatisticsPane = new Pane();
            private Pane secondButtonsPane = new Pane();

    private boolean canNormallyStartSimulation = false;
    private boolean useTwoMaps = false;

    private int windowWidth;

    private int windowHeight;
    private int statisticsInnerWidowHeight;

    private int statisticsButtonsInnerWidowHeight;
    private int statisticsWidth;
    private AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            updater.onUpdate(getMap1(), getMapPane(), getTextWithStatisticsPane());
//            updater.onUpdate(getMap2(), getMAp2Pane());
        }
    };


    @Override
    public void start(Stage primaryStage) throws Exception {
        TileVisualizer.setUpdater(this.updater);

        BufferedReader reader = new BufferedReader(new FileReader("src\\mainPackage\\main\\json\\parameters.json"));
        Gson gson = new Gson();
        this.oasisParameters = gson.fromJson(reader, this.oasisParameters.getClass());

        this.windowHeight = this.oasisParameters.getMapWindowHeight();
        this.windowWidth = this.oasisParameters.getMapWindowWidth();
        this.statisticsWidth = this.oasisParameters.getStatsWindowWidth();

        this.statisticsInnerWidowHeight = this.windowWidth / 2;
        this.statisticsButtonsInnerWidowHeight = this.windowHeight / 2;

        this.map1 = new Oasis(
                this.oasisParameters.getWidth(),
                this.oasisParameters.getHeight(),
                this.oasisParameters.getPlantEnergy(),
                this.oasisParameters.getStartEnergy(),
                this.oasisParameters.getMoveEnergy(),
                this.oasisParameters.getJungleRatio(),
                this.oasisParameters.getNumberOfStartingAnimals(),
                this.oasisParameters.getNumberOfStartingGrass(),
                this.oasisParameters.getStartingGenes(),
                this.oasisParameters.getNumberOfGrassThatGrowsPerDay()
        );

        this.updater.setMapHeight(this.oasisParameters.getHeight());
        this.updater.setMapWidth(this.oasisParameters.getWidth());
        this.updater.setTileHeight(this.windowWidth / this.oasisParameters.getWidth());
        this.updater.setTileWidth(this.windowHeight / this.oasisParameters.getHeight());

        windowsCreator.createStartingWindow(this);
        if(!canNormallyStartSimulation){
            System.out.println("Window was not closed with button.");
            return;
        }

        int startingDay = this.oasisParameters.getSkipToGivenDay();
        for(int i=0;i<startingDay;i++){
            map1.nextDay();
        }


        primaryStage.setTitle("First Map");
        primaryStage.setScene(new Scene(windowsCreator.createContent(this.root, this.map1,this.mapPane,this.textWithStatisticsPane)));
        primaryStage.show();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int dividerX = 5;
        int dividerY = 2;
        primaryStage.setX(screenSize.getWidth() / dividerX);
        primaryStage.setY(screenSize.getHeight() / dividerY - windowHeight / 2);

        if(this.canUseTwoMaps()){
            this.map2 = new Oasis(
                    this.oasisParameters.getWidth(),
                    this.oasisParameters.getHeight(),
                    this.oasisParameters.getPlantEnergy(),
                    this.oasisParameters.getStartEnergy(),
                    this.oasisParameters.getMoveEnergy(),
                    this.oasisParameters.getJungleRatio(),
                    this.oasisParameters.getNumberOfStartingAnimals(),
                    this.oasisParameters.getNumberOfStartingGrass(),
                    this.oasisParameters.getStartingGenes(),
                    this.oasisParameters.getNumberOfGrassThatGrowsPerDay()
            );
            Stage secondaryStage = new Stage();
            secondaryStage.setTitle("Second Map");
            secondaryStage.setX(screenSize.getWidth() / dividerX);
            secondaryStage.setY(screenSize.getHeight() / dividerY - windowHeight / 2);
            secondaryStage.setX(secondaryStage.getX() + 20);
            secondaryStage.setY(secondaryStage.getY() - 20);
        }

    }

    public static void main(String[] args){
        launch(args);
    }

    public int getStatisticsWidth() {
        return statisticsWidth;
    }

    public Pane getRoot() {
        return root;
    }

    public Oasis getMap1() {
        return map1;
    }

    public int getStatisticsInnerWidowHeight() {
        return statisticsInnerWidowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public Updater getUpdater() {
        return updater;
    }

    public int getStatisticsButtonsInnerWidowHeight() {
        return statisticsButtonsInnerWidowHeight;
    }

    public AnimationTimer getAnimationTimer() {
        return animationTimer;
    }

    public Pane getMapPane() {
        return mapPane;
    }

    public Pane getStatisticsPane() {
        return statisticsPane;
    }

    public Pane getTextWithStatisticsPane() {
        return textWithStatisticsPane;
    }

    public Pane getButtonsPane() {
        return buttonsPane;
    }

    public boolean canNormallyStartSimulation() {
        return canNormallyStartSimulation;
    }

    public OasisParameters getOasisParameters() {
        return oasisParameters;
    }

    public void setCanNormallyStartSimulation(boolean canNormallyStartSimulation) {
        this.canNormallyStartSimulation = canNormallyStartSimulation;
    }

    public boolean canUseTwoMaps() {
        return useTwoMaps;
    }

    public void setUseTwoMaps(boolean useTwoMaps) {
        this.useTwoMaps = useTwoMaps;
    }
}