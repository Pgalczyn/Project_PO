package agh.oop.pdw.simulation;

import agh.oop.pdw.presenter.SimulationPresenter;
import agh.oop.pdw.presenter.SimulationWorldMap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SimulationWindow extends Application{
    private Simulation simulation;
    public SimulationWindow(Stage stage, Simulation simulation) {
        this.simulation = simulation;
        try {
            this.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("SimulationMapGridPaneScene.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationWorldMap presenter = loader.getController();
        presenter.setSimulation(simulation);
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

}
