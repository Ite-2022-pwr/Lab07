package pl.pwr.ite.service.rmi;

import lombok.Setter;
import pl.pwr.ite.shop.api.ICallback;
import pl.pwr.ite.shop.api.IDeliverer;
import pl.pwr.ite.shop.api.Item;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class RmiDeliverer extends UnicastRemoteObject implements IDeliverer, Serializable {

    @Setter
    private Consumer<List<Item>> returnOrderCallback;

    @Setter
    private BiConsumer<ICallback, List<Item>> callbackConsumer;

    public RmiDeliverer() throws RemoteException {
    }

    @Override
    public void response(ICallback ic, List<Item> itemList) {
        if(callbackConsumer == null) {
            return;
        }
        callbackConsumer.accept(ic, itemList);
    }

    @Override
    public void returnOrder(List<Item> itemList) {
        if(returnOrderCallback == null) {
            return;
        }
        returnOrderCallback.accept(itemList);
    }
}
