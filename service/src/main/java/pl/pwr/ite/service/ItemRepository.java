package pl.pwr.ite.service;

import pl.pwr.ite.model.Order;
import pl.pwr.ite.shop.api.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ItemRepository {
    private static ItemRepository INSTANCE = null;
    private final List<Item> items = new ArrayList<>();

    public ItemRepository() {
        items.addAll(
                IntStream
                        .range(1, 6)
                        .mapToObj(i ->
                                new Item(
                                        "Item " + i,
                                        new Random().nextInt(1, 4)))
                        .collect(Collectors.toList())
        );
    }

    public List<Item> getAll() {
        return items.stream().filter(i -> i.getQuantity() > 0).collect(Collectors.toList());
    }

    public void remove(String description) {
        var item = items.stream().filter(i -> i.getDescription().equals(description)).findFirst().orElse(null);
        items.remove(item);
    }

    public Item findByDescription(String description) {
        return items.stream().filter(i -> i.getDescription().equals(description) && i.getQuantity() > 0).findFirst().orElse(null);
    }

    public Item find(String description) {
        return items.stream().filter(i -> i.getDescription().equals(description)).findFirst().orElse(null);
    }

    public static ItemRepository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ItemRepository();
        }
        return INSTANCE;
    }
}
