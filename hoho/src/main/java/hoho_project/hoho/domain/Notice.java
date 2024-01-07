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
}
