package vezh_bank.persistence.entity;

import core.enums.UserRequestStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER_REQUESTS")
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REQUEST_ID")
    private int id;

    @Column(name = "USER_ID") // TODO: from user entity
    private int userId;

    @Column(name = "CREATION_DATE")
    private Date date;

    @Column(name = "REQUEST_STATUS")
    private String status;

    @Column(name = "REQUEST_DATA")
    private String data;

    public UserRequest() {
    }

    public UserRequest(int userId, String data) {
        this.userId = userId;
        this.data = data;

        this.date = new Date();
        this.status = UserRequestStatus.OPEN.toString();
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

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
                ", userId=" + userId +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
