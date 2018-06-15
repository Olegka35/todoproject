package todo.methods;

/**
 * Created by Олег on 18.07.2017.
 */
public class SearchText {
    private String text;
    private Integer page;

    public SearchText(String text) {
        this.text = text;
        page = 1;
    }

    public SearchText(Integer page) {
        this.text = "none";
        this.page = page;
    }

    public SearchText(String text, Integer page) {
        this.text = text;
        this.page = page;
    }

    public SearchText() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
