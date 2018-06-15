package todo.service.impl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import todo.configuration.SpringJDBCConfiguration;
import todo.configuration.ToDoListConfiguration;
import todo.dao.DAO;
import todo.dao.TaskDao;
import todo.domain.Article;
import todo.service.ArticleService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Олег on 08.06.2018.
 */

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJDBCConfiguration.class);
    private DAO dao = context.getBean(DAO.class);

    @Override
    public void add(Article article) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Type", article.getType());
        params.put("Num", article.getNum().toString());
        params.put("Price", article.getPrice().toString());
        for (Map.Entry<String, Object> entry: article.getDetails().entrySet()) {
            params.put(entry.getKey(), entry.getValue().toString());
        }
        dao.add("article", params, "Type");
    }

    @Override
    public void update(Article article) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Type", article.getType());
        params.put("Num", article.getNum().toString());
        params.put("Price", article.getPrice().toString());
        for (Map.Entry<String, Object> entry: article.getDetails().entrySet()) {
            params.put(entry.getKey(), entry.getValue().toString());
        }
        dao.update(article.getId(), params);
    }

    @Override
    public void delete(Integer id) {
        dao.delete(id);
    }

    @Override
    public Article getById(int id) {
        Map<String, Object> map = dao.getById(id);
        if(map == null) return null;

        return convertMapToArticle(map);
    }

    @Override
    public List<Article> getArticleList() {
        List<Map<String, Object>> articles = dao.getList("article");
        List<Article> resultList = new ArrayList<Article>();
        for(Map<String, Object> map: articles) {
            resultList.add(convertMapToArticle(map));
        }
        return resultList;
    }

    @Override
    public List<Article> getArticleList(Integer page) {
        List<Map<String, Object>> articles = dao.getList("article", (page-1)* ToDoListConfiguration.TASKS_ON_PAGE, ToDoListConfiguration.TASKS_ON_PAGE);
        List<Article> resultList = new ArrayList<Article>();
        for(Map<String, Object> map: articles) {
            resultList.add(convertMapToArticle(map));
        }
        return resultList;
    }

    @Override
    public List<Article> getArticleList(String search) {
        HashMap<String, String> searchMap = new HashMap<>();
        searchMap.put("Type", search);
        List<Map<String, Object>> articles = dao.getList("article", searchMap);
        List<Article> resultList = new ArrayList<Article>();
        for(Map<String, Object> map: articles) {
            resultList.add(convertMapToArticle(map));
        }
        return resultList;
    }

    @Override
    public List<Article> getArticleList(Integer page, String search) {
        HashMap<String, String> searchMap = new HashMap<>();
        searchMap.put("Type", search);
        List<Map<String, Object>> articles = dao.getList("article", (page-1)* ToDoListConfiguration.TASKS_ON_PAGE, ToDoListConfiguration.TASKS_ON_PAGE, searchMap);
        List<Article> resultList = new ArrayList<Article>();
        for(Map<String, Object> map: articles) {
            resultList.add(convertMapToArticle(map));
        }
        return resultList;
    }

    @Override
    public List<Article> getArticleList(Integer page, String order_field, Boolean reversed, String search) {
        Map<String, String> searchMap;
        if(search != null && search.length() > 0) {
            searchMap = new HashMap<>();
            searchMap.put("Type", search);
        }
        else searchMap = null;

        List<Map<String, Object>> articles = dao.getList("article", (page-1)* ToDoListConfiguration.TASKS_ON_PAGE, ToDoListConfiguration.TASKS_ON_PAGE, order_field, reversed, searchMap);
        if(articles == null) return null;
        List<Article> resultList = new ArrayList<Article>();
        for(Map<String, Object> map: articles) {
            resultList.add(convertMapToArticle(map));
        }
        return resultList;
    }

    @Override
    public Integer getArticlesNum() {
        List<Map<String, Object>> list = dao.getList("article");
        return list.size();
    }

    @Override
    public Integer getArticlesNum(String search) {
        if(search == null) return getArticlesNum();
        HashMap<String, String> searchMap = new HashMap<>();
        searchMap.put("Type", search);
        List<Map<String, Object>> list = dao.getList("article", searchMap);
        return list.size();
    }

    private Article convertMapToArticle(Map<String, Object> map)
    {
        Integer id = Integer.parseInt(map.get("ID").toString());
        String type = map.get("Type").toString();
        Integer num = Integer.parseInt(map.get("Num").toString());
        Integer price = Integer.parseInt(map.get("Price").toString());
        map.remove("ID");
        map.remove("Type");
        map.remove("Num");
        map.remove("Price");
        return new Article(id, type, map, num, price);
    }
}
