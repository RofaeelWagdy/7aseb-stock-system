package backend;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Admin admin = new Admin();

    public static boolean createTeamFromFrontEnd(String teamName) {
        return admin.createTeam(teamName);
    }

    public static boolean loadTeamsFromFileFromFrontEnd() {
        return admin.loadTeamsFromFile();
    }

    public static boolean loadSharesFromFileFromFrontEnd() {
        return admin.loadSharesFromFile();
    }

    public static boolean saveTeamsToFileFromFrontEnd() {
        return admin.saveTeamsToFile();
    }

    public static boolean saveSharesToFileFromFrontEnd() {
        return admin.saveSharesToFile();
    }

    public static ArrayList<Team> getTeamsFromFrontEnd() {
        return admin.getTeamsArray();
    }

    public static int buySharesFromFrontEnd(Team buyerTeam, Team from_team, int quantity) {
        return admin.buyShares(quantity, buyerTeam, from_team);
    }

    public static int sellSharesFromFrontEnd(Team buyerTeam, Team from_team, int quantity) {
        return admin.sellShares(buyerTeam, from_team, quantity);
    }

    public static double getTotalAssetsOfTeam(Team team) {
        return team.calculateTotal_assets();
    }

    public static int addPercentToSharePriceFromFrontEnd(Team team, int added_percent) {
        return admin.addPercentOfShareOfSpecificTeam(team, added_percent);
    }

    public static int subtractPercentFromSharePriceFromFrontEnd(Team team, int subtracted_percent) {
        return admin.subtractPercentOfShareOfSpecificTeam(team, subtracted_percent);
    }

    public static ArrayList<Share> getBoughtSharesFromBackend(Team team) {
        return team.getBought_shares();
    }

    public static int addBalanceToTeamFromFrontEnd(Team team, double amount) {
        return admin.addBalanceToTeam(team, amount);
    }

    public static int subtractBalanceFromTeamFromFrontEnd(Team team, double amount) {
        return admin.subtractBalanceFromTeam(team, amount);
    }
}