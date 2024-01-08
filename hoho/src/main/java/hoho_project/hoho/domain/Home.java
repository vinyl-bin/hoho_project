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

    //==생성 메서드==//
    public static Home createHome(String home_imgName, byte[] home_image) {
        Home home = new Home();
        home.setHome_imgName(home_imgName);
        home.setHome_image(home_image);
        return home;
    }

}
