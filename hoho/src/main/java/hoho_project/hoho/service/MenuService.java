package hoho_project.hoho.service;

import hoho_project.hoho.domain.Home;
import hoho_project.hoho.domain.Menu;
import hoho_project.hoho.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

        //초기화
        Menu menu = null;

        try {

            // 리소스에서 InputStream 얻어오기
            InputStream inputStream = file.getInputStream();

            Resource resource = new UrlResource("file:///home/dabin/Desktop/hohoImages/");

            Path saveFilePath = Paths.get(resource.getURI()).resolve("menu/" + menu_imgStoreName);

            // menu 폴더가 있는지 확인
            File menuDir = saveFilePath.getParent().toFile();
            if (!menuDir.exists()) {
                // menu 폴더가 없으면 생성
                menuDir.mkdir();
            }

            Files.copy(inputStream, saveFilePath, StandardCopyOption.REPLACE_EXISTING);

            // InputStream 및 File을 닫기
            IOUtils.closeQuietly(inputStream);

            System.out.println("파일을 저장하였습니다." + saveFilePath);


            String filePathString = saveFilePath.toString();

            System.out.println(filePathString);

            menu = Menu.createMenu(menu_type, menu_imgStoreName, filePathString, menu_name, menu_ingredient, menu_price);
            menuRepository.save(menu);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return menu;
    }

    //메뉴 수정
    @Transactional
    public Menu updateMenu(Long menu_id, String menu_type, String menu_imgStoreName, String menu_imgPath, MultipartFile file,
                           String menu_name, String menu_ingredient, Long menu_price) {
        Menu menu = null;

        // 수정된 사항에 파일은 없을 때 or 수정된 사항에 파일이 있을 때
        if (file.isEmpty()) {
            menu = Menu.createMenu(menu_type, menu_imgStoreName, menu_imgPath, menu_name, menu_ingredient, menu_price);
        } else {
            UUID uuid = UUID.randomUUID();
            String new_menu_imgStoreName = uuid + "_" + file.getOriginalFilename();

            try {

                // 리소스에서 InputStream 얻어오기
                InputStream inputStream = file.getInputStream();

                Resource resource = new UrlResource("file:///home/dabin/Desktop/hohoImages/");

                Path saveFilePath = Paths.get(resource.getURI()).resolve("menu/" + new_menu_imgStoreName);

                // menu 폴더가 있는지 확인
                File menuDir = saveFilePath.getParent().toFile();
                if (!menuDir.exists()) {
                    // menu 폴더가 없으면 생성
                    menuDir.mkdir();
                }

                Files.copy(inputStream, saveFilePath, StandardCopyOption.REPLACE_EXISTING);

                // InputStream 및 File을 닫기
                IOUtils.closeQuietly(inputStream);

                System.out.println("파일을 저장하였습니다." + saveFilePath);


                String filePathString = saveFilePath.toString();

                System.out.println(filePathString);

                menu = Menu.createMenu(menu_type, new_menu_imgStoreName, filePathString, menu_name, menu_ingredient, menu_price);

            } catch (IOException e) {
                e.printStackTrace();
            }
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