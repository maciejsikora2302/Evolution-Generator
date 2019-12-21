package mainPackage.main;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import mainPackage.main.json.StatisticsGeneratorToJSONFile;

import java.io.IOException;

public class WindowsCreator {
    private final World world;

    public WindowsCreator(World world) {
        this.world = world;
    }

    Parent createContent() {
        world.getRoot().setPrefSize(world.getWindowWidth()+world.getStatisticsWidth(), world.getWindowHeight());
        world.getRoot().getChildren().addAll(world.getMapPane(), this.createStats());
        world.getUpdater().onUpdate();
        return world.getRoot();
    }

    Parent createStats() {
        world.getStatisticsPane().setPrefSize(world.getStatisticsWidth(), world.getWindowHeight());

        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(e -> {
            world.getAnimationTimer().stop();
        });
        Button resumeButton = new Button("Resume");
        resumeButton.setOnAction(e -> {
            world.getAnimationTimer().start();
        });

        HBox buttonsBox = new HBox(10);
        buttonsBox.setPrefWidth(world.getStatisticsWidth());
        buttonsBox.setPrefHeight(world.getStatisticsButtonsInnerWidowHeight());
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(pauseButton, resumeButton);

        world.getButtonsPane().getChildren().add(buttonsBox);
        world.getButtonsPane().setTranslateY(world.getStatisticsInnerWidowHeight());

        world.getStatisticsPane().getChildren().addAll(world.getTextWithStatisticsPane(), world.getButtonsPane());

        return world.getStatisticsPane();
    }

    void createStartingWindow(World world) {
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
            world.setCanNormallyStartSimulation(true);
            window.hide();
        });
        boxForClosing.getChildren().addAll(textForClosing,closeButton);


        HBox boxForNDayStats = new HBox(5);
        boxForNDayStats.setAlignment(Pos.CENTER);
        Text generateStatisticsAfterNDaysText = new Text("Press here to generate stats after N days -> ");
        TextField fieldForInputOfNumberOfDaysToPass = new TextField("0");
        Button generateStatisticsAfterNDaysButton = new Button("Generate");
        generateStatisticsAfterNDaysButton.setOnAction(actionEvent -> {
            try{
                Integer numberOfDays = Integer.valueOf(fieldForInputOfNumberOfDaysToPass.getText());
                if(numberOfDays <= 0) throw new NumberFormatException();
                StatisticsGeneratorToJSONFile statisticsGeneratorToJSONFile =
                        new StatisticsGeneratorToJSONFile(numberOfDays, world.getOasisParameters());
                try {
                    statisticsGeneratorToJSONFile.prepareStatisticsForJSONSerialization();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }catch (NumberFormatException ex){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Put an positive integer value different from 0 into text field");

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
}