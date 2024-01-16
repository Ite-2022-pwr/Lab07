package pl.pwr.ite.model;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.ite.model.enums.OrderStatus;
import pl.pwr.ite.shop.api.ICallback;
import pl.pwr.ite.shop.api.Item;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Order {

    private ICallback user;

    private final List<Item> items = new ArrayList<>();
}