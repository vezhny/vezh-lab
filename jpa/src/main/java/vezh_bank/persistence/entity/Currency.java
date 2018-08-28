package vezh_bank.persistence.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CURRENCIES")
public class Currency {
    @Id
    @Column(name = "CODE")
    private int code;

    @Column(name = "CURRENCY_VALUE")
    private String value;

    @OneToMany(mappedBy = "currency")
    private List<Card> cards;

    public Currency() {
    }

    public Currency(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "code=" + code +
                ", value='" + value + '\'' +
                '}';
    }
}
