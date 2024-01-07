package hoho_project.hoho.dto;

public enum NoticeSearchType {
    TITLE("제목"),
    CONTENT("내용");

    private String kr;

    NoticeSearchType(String kr) {
        this.kr = kr;
    }

    public String getKr() {
        return kr;
    }
}
