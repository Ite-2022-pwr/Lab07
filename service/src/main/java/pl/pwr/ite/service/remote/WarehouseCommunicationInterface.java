package pl.pwr.ite.service.remote;

import pl.pwr.ite.model.Order;
import pl.pwr.ite.model.Product;
import pl.pwr.ite.model.UserSocket;
import pl.pwr.ite.model.enums.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface WarehouseCommunicationInterface extends CommunicationInterface {
    String test(String str);
    UserSocket register(UserRole role, String host, Integer port);
    void unregister(UUID userId);
    Product[] getOffer();
    Order putOrder(UUID userId, ArrayList<UUID> productIds);
    void updateOrder(Order order);
    void returnOrder(Order order);
    Order getOrder();
    UserSocket getInfoByUserId(UUID userId);
    UserSocket getInfoByUserRole(UserRole role);
    Order[] getOrders(UUID userId);
}
