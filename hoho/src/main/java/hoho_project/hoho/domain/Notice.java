package hoho_project.hoho.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notice_id;

    private String notice_title;

    private String notice_date;

    private Long notice_view;

    private String notice_imgName;

    @Lob
    private byte[] notice_image;

    @Column(columnDefinition = "LONGTEXT")
    private String notice_content;

    //==생성 메서드==//
    public static Notice createNoticeWithFile(String notice_title, String notice_date, String notice_imgName,
                                              byte[] notice_image, String notice_content) {

        Notice notice = new Notice();

        notice.setNotice_title(notice_title);
        notice.setNotice_date(notice_date);
        notice.setNotice_view(0L);
        notice.setNotice_imgName(notice_imgName);
        notice.setNotice_image(notice_image);
        notice.setNotice_content(notice_content);

        return notice;
    }

    public static Notice createNoticeNoFile(String notice_title, String notice_date, String notice_content) {

        Notice notice = new Notice();

        notice.setNotice_title(notice_title);
        notice.setNotice_date(notice_date);
        notice.setNotice_view(0L);
        notice.setNotice_content(notice_content);

        return notice;
    }
}
