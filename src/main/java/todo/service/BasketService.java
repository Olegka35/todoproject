package todo.service;


import todo.domain.BasketItem;

import java.util.List;

public interface BasketService {
    void add(BasketItem item);
    void deleteByID(Integer id);
    void deleteByArticle(Integer id);
    BasketItem getById(Integer id);
    List<BasketItem> getByUserID(Integer id);
}
