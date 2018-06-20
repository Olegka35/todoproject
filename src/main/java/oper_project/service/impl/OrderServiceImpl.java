package oper_project.service.impl;

import oper_project.configuration.PageConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import oper_project.configuration.SpringJDBCConfiguration;
import oper_project.dao.DAO;
import oper_project.domain.Order;
import oper_project.domain.OrderItem;
import oper_project.service.OrderService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJDBCConfiguration.class);
    private DAO dao = context.getBean(DAO.class);

    @Override
    public void addOrder(Order order) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Order_UserID", String.valueOf(order.getUser().getID()));
        params.put("Order_Name", order.getName());
        params.put("Order_EMail", order.getEmail());
        params.put("Order_Address", order.getAddress());
        params.put("Order_TelNumber", order.getNumber());
        params.put("Order_Date", new SimpleDateFormat("yyyy.MM.dd").format(order.getDate()));
        params.put("Order_Price", order.getPrice().toString());
        params.put("Order_Status", order.getStatus().toString());
        params.put("Order_PayType", order.getPayType());
        Integer orderID = dao.add("order", params, "Order_Name");

        for(OrderItem item: order.getOrderItems()) {
            params.clear();
            params.put("OI_OrderID", orderID.toString());
            params.put("OI_Type", item.getArticleTypeName());
            params.put("OI_Price", item.getPrice().toString());
            params.put("OI_Num", item.getNum().toString());
            dao.add("order_item", params, "OI_Type");
        }
    }

    @Override
    public void updateStatus(Integer order_id, Integer newStatus) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Order_Status", newStatus.toString());
        dao.update(order_id, params);
    }

    @Override
    public Order getById(Integer order_id) {
        Map<String, Object> map = dao.getById(order_id);
        if(map == null) return null;
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        List<Integer> ids = dao.getIDsByParam("order_item", "OI_OrderID", order_id.toString());
        for(Integer id: ids) {
            Map<String, Object> itemMap = dao.getById(id);
            orderItems.add(new OrderItem(itemMap.get("OI_Type").toString(), Integer.parseInt(itemMap.get("OI_Price").toString()), Integer.parseInt(itemMap.get("OI_Num").toString())));
        }
        return new Order(Integer.parseInt(map.get("ID").toString()), new OperatorServiceImpl().getById(Integer.parseInt(map.get("Order_UserID").toString())), map.get("Order_Name").toString(),
                map.get("Order_EMail").toString(), map.get("Order_Address").toString(), map.get("Order_TelNumber").toString(), getDateFromMap(map, "Order_Date"),
                Integer.parseInt(map.get("Order_Price").toString()), Integer.parseInt(map.get("Order_Status").toString()), map.get("Order_PayType").toString(), orderItems);
    }

    @Override
    public List<Order> getOrderList() {
        List<Map<String, Object>> orders = dao.getList("order");
        List<Order> resultList = new ArrayList<Order>();
        for(Map<String, Object> map: orders) {
            resultList.add(getById(Integer.parseInt(map.get("ID").toString())));
        }
        return resultList;
    }

    @Override
    public List<Order> getOrderList(Integer page) {
        List<Map<String, Object>> orders = dao.getList("order", (page-1)* PageConfiguration.ITEMS_ON_PAGE, PageConfiguration.ITEMS_ON_PAGE);
        List<Order> resultList = new ArrayList<Order>();
        for(Map<String, Object> map: orders) {
            resultList.add(getById(Integer.parseInt(map.get("ID").toString())));
        }
        return resultList;
    }

    @Override
    public List<Order> getOrderList(Integer page, String order_field, Boolean reversed) {
        List<Map<String, Object>> orders = dao.getList("order", (page-1)* PageConfiguration.ITEMS_ON_PAGE, PageConfiguration.ITEMS_ON_PAGE, order_field, reversed, null);
        List<Order> resultList = new ArrayList<Order>();
        for(Map<String, Object> map: orders) {
            resultList.add(getById(Integer.parseInt(map.get("ID").toString())));
        }
        return resultList;
    }

    @Override
    public List<Order> getUserOrderList(Integer userId) {
        List<Integer> ordersIds = dao.getIDsByParam("order", "Order_UserID", userId.toString());
        List<Order> resultList = new ArrayList<Order>();
        for(Integer id: ordersIds) {
            resultList.add(getById(id));
        }
        return resultList;
    }

    private Date getDateFromMap(Map<String, Object> map, String param)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date dueDate = null;
        try {
            dueDate = format.parse(map.get(param).toString());
        } catch (ParseException | NullPointerException e) {
            dueDate = new Date(0);
        }
        return dueDate;
    }
}
