package pl.pwr.ite.customer.service;

import lombok.Setter;
import pl.pwr.ite.model.Order;
import pl.pwr.ite.service.InterfaceServerBase;
import pl.pwr.ite.service.ServerBase;
import pl.pwr.ite.service.remote.CustomerCommunicationInterface;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CustomerServer extends InterfaceServerBase<CustomerServer.CommunicationImpl> {

    public CustomerServer() {
        super(CommunicationImpl.class);
    }

    public static class CommunicationImpl implements CustomerCommunicationInterface {

        @Setter static private Consumer<Order> orderReceivedCallback;

        @Override
        public void putOrder(Order order) {
            orderReceivedCallback.accept(order);
        }
    }

}
