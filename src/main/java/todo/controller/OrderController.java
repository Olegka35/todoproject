package todo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import todo.domain.*;
import todo.service.ArticleService;
import todo.service.BasketService;
import todo.service.OperatorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {
    private final ArticleService articleService;
    private final OperatorService operatorService;
    private final BasketService basketService;

    @Autowired
    public OrderController(ArticleService articleService, OperatorService operatorService, BasketService basketService) {
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

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public ModelAndView showBasket(HttpServletRequest request, HttpServletResponse response) {
        List<BasketItem> basket = new ArrayList<BasketItem>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userID = operatorService.getUser(auth.getName()).getID();
        basket = basketService.getByUserID(userID);
        return new ModelAndView("basket", "basket", basket);
    }

    @RequestMapping(value = "/add_to_basket", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addToBasket(@RequestBody AddToBasketAttr attr) {
        Map<String, Object> responseMap = new HashMap<String, Object>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        if(login.equals("anonymousUser")) {
            responseMap.put("error", true);
            responseMap.put("text", "You should log in first");
        }
        else {
            Operator user = operatorService.getUser(login);
            if(user == null) {
                responseMap.put("error", true);
                responseMap.put("text", "Your account does not exist");
            }
            else {
                Article article = articleService.getById(attr.getArticle_id());
                if(article == null) {
                    responseMap.put("error", true);
                    responseMap.put("text", "Article does not exist. Please, reload the page.");
                }
                else if(article.getNum() < attr.getNum()) {
                    responseMap.put("error", true);
                    responseMap.put("text", "Out of stock");
                }
                else {
                    responseMap.put("error", false);
                    basketService.add(new BasketItem(-1, user.getID(), article, attr.getNum()));
                }
            }
        }
        return responseMap;
    }

    @RequestMapping(value = "/delete_from_basket", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteFromBasket(@RequestBody String param) {
        Integer id = Integer.valueOf(param.split("=")[1]);
        Map<String, Object> responseMap = new HashMap<String, Object>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        if(login.equals("anonymousUser")) {
            responseMap.put("error", true);
            responseMap.put("text", "You should log in first");
        }
        else {
            Operator user = operatorService.getUser(login);
            if(user == null) {
                responseMap.put("error", true);
                responseMap.put("text", "Your account does not exist");
            }
            else {
                Article article = articleService.getById(attr.getArticle_id());
                if(article == null) {
                    responseMap.put("error", true);
                    responseMap.put("text", "Article does not exist. Please, reload the page.");
                }
                else if(article.getNum() < attr.getNum()) {
                    responseMap.put("error", true);
                    responseMap.put("text", "Out of stock");
                }
                else {
                    responseMap.put("error", false);
                    basketService.add(new BasketItem(-1, user.getID(), article, attr.getNum()));
                }
            }
        }
        return responseMap;
    }
}
