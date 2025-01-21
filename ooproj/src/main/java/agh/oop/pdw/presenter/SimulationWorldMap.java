package agh.oop.pdw.presenter;

import agh.oop.pdw.model.*;
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

import java.util.*;

public class SimulationWorldMap implements SimulationListener, WorldMapListener {
    private WorldMap worldMap;
    private Simulation simulation;
    private final Map<Vector2D, WorldMapCell> cells = new HashMap<>();
//    private boolean isRunning = false;
    @FXML
    private GridPane mapGrid;

    @FXML
    private Label allAnimalsAmount;
    @FXML
    private Label allGrassAmount;
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


//    public void setLabels() {
//        WorldMapInfo informer = worldMap.getInformer();
//        informer.getInfoWorldMap(this.simulation);
//        allAnimalsAmount.setText("liczba wszystkich zwierzaków:" + informer.getAmountOfAnimalsOnTheMap());
//        allGrassAmount.setText("liczba wszystkich roślin: " + informer.getAmountOfGrassOnTheMap());
//        allFreeSpotsAmount.setText("liczba wolnych pól:" + informer.getAmountOfEmptyFieldsOnTheMap());
//        mostPopularGeno.setText("najpopularniejszy genotyp: " + informer.getTheMostPopularGenotype());
//        avgEnergy.setText("średni poziomu energii dla żyjących zwierzaków: " + informer.getAverageLevelOfEnergyOfAnimals());
//        avgChildren.setText("średnia długości  " + informer.getAvgLifeTimeForDeadAnimal());
//        avgLife.setText("średnia liczby dzieci dla żyjących zwierzaków: " + informer.getAvgAmountOfChildren());
//    }

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


    public void Initialize() {
        createConstrains(worldMap.getWidth(), worldMap.getHeight());
//        setLabels();
        for (int i = 0; i < worldMap.getHeight(); i++) {
            for (int j = 0; j < worldMap.getWidth(); j++) {
                WorldMapCell cell = new WorldMapCell(worldMap, new Vector2D(j, i));
                cells.put(new Vector2D(j, i), cell);
                mapGrid.add(cell, j, i);
                GridPane.setHalignment(cell, HPos.CENTER);
            }
        }
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
        Platform.runLater(this::Initialize);
        simulation.pause();
        simulation.subscribe(this);
        simulation.getMap().addListener(this);
    }

    @Override
    public void fieldUpdated(Vector2D position) {
        Platform.runLater(() -> {
            synchronized (this) {
                this.cells.get(position).update(simulation.getDay());
            }
        });
    }

    @Override
    public void dayPassed(HashSet<Vector2D> updatedFields) {
        Platform.runLater(() -> {
            for (Vector2D position : updatedFields) {
                this.cells.get(position).update(simulation.getDay());
            }
           // setLabels();
        });
    }
}
