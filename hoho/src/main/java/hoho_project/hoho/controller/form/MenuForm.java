package hoho_project.hoho.controller.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class MenuForm {

    private String menu_type;
    private MultipartFile fileSave;
    private String fileName;
    private String filePath;
    private String menu_name;
    private String menu_ingredient;
    private Long menu_price;
}
