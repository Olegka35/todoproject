package oper_project.methods;

/**
 * Created by Олег on 09.08.2017.
 */
public class UserQuery {
    private Integer userID;
    private Integer page;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public UserQuery(Integer userID, Integer page) {
        this.userID = userID;
        this.page = page;
    }

    public UserQuery() {
    }
}
