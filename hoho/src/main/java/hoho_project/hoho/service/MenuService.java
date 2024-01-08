package hoho_project.hoho.service;

import hoho_project.hoho.domain.Menu;
import hoho_project.hoho.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    //메뉴 저장
    @Transactional
    public Menu saveMenu(String menu_type, String menu_imgName,byte[] menu_image,
                         String menu_name, String menu_ingredient, Long menu_price) {

        Menu menu = Menu.createMenu(menu_type, menu_imgName, menu_image, menu_name, menu_ingredient, menu_price);

        menuRepository.save(menu);

        return menu;
    }

    //메뉴 수정
    @Transactional
    public Menu updateMenu(Long menu_id, String menu_type, String menu_imgName,byte[] menu_image,
                           String menu_name, String menu_ingredient, Long menu_price) {

        // 수정사항 반영해서 다시 menu 만듬
        Menu menu = Menu.createMenu(menu_type, menu_imgName, menu_image, menu_name, menu_ingredient, menu_price);

        // 다시 만든 menu에 원래 menu의 id를 지정해줌
        menu.setMenu_id(menu_id);

        // merge됨
        menuRepository.save(menu);

        return menu;
    }

    // 메뉴 삭제
    @Transactional
    public Menu deleteMenu(Menu menu) {
        menuRepository.delete(menu);

        return menu;
    }
}
