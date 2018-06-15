package todo.service.impl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import todo.configuration.SpringJDBCConfiguration;
import todo.dao.DAO;
import todo.domain.BasketItem;
import todo.service.BasketService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("basketService")
public class BasketServiceImpl implements BasketService {
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJDBCConfiguration.class);
    private DAO dao = context.getBean(DAO.class);

    @Override
    public void add(BasketItem item) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Basket_UserID", item.getUserID().toString());
        params.put("Basket_ArticleID", item.getArticle().getId().toString());
        params.put("Basket_Num", item.getNum().toString());
        dao.add("basket_item", params, "Basket_ArticleID");
    }

    @Override
    public void deleteByID(Integer id) {
        dao.delete(id);
    }

    @Override
    public void deleteByArticle(Integer id) {
        List<Integer> ids = dao.getIDsByParam("basket_item", "Basket_ArticleID", id.toString());
        if(ids != null) {
            for(Integer item_id: ids) dao.delete(id);
        }
    }

    @Override
    public BasketItem getById(Integer id) {
        Map<String, Object> map = dao.getById(id);
        if(map == null) return null;
        return new BasketItem(Integer.parseInt(map.get("ID").toString()), Integer.parseInt(map.get("Basket_UserID").toString()),
                new ArticleServiceImpl().getById(Integer.parseInt(map.get("Basket_ArticleID").toString())), Integer.parseInt(map.get("Basket_Num").toString()));
    }

    @Override
    public List<BasketItem> getByUserID(Integer id) {
        List<Integer> ids = dao.getIDsByParam("basket_item", "Basket_UserID", id.toString());
        List<BasketItem> items = new ArrayList<BasketItem>();
        if(ids != null) {
            for(Integer item_id: ids) items.add(getById(item_id));
        }
        return items;
    }
}
