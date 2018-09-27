package core.dto;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import vezh_bank.constants.DatePatterns;
import vezh_bank.enums.CardStatus;
import vezh_bank.persistence.entity.Card;
import vezh_bank.util.TypeConverter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CardDTO implements BaseDTO<Card> {
    @Expose
    private int id;

    @Expose
    private String pan;

    @Expose
    private BigDecimal amount;

    @Expose
    private UserDTO holder;

    @Expose
    private String creationDate;

    @Expose
    private int cvc;

    @Expose
    private String expiry;

    @Expose
    private CurrencyDTO currency;

    @Expose
    private CardStatus status;

    public CardDTO(String pan, UserDTO holder, String expiry, CurrencyDTO currency) {
        this.pan = pan;
        this.holder = holder;
        this.expiry = expiry;
        this.currency = currency;
        this.amount = new BigDecimal("0.00");
        this.creationDate = new SimpleDateFormat(DatePatterns.DEFAULT_DATE_PATTERN).format(new Date());
        this.cvc = generateCvc();
        this.status = CardStatus.ACTIVE;
    }

    public CardDTO(Card card, UserDTO userDTO) {
        this.id = card.getId();
        this.pan = card.getPan();
        this.holder = userDTO;
        this.expiry = card.getExpiry();
        this.currency = new CurrencyDTO(card.getCurrency());
        this.amount = card.getAmount();
        this.creationDate = card.getCreationDate();
        this.cvc = card.getCvc();
        this.status = CardStatus.valueOf(card.getStatus());
    }

    private int generateCvc() {
        String cvc = "";
        Random random = new Random();
        cvc += String.valueOf(random.nextInt(10));
        cvc += String.valueOf(random.nextInt(10));
        cvc += String.valueOf(random.nextInt(10));
        return TypeConverter.stringToInt(cvc);
    }

    public int getId() {
        return id;
    }

    public String getPan() {
        return pan;
    }

    public UserDTO getHolder() {
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

    public CurrencyDTO getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    @Override
    public Card getEntity() {
        Card card = new Card(pan, holder.getEntity(), cvc, expiry, currency.getEntity());
        return card;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
