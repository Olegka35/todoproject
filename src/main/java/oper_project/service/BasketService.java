package oper_project.service;


import oper_project.domain.BasketItem;

import java.util.List;

public interface BasketService {
    void add(BasketItem item);
    void deleteByID(Integer id);
    void deleteByUserID(Integer user_id);
    void deleteByArticle(Integer id);
    void update(BasketItem item);
    BasketItem getById(Integer id);
    BasketItem getByArticle(Integer articleID);
    List<BasketItem> getByUserID(Integer id);
    String checkForOrder(Integer userID);
    Integer getPrice(Integer userID);
}
