package vezh_bank.persistence.entity;

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

    @Column(name = "BLOCKED")
    private boolean blocked;

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
        this.blocked = false;
    }

    public int getId() {
        return id;
    }

    public String getPan() {

        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public int getHolderId() {
        return holderId;
    }

    public void setHolderId(int holderId) {
        this.holderId = holderId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public int getExpiry() {
        return expiry;
    }

    public void setExpiry(int expiry) {
        this.expiry = expiry;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
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
                ", blocked=" + blocked +
                '}';
    }
}
