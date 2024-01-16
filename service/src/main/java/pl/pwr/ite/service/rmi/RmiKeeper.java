package pl.pwr.ite.service.rmi;

import pl.pwr.ite.model.Order;
import pl.pwr.ite.service.ItemRepository;
import pl.pwr.ite.service.OrderRepository;
import pl.pwr.ite.shop.api.ICallback;
import pl.pwr.ite.shop.api.ICustomer;
import pl.pwr.ite.shop.api.IKeeper;
import pl.pwr.ite.shop.api.Item;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RmiKeeper implements IKeeper {

    private final Map<Integer, ICallback> registeredClients = new HashMap<>();
    private final ItemRepository itemRepository = ItemRepository.getInstance();
    private final OrderRepository orderRepository = OrderRepository.getInstance();

    public RmiKeeper() throws RemoteException {
    }

    @Override
    public int register(ICallback iCallback) {
        int id = registeredClients.size() + 1;
        registeredClients.put(id, iCallback);
        return id;
    }

    @Override
    public boolean unregister(int id) {
        var callback = registeredClients.get(id);
        if(callback == null) {
            return false;
        }
        registeredClients.remove(id);
        return true;
    }

    @Override
    public void getOffer(int userId) throws RemoteException {
        var callback = (ICustomer) registeredClients.get(userId);
        callback.response(null, itemRepository.getAll());
    }

    @Override
    public void putOrder(int customerId, List<Item> items) {
        var callback = registeredClients.get(customerId);
        var order = new Order();
        order.setUser(callback);
        for(var orderItem : items) {
            var item = itemRepository.findByDescription(orderItem.getDescription());
            if(item == null) {
                continue;
            }
            var quantity = Math.min(orderItem.getQuantity(), item.getQuantity());
            item.setQuantity(item.getQuantity() - quantity);
            orderItem.setQuantity(quantity);
            order.getItems().add(orderItem);
        }
        orderRepository.add(order);
    }

    @Override
    public void getOrder(int delivererId) throws RemoteException {
        var order = orderRepository.getFirst();
        if(order == null) {
            return;
        }
        orderRepository.remove(order);
        var delivered = registeredClients.get(delivererId);
        delivered.response(order.getUser(), order.getItems());
    }

    @Override
    public void returnOrder(List<Item> items) {
        for(var orderItem : items) {
            var item = itemRepository.find(orderItem.getDescription());
            if(item == null) {
                continue;
            }
            item.setQuantity(item.getQuantity() + orderItem.getQuantity());
        }
    }
}
