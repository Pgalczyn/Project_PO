package agh.oop.pdw.simulation;

import agh.oop.pdw.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine extends Application{
    private List<Thread> threads = new ArrayList<Thread>();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter mainScreenPresenter = loader.getController();
        mainScreenPresenter.setEngine(this);
        configureStage(stage, viewRoot);
        stage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

    public void runSimulation(SimulationProps props) {
        System.out.println("Running simulation engine");
        Stage newStage = new Stage();
        Simulation newSimulation = new Simulation(props);
        SimulationWindow window = new SimulationWindow(newStage, newSimulation);
    }

}
