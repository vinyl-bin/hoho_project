package hoho_project.hoho.repository;

import hoho_project.hoho.domain.Home;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor //final이나 @NonNull인 필드 값만 파라미터로 받는 생성자 만듦.
public class HomeRepository {

    private final EntityManager em;

    //이미지 추가, 이미지 삭제
    public void save(Home home) {
        em.persist(home);
    }

    public void delete(Home home) {
        em.remove(home);
    }
}
