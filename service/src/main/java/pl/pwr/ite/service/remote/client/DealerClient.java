package pl.pwr.ite.service.remote.client;

import pl.pwr.ite.model.Order;
import pl.pwr.ite.service.InterfaceClientBase;
import pl.pwr.ite.service.remote.DealerCommunicationInterface;

public class DealerClient extends InterfaceClientBase<DealerCommunicationInterface> implements DealerCommunicationInterface {

    public DealerClient(String host, int port) {
        super(host, port, DealerCommunicationInterface.class);
    }

    @Override
    public void acceptOrder(Order order) {
        send("acceptOrder", order);
    }
}
