package vezh_bank.persistence.entity;

import core.enums.CardBrands;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CARD_BRANDS")
public class CardBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BRAND_ID")
    private int id;

    @Column(name = "BRAND_NAME")
    private String name;

    @Column(name = "PAN_POOL")
    private String panPool;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @OneToMany(mappedBy = "brand")
    private List<Card> cards;

    public CardBrand() {
    }

    public int getId() {
        return id;
    }

    // TODO: return CardBrands
    public String getName() {
        return name;
    }

    public String getPanPool() {
        return panPool;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "CardBrand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", panPool='" + panPool + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
