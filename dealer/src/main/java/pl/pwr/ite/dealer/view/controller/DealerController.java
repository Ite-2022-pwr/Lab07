package pl.pwr.ite.dealer.view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.pwr.ite.service.rmi.RmiDeliverer;
import pl.pwr.ite.service.rmi.RmiSeller;
import pl.pwr.ite.shop.api.ICustomer;
import pl.pwr.ite.shop.api.IKeeper;
import pl.pwr.ite.shop.api.ISeller;
import pl.pwr.ite.shop.api.Item;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Random;

public class DealerController {
    @FXML private Button warehouseConnectButton;

    private IKeeper keeperServer;
    private final ISeller sellerClient = new RmiSeller();

    public DealerController() throws RemoteException {
    }

    public void acceptOrderCallback(ICustomer customer, List<Item> boughtItems, List<Item> returnedItems) {
        try {
            keeperServer.returnOrder(returnedItems);
            customer.returnReceipt("Bought items: " + boughtItems.size() + " Money to pay: " + new Random().nextInt(9, 30) * boughtItems.size());
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @FXML private void warehouseConnectButtonClick(ActionEvent event) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        this.keeperServer = (IKeeper) registry.lookup("KeeperServer");
        keeperServer.register(sellerClient);
        ((RmiSeller) sellerClient).setAcceptOrderCallback(this::acceptOrderCallback);
        warehouseConnectButton.setDisable(true);
    }
}