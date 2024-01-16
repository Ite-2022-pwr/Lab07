package pl.pwr.ite.shop.api;

import java.rmi.RemoteException;
import java.util.List;

public interface ICustomer extends ICallback {

    // method called by deliverer
    public void putOrder(ICallback idd, List<Item> itemList) throws RemoteException;

    // method called by seller
    public void returnReceipt(String receipt) throws RemoteException;

}
