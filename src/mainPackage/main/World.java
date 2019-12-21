package mainPackage.main;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import mainPackage.map.oasis.Oasis;
import mainPackage.map.oasis.TileVisualizer;


public class World extends Application {
    private final Updater updater = new Updater(this);
    private final WindowsCreator windowsCreator = new WindowsCreator(this);
    private OasisParameters oasisParameters = new OasisParameters();
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

        BufferedReader reader = new BufferedReader(new FileReader("src\\mainPackage\\main\\parameters.json"));
        Gson gson = new Gson();
//        OasisParameters parameters = new OasisParameters();
        this.oasisParameters = gson.fromJson(reader, this.oasisParameters.getClass());

        this.windowHeight = this.oasisParameters.getMapWindowHeight();
        this.windowWidth = this.oasisParameters.getMapWindowWidth();
        this.statisticsWidth = this.oasisParameters.getStatsWindowWidth();

        this.statisticsInnerWidowHeight = this.windowWidth / 2;
        this.statisticsButtonsInnerWidowHeight = this.windowHeight / 2;

        this.map1 = new Oasis(
                this.oasisParameters.getWidth(), this.oasisParameters.getHeight(),
                this.oasisParameters.getPlantEnergy(),
                this.oasisParameters.getStartEnergy(),
                this.oasisParameters.getMoveEnergy(),
                this.oasisParameters.getJungleRatio(),
                this.oasisParameters.getNumberOfStartingAnimals(),
                this.oasisParameters.getNumberOfStartingGrass(),
                this.oasisParameters.getStartingGenes(),
                this.oasisParameters.getNumberOfGrassThatGrowsPerDay());

        this.updater.setMapHeight(this.oasisParameters.getHeight());
        this.updater.setMapWidth(this.oasisParameters.getWidth());
        this.updater.setTileHeight(this.windowWidth / this.oasisParameters.getWidth());
        this.updater.setTileWidth(this.windowHeight / this.oasisParameters.getHeight());

        createStartingWindow();

        if(!canNormallyStartSimulation){
            System.out.println("Window was not closed with button.");
            return;
        }

        int startingDay = this.oasisParameters.getSkipToGivenDay();
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


        HBox boxForNDayStats = new HBox(5);
        boxForNDayStats.setAlignment(Pos.CENTER);
        Text generateStatisticsAfterNDaysText = new Text("Press here to generate stats after N days -> ");
        TextField fieldForInputOfNumberOfDaysToPass = new TextField("0");
        Button generateStatisticsAfterNDaysButton = new Button("Generate");
        generateStatisticsAfterNDaysButton.setOnAction(actionEvent -> {
            try{
                Integer numberOfDays = Integer.valueOf(fieldForInputOfNumberOfDaysToPass.getText());
                if(numberOfDays == 0) throw new NumberFormatException();
                //todo nałożyć ograniczenie że tylko numerki mogą być wprowadzane
                StatisticsGeneratorToJSONFile statisticsGeneratorToJSONFile = new StatisticsGeneratorToJSONFile(numberOfDays, this.oasisParameters);
                try {
                    statisticsGeneratorToJSONFile.prepareStatisticsForJSONSerialization();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }catch (NumberFormatException ex){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Put an integer value different from 0 into text field");

                alert.showAndWait();
            }

        });



        boxForNDayStats.getChildren().addAll(
                generateStatisticsAfterNDaysText,
                fieldForInputOfNumberOfDaysToPass,
                generateStatisticsAfterNDaysButton
        );
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