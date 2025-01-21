package agh.oop.pdw.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvExport {

    public static void saveData(int day,WorldMapInfo worldMapInfo,int simulationID){

        File file = new File("simulation_" + simulationID + ".csv");

        boolean fileExist = file.exists();

        try(FileWriter writer = new FileWriter(file,true)){

            if (!fileExist) {
                writer.write("day,amountOfAnimalsOnTheMap,theMostPopularGenotype,averageLevelOfEnergyOfAnimals,avgAmountOfChildren,avgLifeTimeForDeadAnimal,amountOfGrassOnTheMap,amountOfEmptyFieldsOnTheMap\n");
            }

            writer.append(String.valueOf(day) +"," + String.valueOf(worldMapInfo.getAmountOfAnimalsOnTheMap())+ "," + worldMapInfo.getTheMostPopularGenotype() + "," +
                    String.valueOf(worldMapInfo.getAverageLevelOfEnergyOfAnimals()) + "," + String.valueOf(worldMapInfo.getAvgAmountOfChildren()) + "," + String.valueOf(worldMapInfo.getAvgLifeTimeForDeadAnimal()) + "," +
                    String.valueOf(worldMapInfo.getAmountOfGrassOnTheMap()) + "," + String.valueOf(worldMapInfo.getAmountOfEmptyFieldsOnTheMap() + "\n")
                    );


        }
        catch (IOException e) {
            System.err.println("Error for day: " + day);
            e.printStackTrace();


    }

}}
