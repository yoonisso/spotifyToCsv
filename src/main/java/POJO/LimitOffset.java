package POJO;
public class LimitOffset {
    private String limit;
    private String offset;

    public LimitOffset(String limit, String offset){
        this.limit = limit;
        this.offset = offset;
    }

    public String getLimit() {
        return limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }
}
