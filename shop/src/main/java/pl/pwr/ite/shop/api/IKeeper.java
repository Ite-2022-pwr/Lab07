package pl.pwr.ite.shop.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IKeeper extends Remote {

    // methods called by customer, deliverer, seller
    public int register(ICallback iCallback) throws RemoteException;
    public boolean unregister(int id) throws RemoteException;

    // methods called by customer
    public void getOffer(int idc) throws RemoteException; // answer send to customer using its response() method
    public void putOrder(int idc, List<Item> itemList) throws RemoteException; // orders must be collected in a queue
    public List<ISeller> getSellers() throws RemoteException; // to let customer randomly choose a seller from a list and call its acceptOrder method
    // method called by deliverer
    public void getOrder(int idd) throws RemoteException; // answer (taken from a queue of orders) send to deliverer using its response() method

    // methods called by deliverer, seller
    public void returnOrder(List<Item> itemList) throws RemoteException;

}
