package pl.pwr.ite.service.rmi;

import lombok.Setter;
import pl.pwr.ite.service.TriConsumer;
import pl.pwr.ite.shop.api.ICallback;
import pl.pwr.ite.shop.api.ICustomer;
import pl.pwr.ite.shop.api.ISeller;
import pl.pwr.ite.shop.api.Item;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.function.BiConsumer;

public class RmiSeller extends UnicastRemoteObject implements ISeller {

    @Setter
    private TriConsumer<ICustomer, List<Item>, List<Item>> acceptOrderCallback;

    @Setter
    private BiConsumer<ICallback, List<Item>> callbackConsumer;

    public RmiSeller() throws RemoteException {
    }

    @Override
    public void response(ICallback ic, List<Item> itemList) {
        if(callbackConsumer == null) {
            return;
        }
        callbackConsumer.accept(ic, itemList);
    }

    @Override
    public void acceptOrder(ICustomer ic, List<Item> boughtItemList, List<Item> returnedItemList) {
        if(acceptOrderCallback == null) {
            return;
        }
        acceptOrderCallback.apply(ic, boughtItemList, returnedItemList);
    }
}
