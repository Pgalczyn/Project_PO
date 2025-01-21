package agh.oop.pdw.config;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ConfigSaver {
    public static void saveConfig(SimulationConfig config) {
        Path filePath = Path.of("src/main/resources/configs/" + config.getConfigName() + ".csv");
        try(FileWriter writer = new FileWriter(filePath.toString())) {
            writer.write(config.toString());
        } catch (IOException e) {
            System.err.println("Error while saving config" + e.getMessage());
        }
    }
}
