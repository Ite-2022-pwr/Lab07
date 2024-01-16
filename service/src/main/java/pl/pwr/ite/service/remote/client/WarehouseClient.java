package pl.pwr.ite.service.remote.client;

import pl.pwr.ite.model.Order;
import pl.pwr.ite.model.Product;
import pl.pwr.ite.model.UserSocket;
import pl.pwr.ite.model.enums.UserRole;
import pl.pwr.ite.service.InterfaceClientBase;
import pl.pwr.ite.service.remote.WarehouseCommunicationInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WarehouseClient extends InterfaceClientBase<WarehouseCommunicationInterface> implements WarehouseCommunicationInterface {

    public WarehouseClient() {
        super("localhost", 5555, WarehouseCommunicationInterface.class);
    }

    @Override
    public String test(String str) {
        return (String) send("test", str);
    }

    @Override
    public UserSocket register(UserRole role, String host, Integer port) {
        return (UserSocket) send("register", role, host, port);
    }

    @Override
    public void unregister(UUID userId) {
        send("unregister", userId);
    }

    @Override
    public Product[] getOffer() {
        return (Product[]) send("getOffer");
    }

    @Override
    public Order putOrder(UUID userId, ArrayList<UUID> productIds) {
        return (Order) send("putOrder", userId, productIds);
    }

    @Override
    public void returnOrder(Order order) {
        send("returnOrder", order);
    }

    @Override
    public Order getOrder() {
        return (Order) send("getOrder");
    }

    @Override
    public UserSocket getInfoByUserId(UUID userId) {
        return (UserSocket) send("getInfoByUserId", userId);
    }

    @Override
    public UserSocket getInfoByUserRole(UserRole role) {
        return (UserSocket) send("getInfoByUserRole", role);
    }

    @Override
    public Order[] getOrders(UUID userId) {
        return (Order[]) send("getOrders", userId);
    }

    @Override
    public void updateOrder(Order order) {
        send("updateOrder", order);
    }
}