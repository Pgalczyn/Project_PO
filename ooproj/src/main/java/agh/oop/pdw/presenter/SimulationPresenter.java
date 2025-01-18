package agh.oop.pdw.presenter;

import agh.oop.pdw.simulation.SimulationEngine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SimulationPresenter {
    private SimulationEngine engine;


    public void openSimulationWindow(){
        System.out.println("HEHEHE");
        engine.runSimulation();
    }

    public void setEngine(SimulationEngine engine){
        this.engine = engine;
    }


}
