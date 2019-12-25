import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purchaselist")
public class Purchaselist {

    @EmbeddedId
    private PurchaseId id;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public PurchaseId getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

}
