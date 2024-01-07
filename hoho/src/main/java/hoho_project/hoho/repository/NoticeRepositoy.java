package hoho_project.hoho.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hoho_project.hoho.domain.Notice;
import hoho_project.hoho.dto.NoticeSearchCondition;
import hoho_project.hoho.dto.NoticeSearchType;
import hoho_project.hoho.dto.ShopSearchType;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Supplier;

import static hoho_project.hoho.domain.QNotice.notice;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoy {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    //저장, 수정, 삭제
    public void save(Notice notice) {
        if (notice.getNotice_id() == null) {
            em.persist(notice);
        }
        else {
            em.merge(notice);
        }
    }

    public void delete(Notice notice) {
        em.remove(notice);
    }

    //검색
    public List<Notice> findAll() {
        return em.createQuery("select n from Notice n", Notice.class)
                .getResultList();
    }

    public List<Notice> findBySearchOption(NoticeSearchCondition condition) {
        return queryFactory
                .selectFrom(notice)
                .where(isSearchable(condition.getType(), condition.getContent()))
                .fetch();
    }

    BooleanBuilder isSearchable(NoticeSearchType searchType, String content) {
        if (searchType == null) {
            return null;
        }
        else if (searchType == searchType.TITLE) {
            return titleCt(content);
        }
        else {
            return contentCt(content);
        }
    }

    //---------------------------
    BooleanBuilder titleCt(String content) {
        return nullSafeBuilder(()->notice.notice_title.contains(content));
    }

    BooleanBuilder contentCt(String content) {
        return nullSafeBuilder(()->notice.notice_content.contains(content));
    }
    //---------------------------

    BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (Exception e) {
            return new BooleanBuilder();
        }
    }
}
