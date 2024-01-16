package pl.pwr.ite.service;

import pl.pwr.ite.model.Order;
import pl.pwr.ite.shop.api.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrderRepository {
    private static OrderRepository INSTANCE = null;
    private final List<Order> orders = new ArrayList<>();

    public List<Order> getAll() {
        return orders;
    }

    public Order getFirst() {
        return orders.stream().findFirst().orElse(null);
    }

    public void add(Order order) {
        orders.add(order);
    }

    public void remove(Order order) {
        orders.remove(order);
    }


    public static OrderRepository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new OrderRepository();
        }
        return INSTANCE;
    }
}
