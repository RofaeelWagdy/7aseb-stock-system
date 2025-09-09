package backend;

import constants.TeamConstants;

import java.util.ArrayList;

public class Team implements TeamConstants {
    private final String team_id;
    private final String team_name;
    private int available_self_shares_quantity;
    private double self_share_price;
    private double balance;
    private ArrayList<Share> bought_shares;
//    private ArrayList<Share> bought_then_sold_shares;

    public Team(String team_id, String team_name) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.available_self_shares_quantity = TeamConstants.AVAILABLE_STOCK_QUANTITY;
        this.self_share_price = TeamConstants.STOCK_PRICE;
        this.balance = TeamConstants.BALANCE;
        this.bought_shares = new ArrayList<>();
//        this.bought_then_sold_shares = new ArrayList<>();
    }

    public Team(String team_id, String team_name, int available_self_shares_quantity, long self_share_price, long balance) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.available_self_shares_quantity = available_self_shares_quantity;
        this.self_share_price = self_share_price;
        this.balance = balance;
        this.bought_shares = new ArrayList<>();
//        this.bought_then_sold_shares = new ArrayList<>();
    }

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

    // when self team buy shares
    public void buy_shares(Share share) {
        bought_shares.add(share);
        this.balance -= (share.getPrice_per_share_when_bought_or_sold() * share.getQuantity());
    }

    // when self team sell shares
    public void sell_share(Share share) {
//        bought_then_sold_shares.add(share);
        bought_shares.remove(share);
        balance += share.getTotal_price_when_bought_or_sold();
    }

    // when another team buy from
    public void decrease_available_self_shares_quantity(int quantity) {
        available_self_shares_quantity -= quantity;
    }

    // when another team sell the shares that brought from self team
    public void increase_available_self_shares_quantity(int quantity) {
        available_self_shares_quantity += quantity;
    }

    // add bought shares to the bought shares array (used when loading data from file)
    public void add_bought_share_to_bought_shares_array(Share share) {
        bought_shares.add(share);
    }

    public void setSelf_share_price(double self_share_price) {
        this.self_share_price = self_share_price;
    }
}
