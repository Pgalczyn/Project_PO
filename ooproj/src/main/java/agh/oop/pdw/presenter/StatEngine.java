package agh.oop.pdw.presenter;

import agh.oop.pdw.model.Animal;
import agh.oop.pdw.model.AnimalStatistics;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;


public class StatEngine {
    private final VBox statBox;
    private Animal animal;

    public StatEngine(VBox box) {
        this.statBox = box;
    }

    public void showStatistics() {
        AnimalStatistics statistics = new AnimalStatistics(animal);
        Pane pane = createStatisticsPane(statistics);
        statBox.getChildren().clear();
        statBox.getChildren().add(pane);
        statBox.setStyle("-fx-spacing: 5px");
    }

    public void updateLabels() {
        if (animal == null || animal.getCurrentEnergy() < 1) {
            return;
        };
        showStatistics();
    }

    private Pane createStatisticsPane(AnimalStatistics stats) {
        VBox statisticsPane = new VBox();
        Label energy = new Label(stats.getEnergy());
        energy.setStyle("-fx-font-size: 11px;");
        statisticsPane.getChildren().add(energy);
        Label age = new Label(stats.getAmountOfDaysAlive());
        age.setStyle("-fx-font-size: 11px;");
        statisticsPane.getChildren().add(age);
        Label children = new Label(stats.getAmountOfChildren());
        children.setStyle("-fx-font-size: 11px;");
        statisticsPane.getChildren().add(children);
        Label descendants = new Label(stats.getAmountOfDescendants());
        descendants.setStyle("-fx-font-size: 11px;");
        statisticsPane.getChildren().add(descendants);
        Label genotype = new Label(stats.getGenotype());
        genotype.setStyle("-fx-font-size: 11px;");
        statisticsPane.getChildren().add(genotype);
        Label activeGene = new Label(stats.getActiveGene());
        activeGene.setStyle("-fx-font-size: 11px;");
        statisticsPane.getChildren().add(activeGene);
        Label position = new Label(stats.getPosition());
        position.setStyle("-fx-font-size: 11px;");
        statisticsPane.getChildren().add(position);

        return statisticsPane;
    }


    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}
