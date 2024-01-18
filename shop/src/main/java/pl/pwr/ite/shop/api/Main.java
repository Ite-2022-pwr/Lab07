package pl.pwr.ite.shop.api;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
     record Order(ICallback ic, List<Item> itemList){
    }
    static class Keeper extends UnicastRemoteObject implements IKeeper{

        List<Item> offersList = List.of(new Item("Adidas", 4), new Item("Puma",6));
        Queue<Order> ordersList = new LinkedList<>();
        IDeliverer id;
        ISeller is;
        ICustomer ic;

        protected Keeper() throws RemoteException {
        }

        @Override
        public int register(ICallback iCallback) {
            if (iCallback instanceof IDeliverer) {
                id = (IDeliverer) iCallback;
                return 1;
            }
            if(iCallback instanceof ICustomer) {
                ic = (ICustomer) iCallback;
                return 2;
            }
            if(iCallback instanceof ISeller) {
                is = (ISeller) iCallback;
                return 3;
            }
            return -1;
        }

        @Override
        public boolean unregister(int i) {
            return false;
        }

        @Override
        public void getOffer(int i) throws RemoteException {
           if(ic!=null)
               ic.response(null,offersList);
        }

        @Override
        public void putOrder(int i, List<Item> itemList) throws RemoteException {
            System.out.println("Keeper received putOrder query");
            ordersList.add(new Order(ic, itemList));

            for (Item item : itemList)
                for (Item o : offersList)
                    if (item.getDescription().equals(o.getDescription()))
                        o.setQuantity(Integer.max(0,o.getQuantity() - item.getQuantity()));
        }

        @Override
        public List<ISeller> getSellers() throws RemoteException {
            return null;
        }

        @Override
        public void getOrder(int i) throws RemoteException {
            System.out.println("Keeper received gerOrder query");
            Order o = ordersList.poll();
            id.response(o.ic(),o.itemList());
        }

        @Override
        public void returnOrder(List<Item> list) throws RemoteException {

        }

    }

    static class Customer extends UnicastRemoteObject implements ICustomer {
        protected Customer() throws RemoteException {
        }

        IDeliverer id;

        @Override
        public void putOrder(ICallback iCallback, List<Item> itemList) throws RemoteException {
            id = (IDeliverer) iCallback;
            System.out.println("Customer received order"+itemList);
        }

        @Override
        public void returnReceipt(String s) throws RemoteException {

        }

        @Override
        public void response(ICallback iCallback, List<Item> itemList) throws RemoteException {
            System.out.println("Customer received offer"+Arrays.toString(itemList.toArray()));
        }
    }


    static class Deliverer extends UnicastRemoteObject implements IDeliverer{

        protected Deliverer() throws RemoteException {
        }

        @Override
        public void returnOrder(List<Item> list) throws RemoteException {

        }

        @Override
        public void response(ICallback iCallback, List<Item> itemList) throws RemoteException {
            System.out.println("Deliverer received respose with order:" + Arrays.toString(itemList.toArray()));
            ((ICustomer) iCallback).putOrder(this,itemList);
    }

        public static void main(String[] args) throws RemoteException, NotBoundException {

            Registry r = LocateRegistry.createRegistry(2000);

            Keeper k = new Keeper();
            r.rebind("Keeper", k);

            Registry r2 = LocateRegistry.getRegistry(2000);
            IKeeper ik = (IKeeper) r2.lookup("Keeper");

            Customer c = new Customer();
            int idc = ik.register(c);
            ik.getOffer(idc);
            ik.putOrder(idc, List.of(new Item("Adidas", 1),new Item("Adidas", 2)));

            Deliverer d = new Deliverer();
            int idd = ik.register(d);
            ik.getOrder(idd);

            ik.getOffer(idc);

            UnicastRemoteObject.unexportObject(k, true);
            UnicastRemoteObject.unexportObject(c, true);
            UnicastRemoteObject.unexportObject(d, true);

        }
     }
}
