package todo.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import todo.dao.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Олег on 05.03.2017.
 */
@Repository("taskDao")
public class TaskDaoImpl implements TaskDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(final String type, final Map<String, String> params, final String name_field) {
        String ADD_PARAMS = "INSERT INTO params VALUES (?, (SELECT attribute_id FROM attribute WHERE attribute_name = ?), ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    String ADD_QUERY = "INSERT INTO object VALUES (0, (SELECT object_type_id FROM object_type WHERE type_name = ?), ?)";
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(ADD_QUERY, new String[] {"object_id"});
                        ps.setString(1, type);
                        ps.setString(2, params.get(name_field));
                        return ps;
                    }
                },
                keyHolder);


        Integer insert_id = keyHolder.getKey().intValue();
        if(params != null) {
            for (Map.Entry<String, String> pair : params.entrySet()) {
                jdbcTemplate.update(ADD_PARAMS, insert_id, pair.getKey(), pair.getValue());
            }
        }
    }

    public void update(Integer id, Map<String, String> params) {
        // UPDATE_QUERY = "UPDATE params SET val = ? WHERE object_id = ? AND attribute_id = (SELECT attribute_id FROM attribute WHERE attribute_name = ?)";
        String UPDATE_QUERY = "REPLACE INTO params VALUES (?, (SELECT attribute_id FROM attribute WHERE attribute_name = ?), ?)";
        for (Map.Entry<String, String> pair: params.entrySet()) {
            jdbcTemplate.update(UPDATE_QUERY, id, pair.getKey(), pair.getValue());
        }
    }


    public void delete(Integer id) {
        String DELETE_OBJECT_QUERY = "DELETE FROM object WHERE object_id = ?";
        String DELETE_PARAMS_QUERY = "DELETE FROM params WHERE object_id = ?";
        jdbcTemplate.update(DELETE_PARAMS_QUERY, id);
        jdbcTemplate.update(DELETE_OBJECT_QUERY, id);
    }

    public Map<String, Object> getById(int id) {
        String SELECT_QUERY = "SELECT at.attribute_name AS name, val FROM params JOIN attribute AS at ON at.attribute_id = params.attribute_id WHERE object_id = ?";
        List<Map<String, Object>> queryList = jdbcTemplate.queryForList(SELECT_QUERY, id);

        if(queryList.size() == 0)
            return null;

        Map<String, Object> map = new HashMap<String, Object>();
        for(Map<String, Object> tempMap: queryList) {
            map.put(tempMap.get("name").toString(), tempMap.get("val"));
        }
        map.put("ID", id);
        return map;
    }

    private List<Map<String, Object>> getListEx(List<Integer> ids)
    {
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        for(Integer id: ids) {
            list.add(getById(id));
        }
        return list;
    }

    public List<Map<String, Object>> getList(String type) {
        List<Integer> ids = jdbcTemplate.queryForList("SELECT object_id FROM object WHERE object_type_id = (SELECT object_type_id FROM object_type WHERE type_name = ?)", Integer.class, type);
        return getListEx(ids);
    }

    public List<Map<String, Object>> getList(String type, Integer first, Integer num) {
        List<Integer> ids = jdbcTemplate.queryForList("SELECT object_id FROM object WHERE object_type_id = (SELECT object_type_id FROM object_type WHERE type_name = ?) LIMIT ?, ?", Integer.class, type, first, num);
        return getListEx(ids);
    }

    public List<Map<String, Object>> getList(String type, Map<String, String> filterMap) {
        String SELECT_QUERY = SELECT_QUERY = filterQuery("SELECT obj.object_id FROM object AS obj ", filterMap) +
                "WHERE obj.object_type_id = (SELECT object_type_id FROM object_type WHERE type_name = ?)";
        List<Integer> ids = jdbcTemplate.queryForList(SELECT_QUERY, Integer.class, type);
        return getListEx(ids);
    }

    public List<Map<String, Object>> getList(String type, Integer first, Integer num, Map<String, String> filterMap) {
        String SELECT_QUERY = SELECT_QUERY = filterQuery("SELECT obj.object_id FROM object AS obj ", filterMap) +
                "WHERE obj.object_type_id = (SELECT object_type_id FROM object_type WHERE type_name = ?) LIMIT ?, ?";
        List<Integer> ids = jdbcTemplate.queryForList(SELECT_QUERY, Integer.class, type, first, num);
        return getListEx(ids);
    }

    public Integer getIdByParam(String type, String param, String value) {
        try {
            String SELECT_QUERY = "SELECT obj.object_id FROM object AS obj JOIN params ON obj.object_id = params.object_id WHERE attribute_id = (SELECT attribute_id FROM attribute WHERE attribute_name = ?) AND val = ? AND obj.object_type_id = (SELECT object_type_id FROM object_type WHERE type_name = ?) LIMIT 1";
            return jdbcTemplate.queryForObject(SELECT_QUERY, Integer.class, param, value, type);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Integer> getIDsByParam(String type, String param, String value) {
        String SELECT_QUERY = "SELECT obj.object_id FROM object AS obj JOIN params ON obj.object_id = params.object_id WHERE attribute_id = (SELECT attribute_id FROM attribute WHERE attribute_name = ?) AND val LIKE ? AND obj.object_type_id = (SELECT object_type_id FROM object_type WHERE type_name = ?)";
        return jdbcTemplate.queryForList(SELECT_QUERY, Integer.class, param, value, type);
    }

    public List<Integer> getIDsByParam(String type, String param, String value, Integer first, Integer num) {
        String SELECT_QUERY = "SELECT obj.object_id FROM object AS obj " +
                "JOIN params ON obj.object_id = params.object_id " +
                "WHERE attribute_id = (SELECT attribute_id FROM attribute WHERE attribute_name = ?) AND val LIKE ? AND obj.object_type_id = (SELECT object_type_id FROM object_type WHERE type_name = ?) LIMIT ?, ?";
        return jdbcTemplate.queryForList(SELECT_QUERY, Integer.class, param, value, type, first, num);
    }

    public List<Integer> getIDsByParam(String type, String param, String value, Integer first, Integer num, String order_field, Boolean reversed, Map<String, String> filterMap) {
        String SELECT_QUERY = "SELECT DISTINCT obj.object_id FROM object AS obj " +
                "JOIN params ON obj.object_id = params.object_id ";

        /* Поле сортировки */
        if(order_field != null && !order_field.equals("object_id"))
            SELECT_QUERY += "LEFT JOIN params AS extra ON extra.object_id = params.object_id AND extra.attribute_id = (SELECT attribute_id FROM attribute WHERE attribute_name = ?) ";

        // Фильтрация
        if(filterMap != null)
            SELECT_QUERY = filterQuery(SELECT_QUERY, filterMap);

        SELECT_QUERY += "WHERE params.attribute_id = (SELECT attribute_id FROM attribute WHERE attribute_name = ?) AND params.val LIKE ? AND obj.object_type_id = (SELECT object_type_id FROM object_type WHERE type_name = ?) ";

        /* Порядок сортировки */
        if(order_field != null) {
            if (!order_field.equals("object_id"))
                SELECT_QUERY += "ORDER BY extra.val";
            else
                SELECT_QUERY += "ORDER BY params.object_id";
            if (reversed)
                SELECT_QUERY += " DESC";
        }

        /* Пагинация */
        SELECT_QUERY += " LIMIT ?, ?";

        if(order_field != null && !order_field.equals("object_id"))
            return jdbcTemplate.queryForList(SELECT_QUERY, Integer.class, order_field, param, value, type, first, num);
        else
            return jdbcTemplate.queryForList(SELECT_QUERY, Integer.class, param, value, type, first, num);
    }

    public List<Integer> getIDsByParam(String type, String param, String value, Map<String, String> filterMap) {
        String SELECT_QUERY = "SELECT DISTINCT obj.object_id FROM object AS obj " +
                "JOIN params ON obj.object_id = params.object_id ";

        // Фильтрация
        if(filterMap != null)
            SELECT_QUERY = filterQuery(SELECT_QUERY, filterMap);

        SELECT_QUERY += "WHERE params.attribute_id = (SELECT attribute_id FROM attribute WHERE attribute_name = ?) AND params.val LIKE ? AND obj.object_type_id = (SELECT object_type_id FROM object_type WHERE type_name = ?)";
        return jdbcTemplate.queryForList(SELECT_QUERY, Integer.class, param, value, type);
    }

    private String filterQuery(String SELECT_QUERY, Map<String, String> filterMap)
    {
        int temp = 1;
        for(Map.Entry<String, String> filter: filterMap.entrySet()) {
            String filterField = "filter" + temp++;
            if(!filter.getKey().contains(" ")) {
                SELECT_QUERY += "JOIN params AS " + filterField + " ON " + filterField + ".object_id = obj.object_id AND " + filterField +
                        ".attribute_id = (SELECT attribute_id FROM attribute WHERE attribute_name = '" + filter.getKey() + "') AND " + filterField + ".val LIKE " + filter.getValue() + " ";
            }
            else {
                String fields = "'" + filter.getKey().replace(" ", "', '") + "'";
                SELECT_QUERY += "JOIN params AS " + filterField + " ON " + filterField + ".object_id = obj.object_id AND " + filterField +
                        ".attribute_id IN (SELECT attribute_id FROM attribute WHERE attribute_name IN (" + fields + ")) AND " + filterField + ".val LIKE '%" + filter.getValue() + "%' ";
            }
        }
        return SELECT_QUERY;
    }
}
