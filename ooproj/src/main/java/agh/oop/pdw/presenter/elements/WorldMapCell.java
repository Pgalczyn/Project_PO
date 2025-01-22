package agh.oop.pdw.presenter.elements;

import agh.oop.pdw.model.Animal;
import agh.oop.pdw.model.Exceptions.WrongColorFormatException;
import agh.oop.pdw.model.Vector2D;
import agh.oop.pdw.model.WorldElement;
import agh.oop.pdw.model.WorldMap;
import agh.oop.pdw.presenter.ColorManager;
import agh.oop.pdw.presenter.StatEngine;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Ellipse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static agh.oop.pdw.presenter.ImageLoader.GetImage;

public class WorldMapCell extends Pane {
    private final Vector2D position;
    private final WorldMap map;
    private int lastUpdateDay = 0;
    private VBox statBox;

    public WorldMapCell(WorldMap map, Vector2D position, VBox statBox) {
        this.map = map;
        this.position = position;
        this.statBox = statBox;
        this.setStyle("-fx-padding: 3px;");
        if (map.objectAt(position) != null) {
            List<WorldElement> animals = map.objectAt(position)
                    .stream()
                    .filter(a -> a instanceof Animal)
                    .toList();
            if (!animals.isEmpty()) {
                this.setStyle("-fx-background-color: #ff8585;");
            } else {
                this.setStyle("-fx-background-color: none;");
                this.loadImage(map.objectAt(position).getFirst().srcImage());
            }
        }
        this.setOnMouseClicked(this::handleClick);
    }

    private void handleClick(MouseEvent event) {
        ArrayList<WorldElement> elements = new ArrayList<WorldElement>(map.objectAt(position));
        if (elements.isEmpty()) return;
        Animal animal = elements
                .stream()
                .filter(a -> a instanceof Animal)
                .map(a -> (Animal) a)
                .findFirst()
                .orElse(null);
        if (animal == null) return;
        StatEngine.showStatistics(statBox, animal);
    }


    public void update(int day, int startEnergy) {
        if (day == lastUpdateDay) return;
        lastUpdateDay = day;
        this.getChildren().clear();
        this.setStyle("-fx-padding: 3px; -fx-background-radius: 100%");
        List<WorldElement> obejcts = map.objectAt(position);
        if (obejcts != null) {
            List<WorldElement> animals = obejcts
                    .stream()
                    .filter(a -> a instanceof Animal)
                    .toList();
            if (animals.size() > 1) {
                this.setStyle("-fx-background-color: #3c96ff;-fx-background-radius: 100%");
            } else if (!animals.isEmpty()) {
                Animal animal = (Animal) animals.getFirst();
                try {
                    String color = ColorManager.getColor(Double.min(Double.max(animal.getEnergy(), 0) / (startEnergy * 2), 1.0));
                    this.setStyle("-fx-background-color:" + color + ";-fx-background-radius: 100%");
                } catch (WrongColorFormatException e) {
                    System.out.println("Wrong color format: " + e.getMessage());
                }
//                this.loadImage(animals.getFirst().srcImage());
            } else {
                this.setStyle("-fx-background-color: none;");
                WorldElement element = map.objectAt(position).getFirst();
                if (element == null) return;
                this.loadImage(element.srcImage());
            }
        }
    }

    private void loadImage(String path) {
        ImageView imageView = new ImageView();
        Image image = GetImage(path);
        imageView.setImage(image);
        imageView.fitWidthProperty().bind(this.widthProperty());
        imageView.fitHeightProperty().bind(this.heightProperty());
        imageView.setPreserveRatio(true);
        this.getChildren().add(imageView);
    }


}
