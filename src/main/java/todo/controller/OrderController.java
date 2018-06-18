package todo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import todo.domain.*;
import todo.domain.html_requests.AddToBasketAttr;
import todo.domain.html_requests.MakeOrder;
import todo.service.ArticleService;
import todo.service.BasketService;
import todo.service.OperatorService;
import todo.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class OrderController {
    private final ArticleService articleService;
    private final OperatorService operatorService;
    private final BasketService basketService;
    private final OrderService orderService;

    @Autowired
    public OrderController(ArticleService articleService, OperatorService operatorService, BasketService basketService, OrderService orderService) {
        this.articleService = articleService;
        this.operatorService = operatorService;
        this.basketService = basketService;
        this.orderService = orderService;
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

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public ModelAndView showOrderList(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("orders");
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
                    BasketItem tryFind = basketService.getByArticle(article.getId());
                    if(tryFind == null)
                        basketService.add(new BasketItem(-1, user.getID(), article, attr.getNum()));
                    else
                        basketService.update(new BasketItem(tryFind.getId(), user.getID(), article, tryFind.getNum() + attr.getNum()));
                }
            }
        }
        return responseMap;
    }

    @RequestMapping(value = "/delete_from_basket", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteFromBasket(@RequestBody String param) {
        Integer id = Integer.valueOf(param.split("=")[1]);
        BasketItem item = basketService.getById(id);
        Map<String, Object> responseMap = new HashMap<String, Object>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        if(login.equals("anonymousUser")) {
            responseMap.put("error", true);
            responseMap.put("text", "You should log in first");
        }
        else {
            Operator user = operatorService.getUser(login);
            if(user == null || item == null || user.getID() != item.getUserID()) {
                responseMap.put("error", true);
                responseMap.put("text", "You have no access to delete this item from the basket");
            }
            else {
                responseMap.put("error", false);
                basketService.deleteByID(item.getId());
            }
        }
        return responseMap;
    }

    @RequestMapping(value = "/check_for_order", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkForOrder(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userID = operatorService.getUser(auth.getName()).getID();
        String result = basketService.checkForOrder(userID);
        Map<String, Object> responseMap = new HashMap<String, Object>();

        if(result != null) {
            responseMap.put("error", true);
            responseMap.put("text", result);
        }
        else {
            responseMap.put("error", false);
        }
        return responseMap;
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> makeOrder(@RequestBody MakeOrder order) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Operator user = operatorService.getUser(auth.getName());
        Integer userID = user.getID();
        String result = basketService.checkForOrder(userID);
        Map<String, Object> responseMap = new HashMap<String, Object>();

        if(result != null) {
            responseMap.put("error", true);
            responseMap.put("text", result);
        }
        else {
            List<OrderItem> details = new ArrayList<>();
            List<BasketItem> basketItems = basketService.getByUserID(userID);
            for(BasketItem item: basketItems) {
                details.add(new OrderItem(item.getArticle().getType(), item.getArticle().getPrice(), item.getNum()));
            }
            orderService.addOrder(new Order(-1, user, order.getName(), order.getEmail(), order.getAddress(), order.getTelephone(), new Date(), 0, 0, order.getPay_type(), details));
            basketService.deleteByUserID(userID);
            responseMap.put("error", false);
        }
        return responseMap;
    }
}
