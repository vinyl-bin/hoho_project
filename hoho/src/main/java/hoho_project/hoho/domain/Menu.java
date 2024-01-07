package hoho_project.hoho.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menu_id;

    private String menu_type;

    private String menu_imgName;

    @Lob
    private byte[] menu_image;

    private String menu_name;

    private String menu_ingredient;

    private Long menu_price;
}