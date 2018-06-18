package todo.service;

import todo.domain.Order;

import java.util.List;

public interface OrderService {
    void addOrder(Order order);
    void updateStatus(Integer order_id, Integer newStatus);
    Order getById(Integer order_id);
    List<Order> getOrderList();
    List<Order> getOrderList(Integer page);
    List<Order> getOrderList(Integer page, String order_field, Boolean reversed);
    List<Order> getUserOrderList(Integer userId);
}
