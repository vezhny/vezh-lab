package vezh_bank.persistence.entity;

import vezh_bank.enums.UserRequestStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER_REQUESTS")
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REQUEST_ID")
    private int id;

    @ManyToOne
    @JoinTable(name = "USERS")
//    @Column(name = "USER_ID")
    private User user;

    @Column(name = "CREATION_DATE")
    private Date date;

    @Column(name = "REQUEST_STATUS")
    private String status;

    @Column(name = "REQUEST_DATA")
    private String data;

    public UserRequest() {
    }

    public UserRequest(User user, String data) {
        this.user = user;
        this.data = data;

        this.date = new Date();
        this.status = UserRequestStatus.OPEN.toString();
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    // TODO: return RequestStatus
    public String getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "id=" + id +
                ", user=" + user +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
