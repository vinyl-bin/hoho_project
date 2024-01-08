package hoho_project.hoho.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hoho_project.hoho.domain.Shop;
import hoho_project.hoho.dto.ShopSearchCondition;
import hoho_project.hoho.dto.ShopSearchType;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Supplier;

import static hoho_project.hoho.domain.QShop.shop;

@Repository
@RequiredArgsConstructor
public class ShopRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    //저장, 수정, 삭제
    public void save(Shop shop) {
        if (shop.getShop_id() == null) {
            em.persist(shop);
        }
        else {
            em.merge(shop);
        }
    }

    public void delete(Shop shop) {
        em.remove(shop);
    }

    //검색
    public Shop findOne(Long shop_id) {
        return em.find(Shop.class, shop_id);
    }

    public List<Shop> findAll() {
        return em.createQuery("select s from Shop s", Shop.class)
                .getResultList();
    }

    public List<Shop> findBySearchOption(ShopSearchCondition condition) {
        return queryFactory
                .selectFrom(shop)
                .where(isSearchable(condition.getType(), condition.getContent()))
                .fetch();
    }

    BooleanBuilder isSearchable(ShopSearchType searchType, String content) {
        if (searchType == null) {
            return null;
        }
        else if (searchType == searchType.REGION) {
            return regionCt(content);
        }
        else if (searchType == searchType.NAME) {
            return nameCt(content);
        }
        else {
            return addressCt(content);
        }
    }

    //------------------------
    BooleanBuilder regionCt(String content) {
        return nullSafeBuilder(() -> shop.shop_region.contains(content));
    }

    BooleanBuilder nameCt(String content) {
        return nullSafeBuilder(() -> shop.shop_name.contains(content));
    }

    BooleanBuilder addressCt(String content) {
        return nullSafeBuilder(() -> shop.shop_address.contains(content));
    }
    //-----------------------


    BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (Exception e) {
            return new BooleanBuilder();
        }
    }
}
