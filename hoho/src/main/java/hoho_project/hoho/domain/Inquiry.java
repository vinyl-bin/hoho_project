package hoho_project.hoho.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiry_id;

    private String inquiry_type;

    private String inquiry_imgStoreName;

    private String inquiry_imgPath;

    //==생성 메서드==//
    public static Inquiry createInquiry(String inquiry_type, String inquiry_imgStoreName, String inquiry_imgPath) {
        Inquiry inquiry = new Inquiry();

        inquiry.setInquiry_type(inquiry_type);
        inquiry.setInquiry_imgStoreName(inquiry_imgStoreName);
        inquiry.setInquiry_imgPath(inquiry_imgPath);

        return inquiry;
    }
}
