package hoho_project.hoho.service;

import hoho_project.hoho.domain.Home;
import hoho_project.hoho.repository.HomeRepository;
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
public class HomeService {

    private final HomeRepository homeRepository;

    // 사진 등록
    @Transactional
    public Home saveHome(MultipartFile file) {

        //home_imgStoreName과 home_imgPath 정하기
        UUID uuid = UUID.randomUUID();
        String home_imgStoreName = uuid + "_" + file.getOriginalFilename();

        //초기화
        Home home = null;

        try {
            // 리소스에서 InputStream 얻어오기
            InputStream inputStream = file.getInputStream();

            Resource resource = new UrlResource("file:///home/dabin/Desktop/hohoImages/");

            Path saveFilePath = Paths.get(resource.getURI()).resolve("home/" + home_imgStoreName);

            // home 폴더가 있는지 확인
            File homeDir = saveFilePath.getParent().toFile();
            if (!homeDir.exists()) {
                // home 폴더가 없으면 생성
                homeDir.mkdir();
            }

            Files.copy(inputStream, saveFilePath, StandardCopyOption.REPLACE_EXISTING);

            // InputStream 및 File을 닫기
            IOUtils.closeQuietly(inputStream);

            System.out.println("파일을 저장하였습니다." + saveFilePath);


            String filePathString = saveFilePath.toString();

            System.out.println(filePathString);

            // home 생성
            home = Home.createHome(home_imgStoreName, filePathString);

            // home 저장
            homeRepository.save(home);

        } catch (IOException e) {
            System.err.println("Exception이 발생했습니다." + System.getProperty("user.dir"));
//            e.printStackTrace();
        }

        return home;
    }

    // 사진 삭제
    @Transactional
    public Home deleteHome(Home home) {

//        File fileToDelete = new File(System.getProperty("user.dir") + "/src/main/resources/static" + home.getHome_imgPath());
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
