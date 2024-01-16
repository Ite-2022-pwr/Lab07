package pl.pwr.ite.provider.view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.pwr.ite.model.Order;
import pl.pwr.ite.model.enums.OrderStatus;
import pl.pwr.ite.service.remote.client.CustomerClient;
import pl.pwr.ite.service.rmi.RmiDeliverer;
import pl.pwr.ite.shop.api.*;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

public class ProviderController implements Initializable {
    @FXML private Button warehouseConnectButton;
    @FXML private TableView<Item> itemTable;
    @FXML private Button fillOrderButtonClick;

    private Order currentOrder;
    private IKeeper keeperServer;
    private final IDeliverer delivererClient = new RmiDeliverer();
    private Integer delivererId;

    public ProviderController() throws RemoteException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTable();
    }

    @FXML private void warehouseConnectButtonClick(ActionEvent event) throws NotBoundException, RemoteException {
        Registry registry = LocateRegistry.getRegistry();
        this.keeperServer = (IKeeper) registry.lookup("KeeperServer");
        this.delivererId = keeperServer.register(delivererClient);
        ((RmiDeliverer) delivererClient).setReturnOrderCallback(this::returnOrderCallback);
        ((RmiDeliverer) delivererClient).setCallbackConsumer(this::getOrderCallback);
        warehouseConnectButton.setDisable(true);
    }

    @FXML private void fetchOrderButtonClick(ActionEvent event) throws RemoteException {
        keeperServer.getOrder(delivererId);
    }

    public void getOrderCallback(ICallback callback, List<Item> items) {
        var order = new Order();
        order.setUser(callback);
        order.getItems().addAll(items);
        currentOrder = order;
        itemTable.getItems().clear();
        itemTable.getItems().addAll(items);
        fillOrderButtonClick.setDisable(false);
    }

    public void returnOrderCallback(List<Item> items) {
        try {
            keeperServer.returnOrder(items);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @FXML private void fillOrderButtonClick(ActionEvent event) throws RemoteException {
        var callback = currentOrder.getUser();
        itemTable.getItems().clear();
        fillOrderButtonClick.setDisable(true);
        ((ICustomer) callback).putOrder(delivererClient, currentOrder.getItems());
        this.currentOrder = null;
    }

    private void setupTable() {
        var descriptionColumn = new TableColumn<Item, String>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        var quantityColumn = new TableColumn<Item, String>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        itemTable.getColumns().clear();
        itemTable.getColumns().addAll(descriptionColumn, quantityColumn);
    }
}