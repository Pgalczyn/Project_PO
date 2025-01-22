package agh.oop.pdw.presenter;

import agh.oop.pdw.model.Animal;
import agh.oop.pdw.model.AnimalStatistics;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;


public class StatEngine {
    public static void showStatistics(VBox statisticsPane, Animal animal) {
        AnimalStatistics statistics = new AnimalStatistics(animal);
        Pane pane = createStatisticsPane(statistics);
        statisticsPane.getChildren().clear();
        statisticsPane.getChildren().add(pane);
        statisticsPane.setSpacing(5);
    }

    private static Pane createStatisticsPane(AnimalStatistics stats) {
        VBox statisticsPane = new VBox();
        Label energy = new Label(stats.getEnergy());
        statisticsPane.getChildren().add(energy);
        Label age = new Label(stats.getAmountOfDaysAlive());
        statisticsPane.getChildren().add(age);
        Label children = new Label(stats.getAmountOfChildren());
        statisticsPane.getChildren().add(children);
        Label descendants = new Label(stats.getAmountOfDescendants());
        statisticsPane.getChildren().add(descendants);
        Label genotype = new Label(stats.getGenotype());
        statisticsPane.getChildren().add(genotype);
        Label activeGene = new Label(stats.getActiveGene());
        statisticsPane.getChildren().add(activeGene);


        return statisticsPane;
    }
}
