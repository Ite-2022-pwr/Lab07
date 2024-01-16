package pl.pwr.ite.service.remote.client;

import pl.pwr.ite.model.Order;
import pl.pwr.ite.service.InterfaceClientBase;
import pl.pwr.ite.service.remote.ProviderCommunicationInterface;

import java.security.Provider;

public class ProviderClient extends InterfaceClientBase<ProviderCommunicationInterface> implements ProviderCommunicationInterface {

    public ProviderClient(String host, int port) {
        super(host, port, ProviderCommunicationInterface.class);
    }

    @Override
    public void returnOrder(Order order) {
        send("returnOrder", order);
    }
}
