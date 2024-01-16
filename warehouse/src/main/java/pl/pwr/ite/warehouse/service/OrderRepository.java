//package pl.pwr.ite.warehouse.service;
//
//import pl.pwr.ite.model.Order;
//import pl.pwr.ite.model.Product;
//import pl.pwr.ite.model.enums.OrderStatus;
//import pl.pwr.ite.service.RepositoryBase;
//
//import java.util.List;
//import java.util.UUID;
//
//public class OrderRepository extends RepositoryBase<Order> {
//    private static OrderRepository INSTANCE = null;
//
//    public Order findFirst() {
//        return entities.stream().filter(e -> OrderStatus.Ordered.equals(e.getStatus())).findFirst().orElse(null);
//    }
//
//    public List<Order> findAllByUserId(UUID userId) {
//        return entities.stream().filter(e -> e.getUser().getId().equals(userId)).toList();
//    }
//
//    public static OrderRepository getInstance() {
//        if(INSTANCE == null) {
//            INSTANCE = new OrderRepository();
//        }
//        return INSTANCE;
//    }
//}
