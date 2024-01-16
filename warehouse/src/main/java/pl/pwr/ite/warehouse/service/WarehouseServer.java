//package pl.pwr.ite.warehouse.service;
//
//import lombok.NoArgsConstructor;
//import pl.pwr.ite.model.Order;
//import pl.pwr.ite.model.Product;
//import pl.pwr.ite.model.User;
//import pl.pwr.ite.model.UserSocket;
//import pl.pwr.ite.model.enums.OrderStatus;
//import pl.pwr.ite.model.enums.UserRole;
//import pl.pwr.ite.service.InterfaceServerBase;
//import pl.pwr.ite.service.remote.WarehouseCommunicationInterface;
//
//import java.util.ArrayList;
//import java.util.UUID;
//
//public class WarehouseServer extends InterfaceServerBase<WarehouseServer.CommunicationImpl> {
//
//    public WarehouseServer() {
//        super(CommunicationImpl.class);
//    }
//
//    @NoArgsConstructor
//    public static class CommunicationImpl implements WarehouseCommunicationInterface {
//        private final UserSocketRepository userSocketRepository = UserSocketRepository.getInstance();
//        private final UserRepository userRepository = UserRepository.getInstance();
//        private final ProductRepository productRepository = ProductRepository.getInstance();
////        private final OrderRepository orderRepository = OrderRepository.getInstance();
//        @Override
//        public String test(String str) {
//            var response = str + " - response.";
//            return response;
//        }
//
//        @Override
//        public UserSocket register(UserRole role, String host, Integer port) {
//            if(userSocketRepository.findByHostAndPort(host, port) != null) {
//                return null;
//            }
//
//            var user = new User();
//            user.setId(UUID.randomUUID());
//            user.setRole(role);
//            user = userRepository.add(user);
//            var userSocket = new UserSocket();
//            userSocket.setHost(host);
//            userSocket.setPort(port);
//            userSocket.setUser(user);
//
//            return userSocketRepository.add(userSocket);
//        }
//
//        @Override
//        public void unregister(UUID userId) {
//            var user = userRepository.findById(userId);
//            if(user == null) {
//                return;
//            }
//            var userSocket = userSocketRepository.findByUserId(user.getId());
//            if(userSocket == null) {
//                return;
//            }
//            userSocketRepository.remove(userSocket);
//            userRepository.remove(user);
//        }
//
//        @Override
//        public Product[] getOffer() {
//            return productRepository.findAll().toArray(new Product[0]);
//        }
//
//        @Override
//        public Order putOrder(UUID userId, ArrayList<UUID> productIds) {
//            var productsToOder = productRepository.getAllByProductIds(productIds);
//            var user = userRepository.findById(userId);
//            var order = new Order();
//            var iterator = productsToOder.iterator();
//            while (iterator.hasNext()) {
//                var product = iterator.next();
//                product.setOrdered(true);
//                order.getProducts().add(product);
//                productRepository.remove(product);
//            }
//            var amount = order.getProducts().size();
//            order.setProductAmount(amount);
//            order.setUser(user);
//            order.setStatus(OrderStatus.Ordered);
//            return orderRepository.add(order);
//        }
//
//        @Override
//        public void returnOrder(Order order) {
////            var order = orderRepository.findById(ord.getId());
//            for(var product : order.getProducts()) {
//                productRepository.add(product);
//                product.setOrdered(false);
//            }
//            orderRepository.remove(order);
//        }
//
//        @Override
//        public Order getOrder() {
//            return orderRepository.findFirst();
//        }
//
//        @Override
//        public UserSocket getInfoByUserId(UUID userId) {
//            return userSocketRepository.findByUserId(userId);
//        }
//
//        @Override
//        public UserSocket getInfoByUserRole(UserRole role) {
//            return userSocketRepository.findFirstByRole(role);
//        }
//
//        @Override
//        public Order[] getOrders(UUID userId) {
//            return orderRepository.findAllByUserId(userId).toArray(new Order[0]);
//        }
//
//        @Override
//        public void updateOrder(Order order) {
//            var or = orderRepository.findById(order.getId());
//            or.setStatus(order.getStatus());
//        }
//    }
//}
