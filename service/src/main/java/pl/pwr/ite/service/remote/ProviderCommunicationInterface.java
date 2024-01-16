package pl.pwr.ite.service.remote;

import pl.pwr.ite.model.Order;

public interface ProviderCommunicationInterface extends CommunicationInterface {

    void returnOrder(Order order);
}
