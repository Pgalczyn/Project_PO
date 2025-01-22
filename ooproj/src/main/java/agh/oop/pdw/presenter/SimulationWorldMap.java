package agh.oop.pdw.presenter;

import agh.oop.pdw.model.*;
import agh.oop.pdw.presenter.elements.WorldMapCell;
import agh.oop.pdw.simulation.Simulation;
import agh.oop.pdw.simulation.SimulationProps;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class SimulationWorldMap implements SimulationListener, WorldMapListener {
    private WorldMap worldMap;
    private Simulation simulation;
    private final Map<Vector2D, WorldMapCell> cells = new HashMap<>();
    private StatEngine statEngine;
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
    @FXML
    private VBox statBox;

    public void setLabels() {
        WorldMapInfo informer = worldMap.getInformer();
        informer.getInfoWorldMap(this.simulation);

        allAnimalsAmount.setText(informer.getAmountOfAnimalsOnTheMap());
        allGrassAmount.setText(informer.getAmountOfGrassOnTheMap());
        allFreeSpotsAmount.setText(informer.getAmountOfEmptyFieldsOnTheMap());
        mostPopularGeno.setText(informer.getTheMostPopularGenotype());
        avgEnergy.setText(informer.getAverageLevelOfEnergyOfAnimals());
        avgChildren.setText(informer.getAvgLifeTimeForDeadAnimal());
        avgLife.setText(informer.getAvgAmountOfChildren());


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
        setLabels();
        for (int i = 0; i < worldMap.getHeight(); i++) {
            for (int j = 0; j < worldMap.getWidth(); j++) {
                WorldMapCell cell = new WorldMapCell(worldMap, new Vector2D(j, i), statEngine, this.simulation);
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
        this.statEngine = new StatEngine(statBox);
    }

    @Override
    public void fieldUpdated(Vector2D position) {
        Platform.runLater(() -> {
            synchronized (this) {
                this.cells.get(position).update(simulation.getDay(), simulation.getProps().getStartEnergy());
            }
        });
    }

    @Override
    public void dayPassed(HashSet<Vector2D> updatedFields) {
        Platform.runLater(() -> {
            for (Vector2D position : updatedFields) {
                this.cells.get(position).update(simulation.getDay(), simulation.getProps().getStartEnergy());
            }
            setLabels();
            statEngine.updateLabels();
        });
    }

    @Override
    public void endOfSimulaltion() {
        Platform.runLater(() -> {
            Stage stage = (Stage) startStopButton.getScene().getWindow();
            stage.close();
        });
    }
}
