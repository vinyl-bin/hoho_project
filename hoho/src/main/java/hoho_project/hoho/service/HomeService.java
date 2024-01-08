package hoho_project.hoho.service;

import hoho_project.hoho.domain.Home;
import hoho_project.hoho.repository.HomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    private final HomeRepository homeRepository;

    // 사진 등록
    @Transactional
    public Home saveHome(String home_imgName, byte[] home_image) {

        // home 생성
        Home home = Home.createHome(home_imgName, home_image);

        // home 저장
        homeRepository.save(home);

        return home;
    }

    // 사진 삭제
    @Transactional
    public Home deleteHome(Home home) {
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
