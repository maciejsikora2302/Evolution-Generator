package mainPackage.main;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import mainPackage.map.oasis.Oasis;
import mainPackage.map.oasis.TileVisualizer;


public class World extends Application {
    private final Updater updater = new Updater(this);
    private final WindowsCreator windowsCreator = new WindowsCreator(this);
    private Oasis map1;
    private Oasis map2;
    private boolean canNormallyStartSimulation = false;
    private Pane root = new Pane();
        private Pane mapPane = new Pane();
        private Pane statisticsPane = new Pane();
            private Pane textWithStatisticsPane = new Pane();
            private Pane buttonsPane = new Pane();

    private int windowWidth;
    private int windowHeight;

    private int statisticsInnerWidowHeight;
    private int statisticsButtonsInnerWidowHeight;
    private int statisticsWidth;

    private AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            updater.onUpdate();
        }
    };


    @Override
    public void start(Stage primaryStage) throws Exception {
        TileVisualizer.setUpdater(this.updater);

        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\admin\\Documents\\Studia\\Semestr III\\Programowanie obiektowe\\Evolution-Generator\\src\\mainPackage\\main\\parameters.json"));
        Gson gson = new Gson();
        OasisParameters parameters = new OasisParameters();
        parameters = gson.fromJson(reader, parameters.getClass());

        this.windowHeight = parameters.getMapWindowHeight();
        this.windowWidth = parameters.getMapWindowWidth();
        this.statisticsWidth = parameters.getStatsWindowWidth();

        this.statisticsInnerWidowHeight = this.windowWidth / 2;
        this.statisticsButtonsInnerWidowHeight = this.windowHeight / 2;

        this.map1 = new Oasis(
                parameters.getWidth(), parameters.getHeight(),
                parameters.getPlantEnergy(),
                parameters.getStartEnergy(),
                parameters.getMoveEnergy(),
                parameters.getJungleRatio(),
                parameters.getNumberOfStartingAnimals(),
                parameters.getNumberOfStartingGrass(),
                parameters.getStartingGenes(),
                parameters.getNumberOfGrassThatGrowsPerDay());

        this.updater.setMapHeight(parameters.getHeight());
        this.updater.setMapWidth(parameters.getWidth());
        this.updater.setTileHeight(this.windowWidth / parameters.getWidth());
        this.updater.setTileWidth(this.windowHeight / parameters.getHeight());

        createStartingWindow();

        if(!canNormallyStartSimulation){
            System.out.println("Window was not closed normally.");
            return;
        }

        int startingDay = parameters.getSkipToGivenDay();
        for(int i=0;i<startingDay;i++){
            map1.nextDay();
        }

        //MapWindow
        primaryStage.setTitle("First Map");
        primaryStage.setScene(new Scene(windowsCreator.createContent()));
        primaryStage.show();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int dividerX = 5    ;
        int dividerY = 2;
        primaryStage.setX(screenSize.getWidth() / dividerX);
        primaryStage.setY(screenSize.getHeight() / dividerY - windowHeight / 2);
    }

    private void createStartingWindow() {
        Stage startingWindow = new Stage();
        startingWindow.setTitle("Main menu");
        Pane startingWindowPane = new Pane();

        int startingWindowWidth = 500;
        int startingWindowHeight = 600;
        int numberOfElementsAtStartingWindow = 2;

        startingWindowPane.setPrefSize(startingWindowWidth,startingWindowHeight);
        HBox horizontalButtons = new HBox();



        HBox boxForClosing = new HBox();
        boxForClosing.setAlignment(Pos.CENTER);
        boxForClosing.setPrefSize(startingWindowWidth,startingWindowHeight/numberOfElementsAtStartingWindow);
        Text textForClosing = new Text("Click here to close this window and start simulation -> ");
        Button closeButton = new Button("Close");
        closeButton.setOnAction(event ->{
            Window window = closeButton.getScene().getWindow();
            this.canNormallyStartSimulation = true;
            window.hide();
        });
//        closeButton.setAlignment(Pos.CENTER);
        boxForClosing.getChildren().addAll(textForClosing,closeButton);


        HBox boxForNDayStats = new HBox();
        boxForNDayStats.setAlignment(Pos.CENTER);
        Button generateStatisticsAfterNDaysButton = new Button("Generate");
        generateStatisticsAfterNDaysButton.setOnAction(actionEvent -> {

        });
        Text generateStatisticsAfterNDaysText = new Text("Press here to generate stats after N days -> ");
        boxForNDayStats.getChildren().addAll(generateStatisticsAfterNDaysText,generateStatisticsAfterNDaysButton);
        boxForNDayStats.setPrefSize(startingWindowWidth,startingWindowHeight / numberOfElementsAtStartingWindow);


        VBox allElements = new VBox();
        allElements.setAlignment(Pos.CENTER);
        allElements.getChildren().addAll(boxForNDayStats,boxForClosing);




        startingWindowPane.getChildren().add(allElements);
        Scene startingWindowScene = new Scene(startingWindowPane);
        startingWindow.setScene(startingWindowScene);
        startingWindow.showAndWait();
    }

    public static void main(String[] args) throws IOException {
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


//    public Pane getStatsRootPane() {
//        return statsRootPane;
//    }



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
}