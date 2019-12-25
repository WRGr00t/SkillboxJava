import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @EmbeddedId
    private PurchaseId id;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public Subscription(PurchaseId id) {
        this.id = id;
    }

    public PurchaseId getId() {
        return id;
    }
    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}
