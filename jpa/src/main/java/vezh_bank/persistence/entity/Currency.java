package vezh_bank.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CURRENCIES")
public class Currency {
    @Id
    @Column(name = "CODE")
    private int code;

    @Column(name = "CURRENCY_VALUE")
    private String value;

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
