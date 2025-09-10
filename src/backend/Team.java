package backend;

import backend.constants.TeamConstants;

import java.util.ArrayList;

public class Team implements TeamConstants {
    private final String team_id;
    private final String team_name;
    private int available_self_shares_quantity;
    private double self_share_price;
    private double balance;
    private ArrayList<Share> bought_shares;
//    private ArrayList<Share> bought_then_sold_shares;


    //    constructor used when the user create team for the first time
    public Team(String team_id, String team_name) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.available_self_shares_quantity = TeamConstants.AVAILABLE_STOCK_QUANTITY;
        this.self_share_price = TeamConstants.STOCK_PRICE;
        this.balance = TeamConstants.BALANCE;
        this.bought_shares = new ArrayList<>();
//        this.bought_then_sold_shares = new ArrayList<>();
    }

    //    constructor used by the program to create team from loading data from file
    public Team(String team_id, String team_name, int available_self_shares_quantity, double self_share_price, double balance) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.available_self_shares_quantity = available_self_shares_quantity;
        this.self_share_price = self_share_price;
        this.balance = balance;
        this.bought_shares = new ArrayList<>();
//        this.bought_then_sold_shares = new ArrayList<>();
    }


    //    getters
    public String getTeam_id() {
        return team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public int getAvailable_self_shares_quantity() {
        return available_self_shares_quantity;
    }

    public double getSelf_share_price() {
        return self_share_price;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Share> getBought_shares() {
        return bought_shares;
    }


    // setters
    public void setSelf_share_price(double self_share_price) {
        this.self_share_price = self_share_price;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAvailable_self_shares_quantity(int available_self_shares_quantity) {
        this.available_self_shares_quantity = available_self_shares_quantity;
    }

    // used to calculate total assets of self team
    public double calculateTotal_assets() {
        double total_assets = balance;
        for (Share share : bought_shares) {
            total_assets += share.getTotalShare_price();
        }
        return total_assets;
    }

    // when self team buy shares
    public void buy_shares(Share share) {
        bought_shares.add(share);
        this.balance -= (share.getPrice_per_share_when_bought_or_sold() * share.getQuantity());
    }

    // when self team buy shares
    public void update_existing_shares(Share share, int quantity) {
        Share oldShare = isBoughtFromSpecificTeam(share.getFrom_Team());
        oldShare.setQuantity(oldShare.getQuantity() + quantity);
        this.balance -= (share.getFrom_Team().getSelf_share_price() * quantity);
    }

    // add bought shares to the bought shares array (used when loading data from file)
    public void add_bought_share_to_bought_shares_array(Share share) {
        bought_shares.add(share);
    }

    // check if self team has bought from certain given team
    public Share isBoughtFromSpecificTeam(Team suspiciousTeam) {
        for (Share share : bought_shares) {
            if (share.getFrom_Team().equals(suspiciousTeam)) {
                return share;
            }
        }
        return null;
    }
}
