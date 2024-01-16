package pl.pwr.ite.service.remote;

import pl.pwr.ite.model.Order;

public interface DealerCommunicationInterface extends CommunicationInterface {

    void acceptOrder(Order order);
}
