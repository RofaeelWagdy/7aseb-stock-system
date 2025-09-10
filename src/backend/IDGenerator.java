package backend;

import backend.constants.FileNames;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IDGenerator implements FileNames {
    private static final String ID_COUNTER_FILENAME = "id_counters.txt";
    private static final String TEAM_PREFIX = "T";
    private static final String SHARE_PREFIX = "S";
    private static final int STARTING_NUMBER = 101;
    
    private int teamCounter;
    private int shareCounter;
    
    public IDGenerator() {
    }

    public String generateTeamID() {
        String teamID = TEAM_PREFIX + teamCounter;
        teamCounter++;
//        saveCountersToFile();
        return teamID;
    }

    public String generateShareID() {
        String shareID = SHARE_PREFIX + shareCounter;
        shareCounter++;
//        saveCountersToFile();
        return shareID;
    }

    private void loadCountersFromFile() {
        try {
            if (Files.exists(Paths.get(ID_COUNTER_FILENAME))) {
                String fileContent = Files.readString(Paths.get(ID_COUNTER_FILENAME));
                if (!fileContent.isBlank()) {
                    String[] lines = fileContent.split("\n");
                    if (lines.length >= 2) {
                        teamCounter = Integer.parseInt(lines[0].trim());
                        shareCounter = Integer.parseInt(lines[1].trim());
                        return;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading ID counters, using default values");
        }
        
        // Initialize with starting values if file doesn't exist or is corrupted
        teamCounter = STARTING_NUMBER;
        shareCounter = STARTING_NUMBER;
        saveCountersToFile();
    }

    private void saveCountersToFile() {
        try (FileWriter writer = new FileWriter(ID_COUNTER_FILENAME)) {
            writer.write(teamCounter + "\n");
            writer.write(shareCounter + "\n");
        } catch (IOException e) {
            System.out.println("Error saving ID counters: " + e.getMessage());
        }
    }

    public int getTeamCounter() {
        return teamCounter;
    }

    public int getShareCounter() {
        return shareCounter;
    }

    public void resetCounters() {
        teamCounter = STARTING_NUMBER;
        shareCounter = STARTING_NUMBER;
//        saveCountersToFile();
    }
}