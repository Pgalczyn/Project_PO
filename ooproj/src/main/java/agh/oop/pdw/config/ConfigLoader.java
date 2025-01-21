package agh.oop.pdw.config;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;

public class ConfigLoader {
    public static void list(ChoiceBox box) {
        try {
            Path folderPath = Path.of("src/main/resources/configs");
            if (!folderPath.toFile().exists()) {
                folderPath.toFile().mkdir();
            }
            File folder = new File(folderPath.toString());
            File[] listOfFiles = folder.listFiles(((dir, name) -> name.endsWith(".csv")));
            box.getItems().clear();
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    String fileName = file.getName().replace(".csv", "");
                    box.getItems().add(fileName);
                }
            }
        } catch (Exception e) {
            System.err.println("Error while refreshing config list" + e.getMessage());
        }
    }

    public static SimulationConfig load(String configName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/configs/" + configName + ".csv"))) {
            SimulationConfig config = new SimulationConfig();
            String line = reader.readLine();
            String[] values = line.split(";");
            config.setMapWidth(Integer.parseInt(values[0]));
            config.setMapHeight(Integer.parseInt(values[1]));
            config.setPlantsOnStart(Integer.parseInt(values[2]));
            config.setDailyPlants(Integer.parseInt(values[3]));
            config.setStartEnergy(Integer.parseInt(values[4]));
            config.setEnergyOnEat(Integer.parseInt(values[5]));
            config.setEnergyPerMove(Integer.parseInt(values[6]));
            config.setGenomeLength(Integer.parseInt(values[7]));
            config.setEnergyToBreed(Integer.parseInt(values[8]));
            config.setBreedCost(Integer.parseInt(values[9]));
            config.setStartAnimals(Integer.parseInt(values[10]));
            config.setMinMutations(Integer.parseInt(values[11]));
            config.setMaxMutations(Integer.parseInt(values[12]));
            config.setDayLimit(Integer.parseInt(values[13]));
            config.setSpecialMutation(Boolean.parseBoolean(values[14]));
            config.setMapPoles(Boolean.parseBoolean(values[15]));
            config.setSimulationTimeout(Integer.parseInt(values[16]));
            config.setConfigName(configName);
            return config;
        } catch (Exception e) {
            System.err.println("Error while loading config: " + e.getMessage());
            return null;
        }

    }
}
