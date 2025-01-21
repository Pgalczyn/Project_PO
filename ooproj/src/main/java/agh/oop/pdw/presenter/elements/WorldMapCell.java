package agh.oop.pdw.presenter.elements;

import agh.oop.pdw.model.Animal;
import agh.oop.pdw.model.Vector2D;
import agh.oop.pdw.model.WorldElement;
import agh.oop.pdw.model.WorldMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.stream.Collectors;

import static agh.oop.pdw.presenter.ImageLoader.GetImage;

public class WorldMapCell extends Pane {
    private final Vector2D position;
    private final WorldMap map;

    public WorldMapCell(WorldMap map, Vector2D position) {
        this.map = map;
        this.position = position;
        this.setStyle("-fx-padding: 3px;");
        if (map.objectAt(position) != null) {
            List<WorldElement> animals = map.objectAt(position)
                    .stream()
                    .filter(a -> a instanceof Animal)
                    .toList();
            if (!animals.isEmpty()) {
                this.setStyle("-fx-background-color: #ff8585;");
                this.loadImage(animals.getFirst().srcImage());
            } else {
                this.setStyle("-fx-background-color: none;");
                this.loadImage(map.objectAt(position).getFirst().srcImage());
            }
        }
    }


    public void update() {
        this.getChildren().clear();
        this.setStyle("-fx-padding: 3px;");
        if (map.objectAt(position) != null) {
            List<WorldElement> animals = map.objectAt(position)
                    .stream()
                    .filter(a -> a instanceof Animal)
                    .toList();
            if (!animals.isEmpty()) {
                this.setStyle("-fx-background-color: #ff0000;");
                this.loadImage(animals.getFirst().srcImage());
            } else {
                this.setStyle("-fx-background-color: none;");
                this.loadImage(map.objectAt(position).getFirst().srcImage());
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
