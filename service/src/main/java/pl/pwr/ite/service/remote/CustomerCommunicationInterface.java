package pl.pwr.ite.service.remote;

import pl.pwr.ite.model.Order;

public interface CustomerCommunicationInterface extends CommunicationInterface {
    void putOrder(Order order);
}
