package hoho_project.hoho.service;

import hoho_project.hoho.domain.Shop;
import hoho_project.hoho.dto.ShopSearchCondition;
import hoho_project.hoho.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    // 매장 정보 저장
    @Transactional
    public Shop saveShop(String shop_region, String shop_name, Long shop_phone, String shop_address) {

        Shop shop = Shop.createShop(shop_region, shop_name, shop_phone, shop_address);

        shopRepository.save(shop);

        return shop;
    }

    // 매장 정보 수정
    @Transactional
    public Shop updateShop(Long shop_id, String shop_region, String shop_name, Long shop_phone, String shop_address) {

        Shop shop = Shop.createShop(shop_region, shop_name, shop_phone, shop_address);

        shop.setShop_id(shop_id);

        shopRepository.save(shop);

        return shop;
    }

    // 매장 정보 삭제
    @Transactional
    public Shop deleteShop(Shop shop) {
        shopRepository.delete(shop);

        return shop;
    }

    //검색
    public Shop findOne(Long shop_id) {
        return shopRepository.findOne(shop_id);
    }

    public List<Shop> findShops(ShopSearchCondition searchCondition) {
        return shopRepository.findBySearchOption(searchCondition);
    }
}
