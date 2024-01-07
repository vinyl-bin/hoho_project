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
}
