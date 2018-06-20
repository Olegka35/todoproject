package oper_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import oper_project.domain.Article;
import oper_project.service.ArticleService;
import oper_project.service.BasketService;
import oper_project.service.OperatorService;
import oper_project.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class RestController {
    private final ArticleService articleService;
    private final OperatorService operatorService;
    private final BasketService basketService;
    private final OrderService orderService;

    @Autowired
    public RestController(ArticleService articleService, OperatorService operatorService, BasketService basketService, OrderService orderService) {
        this.articleService = articleService;
        this.operatorService = operatorService;
        this.basketService = basketService;
        this.orderService = orderService;
    }

    /* articles */
    @RequestMapping(method = RequestMethod.GET, value = "/rest/articles", produces = "application/json")
    public ResponseEntity<List<Article>> restGetArticles() {
        List<Article> articleList = articleService.getArticleList();
        if(articleList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(articleList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/rest/articles/{id}", produces = "application/json")
    public ResponseEntity<Article> restGetArticle(@PathVariable("id") Integer id) {
        Article article = articleService.getById(id);
        if(article == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/rest/articles/{id}", produces = "application/json")
    public ResponseEntity<Article> restGetArticle(HttpServletRequest request, @PathVariable("id") int id) {
        if(!request.isUserInRole("ROLE_ADMIN"))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Article article = articleService.getById(id);
        if(article == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        articleService.delete(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }
}
