package hoho_project.hoho.service;

import hoho_project.hoho.domain.Home;
import hoho_project.hoho.repository.HomeRepository;
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
public class HomeService {

    private final HomeRepository homeRepository;

    // 사진 등록
    @Transactional
    public Home saveHome(MultipartFile file) {

        //home_imgStoreName과 home_imgPath 정하기
        UUID uuid = UUID.randomUUID();
        String home_imgStoreName = uuid + "_" + file.getOriginalFilename();

        String projectPath = System.getProperty("user.dir") + "hoho/src/main/resources/static/saveFiles/home/";

        File saveFile = new File(projectPath, home_imgStoreName);

        try {
            file.transferTo(saveFile);
            System.out.println("file saved successfully to " + saveFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String home_imgPath = saveFile.getAbsolutePath();

        // home 생성
        Home home = Home.createHome(home_imgStoreName, home_imgPath);

        // home 저장
        homeRepository.save(home);

        return home;
    }

    // 사진 삭제
    @Transactional
    public Home deleteHome(Home home) {

        File fileToDelete = new File(home.getHome_imgPath());

        if (fileToDelete.delete()) {
            System.out.println("successfully deleted");
        } else {
            System.out.println("failed");
        }

        homeRepository.delete(home);

        return home;
    }

    public Home findOne(Long home_id) {
        return homeRepository.findOne(home_id);
    }

    public List<Home> findHomes() {
        return homeRepository.findAll();
    }

}
