package vezh_bank.persistence.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PAYMENTS")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    private int id;

    @Column(name = "PAYMENT_NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "MIN_AMOUNT")
    private String minAmount;

    @Column(name = "MAX_AMOUNT")
    private String maxAmount;

    @Column(name = "COMMISSION")
    private String commission;

    @ManyToOne
//    @Column(name = "CURRENCY")
    @JoinTable(name = "CURRENCIES")
    private Currency currency;

    public Payment() {
    }

    public Payment(String name, String description, String minAmount,
                   String maxAmount, String commission, Currency currency) {
        this.name = name;
        this.description = description;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.commission = commission;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMinAmount() {
        return new BigDecimal(minAmount);
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = String.valueOf(minAmount);
    }

    public BigDecimal getMaxAmount() {
        return new BigDecimal(maxAmount);
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = String.valueOf(maxAmount);
    }

    public BigDecimal getCommission() {
        return new BigDecimal(commission);
    }

    public void setCommission(BigDecimal commission) {
        this.commission = String.valueOf(commission);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", minAmount=" + minAmount +
                ", maxAmount=" + maxAmount +
                ", commission=" + commission +
                ", currency=" + currency +
                '}';
    }
}
