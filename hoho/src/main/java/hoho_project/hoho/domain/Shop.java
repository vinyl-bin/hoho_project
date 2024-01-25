package hoho_project.hoho.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shop_id;

    private String shop_region;

    private String shop_name;

    private Long shop_phone;

    private String shop_address;

    //==생성 메서드==//
    public static Shop createShop(String shop_region, String shop_name, Long shop_phone, String shop_address) {

        Shop shop = new Shop();

        shop.setShop_region(shop_region);
        shop.setShop_name(shop_name);
        shop.setShop_phone(shop_phone);
        shop.setShop_address(shop_address);

        return shop;
    }
}