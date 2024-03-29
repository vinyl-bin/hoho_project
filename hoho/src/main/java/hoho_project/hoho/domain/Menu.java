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

    private String menu_imgStoreName;

    private String menu_imgPath;

    private String menu_name;

    private String menu_ingredient;

    private Long menu_price;

    //==생성 메서드==//
    public static Menu createMenu(String menu_type, String menu_imgStoreName,String menu_imgPath,
                                  String menu_name, String menu_ingredient, Long menu_price) {
        Menu menu = new Menu();

        menu.setMenu_type(menu_type);
        menu.setMenu_imgStoreName(menu_imgStoreName);
        menu.setMenu_imgPath(menu_imgPath);
        menu.setMenu_name(menu_name);
        menu.setMenu_ingredient(menu_ingredient);
        menu.setMenu_price(menu_price);

        return menu;
    }
}