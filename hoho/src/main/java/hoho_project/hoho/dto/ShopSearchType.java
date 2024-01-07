package hoho_project.hoho.dto;

public enum ShopSearchType {
    REGION("지역"),
    NAME("매장명"),
    ADDRESS("주소");

    private String kr;

    ShopSearchType(String kr) {
        this.kr = kr;
    }

    public String getKr() {
        return kr;
    }
}
