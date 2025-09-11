package backend;

import backend.constants.FileNames;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogsWriter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    // Method to append a log entry to the file
    private void appendLog(String logMessage) {
        try (FileWriter writer = new FileWriter(FileNames.LOGS_FILENAME, true)) { // true enables append mode

            String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
            writer.write("[" + timestamp + "] \t" + logMessage + System.lineSeparator());

        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //    logs for creating a team
    public void blogCreatingTeam(String team_name, String generatedTeamId) {
        appendLog("Team Created, Team Name: \"" + team_name + "\", with Team ID: \"" + generatedTeamId + "\"");
    }

    //    logs for buying shares
    public void blogBuyingShares(String buyerTeam, String fromTeam, int quantity, double singleSharePrice, double totalSharePrice) {
        appendLog("Shares Bought, Buyer Team Name: \"" + buyerTeam + "\", From Team Name: \"" + fromTeam + "\", Quantity: \"" + quantity + "\", Single Share Price\"" + singleSharePrice + "\", Total Share Price\"" + totalSharePrice + "\"");
    }

    //    logs for selling shares
    public void blogSellingShares(String buyerTeam, String fromTeam, int quantity, double singleSharePrice, double totalSharePrice) {
        appendLog("Shares Sold, Buyer Team Name: \"" + buyerTeam + "\", From Team Name: \"" + fromTeam + "\", Quantity: \"" + quantity + "\", Single Share Price\"" + singleSharePrice + "\", Total Share Price\"" + totalSharePrice + "\"");
    }

    //    logs for adding percent to the share price
    public void blogAddingPercentToSharePrice(String teamName, double oldSharePrice, double percentAdded, double newSharePrice) {
        appendLog("Share price Increased, Team Name: \"" + teamName + "\", Old Share Price: \"" + oldSharePrice + "\", Percent Added: \"" + percentAdded + "\", New Share Price:\"" + newSharePrice + "\"");
    }

    //    logs for subtracting percent from the share price
    public void blogSubtractingPercentFromSharePrice(String teamName, double oldSharePrice, double percentSubtracted, double newSharePrice) {
        appendLog("Share price Increased, Team Name: \"" + teamName + "\", Old Share Price: \"" + oldSharePrice + "\", Percent Added: \"" + percentSubtracted + "\", New Share Price:\"" + newSharePrice + "\"");
    }
}
