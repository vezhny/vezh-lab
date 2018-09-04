package vezh_bank.persistence.entity;

import vezh_bank.enums.CardStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static vezh_bank.constants.DatePatterns.DEFAULT_DATE_PATTERN;

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
    private String amount;

    @ManyToOne
    @JoinColumn(name = "HOLDER_ID")
    private User holder;

    @Column(name = "CREATION_DATE")
    private String creationDate;

    @Column(name = "CVC")
    private int cvc;

    @Column(name = "EXPIRY")
    private String expiry;

    @ManyToOne
    @JoinColumn(name = "CURRENCY")
    private Currency currency;

    @Column(name = "CARD_STATUS")
    private String status;

    public Card() {
    }

    public Card(String pan, User holder, int cvc, String expiry, Currency currency) {
        this.pan = pan;
        this.holder = holder;
        this.expiry = expiry;
        this.currency = currency;

        this.amount = "0.00";
        this.creationDate = new SimpleDateFormat(DEFAULT_DATE_PATTERN).format(new Date());
        this.cvc = cvc;
        this.status = CardStatus.ACTIVE.toString();
    }

    public int getId() {
        return id;
    }

    public String getPan() {
        return pan;
    }

    public BigDecimal getAmount() {
        return new BigDecimal(amount);
    }

    public User getHolder() {
        return holder;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public int getCvc() {
        return cvc;
    }

    public String getExpiry() {
        return expiry;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status.toString();
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount.toString();
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
