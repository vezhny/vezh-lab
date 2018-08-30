package vezh_bank.persistence.entity;

import javax.persistence.*;

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

    // TODO: use something else for money
    @Column(name = "MIN_AMOUNT")
    private int minAmount;

    @Column(name = "MAX_AMOUNT")
    private int maxAmount;

    @Column(name = "COMMISSION")
    private int commission;

    @ManyToOne
//    @Column(name = "CURRENCY")
    @JoinTable(name = "CURRENCIES")
    private Currency currency;

    public Payment() {
    }

    public Payment(String name, String description, int minAmount, int maxAmount, int commission, Currency currency) {
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

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
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
