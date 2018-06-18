package todo.domain.html_requests;

public class AddToBasketAttr {
    private Integer article_id = 0;
    private Integer num = 0;

    public AddToBasketAttr() {
    }

    public AddToBasketAttr(Integer article_id, Integer num) {
        this.article_id = article_id;
        this.num = num;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    @Override
    public String toString() {
        return "AddToBasketAttr{" +
                "num=" + num +
                ", article_id=" + article_id +
                '}';
    }
}
