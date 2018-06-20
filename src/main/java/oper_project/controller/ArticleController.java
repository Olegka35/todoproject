package oper_project.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import oper_project.domain.Article;
import oper_project.domain.html_requests.ArticleList;
import oper_project.domain.BasketItem;
import oper_project.domain.Operator;
import oper_project.methods.SearchText;
import oper_project.methods.ArticleCookie;
import oper_project.service.ArticleService;
import oper_project.service.BasketService;
import oper_project.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Олег on 09.06.2018.
 */

@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final OperatorService operatorService;
    private final BasketService basketService;

    @Autowired
    public ArticleController(ArticleService articleService, OperatorService operatorService, BasketService basketService) {
        this.articleService = articleService;
        this.operatorService = operatorService;
        this.basketService = basketService;
    }

    @ModelAttribute("operatorBean")
    public Operator createOperatorBean() {
        return new Operator();
    }

    @ModelAttribute("articleBean")
    public Article createArticleBean() {
        return new Article();
    }

    @ModelAttribute("basketBean")
    public BasketItem createBasketBean() {
        return new BasketItem();
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView displayArticles(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam(value = "page", required = false) Integer page,
                                     @RequestParam(value = "sort", required = false) String sort,
                                     @RequestParam(value = "reversed", required = false) Boolean reversed,
                                     @RequestParam(value = "search", required = false) String search) {
        //try {
            if (page == null) page = 1;
            if (sort != null && sort.equals("object_id") && !reversed)
                sort = null;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            //Operator user = operatorService.getUser(auth.getName());
            return ShowArticleList(request, response, page, sort, reversed, search);
        //}
        //catch (NullPointerException e) {
        //    return new ModelAndView("acc_error");
        //}
    }

    @RequestMapping(value = "/article", method = RequestMethod.POST)
    public
    @ResponseBody
    Article getArticleInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody String id) {
        return articleService.getById(Integer.parseInt(id.split("=")[1]));
    }

    @RequestMapping(value = "/index/delete", method = RequestMethod.POST)
    public String deleteArticle(HttpServletRequest request, @RequestBody String id) {
        if(request.isUserInRole("ROLE_ADMIN")) {
            Integer articleId = Integer.parseInt(id.split("=")[1]);
            basketService.deleteByArticle(articleId);
            articleService.delete(articleId);
        }
        return getArticle(request);
    }

    @RequestMapping(value = "/index/edit", method = RequestMethod.POST)
    public String updateArticle(HttpServletRequest request) {
        if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_MANAGER")) {
            Map<String, String[]> map = request.getParameterMap();
            Map<String, Object> details = new HashMap<String, Object>();
            Integer price = null, num = null;
            String type = null;
            Article article = articleService.getById(Integer.parseInt(map.get("ID")[0]));
            if (article != null) {
                for (Map.Entry entry : map.entrySet()) {
                    String key = entry.getKey().toString();
                    if (!Arrays.asList("Type", "Num", "Price").contains(key)) {
                        Integer attrId = operatorService.getAttrIDByName(key);
                        if (attrId != null)
                            details.put(key, map.get(key)[0]);
                    }
                    article.setDetails(details);
                }
                article.setType(map.get("Type")[0]);
                article.setPrice(Integer.parseInt(map.get("Price")[0]));
                article.setNum(Integer.parseInt(map.get("Num")[0]));
            }
            articleService.update(article);
        }
        return getArticle(request);
    }

    @RequestMapping(value = "/index/add", method = RequestMethod.POST)
    public String addArticle(HttpServletRequest request) {
        if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_MANAGER")) {
            Map<String, String[]> map = request.getParameterMap();
            Map<String, Object> details = new HashMap<String, Object>();
            Integer price = null, num = null;
            String type = null;
            for (Map.Entry entry : map.entrySet()) {
                String key = entry.getKey().toString();
                if (!Arrays.asList("Type", "Num", "Price").contains(key)) {
                    Integer attrId = operatorService.getAttrIDByName(key);
                    if (attrId != null)
                        details.put(key, map.get(key)[0]);
                }
            }
            type = map.get("Type")[0];
            price = Integer.parseInt(map.get("Price")[0]);
            num = Integer.parseInt(map.get("Num")[0]);
            if (price < 0) price = 0;
            if (num < 0) num = 0;
            Article article = new Article(-1, type, details, num, price);
            articleService.add(article);
        }
        return getArticle(request);
    }

    @RequestMapping(value = "/index/sort", method = RequestMethod.POST)
    public
    @ResponseBody
    ArticleList sort(HttpServletRequest request, HttpServletResponse response, @RequestBody String field) {
        ArticleCookie cookie = new ArticleCookie(request, response);
        String field_name = field.split("=")[1];

        boolean reversed
                = cookie.getSortField().equals(field_name) && !cookie.getReversed();

        ArticleList articleList = new ArticleList();
        articleList.setArticleList(articleService.getArticleList(1, field_name, reversed, cookie.getSearchCookie()));
        articleList.setPage(1);
        articleList.setPageNumEx(articleService.getArticlesNum(cookie.getSearchCookie()));
        articleList.setParams(operatorService.getVarArticleParams());

        cookie.setSortField(field_name);
        cookie.setWatchPage(1);
        cookie.setReversed(reversed);
        return articleList;
    }

    @RequestMapping(value = "/index/page", method = RequestMethod.POST)
    public
    @ResponseBody
    ArticleList filter(HttpServletRequest request, HttpServletResponse response, @RequestBody String page) {
        ArticleCookie taskCookie = new ArticleCookie(request, response);
        Integer pageVal = Integer.parseInt(page.split("=")[1]);

        ArticleList articleList = new ArticleList();
        articleList.setArticleList(articleService.getArticleList(pageVal, taskCookie.getSortField(), taskCookie.getReversed(), taskCookie.getSearchCookie()));
        articleList.setPage(pageVal);
        articleList.setPageNumEx(articleService.getArticlesNum(taskCookie.getSearchCookie()));
        articleList.setParams(operatorService.getVarArticleParams());
        taskCookie.setWatchPage(pageVal);
        return articleList;
    }

    @RequestMapping(value = "/index/search", method = RequestMethod.POST)
    public
    @ResponseBody
    ArticleList search(HttpServletRequest request, HttpServletResponse response, @RequestBody SearchText search) {
        ArticleCookie cookie = new ArticleCookie(request, response);
        String text = search.getText();

        cookie.setSearchCookie(text);

        ArticleList articleList = new ArticleList();
        articleList.setArticleList(articleService.getArticleList(1, cookie.getSortField(), cookie.getReversed(), cookie.getSearchCookie()));
        articleList.setPage(1);
        articleList.setPageNumEx(articleService.getArticlesNum(cookie.getSearchCookie()));
        articleList.setSearch(text);
        articleList.setParams(operatorService.getVarArticleParams());
        cookie.setWatchPage(1);
        return articleList;
    }

    private ModelAndView ShowArticleList(HttpServletRequest request, HttpServletResponse response,
                                         int page, String order, Boolean reversed, String search) {
        ArticleCookie cookie = new ArticleCookie(request, response, page, order, false, search);

        List<Article> listOfArticles;
        listOfArticles = articleService.getArticleList(page, order, reversed, search);

        ArticleList articleList = new ArticleList();
        articleList.setArticleList(listOfArticles);
        //articleList.setLogin(user.getLogin());
        articleList.setPage(page);
        articleList.setPageNumEx(articleService.getArticlesNum(search));
        articleList.setSearch(search);
        articleList.setParams(operatorService.getVarArticleParams());
        return new ModelAndView("index", "articleList", articleList);
    }

    private String getArticle(HttpServletRequest request) {
        try {
            ArticleCookie cookie = new ArticleCookie(request);
            String result = "redirect:/index/";
            result += '?';
            if (cookie.getWatchPage() > 1)
                result += "page=" + cookie.getWatchPage() + "&";
            if (!cookie.getSortField().equals("object_id") || cookie.getReversed()) {
                result += "sort=" + cookie.getSortField() + "&reversed=" + cookie.getReversed() + "&";
            }
            if (cookie.getSearchCookie().length() > 0) {
                result += "search=" + cookie.getSearchCookie() + "&";
            }
            return result;
        }
        catch (NullPointerException e) {
            return "redirect:/logout";
        }
    }
}
