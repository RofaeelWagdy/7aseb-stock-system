package backend;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();
        admin.loadTeamsFromFile();
        admin.loadSharesFromFile();

        int choice;
        do {
            System.out.println("1- Create Team\n2- Display Teams\n3- Save Teams\n4- Buy Share\n5- Sell Share\n6- Display Shares\n7- Save Shares\n8- Display Shares of Specific Team\n9- Show Total Assets of Specific Team\n10- Add Percent to Team's Share\n11- Subtract from Team's Share\n0- Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            String teamID, shareID;
            switch (choice) {
                case 1:
                    System.out.println("Enter the ID of the team:");
                    teamID = scanner.nextLine();
                    System.out.println("Enter the name of the team:");
                    String teamName = scanner.nextLine();
                    admin.createTeam(teamID, teamName);
                    break;
                case 2:
                    admin.displayTeams();
                    break;
                case 3:
                    admin.saveTeamsToFile();
                    break;
                case 4:
                    System.out.println("Enter the ID of the buyer team:");
                    String buyerTeamID = scanner.nextLine();
                    System.out.println("Enter the ID of the from team:");
                    String fromTeamID = scanner.nextLine();
                    System.out.println("Enter the ID of the share:");
                    shareID = scanner.nextLine();
                    System.out.println("Enter the quantity of the shares:");
                    int quantity = scanner.nextInt();
                    admin.buyShares(shareID, quantity, admin.getTeamFromArrayUsingID(buyerTeamID), admin.getTeamFromArrayUsingID(fromTeamID));
                    break;
                case 5:
                    System.out.println("Enter the ID of the Share:");
                    shareID = scanner.nextLine();
                    admin.sellShares(shareID);
                    break;
                case 6:
                    admin.displayTotalShares();
                    break;
                case 7:
                    admin.saveSharesToFile();
                    break;
                case 8:
                    System.out.println("Enter the ID of the team:");
                    teamID = scanner.nextLine();
                    admin.displaySharesForSpecificTeam(admin.getTeamFromArrayUsingID(teamID));
                    break;
                case 9:
                    System.out.println("Enter the ID of the team:");
                    teamID = scanner.nextLine();
                    System.out.println(admin.calculateTotalAssetsOfSpecificTeam(admin.getTeamFromArrayUsingID(teamID)));
                    break;
                case 10:
                    System.out.println("Enter the ID of the team: ");
                    teamID = scanner.nextLine();
                    System.out.println("Enter the percent u want to add: ");
                    int added_percent = scanner.nextInt();
                    admin.addPercentOfShareOfSpecificTeam(admin.getTeamFromArrayUsingID(teamID),  added_percent);
                    break;
                case 11:
                    System.out.println("Enter the ID of the team: ");
                    teamID = scanner.nextLine();
                    System.out.println("Enter the percent u want to subtract: ");
                    int subtracted_percent = scanner.nextInt();
                    admin.subtractPercentOfShareOfSpecificTeam(admin.getTeamFromArrayUsingID(teamID),  subtracted_percent);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            System.out.println(); // Add a blank line for better readability
        } while (choice != 0);
    }
}