package hoho_project.hoho.service;

import hoho_project.hoho.domain.Menu;
import hoho_project.hoho.domain.Notice;
import hoho_project.hoho.dto.NoticeSearchCondition;
import hoho_project.hoho.repository.NoticeRepositoy;
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
public class NoticeService {

    private final NoticeRepositoy noticeRepository;

    //공지사항 저장
    @Transactional
    public Notice saveNotice(String notice_title, String notice_date, String notice_content, MultipartFile file) {

        Notice notice = null;

        if (!file.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String notice_imgStoreName = uuid + "_" + file.getOriginalFilename();

            try {

                // 리소스에서 InputStream 얻어오기
                InputStream inputStream = file.getInputStream();

                Resource resource = new UrlResource("file:///home/dabin/Desktop/hohoImages/");

                Path saveFilePath = Paths.get(resource.getURI()).resolve("notice/" + notice_imgStoreName);

                // notice 폴더가 있는지 확인
                File noticeDir = saveFilePath.getParent().toFile();
                if (!noticeDir.exists()) {
                    // notice 폴더가 없으면 생성
                    noticeDir.mkdir();
                }

                Files.copy(inputStream, saveFilePath, StandardCopyOption.REPLACE_EXISTING);

                // InputStream 및 File을 닫기
                IOUtils.closeQuietly(inputStream);

                System.out.println("파일을 저장하였습니다." + saveFilePath);


                String filePathString = saveFilePath.toString();

                System.out.println(filePathString);

                notice = Notice.createNoticeWithFile(notice_title, notice_date, notice_imgStoreName, notice_imgStoreName, notice_content);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            notice = Notice.createNoticeNoFile(notice_title, notice_date, notice_content);
        }

        noticeRepository.save(notice);

        return notice;
    }

    //공지사항 수정
    @Transactional
    public Notice updateNotice(Long notice_id, String notice_title, String notice_date, String notice_imgStoreName,
                               String notice_imgPath, String notice_content, MultipartFile file) {

        Notice notice = null;

        // 새롭게 저장된 파일이 없고 기존에 저장된 파일도 없는 경우, 새롭게 저장된 파일은 없지만 기존에 저장된 파일은 있는 경우, 새롭게 저장된 파일이 있는 경우
        if (file.isEmpty() && notice_imgStoreName.equals(null)) {
            notice = Notice.createNoticeNoFile(notice_title, notice_date, notice_content);
        }
        else if (file.isEmpty()) {
            notice = Notice.createNoticeWithFile(notice_title, notice_date, notice_imgStoreName, notice_imgPath, notice_content);
        }
        else {

            UUID uuid = UUID.randomUUID();
            String new_notice_imgStoreName = uuid + "_" + file.getOriginalFilename();

            try {

                // 리소스에서 InputStream 얻어오기
                InputStream inputStream = file.getInputStream();

                Resource resource = new UrlResource("file:///home/dabin/Desktop/hohoImages/");

                Path saveFilePath = Paths.get(resource.getURI()).resolve("notice/" + new_notice_imgStoreName);

                // notice 폴더가 있는지 확인
                File noticeDir = saveFilePath.getParent().toFile();
                if (!noticeDir.exists()) {
                    // notice 폴더가 없으면 생성
                    noticeDir.mkdir();
                }

                Files.copy(inputStream, saveFilePath, StandardCopyOption.REPLACE_EXISTING);

                // InputStream 및 File을 닫기
                IOUtils.closeQuietly(inputStream);

                System.out.println("파일을 저장하였습니다." + saveFilePath);


                String filePathString = saveFilePath.toString();

                System.out.println(filePathString);

                notice = Notice.createNoticeWithFile(notice_title, notice_date, new_notice_imgStoreName, filePathString, notice_content);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!notice_imgStoreName.equals(null)) {
                File fileToDelete = new File(notice_imgPath);
                fileToDelete.delete();
            }
        }

        notice.setNotice_id(notice_id);

        noticeRepository.save(notice);

        return notice;
    }

    //공지사항 삭제
    @Transactional
    public Notice deleteNotice(Notice notice) {

        if (!notice.getNotice_imgStoreName().equals(null)) {

            File fileToDelete = new File(notice.getNotice_imgPath());
            fileToDelete.delete();
        }

        noticeRepository.delete(notice);

        return notice;
    }

    //조회수 올리기
    @Transactional
    public Notice updateNoticeView(Notice notice) {

        notice.setNotice_view(notice.getNotice_view()+1);

        noticeRepository.save(notice);

        return notice;
    }

    //공지사항 검색
    public Notice findOne(Long notice_id) {
        return noticeRepository.findOne(notice_id);
    }

    public List<Notice> findNotices(NoticeSearchCondition searchCondition) {
        return noticeRepository.findBySearchOption(searchCondition);
    }
}