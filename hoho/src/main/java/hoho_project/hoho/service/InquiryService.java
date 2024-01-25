package hoho_project.hoho.service;

import hoho_project.hoho.domain.Home;
import hoho_project.hoho.domain.Inquiry;
import hoho_project.hoho.repository.InquiryRepository;
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
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    //사진 등록
    @Transactional
    public Inquiry saveInquiry(String inquiry_type, MultipartFile file) {

        UUID uuid = UUID.randomUUID();
        String inquiry_imgStoreName = uuid + "_" + file.getOriginalFilename();

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/saveFiles/inquiry/";

        File saveFile = new File(projectPath, inquiry_imgStoreName);

        try {
            file.transferTo(saveFile);
            System.out.println("file saved successfully to " + saveFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String inquiry_imgPath = saveFile.getAbsolutePath();

        Inquiry inquiry = Inquiry.createInquiry(inquiry_type, inquiry_imgStoreName, inquiry_imgPath);

        inquiryRepository.save(inquiry);

        return inquiry;
    }

    //사진 삭제
    @Transactional
    public Inquiry deleteInquiry(Inquiry inquiry) {

        File fileToDelete = new File(inquiry.getInquiry_imgPath());

        if (fileToDelete.delete()) {
            System.out.println("successfully deleted");
        } else {
            System.out.println("failed");
        }

        inquiryRepository.delete(inquiry);

        return inquiry;
    }

    public Inquiry findOne(Long inquiry_id) {
        return inquiryRepository.findOne(inquiry_id);
    }

    public List<Inquiry> findInquiries() {
        return inquiryRepository.findAll();
    }
}