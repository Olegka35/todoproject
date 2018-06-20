package oper_project.methods;

/**
 * Created by Олег on 12.07.2017.
 */
public class FilterQuery {
    private String field;
    private Integer value;

    public FilterQuery() {
    }

    public FilterQuery(String field, Integer value) {
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
