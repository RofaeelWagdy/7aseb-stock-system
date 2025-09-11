package backend;

public class Share {
    private final String share_id;
    private int quantity;
    private final Team buyer_Team;
    private final Team from_Team;


    public Share(String share_id, int quantity, Team buyer_Team, Team from_Team) {
        this.share_id = share_id;
        this.quantity = quantity;
        this.buyer_Team = buyer_Team;
        this.from_Team = from_Team;
    }


    // getters
    public String getShare_id() {
        return share_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Team getBuyer_Team() {
        return buyer_Team;
    }

    public Team getFrom_Team() {
        return from_Team;
    }

    // setters
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalShare_price() {
        return this.quantity * this.getFrom_Team().getSelf_share_price();
    }
}
