package oper_project.domain.html_requests;

import oper_project.configuration.PageConfiguration;
import oper_project.domain.Article;

import java.util.List;
import java.util.Map;

/**
 * Created by Олег on 09.06.2018.
 */
public class ArticleList {
    private List<Article> articleList;
    private String login = "Guest";
    private Integer page;
    private Integer pageNum;
    private String search;
    private List<Map<String, Object>> params;

    public List<Map<String, Object>> getParams() {
        return params;
    }

    public void setParams(List<Map<String, Object>> params) {
        this.params = params;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setPageNumEx(Integer num)
    {
        setPageNum(num / PageConfiguration.ITEMS_ON_PAGE + (num % PageConfiguration.ITEMS_ON_PAGE > 0 ? 1 : 0));
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}

