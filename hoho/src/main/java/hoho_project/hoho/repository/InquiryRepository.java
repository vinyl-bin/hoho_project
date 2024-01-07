package hoho_project.hoho.repository;

import hoho_project.hoho.domain.Inquiry;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
