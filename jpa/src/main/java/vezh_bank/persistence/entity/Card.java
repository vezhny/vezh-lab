package vezh_bank.persistence.entity;

import vezh_bank.enums.CardStatus;

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

    @ManyToOne
    @JoinTable(name = "USERS")
    @Column(name = "HOLDER_ID")
    private User holder;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "CVC")
    private int cvc;

    @Column(name = "EXPIRY")
    private int expiry;

    @ManyToOne
    @JoinTable(name = "CURRENCIES")
    @Column(name = "CURRENCY")
    private Currency currency;

    @Column(name = "CARD_STATUS")
    private String status;

    public Card() {
    }

    public Card(String pan, User holder, int expiry, Currency currency) {
        this.pan = pan;
        this.holder = holder;
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

    public User getHolder() {
        return holder;
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

    public Currency getCurrency() {
        return currency;
    }

    // TODO: return CardStatus
    public String getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status.toString();
    }


    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", pan='" + pan + '\'' +
                ", amount=" + amount +
                ", holder=" + holder +
                ", creationDate=" + creationDate +
                ", cvc=" + cvc +
                ", expiry=" + expiry +
                ", currency=" + currency +
                ", status='" + status + '\'' +
                '}';
    }
}
