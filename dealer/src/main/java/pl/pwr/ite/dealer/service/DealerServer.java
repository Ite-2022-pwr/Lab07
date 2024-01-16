//package pl.pwr.ite.dealer.service;
//
//import pl.pwr.ite.model.Order;
//import pl.pwr.ite.model.enums.OrderStatus;
//import pl.pwr.ite.model.enums.ProductStatus;
//import pl.pwr.ite.service.InterfaceServerBase;
//import pl.pwr.ite.service.remote.DealerCommunicationInterface;
//import pl.pwr.ite.service.remote.client.WarehouseClient;
//
//public class DealerServer extends InterfaceServerBase<DealerServer.CommunicationImpl> {
//
//    public DealerServer() {
//        super(CommunicationImpl.class);
//    }
//
//    public static class CommunicationImpl implements DealerCommunicationInterface {
//
//        private static WarehouseClient warehouseClient;
//
//        @Override
//        public void acceptOrder(Order order) {
//            var buyOrder = new Order();
//            var returnOrder = new Order();
//            order.setStatus(OrderStatus.Paid);
//            buyOrder.setStatus(OrderStatus.Paid);
//            returnOrder.setStatus(OrderStatus.Delivered);
//            for(var product : order.getProducts()) {
//                if(ProductStatus.Buy.equals(product.getStatus()) || ProductStatus.Other.equals(product.getStatus())) {
//                    buyOrder.getProducts().add(product);
//                } else {
//                    returnOrder.getProducts().add(product);
//                }
//            }
//            warehouseClient.returnOrder(returnOrder);
//            warehouseClient.updateOrder(buyOrder);
//            warehouseClient.updateOrder(order);
//        }
//
//        public static void setWarehouseClient(WarehouseClient warehouseClient) {
//            CommunicationImpl.warehouseClient = warehouseClient;
//        }
//    }
//}
