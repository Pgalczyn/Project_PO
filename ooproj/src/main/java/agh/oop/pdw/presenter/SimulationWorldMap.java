package agh.oop.pdw.presenter;

import agh.oop.pdw.model.SimulationListener;
import agh.oop.pdw.model.Vector2D;
import agh.oop.pdw.model.WorldMap;
import agh.oop.pdw.simulation.Simulation;
import agh.oop.pdw.simulation.SimulationProps;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class SimulationWorldMap implements SimulationListener {
    private WorldMap worldMap;
    private Simulation simulation;
    private boolean isRunning = false;
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


    private void clearGrid() {
        if (!mapGrid.getChildren().isEmpty()) {
            mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        } // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }


    public void setLabels(){
        allAnimalsAmount.setText("liczba wszystkich zwierzaków:" + worldMap.amountOfAnimalsOnTheMap());
        allGrassAMount.setText("liczba wszystkich roślin: " + worldMap.amountOfGrassOnTheMap());
        allFreeSpotsAmount.setText("liczba wolnych pól:" + worldMap.amountOfEmptyFields() );
        mostPopularGeno.setText("najpopularniejszy genotyp: " + worldMap.theMostPopularGenotype());
        avgEnergy.setText("średni poziomu energii dla żyjących zwierzaków: " + worldMap.averageLevelOfEnergyOfAnimals());
        avgChildren.setText("średnia długości życia zwierzaków dla martwych zwierzaków: " + worldMap.avgAmountOfChildren());
        avgLife.setText("średnia liczby dzieci dla żyjących zwierzaków: " + worldMap.avgLifeTimeForDeadAnimal());
    }



    public void drawMap() {
        clearGrid();
        setLabels();
        int MX = worldMap.getWidth();
        int MY = worldMap.getHeight();
        int mX = 0;
        int mY = 0;
        for (int i = 0; i < MY; i++) {
            for (int j = 0; j < MX; j++) {
                Label label = new Label();
                if (i == 0) {
                    ColumnConstraints columnConstraints = new ColumnConstraints();
                    columnConstraints.setPercentWidth(100.0 / (MX));
                    mapGrid.getColumnConstraints().add(columnConstraints);
                }
                if (j == 0) {
                    RowConstraints rowConstraints = new RowConstraints();
                    rowConstraints.setPercentHeight(100.0 / (MY));
                    mapGrid.getRowConstraints().add(rowConstraints);
                }
                label.setText(Integer.toString(simulation.getDay()));
                label.setStyle("-fx-background-color: #999");
                mapGrid.add(label, j, i);
                GridPane.setHalignment(label, HPos.CENTER);
            }
        }
    }


    @Override
    public void dayPassed() {
        Platform.runLater(this::drawMap);
    }


    public void startStopSimulation() {
        Thread thread = new Thread(simulation);
        thread.start();
        if (!isRunning) {
            isRunning = true;
        }
        else if (simulation.isPaused()) {
            simulation.resume();
        } else {
            simulation.pause();
        }
        startStopButton.setText(simulation.isPaused() ? "START" : "STOP");
    }


    public void setSimulation(Simulation simulation) {
        this.worldMap = simulation.getMap();
        this.simulation = simulation;
        simulation.subscribe(this);
    }
}
