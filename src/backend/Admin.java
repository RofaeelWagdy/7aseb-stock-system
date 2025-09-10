package backend;

import backend.constants.FileNames;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Admin implements FileNames {
    ArrayList<Team> teamsArray = new ArrayList<>();
    ArrayList<Share> boughtSharesArray = new ArrayList<>();
    ArrayList<Share> soldSharesArray = new ArrayList<>();


    public void createTeam(String team_id, String team_name) {
        teamsArray.add(new Team(team_id, team_name));
        System.out.println("Team created successfully!\n##################################");
    }

//    used for system to create teams that are stored in the files (when loading the data)
    public void addTeamWhenLoadFromFile(String team_id, String team_name, int available_self_stock_quantity, long self_stock_price, long balance) {
        teamsArray.add(new Team(team_id, team_name, available_self_stock_quantity, self_stock_price, balance));
        System.out.println("Team created successfully!\n#################################");
    }

//    used to print all details of the teams on the console
    public void displayTeams() {
        if (!teamsArray.isEmpty()) {
            System.out.println("Teams:");
            for (Team team : teamsArray) {
                System.out.println("Team ID: " + team.getTeam_id());
                System.out.println("Team Name: " + team.getTeam_name());
                System.out.println("Team Available Stock Quantity: " + team.getAvailable_self_shares_quantity());
                System.out.println("Team Stock Price: " + team.getSelf_share_price());
                System.out.println("Team Balance: " + team.getBalance());
                System.out.println("Team Bought Stocks: " + team.getBought_shares().size());
                System.out.println();
            }
        } else {
            System.out.println("No teams available.");
        }
        System.out.println("##################################");
    }

//    used to save team data into text file
    public void saveTeamsToFile() {
        try {
            // creating new Write instance
            Writer writer = new FileWriter(FileNames.TEAMS_FILENAME);
            if (!teamsArray.isEmpty()) {
                for (Team team : teamsArray) {
                    writer.write(team.getTeam_id() + "," + team.getTeam_name() + "," + team.getAvailable_self_shares_quantity() + "," + team.getSelf_share_price() + "," + team.getBalance() + "\n");
                }
                writer.close();
            }
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

//    used to load teams from the text file
    public void loadTeamsFromFile() {
        String fileContentString;
        try {
            // Check if file exists first
            if (!Files.exists(Paths.get(FileNames.TEAMS_FILENAME))) {
                System.out.println("Teams file does not exist. Starting with empty teams list.");
                return;
            }
            // store the file content as 1 string in fileContentString
            fileContentString = Files.readString(Paths.get(FileNames.TEAMS_FILENAME));
        } catch (IOException e) {
            System.out.println("Error reading teams file: " + e.getMessage());
            return;
        }
        if (fileContentString.isBlank()) {
            System.out.println("Teams file is empty");
        } else {
            // separating each line and storing it in a String array
            String[] lines = fileContentString.split("\n");
            for (String line : lines) {
                // delimiting each line into 5 strings
                String[] tokens = line.split(",");
                for (int i = 0; i < tokens.length; i++)
                    tokens[i] = tokens[i].trim();

                // creating new backend.Team object
                addTeamWhenLoadFromFile(tokens[0], tokens[1], Integer.parseInt(tokens[2]), Long.parseLong(tokens[3]), Long.parseLong(tokens[4]));
            }
        }
    }

//    used to get team by ID (searching across the teams array)
    public Team getTeamFromArrayUsingID(String key) {
        if (!teamsArray.isEmpty()) {
            for (Team team : teamsArray) {
                if (team.getTeam_id().equals(key))
                    return team;
            }
        }
        System.out.println("Team not found");
        return null;
    }

//    used when buying share by team from another team
//    to-do:
//    validation for :  1) if the from team has available shares to buy from 2)
//                      2) the "only 20 shares from any team" rule
    public void buyShares(String share_id, int quantity, Team buyer_team, Team from_team) {
        Share newShare = new Share(share_id, quantity, buyer_team, from_team);
        if (buyer_team.getBalance() < (from_team.getSelf_share_price() * quantity)) {
            System.out.println("Insufficient balance");
            return;
        }
        boughtSharesArray.add(newShare);
        buyer_team.buy_shares(newShare);
        from_team.decrease_available_self_shares_quantity(quantity);
        System.out.println("Share created successfully!\n##################################");
    }

//    used by program to create shares that are loaded from the file
    public void addSharesWhenLoadFromFile(String share_id, int quantity, Team buyer_team, Team from_team, String time_when_bought, long price_when_bought) {
        Share newShare = new Share(share_id, quantity, buyer_team, from_team, time_when_bought, price_when_bought);
        boughtSharesArray.add(newShare);
        buyer_team.add_bought_share_to_bought_shares_array(newShare);
        System.out.println("Share created successfully!\n##################################");
    }

//    used to search for specific share by shareID
    public Share getSharesFromArrayUsingID(String key) {
        if (!boughtSharesArray.isEmpty()) {
            for (Share share : boughtSharesArray) {
                if (share.getShare_id().equals(key))
                    return share;
            }
        }
        System.out.println("Share not found");
        return null;
    }

//    used by user to sell shares
    public void sellShares(String share_id) {
        Share soldShare = getSharesFromArrayUsingID(share_id);
        if (soldShare != null) {
            soldShare.getBuyer_Team().sell_share(soldShare);
            soldShare.getFrom_Team().increase_available_self_shares_quantity(soldShare.getQuantity());
            boughtSharesArray.remove(soldShare);
            soldSharesArray.add(soldShare);
        }
    }

//    used to print details of given arrayList of shares
    public void displaySpecificShares(ArrayList<Share> shares) {
        for (Share share : shares) {
            System.out.println("Share ID: " + share.getShare_id());
            System.out.println("quantity: " + share.getQuantity());
            System.out.println("buyer team: " + share.getBuyer_Team().getTeam_name());
            System.out.println("from team: " + share.getFrom_Team().getTeam_name());
            System.out.println("time when bought: " + share.getTime_when_bought_or_sold());
            System.out.println("price when bought: " + share.getPrice_per_share_when_bought_or_sold());
            System.out.println();
        }
    }

//    used to print all details of all shares
    public void displayTotalShares() {
        displaySpecificShares(boughtSharesArray);
    }

//    used to print all shares of specific team
    public void displaySharesForSpecificTeam(Team team) {
        displaySpecificShares(team.getBought_shares());
    }

//    used to save shares into files
    public void saveSharesToFile() {
        try {
            // creating new Write instance
            Writer writer = new FileWriter(FileNames.SHARES_FILENAME);
            if (!boughtSharesArray.isEmpty()) {
                for (Share share : boughtSharesArray) {
                    writer.write(share.getShare_id() + "," + share.getQuantity() + "," + share.getBuyer_Team().getTeam_id() + "," + share.getFrom_Team().getTeam_id() + "," + share.getTime_when_bought_or_sold() + "," + share.getPrice_per_share_when_bought_or_sold() + "\n");
                }
                writer.close();
            }
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

//    used to load shares from files
    public void loadSharesFromFile() {
        String fileContentString;
        try {
            // Check if file exists first
            if (!Files.exists(Paths.get(FileNames.SHARES_FILENAME))) {
                System.out.println("Shares file does not exist. Starting with empty shares list.");
                return;
            }
            // store the file content as 1 string in fileContentString
            fileContentString = Files.readString(Paths.get(FileNames.SHARES_FILENAME));
        } catch (IOException e) {
            System.out.println("Error reading shares file: " + e.getMessage());
            return;
        }
        if (fileContentString.isBlank()) {
            System.out.println("Shares file is empty");
        } else {
            // separating each line and storing it in a String array
            String[] lines = fileContentString.split("\n");
            for (String line : lines) {
                // delimiting each line into 5 strings
                String[] tokens = line.split(",");
                for (int i = 0; i < tokens.length; i++)
                    tokens[i] = tokens[i].trim();

                // creating new backend.Team object
                addSharesWhenLoadFromFile(tokens[0], Integer.parseInt(tokens[1]), getTeamFromArrayUsingID(tokens[2]), getTeamFromArrayUsingID(tokens[3]), tokens[4], Long.parseLong(tokens[5]));

            }
        }
    }

//    used to add percent to specific share
    public void addPercentOfShareOfSpecificTeam(Team team, long added_percent) {
        team.setSelf_share_price(team.getSelf_share_price() + ((double) added_percent /100 * team.getSelf_share_price()));
    }

//    used to subtract percent from specific share
    public void subtractPercentOfShareOfSpecificTeam(Team team, long added_percent) {
        team.setSelf_share_price(team.getSelf_share_price() - ((double) added_percent /100 * team.getSelf_share_price()));
    }

}
