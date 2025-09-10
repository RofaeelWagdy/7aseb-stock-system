package backend;

import backend.constants.FileNames;
import backend.constants.TeamConstants;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class Admin implements FileNames {
    ArrayList<Team> teamsArray = new ArrayList<>();
    ArrayList<Share> boughtSharesArray = new ArrayList<>();
    ArrayList<Share> soldSharesArray = new ArrayList<>();
    private final IDGenerator idGenerator;

    public Admin() {
        this.idGenerator = new IDGenerator();
    }

    //    used for user to create team (with auto-generated ID)
    public void createTeam(String team_name) {
        String generatedTeamId = idGenerator.generateTeamID();
        teamsArray.add(new Team(generatedTeamId, team_name));
        System.out.println("Team created successfully with ID: " + generatedTeamId + "\n##################################");
    }

    //    used for system to create teams that are stored in the files (when loading the data)
    public void addTeamWhenLoadFromFile(String team_name, int available_self_stock_quantity, double self_stock_price, double balance) {
        String generatedTeamId = idGenerator.generateTeamID();
        teamsArray.add(new Team(generatedTeamId, team_name, available_self_stock_quantity, self_stock_price, balance));
        System.out.println("Team created successfully!\n#################################");
    }

    //    used to print all details of the teams on the console
    public void displayTeams() {
        if (!teamsArray.isEmpty()) {
            System.out.println("Teams:");
            for (Team team : teamsArray) {
                System.out.println("Team ID: " + team.getTeam_id());
                System.out.println("Team Name: " + team.getTeam_name());
                System.out.println("Team Available Shares Quantity: " + team.getAvailable_self_shares_quantity());
                System.out.println("Team Share Price: " + team.getSelf_share_price());
                System.out.println("Team Balance: " + team.getBalance());
                int totalBoughtShares = 0;
                for (Share share : team.getBought_shares()) {
                    totalBoughtShares += share.getQuantity();
                }
                System.out.println("Team Bought Shares: " + totalBoughtShares);
                System.out.println("Team total assets: " + team.calculateTotal_assets());
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
                System.out.println("Teams saved successfully!");
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
                addTeamWhenLoadFromFile(tokens[1], Integer.parseInt(tokens[2]), Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]));
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
    public void buyShares(int quantity, Team buyer_team, Team from_team) {
//        check if the buyer team exists
        if (getTeamFromArrayUsingID(buyer_team.getTeam_id()) == null) {
            System.out.println("Buyer Team not found");
            return;
        }
//        check if the from team exists
        if (getTeamFromArrayUsingID(from_team.getTeam_id()) == null) {
            System.out.println("From Team not found");
            return;
        }
//        check if the buyer has enough balance to buy the share
        if (buyer_team.getBalance() < (from_team.getSelf_share_price() * quantity)) {
            System.out.println("Insufficient balance");
            return;
        }
//        check if the from team has available shares to buy from
        if (from_team.getAvailable_self_shares_quantity() < quantity) {
            System.out.println("From Team doesn't have enough self shares");
            return;
        }
//        check if the buyer has already bought the same team before
        if (buyer_team.isBoughtFromSpecificTeam(from_team) != null) {

//        check for the "only 20 shares from any team" rule
            if ((buyer_team.isBoughtFromSpecificTeam(from_team).getQuantity() + quantity) > TeamConstants.ALLOWED_NUMBER_SHARES_TO_BUY_FROM_EACH_TEAM) {
                System.out.println("7aseeeeeb .... U can only buy 20 shares from any team");
                System.out.println("U have already bought " + buyer_team.isBoughtFromSpecificTeam(from_team).getQuantity() + " shares");
                return;
            }
            System.out.println("You have bought from this team before");
            Share oldShare = buyer_team.isBoughtFromSpecificTeam(from_team);
            buyer_team.update_existing_shares(oldShare, quantity);
            from_team.setAvailable_self_shares_quantity(from_team.getAvailable_self_shares_quantity() - quantity);

        } else {
//        check for the "only 20 shares from any team" rule
            if (quantity > 20) {
                System.out.println("U can only buy 20 shares from any team");
                return;
            }
            String generatedShareID = idGenerator.generateShareID();
            Share newShare = new Share(generatedShareID, quantity, buyer_team, from_team);
            boughtSharesArray.add(newShare);
            buyer_team.buy_shares(newShare);
            from_team.setAvailable_self_shares_quantity(from_team.getAvailable_self_shares_quantity() - quantity);
        }
        System.out.println("Share created successfully!\n##################################");
    }

    //    used by program to create shares that are loaded from the file
    public void addSharesWhenLoadFromFile(int quantity, Team buyer_team, Team from_team, String time_when_bought) {
        String generatedShareID = idGenerator.generateShareID();
        Share newShare = new Share(generatedShareID, quantity, buyer_team, from_team, time_when_bought);
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

    //    used by user to sell shares using the ID of trams
    public void sellShares(Team buyer_team, Team from_team, int quantity) {
//        check if the buyer team exists
        if (getTeamFromArrayUsingID(buyer_team.getTeam_id()) == null) {
            System.out.println("Buyer Team not found");
            return;
        }
//        check if the from team exists
        if (getTeamFromArrayUsingID(from_team.getTeam_id()) == null) {
            System.out.println("From Team not found");
            return;
        }

        // get the share
        for (Share share : buyer_team.getBought_shares()) {
            if (Objects.equals(share.getFrom_Team().getTeam_id(), from_team.getTeam_id())) {
                // remove the share from the bought shares array
                // buyer team :
                // remove the quantity from the buyer team
                share.setQuantity(share.getQuantity() - quantity);
                // add the balance to the buyer team
                buyer_team.setBalance(buyer_team.getBalance() + (quantity * from_team.getSelf_share_price()));

                // from team :
                // update the available_self_shares_quantity of the from team
                from_team.setAvailable_self_shares_quantity(quantity + from_team.getAvailable_self_shares_quantity());
                System.out.println("Share sold successfully");
                return;
            }
        }
        System.out.println("Share not found");
    }

    //    used to print details of given arrayList of shares
    public void displaySpecificShares(ArrayList<Share> shares) {
        if (!shares.isEmpty()) {
            for (Share share : shares) {
                System.out.println("\nShare ID: " + share.getShare_id());
                System.out.println("quantity: " + share.getQuantity());
                System.out.println("buyer team: " + share.getBuyer_Team().getTeam_name());
                System.out.println("from team: " + share.getFrom_Team().getTeam_name());
                System.out.println("price of the single share: " + share.getFrom_Team().getSelf_share_price());
                System.out.println("price of the total share: " + (share.getQuantity() * share.getFrom_Team().getSelf_share_price()));
                System.out.println();
            }
        } else {
            System.out.println("No shares found");
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
                System.out.println("Shares Saved successfully!");
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
