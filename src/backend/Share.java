package backend;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Share {
    private final String share_id;
    private final int quantity;
    private final Team buyer_Team;
    private final Team from_Team;
    private final String time_when_bought_or_sold;


    public Share(String share_id, int quantity, Team buyer_Team, Team from_Team) {
        this.share_id = share_id;
        this.quantity = quantity;
        this.buyer_Team = buyer_Team;
        this.from_Team = from_Team;
        this.time_when_bought_or_sold = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public Share(String share_id, int quantity, Team buyer_Team, Team from_Team, String time_when_bought_or_sold) {
        this.share_id = share_id;
        this.quantity = quantity;
        this.buyer_Team = buyer_Team;
        this.from_Team = from_Team;
        this.time_when_bought_or_sold = time_when_bought_or_sold;
    }

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

    public String getTime_when_bought_or_sold() {
        return time_when_bought_or_sold;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
