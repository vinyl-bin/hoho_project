package hoho_project.hoho.repository;

import hoho_project.hoho.domain.Home;
import hoho_project.hoho.domain.Menu;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public Menu findOne(Long menu_id) {
        return em.find(Menu.class, menu_id);
    }

    public List<Menu> findAll() {
        return em.createQuery("select m from Menu m", Menu.class)
                .getResultList();
    }
}
