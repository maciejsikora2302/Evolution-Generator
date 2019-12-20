package mainPackage.main;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

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
}