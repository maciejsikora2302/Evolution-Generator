package mainPackage.main;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

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
            if (canUseTwoMaps()) {
                updater.onUpdate(map2, secondMapPane, secondTextWithStatisticsPane);
            }
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
        if (!canNormallyStartSimulation) {
            System.out.println("Window was not closed with button.");
            return;
        }

        int startingDay = this.oasisParameters.getSkipToGivenDay();
        for (int i = 0; i < startingDay; i++) {
            map1.nextDay();
        }


        primaryStage.setTitle("First Map");
        primaryStage.setScene(new Scene(windowsCreator.createContent(
                this.map1,
                this.root, this.mapPane,
                this.statisticsPane,
                this.textWithStatisticsPane,
                this.buttonsPane)));
        primaryStage.show();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int dividerX = 5;
        int dividerY = 2;
        primaryStage.setX(screenSize.getWidth() / dividerX);
        primaryStage.setY(screenSize.getHeight() / dividerY - windowHeight / 2);

        if (this.canUseTwoMaps()) {
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
            secondaryStage.setScene(new Scene(
                    windowsCreator.createContent(
                            this.map2,
                            this.secondRoot,
                            this.secondMapPane,
                            this.secondsStatisticsPane,
                            this.secondTextWithStatisticsPane,
                            this.secondButtonsPane)));
            secondaryStage.show();
            secondaryStage.setX(screenSize.getWidth() / dividerX);
            secondaryStage.setY(screenSize.getHeight() / dividerY - windowHeight / 2);
            secondaryStage.setX(secondaryStage.getX() + 20);
            secondaryStage.setY(secondaryStage.getY() - 20);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    int getStatisticsWidth() {
        return statisticsWidth;
    }

    private Oasis getMap1() {
        return map1;
    }

    int getStatisticsInnerWidowHeight() {
        return statisticsInnerWidowHeight;
    }

    int getWindowWidth() {
        return windowWidth;
    }

    int getWindowHeight() {
        return windowHeight;
    }

    Updater getUpdater() {
        return updater;
    }

    int getStatisticsButtonsInnerWidowHeight() {
        return statisticsButtonsInnerWidowHeight;
    }

    AnimationTimer getAnimationTimer() {
        return animationTimer;
    }

    private Pane getMapPane() {
        return mapPane;
    }

    private Pane getTextWithStatisticsPane() {
        return textWithStatisticsPane;
    }

    OasisParameters getOasisParameters() {
        return oasisParameters;
    }

    void setCanNormallyStartSimulationToTrue() {
        this.canNormallyStartSimulation = true;
    }

    private boolean canUseTwoMaps() {
        return useTwoMaps;
    }

    void setUseTwoMaps(boolean useTwoMaps) {
        this.useTwoMaps = useTwoMaps;
    }
}