package hoho_project.hoho.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class NoticeSearchCondition {

    NoticeSearchType type;
    String content;

    public NoticeSearchCondition(NoticeSearchType type, String content) {
        this.type = type;
        this.content = content;
    }
}
