package vezh_bank.persistence.entity;

import vezh_bank.enums.TransactionStatus;
import vezh_bank.enums.TransactionType;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static vezh_bank.constants.DatePatterns.DEFAULT_DATE_TIME_PATTERN;

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
    private String dateTime;

    @Column(name = "TRX_DATA")
    private String data;

    @Column(name = "TRX_STATUS")
    private String status;

    public Transaction() {
    }

    public Transaction(TransactionType type, String data, TransactionStatus status) {
        this.type = type.toString();
        this.dateTime = new SimpleDateFormat(DEFAULT_DATE_TIME_PATTERN).format(new Date());
        this.data = data;
        this.status = status.toString();
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getData() {
        return data;
    }

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
