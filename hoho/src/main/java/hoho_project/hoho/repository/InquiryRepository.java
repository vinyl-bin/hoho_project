package hoho_project.hoho.repository;

import hoho_project.hoho.domain.Home;
import hoho_project.hoho.domain.Inquiry;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InquiryRepository {

    private final EntityManager em;

    // 이미지 추가, 삭제
    public void save(Inquiry inquiry) {
        em.persist(inquiry);
    }

    public void delete(Inquiry inquiry) {
        em.remove(inquiry);
    }

    public Inquiry findOne(Long inquiry_id) {
        return em.find(Inquiry.class, inquiry_id);
    }

    public List<Inquiry> findAll() {
        return em.createQuery("select i from Inquiry i", Inquiry.class)
                .getResultList();
    }
}
