import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity(name = "Purchaselist2")
@Table(name = "PurchaseList2")
public class Purchaselist2 {
    @EmbeddedId
    private PurchaseId id;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public Purchaselist2(PurchaseId purchaseId) {
        id = purchaseId;
    }

    public PurchaseId getId() {
        return id;
    }

    public void setId(PurchaseId id) {
        this.id = id;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}
