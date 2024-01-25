package hoho_project.hoho.service;

import hoho_project.hoho.domain.Home;
import hoho_project.hoho.domain.Menu;
import hoho_project.hoho.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    //메뉴 저장
    @Transactional
    public Menu saveMenu(String menu_type, MultipartFile file,
                         String menu_name, String menu_ingredient, Long menu_price) {

        UUID uuid = UUID.randomUUID();
        String menu_imgStoreName = uuid + "_" + file.getOriginalFilename();

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/saveFiles/menu";

        File saveFile = new File(projectPath, menu_imgStoreName);

        try {
            file.transferTo(saveFile);
            System.out.println("file Saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String menu_imgPath = saveFile.getAbsolutePath();

        Menu menu = Menu.createMenu(menu_type, menu_imgStoreName, menu_imgPath, menu_name, menu_ingredient, menu_price);

        menuRepository.save(menu);

        return menu;
    }

    //메뉴 수정
    @Transactional
    public Menu updateMenu(Long menu_id, String menu_type, String menu_imgStoreName, String menu_imgPath, MultipartFile file,
                           String menu_name, String menu_ingredient, Long menu_price) {
        Menu menu;

        // 수정된 사항에 파일은 없을 때, 수정된 사항에 파일이 있을 때
        if (file.isEmpty()) {
            menu = Menu.createMenu(menu_type, menu_imgStoreName, menu_imgPath, menu_name, menu_ingredient, menu_price);
        } else {
            UUID uuid = UUID.randomUUID();
            String new_menu_imgStoreName = uuid + "_" + file.getOriginalFilename();

            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/saveFiles/menu";

            File saveFile = new File(projectPath, new_menu_imgStoreName);

            try {
                file.transferTo(saveFile);
                System.out.println("file Saved successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }

            String new_menu_imgPath = saveFile.getAbsolutePath();

            menu = Menu.createMenu(menu_type, new_menu_imgStoreName, new_menu_imgPath, menu_name, menu_ingredient, menu_price);
        }

        // 다시 만든 menu에 원래 menu의 id를 지정해줌
        menu.setMenu_id(menu_id);

        // merge됨
        menuRepository.save(menu);

        return menu;
    }

    // 메뉴 삭제
    @Transactional
    public Menu deleteMenu(Menu menu) {

        File fileToDelete = new File(menu.getMenu_imgPath());

        if (fileToDelete.delete()) {
            System.out.println("successfully deleted");
        } else {
            System.out.println("failed");
        }

        menuRepository.delete(menu);

        return menu;
    }

    public Menu findOne(Long menu_id) {
        return menuRepository.findOne(menu_id);
    }

    public List<Menu> findMenus() {
        return menuRepository.findAll();
    }
}