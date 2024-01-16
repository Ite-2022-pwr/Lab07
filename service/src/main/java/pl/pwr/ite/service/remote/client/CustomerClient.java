package pl.pwr.ite.service.remote.client;

import pl.pwr.ite.model.Order;
import pl.pwr.ite.service.InterfaceClientBase;
import pl.pwr.ite.service.remote.CustomerCommunicationInterface;

public class CustomerClient extends InterfaceClientBase<CustomerCommunicationInterface> implements CustomerCommunicationInterface {
    public CustomerClient(String host, int port) {
        super(host, port, CustomerCommunicationInterface.class);
    }

    @Override
    public void putOrder(Order order) {
        send("putOrder", order);
    }
}
