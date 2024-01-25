package hoho_project.hoho.service;

import hoho_project.hoho.domain.Notice;
import hoho_project.hoho.dto.NoticeSearchCondition;
import hoho_project.hoho.repository.NoticeRepositoy;
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
public class NoticeService {

    private final NoticeRepositoy noticeRepository;

    //공지사항 저장
    @Transactional
    public Notice saveNotice(String notice_title, String notice_date, String notice_content, MultipartFile file) {

        Notice notice;

        if (!file.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String notice_imgStoreName = uuid + "_" + file.getOriginalFilename();

            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/saveFiles/notice";

            File saveFile = new File(projectPath, notice_imgStoreName);

            try {
                file.transferTo(saveFile);
                System.out.println("file Saved successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }

            String notice_imgPath = saveFile.getAbsolutePath();

            notice = Notice.createNoticeWithFile(notice_title, notice_date, notice_imgStoreName, notice_imgPath, notice_content);
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

        Notice notice;

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

            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/saveFiles/notice";

            File saveFile = new File(projectPath, new_notice_imgStoreName);

            try {
                file.transferTo(saveFile);
                System.out.println("file Saved successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }

            String new_notice_imgPath = saveFile.getAbsolutePath();

            notice = Notice.createNoticeWithFile(notice_title, notice_date, new_notice_imgStoreName, new_notice_imgPath, notice_content);

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