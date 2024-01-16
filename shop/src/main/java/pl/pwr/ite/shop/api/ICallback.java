package pl.pwr.ite.shop.api;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ICallback extends Remote {
    // method called by keeper
    // on customer (response to getOffer)
    // on deliverer (response to getOrder)
    void response(ICallback ic, List<Item> itemList) throws RemoteException;
}
