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

    private String inquiry_imgName;

    @Lob
    private byte[] inquiry_image;

    //==생성 메서드==//
    public static Inquiry createInquiry(String inquiry_type, String inquiry_imgName, byte[] inquiry_image) {
        Inquiry inquiry = new Inquiry();

        inquiry.setInquiry_type(inquiry_type);
        inquiry.setInquiry_imgName(inquiry_imgName);
        inquiry.setInquiry_image(inquiry_image);

        return inquiry;
    }
}
