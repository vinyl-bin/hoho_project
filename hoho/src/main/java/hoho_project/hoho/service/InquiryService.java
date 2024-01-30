package hoho_project.hoho.service;

import hoho_project.hoho.domain.Home;
import hoho_project.hoho.domain.Inquiry;
import hoho_project.hoho.repository.InquiryRepository;
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
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    //사진 등록
    @Transactional
    public Inquiry saveInquiry(String inquiry_type, MultipartFile file) {

        UUID uuid = UUID.randomUUID();
        String inquiry_imgStoreName = uuid + "_" + file.getOriginalFilename();

        //초기화
        Inquiry inquiry = null;

        try {
            // 리소스에서 InputStream 얻어오기
            InputStream inputStream = file.getInputStream();

            Resource resource = new UrlResource("file:///home/dabin/Desktop/hohoImages/");

            Path saveFilePath = Paths.get(resource.getURI()).resolve("inquiry/" + inquiry_imgStoreName);

            // inquiry 폴더가 있는지 확인
            File inquiryDir = saveFilePath.getParent().toFile();
            if (!inquiryDir.exists()) {
                // inquiry 폴더가 없으면 생성
                inquiryDir.mkdir();
            }

            Files.copy(inputStream, saveFilePath, StandardCopyOption.REPLACE_EXISTING);

            // InputStream 및 File을 닫기
            IOUtils.closeQuietly(inputStream);

            System.out.println("파일을 저장하였습니다." + saveFilePath);


            String filePathString = saveFilePath.toString();

            System.out.println(filePathString);

            // inquiry 생성
            inquiry = inquiry.createInquiry(inquiry_type, inquiry_imgStoreName, filePathString);

            // inquiry 저장
            inquiryRepository.save(inquiry);
        } catch (IOException e) {
            System.err.println("Exception이 발생했습니다." + System.getProperty("user.dir"));
        }

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