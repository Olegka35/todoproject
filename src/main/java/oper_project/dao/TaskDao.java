package oper_project.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Олег on 05.03.2017.
 */
public interface TaskDao {
    void add(String type, Map<String, String> params, String name_field);
    void update(Integer id, Map<String, String> params);
    void delete(Integer id);
    Map<String, Object> getById(int id);
    List<Map<String, Object>> getList(String type);
    List<Map<String, Object>> getList(String type, Integer first, Integer num);
    List<Map<String, Object>> getList(String type, Map<String, String> filterMap);
    List<Map<String, Object>> getList(String type, Integer first, Integer num, Map<String, String> filterMap);
    Integer getIdByParam(String type, String param, String value);
    List<Integer> getIDsByParam(String type, String param, String value);
    List<Integer> getIDsByParam(String type, String param, String value, Integer first, Integer num);
    List<Integer> getIDsByParam(String type, String param, String value, Integer first, Integer num, String order_field, Boolean reversed, Map<String, String> filterMap);
    List<Integer> getIDsByParam(String type, String param, String value, Map<String, String> filterMap);
}