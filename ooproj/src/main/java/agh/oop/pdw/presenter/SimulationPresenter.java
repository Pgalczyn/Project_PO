package agh.oop.pdw.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SimulationPresenter {
    @FXML
    private Button startSimulationButton;

    public void startSimulation(){
        System.out.println("Simulation started");
        System.out.println(startSimulationButton);
    }

}
