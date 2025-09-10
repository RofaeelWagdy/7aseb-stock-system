package backend;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();
        admin.loadTeamsFromFile();
        admin.loadSharesFromFile();
        String teamID, shareID, buyerTeamID, fromTeamID;
        int quantity;

        int choice;
        do {
            System.out.println("1- Create Team" +
                    "\n2- Display Teams" +
                    "\n3- Buy Share" +
                    "\n4- Sell Share" +
                    "\n5- Display Shares" +
                    "\n6- Display Shares of Specific Team" +
                    "\n7- Show Total Assets of Specific Team" +
                    "\n8- Add Percent to Team's Share" +
                    "\n9- Subtract from Team's Share" +
                    "\n0- Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter the name of the team:");
                    String teamName = scanner.nextLine();
                    admin.createTeam(teamName);
                    admin.saveTeamsToFile();
                    break;
                case 2:
                    admin.displayTeams();
                    break;
                case 3:
                    System.out.println("Enter the ID of the buyer team:");
                    buyerTeamID = scanner.nextLine();
                    System.out.println("Enter the ID of the from team:");
                    fromTeamID = scanner.nextLine();
                    System.out.println("Enter the quantity of the shares:");
                    quantity = scanner.nextInt();
                    admin.buyShares(quantity, admin.getTeamFromArrayUsingID(buyerTeamID), admin.getTeamFromArrayUsingID(fromTeamID));
                    admin.saveTeamsToFile();
                    admin.saveSharesToFile();
                    break;
                case 4:
                    System.out.println("Enter the ID of the buyer team:");
                     buyerTeamID = scanner.nextLine();
                    System.out.println("Enter the ID of the from team:");
                     fromTeamID = scanner.nextLine();
                    System.out.println("Enter the quantity of the shares:");
                    quantity = scanner.nextInt();
                    admin.sellShares(admin.getTeamFromArrayUsingID(buyerTeamID), admin.getTeamFromArrayUsingID(fromTeamID), quantity);
                    admin.saveTeamsToFile();
                    admin.saveSharesToFile();
                    break;
                case 5:
                    admin.displayTotalShares();
                    break;
                case 6:
                    System.out.println("Enter the ID of the team:");
                    teamID = scanner.nextLine();
                    admin.displaySharesForSpecificTeam(admin.getTeamFromArrayUsingID(teamID));
                    break;
                case 7:
                    System.out.println("Enter the ID of the team:");
                    teamID = scanner.nextLine();
//                    System.out.println(admin.calculateTotalAssetsOfSpecificTeam(admin.getTeamFromArrayUsingID(teamID)));
                    break;
                case 8:
                    System.out.println("Enter the ID of the team: ");
                    teamID = scanner.nextLine();
                    System.out.println("Enter the percent u want to add: ");
                    int added_percent = scanner.nextInt();
                    admin.addPercentOfShareOfSpecificTeam(admin.getTeamFromArrayUsingID(teamID), added_percent);
                    break;
                case 9:
                    System.out.println("Enter the ID of the team: ");
                    teamID = scanner.nextLine();
                    System.out.println("Enter the percent u want to subtract: ");
                    int subtracted_percent = scanner.nextInt();
                    admin.subtractPercentOfShareOfSpecificTeam(admin.getTeamFromArrayUsingID(teamID), subtracted_percent);
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