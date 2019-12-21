package mainPackage.main;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import mainPackage.main.json.StatisticsGeneratorToJSONFile;
import mainPackage.map.oasis.Oasis;
import mainPackage.map.oasis.tile.TileVisualizer;


import java.io.IOException;

class WindowsCreator {
    private final World world;

    WindowsCreator(World world) {
        this.world = world;
    }

    Parent createContent(Oasis map,
                         Pane rootPane,
                         Pane mapPane,
                         Pane statisticsPane,
                         Pane textWithStatisticsPane, Pane buttonsPane) {
        rootPane.setPrefSize(world.getWindowWidth() + world.getStatisticsWidth(), world.getWindowHeight());
        rootPane.getChildren().addAll(mapPane, this.createStats(statisticsPane, buttonsPane, textWithStatisticsPane, mapPane, map));
        world.getUpdater().onUpdate(map, mapPane, textWithStatisticsPane);
        return rootPane;
    }

    private Parent createStats(Pane statisticsPane, Pane buttonsPane, Pane textWithStatisticsPane, Pane mapPane, Oasis map) {
        statisticsPane.setPrefSize(world.getStatisticsWidth(), world.getWindowHeight());

        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(e -> {
            world.getAnimationTimer().stop();
        });
        Button resumeButton = new Button("Resume");
        resumeButton.setOnAction(e -> {
            world.getAnimationTimer().start();
        });
        Button showAnimalsWithMostCommonGenotypeButton = new Button("Highlight animals with most common genotype");
        showAnimalsWithMostCommonGenotypeButton.setOnAction(event -> {
            TileVisualizer.setHighlightAnimalsWithMostCommonGenotype(true);
            for (int i = 0; i < world.getUpdater().getMapWidth(); i++) {
                for (int j = 0; j < world.getUpdater().getMapHeight(); j++) {
                    TileVisualizer tile = new TileVisualizer(world.getUpdater().getTileWidth(), world.getUpdater().getTileHeight(), map, j, i, world.getStatisticsWidth());
                    mapPane.getChildren().add(tile);
                }
            }
        });
        Button stopHighlighting = new Button("Stop Highlighting");
        stopHighlighting.setOnAction(event -> {
            TileVisualizer.setHighlightAnimalsWithMostCommonGenotype(false);
        });

        HBox stopResumeButtonsBox = new HBox(10);
        stopResumeButtonsBox.setPrefWidth(world.getStatisticsWidth());
        stopResumeButtonsBox.setAlignment(Pos.CENTER);
        stopResumeButtonsBox.getChildren().addAll(pauseButton, resumeButton);
        HBox highlightButtonsBox = new HBox(10);
        highlightButtonsBox.setPrefWidth(world.getStatisticsWidth());
        highlightButtonsBox.setAlignment(Pos.CENTER);
        highlightButtonsBox.getChildren().addAll(showAnimalsWithMostCommonGenotypeButton, stopHighlighting);
        VBox moreButtonsBox = new VBox(10);
        moreButtonsBox.setPrefWidth(world.getStatisticsWidth());
        moreButtonsBox.setPrefHeight(world.getStatisticsButtonsInnerWidowHeight());
        moreButtonsBox.setAlignment(Pos.CENTER);
        moreButtonsBox.getChildren().addAll(stopResumeButtonsBox, highlightButtonsBox);


        buttonsPane.getChildren().add(moreButtonsBox);
        buttonsPane.setTranslateY(world.getStatisticsInnerWidowHeight());

        statisticsPane.getChildren().addAll(textWithStatisticsPane, buttonsPane);

        return statisticsPane;
    }

    void createStartingWindow(World world) {
        Stage startingWindow = new Stage();
        startingWindow.setTitle("Main menu");
        Pane startingWindowPane = new Pane();
        startingWindowPane.setId("menu");


        int startingWindowWidth = 900;
        int startingWindowHeight = 600;
        int numberOfElementsAtStartingWindow = 2;

        startingWindowPane.setPrefSize(startingWindowWidth, startingWindowHeight);
        //HBox horizontalButtons = new HBox();


        HBox boxForClosing = new HBox();
        boxForClosing.setAlignment(Pos.CENTER);
        boxForClosing.setPrefWidth(startingWindowWidth);
        Label textForClosing = new Label("Click here to close this window and start simulation -> ");
        textForClosing.setId("text");
        Button closeButtonAndUseOneMap = new Button("Use One Map");
        closeButtonAndUseOneMap.setOnAction(event -> {
            Window window = closeButtonAndUseOneMap.getScene().getWindow();
            world.setCanNormallyStartSimulationToTrue();
            window.hide();
        });
        Button closeButtonAndUseTwoMaps = new Button("Use Two Maps");
        closeButtonAndUseTwoMaps.setOnAction(event -> {
            Window window = closeButtonAndUseTwoMaps.getScene().getWindow();
            world.setCanNormallyStartSimulationToTrue();
            world.setUseTwoMaps(true);
            window.hide();
        });

        boxForClosing.getChildren().addAll(textForClosing, closeButtonAndUseOneMap, closeButtonAndUseTwoMaps);


        HBox boxForNDayStats = new HBox(5);
        boxForNDayStats.setAlignment(Pos.CENTER);

        Label generateStatisticsAfterNDaysText = new Label("Input number of days and press \"Generate\"\nto get JSON file with average statistics after given number of days ");
        generateStatisticsAfterNDaysText.setId("text");
        generateStatisticsAfterNDaysText.setLayoutY(generateStatisticsAfterNDaysText.getLayoutY() + 5);

        TextField fieldForInputOfNumberOfDaysToPass = new TextField("0");

        Button generateStatisticsAfterNDaysButton = new Button("Generate");
        generateStatisticsAfterNDaysButton.setOnAction(actionEvent -> {
            try {
                int numberOfDays = Integer.parseInt(fieldForInputOfNumberOfDaysToPass.getText());
                if (numberOfDays <= 0) throw new NumberFormatException();
                StatisticsGeneratorToJSONFile statisticsGeneratorToJSONFile =
                        new StatisticsGeneratorToJSONFile(numberOfDays, world.getOasisParameters());
                try {
                    statisticsGeneratorToJSONFile.prepareStatisticsForJSONSerialization();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (NumberFormatException ex) {
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
        boxForNDayStats.setPrefSize(startingWindowWidth, startingWindowHeight*3/4);


        VBox allElements = new VBox();
        allElements.setAlignment(Pos.CENTER);
        allElements.getChildren().addAll(boxForNDayStats, boxForClosing);
        allElements.setPrefHeight(startingWindowHeight);


        startingWindowPane.getChildren().add(allElements);
        Scene startingWindowScene = new Scene(startingWindowPane);
        startingWindowScene.getStylesheets().addAll(this.getClass().getResource("backgroundMenu.css").toExternalForm());
        startingWindow.setScene(startingWindowScene);
        startingWindow.showAndWait();
    }
}