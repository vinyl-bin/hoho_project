package hoho_project.hoho.repository;

import hoho_project.hoho.domain.Menu;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MenuRepository {

    private final EntityManager em;

    //저장, 수정, 삭제
    public void save(Menu menu) {
        if (menu.getMenu_id() == null) {
            em.persist(menu);
        }
        else {
            em.merge(menu);  //수정할 때 merge함.
        }
    }

    public void delete(Menu menu) {
        em.remove(menu);
    }
}
