package pl.pwr.ite.service.rmi;

import lombok.Setter;
import pl.pwr.ite.shop.api.ICallback;
import pl.pwr.ite.shop.api.ICustomer;
import pl.pwr.ite.shop.api.Item;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class RmiCustomer extends UnicastRemoteObject implements ICustomer, Serializable {

    @Setter
    private BiConsumer<ICallback, List<Item>> putOrderCallback;

    @Setter
    private Consumer<String> returnReceiptCallback;

    @Setter
    private BiConsumer<ICallback, List<Item>> callbackConsumer;

    public RmiCustomer() throws RemoteException {
    }

    @Override
    public void response(ICallback ic, List<Item> itemList) {
        if(callbackConsumer == null) {
            return;
        }
        callbackConsumer.accept(ic, itemList);
    }

    @Override
    public void putOrder(ICallback idd, List<Item> itemList) {
        if(putOrderCallback == null) {
            return;
        }
        putOrderCallback.accept(idd, itemList);
    }

    @Override
    public void returnReceipt(String receipt) {
        if(returnReceiptCallback == null) {
            return;
        }
        returnReceiptCallback.accept(receipt);
    }
}
