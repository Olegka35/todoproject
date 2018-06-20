package oper_project.methods;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Олег on 19.07.2017.
 */
public class ArticleCookie {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Integer watchPage;
    private String sortField;
    private Boolean reversed;
    private String searchCookie;

    public ArticleCookie(HttpServletRequest request) {
        this.request = request;
        this.watchPage = GetWatchPage(request);
        this.sortField = GetSortField(request);
        this.reversed = GetReversed(request);
        this.searchCookie = GetSearchCookie(request);
    }

    public ArticleCookie(HttpServletRequest request, HttpServletResponse response) {
        this(request);
        this.response = response;
    }

    public ArticleCookie(HttpServletRequest request, HttpServletResponse response, Integer watchPage, String sortField, Boolean reversed, String searchCookie) {
        this.request = request;
        this.response = response;
        setWatchPage(watchPage);
        setSortField(sortField);
        setReversed(reversed);
        setSearchCookie(searchCookie);
    }

    public Integer getWatchPage() {
        return watchPage;
    }

    public void setWatchPage(Integer watchPage) {
        this.watchPage = watchPage;
        AddWatchPageCookie(watchPage);
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
        AddSortFieldCookie(sortField);
    }

    public Boolean getReversed() {
        return reversed;
    }

    public void setReversed(Boolean reversed) {
        this.reversed = reversed;
        AddReversedCookie(reversed);
    }

    public String getSearchCookie() {
        return searchCookie;
    }

    public void setSearchCookie(String searchCookie) {
        this.searchCookie = searchCookie;
        AddSearchCookie(searchCookie);
    }

    private void AddWatchPageCookie(int page) {
        AddCookie("page", Integer.valueOf(page).toString());
    }

    private void AddSortFieldCookie(String field) {
        if (field == null)
            field = "object_id";
        AddCookie("sort_field", field);
    }

    private void AddReversedCookie(boolean reversed) {
        int value = reversed ? 1 : 0;
        AddCookie("reversed", Integer.valueOf(value).toString());
    }

    private void AddSearchCookie(String search) {
        AddCookie("search", search);
    }

    private int GetWatchPage(HttpServletRequest request) {
        return Integer.parseInt(GetCookie("page"));
    }

    private String GetSortField(HttpServletRequest request) {
        return GetCookie("sort_field");
    }

    private boolean GetReversed(HttpServletRequest request) {
        return Integer.parseInt(GetCookie("reversed")) != 0;
    }

    private String GetSearchCookie(HttpServletRequest request) {
        return GetCookie("search");
    }

    private void AddCookie(String name, String value) {
        Cookie[] cookies = request.getCookies();
        Cookie OurCookie = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    OurCookie = cookie;
                    break;
                }
            }
        }
        if (OurCookie == null)
            OurCookie = new Cookie(name, value);
        else
            OurCookie.setValue(value);
        OurCookie.setMaxAge(-1);
        response.addCookie(OurCookie);
    }

    private String GetCookie(String name) {
        Cookie[] cookies = request.getCookies();
        String result = "-1";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    result = cookie.getValue();
                    break;
                }
            }
        }
        return result;
    }
}
