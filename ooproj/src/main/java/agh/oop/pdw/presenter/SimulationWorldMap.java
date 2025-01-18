package agh.oop.pdw.presenter;

import agh.oop.pdw.model.SimulationListener;
import agh.oop.pdw.model.Vector2D;
import agh.oop.pdw.model.WorldMap;
import agh.oop.pdw.simulation.Simulation;
import agh.oop.pdw.simulation.SimulationProps;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class SimulationWorldMap implements SimulationListener {
    private WorldMap worldMap = new WorldMap(10, 10, 10);
    private Simulation simulation;
    @FXML
    private GridPane mapGrid;

    @FXML
            private Label label;


    int mapWidth = worldMap.getWidth();
    int mapHeight= worldMap.getHeight();
    int colWidth = 30;
    int colHeight = 30;


    public void setMapGrid(GridPane mapGrid) {
        mapGrid.getColumnConstraints().add(new ColumnConstraints(colWidth));
        mapGrid.getRowConstraints().add(new RowConstraints(colHeight));
        mapGrid.setGridLinesVisible(true);
        Label label = new Label("y/x");
        mapGrid.add(label,0,0);
        GridPane.setHalignment(label, HPos.CENTER);
    }

    public void columnsLabels(){
        for(int i = 0; i < mapWidth; i++){
            Label label = new Label(Integer.toString(i));
            mapGrid.add(label,i+1,0);
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(colWidth));
            mapGrid.getRowConstraints().add(new RowConstraints(colHeight));
        }
    }

    public void rowsLabels(){
        for(int i = 0; i<mapHeight; i++){
            Label label = new Label(Integer.toString(i));
            mapGrid.add(label,0,i+1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }


    public void addEmlementsToGrid(){
        for(int i = 0; i<=mapHeight; i++){
            for(int j = 0; j<=mapWidth; j++){
                Vector2D vector = new Vector2D(i, j);
                if(worldMap.isOccupied(vector)){
                    Label label = new Label(worldMap.objectAt(vector).toString());
                    mapGrid.add(label, i+1, j+1 );
                    GridPane.setHalignment(label, HPos.CENTER);
                }
                else {
                    Label label = new Label(" ");
                    mapGrid.add(label, i+1 , j+1);
                    GridPane.setHalignment(label, HPos.CENTER);
                }

            }
        }}
    private void clearGrid() {
        if (!mapGrid.getChildren().isEmpty()) {
            mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        } // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }



     public void drawMap() {
        int MX = mapWidth;
        int MY = mapHeight;
        int mX = 0;
        int mY = 0;
        for (int i = 0; i <= MY - mY + 1; i++) {
            for (int j = 0; j <= MX - mX + 1; j++) {
                Label label = new Label();
                if (i == 0 && j == 0) {
                    label.setText("y/x");
                    mapGrid.getColumnConstraints().add(new ColumnConstraints((double) 400 / (MX - mX + 2)));
                    mapGrid.getRowConstraints().add(new RowConstraints((double) 400 / (MY - mY + 2)));
                } else if (i == 0) {
                    label.setText(String.valueOf(mX + j - 1));
                    mapGrid.getColumnConstraints().add(new ColumnConstraints((double) 400 / (MX - mX + 2)));
                } else if (j == 0) {
                    label.setText(String.valueOf(MY - i + 1));
                    mapGrid.getRowConstraints().add(new RowConstraints((double) 400 / (MY - mY + 2)));
                } else {
                     label.setText(Integer.toString(simulation.getDay()));
                }
                label.setStyle("-fx-background-color: #999");
                mapGrid.add(label, j, i);
                GridPane.setHalignment(label, HPos.CENTER);
            }
        }
    }


    @Override
    public void dayPassed(){
        Platform.runLater(this::drawMap);
    }




    @FXML
    public void startSimulation() {

        // Przykładowe dane do stworzenia obiektu SimulationProps
        SimulationProps props = new SimulationProps(
                10, // mapWidth
                10, // mapHeight
                50, // startEnergy
                false, // isMapPoles
                30, // plants
                10, // energyOnEat
                5,  // plantsPerDay
                1,  // energyPerMove
                10, // startAnimals
                20, // energyToBreed
                10, // energyLossOnBreed
                1,  // minChildrenMutations
                3,  // maxChildrenMutations
                false, // isSpecialMutation
                100, // dayLimit
                10   // animalGenomeLength
        );
        simulation = new Simulation(props);
        simulation.subscribe(this);
        Thread thread = new Thread(simulation);
        thread.start();
        drawMap();
    }

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }



}
