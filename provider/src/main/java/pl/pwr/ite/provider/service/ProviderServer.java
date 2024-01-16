package pl.pwr.ite.provider.service;

import pl.pwr.ite.model.Order;
import pl.pwr.ite.service.InterfaceServerBase;
import pl.pwr.ite.service.remote.ProviderCommunicationInterface;
import pl.pwr.ite.service.remote.client.WarehouseClient;

public class ProviderServer extends InterfaceServerBase<ProviderServer.CommunicationImpl> {

    public ProviderServer() {
        super(CommunicationImpl.class);
    }

    public static class CommunicationImpl implements ProviderCommunicationInterface {
        private static WarehouseClient warehouseClient;

        @Override
        public void returnOrder(Order order) {
            warehouseClient.returnOrder(order);
        }

        public static void setWarehouseClient(WarehouseClient warehouseClient) {
            CommunicationImpl.warehouseClient = warehouseClient;
        }
    }
}
