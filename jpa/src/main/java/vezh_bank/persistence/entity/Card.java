package vezh_bank.persistence.entity;

import core.enums.CardStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CARDS")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CARD_ID")
    private int id;

    @Column(name = "PAN")
    private String pan;

    @Column(name = "AMOUNT")
    private Long amount;

    @Column(name = "HOLDER_ID") // TODO: from user entity
    private int holderId;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "CVC")
    private int cvc;

    @Column(name = "EXPIRY")
    private int expiry;

    @Column(name = "CURRENCY")  // TODO: from currency entity
    private int currency;

    @Column(name = "STATUS")
    private String status;

    public Card() {
    }

    public Card(String pan, int holderId, int expiry, int currency) {
        this.pan = pan;
        this.holderId = holderId;
        this.expiry = expiry;
        this.currency = currency;

        this.amount = 0L;
        this.creationDate = new Date();
        // TODO: generate cvc
        this.status = CardStatus.ACTIVE.toString();
    }

    public int getId() {
        return id;
    }

    public String getPan() {
        return pan;
    }

    public Long getAmount() {
        return amount;
    }

    public int getHolderId() {
        return holderId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getCvc() {
        return cvc;
    }

    public int getExpiry() {
        return expiry;
    }

    public int getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", pan='" + pan + '\'' +
                ", amount=" + amount +
                ", holderId=" + holderId +
                ", creationDate=" + creationDate +
                ", cvc=" + cvc +
                ", expiry=" + expiry +
                ", currency=" + currency +
                ", status='" + status + '\'' +
                '}';
    }
}
