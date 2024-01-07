package hoho_project.hoho.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data //getter,setter 등등 다 포함됨.
public class ShopSearchCondition {

    ShopSearchType type;
    String content;

    public ShopSearchCondition(ShopSearchType type, String content) {
        this.type = type;
        this.content = content;
    }
}
