package backend;

public class Share {
    private final String share_id;
    private int quantity;
    private final Team buyer_team;
    private final Team from_team;


    public Share(String share_id, int quantity, Team buyer_team, Team from_team) {
        this.share_id = share_id;
        this.quantity = quantity;
        this.buyer_team = buyer_team;
        this.from_team = from_team;
    }


    // getters
    public String getShare_id() {
        return share_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Team getBuyer_team() {
        return buyer_team;
    }

    public Team getFrom_team() {
        return from_team;
    }

    public double getSingle_share_price() {
        return this.getFrom_team().getSelf_share_price();
    }

    public double getTotal_share_price() {
        return this.quantity * this.getFrom_team().getSelf_share_price();
    }

    // setters
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalShare_price() {
        return this.quantity * this.getFrom_team().getSelf_share_price();
    }


}
