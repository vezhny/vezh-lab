package vezh_bank.persistence.entity;

import core.enums.TransactionStatus;
import core.enums.TransactionType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRX_ID")
    private int id;

    @Column(name = "TRX_TYPE")
    private String type;

    @Column(name = "DATE_TIME")
    private Date dateTime;

    @Column(name = "TRX_DATA")
    private String data;

    @Column(name = "TRX_STATUS")
    private String status;

    public Transaction() {
    }

    public Transaction(TransactionType type, String data, TransactionStatus status) {
        this.type = type.toString();
        this.dateTime = new Date();
        this.data = data;
        this.status = status.toString();
    }

    public int getId() {
        return id;
    }

    // TODO: return TransactionType
    public String getType() {
        return type;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getData() {
        return data;
    }

    // TODO: return TransactionStatus
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", dateTime=" + dateTime +
                ", data='" + data + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
