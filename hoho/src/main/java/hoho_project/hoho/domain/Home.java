package hoho_project.hoho.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor  //기본 생성자 만들지 않아도 됨.
public class Home {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long home_id;


    private String home_imgName;

    @Lob
    private byte[] home_image;

}
