package todo.domain;

/**
 * Created by Олег on 09.06.2018.
 */
public class Attribute {
    private Integer id = 0;
    private String name = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
