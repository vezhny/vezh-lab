package vezh_bank.persistence.entity;

import vezh_bank.enums.UserRequestStatus;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static vezh_bank.constants.DatePatterns.DEFAULT_DATE_PATTERN;

@Entity
@Table(name = "USER_REQUESTS")
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REQUEST_ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "CREATION_DATE")
    private String date;

    @Column(name = "REQUEST_STATUS")
    private String status;

    @Column(name = "REQUEST_DATA")
    private String data;

    public UserRequest() {
    }

    public UserRequest(User user, String data) {
        this.user = user;
        this.data = data;

        this.date = new SimpleDateFormat(DEFAULT_DATE_PATTERN).format(new Date());
        this.status = UserRequestStatus.OPEN.toString();
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

    public void setStatus(UserRequestStatus status) {
        this.status = status.toString();
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
