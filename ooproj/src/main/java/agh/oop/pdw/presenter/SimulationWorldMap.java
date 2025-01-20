package agh.oop.pdw.presenter;

import agh.oop.pdw.model.SimulationListener;
import agh.oop.pdw.model.Vector2D;
import agh.oop.pdw.model.WorldElement;
import agh.oop.pdw.model.WorldMap;
import agh.oop.pdw.presenter.elements.WorldMapCell;
import agh.oop.pdw.simulation.Simulation;
import agh.oop.pdw.simulation.SimulationProps;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SimulationWorldMap implements SimulationListener {
    private WorldMap worldMap;
    private Simulation simulation;
//    private boolean isRunning = false;
    @FXML
    private GridPane mapGrid;

    @FXML
    private Label allAnimalsAmount;
    @FXML
    private Label allGrassAMount;
    @FXML
    private Label allFreeSpotsAmount;
    @FXML
    private Label mostPopularGeno;
    @FXML
    private Label avgEnergy;
    @FXML
    private Label avgLife;
    @FXML
    private Label avgChildren;


    @FXML
    private Button startStopButton;


    public void setLabels() {
        allAnimalsAmount.setText("liczba wszystkich zwierzaków:" + worldMap.amountOfAnimalsOnTheMap());
        allGrassAMount.setText("liczba wszystkich roślin: " + worldMap.amountOfGrassOnTheMap());
        allFreeSpotsAmount.setText("liczba wolnych pól:" + worldMap.amountOfEmptyFields());
        mostPopularGeno.setText("najpopularniejszy genotyp: " + worldMap.theMostPopularGenotype());
        avgEnergy.setText("średni poziomu energii dla żyjących zwierzaków: " + worldMap.averageLevelOfEnergyOfAnimals());
        avgChildren.setText("średnia długości życia zwierzaków dla martwych zwierzaków: " + worldMap.avgAmountOfChildren());
        avgLife.setText("średnia liczby dzieci dla żyjących zwierzaków: " + worldMap.avgLifeTimeForDeadAnimal());
    }

    private void clearGrid() {
        if (!mapGrid.getChildren().isEmpty()) {
            mapGrid.getChildren().clear();
        }
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }


    private void createConstrains(int MX, int MY) {
        for (int i = 0; i < MX; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / (MX));
            mapGrid.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < MY; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / (MY));
            mapGrid.getRowConstraints().add(rowConstraints);
        }
    }


    public void drawMap() {
        clearGrid();
        createConstrains(worldMap.getWidth(), worldMap.getHeight());
        setLabels();
        for (int i = 0; i < worldMap.getHeight(); i++) {
            for (int j = 0; j < worldMap.getWidth(); j++) {
                WorldMapCell cell = new WorldMapCell(worldMap, new Vector2D(j, i));
                mapGrid.add(cell, j, i);
                GridPane.setHalignment(cell, HPos.CENTER);
            }
        }
    }


    @Override
    public void dayPassed() {
        mapGrid.setGridLinesVisible(true);
        Platform.runLater(this::drawMap);
    }


    public void startStopSimulation() {
        if (simulation.isPaused()) {
            simulation.resume();
        } else {
            simulation.pause();
        }
        startStopButton.setText(simulation.isPaused() ? "START" : "STOP");
    }


    public void setSimulation(Simulation simulation) {
        this.worldMap = simulation.getMap();
        this.simulation = simulation;
        Thread thread = new Thread(simulation);
        thread.start();
        simulation.pause();
        simulation.subscribe(this);
    }
}
