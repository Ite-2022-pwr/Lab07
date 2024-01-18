package pl.pwr.ite.customer.view.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;
import pl.pwr.ite.customer.service.exception.JavaFXException;
import pl.pwr.ite.model.Order;
import pl.pwr.ite.model.Product;
import pl.pwr.ite.model.enums.ProductStatus;
import pl.pwr.ite.service.rmi.RmiCustomer;
import pl.pwr.ite.shop.api.*;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerController implements Initializable {
    @FXML private Button payButton;
    @FXML private Label selectedProductsLabel;
    @FXML private TableView<Item> productTable;
    @FXML private TextField portTextField;
    @FXML private TextField hostTextField;
    @FXML private Button warehouseConnectButton;
    @FXML private TabPane mainTabPane;
    @FXML private TableView<OrderItem> orderProductsTable;
    @FXML private Button returnButton;

    private IKeeper keeperServer;
    private final ICustomer customerClient = new RmiCustomer();
    private Integer customerId;
    private final List<OrderItem> localItems = new ArrayList<>();


    public CustomerController() throws RemoteException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTables();
    }

    @FXML private void warehouseConnectButtonClick(ActionEvent event) throws Exception {
        Registry registry = LocateRegistry.getRegistry();
        this.keeperServer = (IKeeper) registry.lookup("KeeperServer");
        this.customerId = keeperServer.register(customerClient);
        ((RmiCustomer) customerClient).setPutOrderCallback(this::putOrderCallback);
        ((RmiCustomer) customerClient).setReturnReceiptCallback(this::returnReceiptCallback);
        ((RmiCustomer) customerClient).setCallbackConsumer(this::callbackConsumer);
        portTextField.setDisable(true);
        hostTextField.setDisable(true);
        warehouseConnectButton.setDisable(true);
        mainTabPane.setVisible(true);
        offersTabClicked(null);
    }

    public void callbackConsumer(ICallback callback, List<Item> items) {
        productTable.getItems().clear();
        productTable.getItems().addAll(items);
    }

    public void returnReceiptCallback(String receipt) {
        System.out.println(receipt);
        throw new JavaFXException(receipt);
    }

    public void putOrderCallback(ICallback callback, List<Item> items) {
        localItems.addAll(items.stream().map(i -> new OrderItem(i.getDescription(), i.getQuantity(), "Delivered")).collect(Collectors.toList()));
        offersTabClicked(null);
    }

    @FXML private void returnButtonClick(ActionEvent event) throws RemoteException {
        if(localItems.isEmpty()) {
            return;
        }
        var itemsToReturn = localItems.stream().map(li -> new Item(li.getDescription(), li.getQuantity())).collect(Collectors.toList());
        keeperServer.returnOrder(itemsToReturn);
        localItems.clear();
        offersTabClicked(null);
    }

    @FXML private void orderTableCellClicked(Event event) {
    }

    @FXML private void offersTabClicked(Event event) {
        if(orderProductsTable == null) {
            return;
        }
        orderProductsTable.getItems().clear();
        orderProductsTable.getItems().addAll(localItems);
        if(keeperServer == null) {
            return;
        }
        try {
            keeperServer.getOffer(customerId);
        } catch (RemoteException ex) {
            throw new JavaFXException("Error fetching data.", ex);
        }
    }

    @FXML private void placeOrderButtonClick(ActionEvent event) throws RemoteException {
        var items = List.copyOf(productTable.getSelectionModel().getSelectedItems());
        keeperServer.putOrder(customerId, items);
        offersTabClicked(null);
        tableCellSelected(null);
    }

    @FXML private void tableCellSelected(Event event) {
        selectedProductsLabel.setText("Selected products: " + productTable.getSelectionModel().getSelectedItems().size());
    }


    @FXML private void payButtonClick(ActionEvent event) throws RemoteException, NotBoundException {
        var itemsToBuy = new ArrayList<Item>();
        var itemsToReturn = new ArrayList<Item>();
        var iterator = localItems.iterator();
        while (iterator.hasNext()) {
            var orderItem = iterator.next();
            if(orderItem.getStatus().equals("Delivered")) {
                continue;
            }
            var item = new Item(orderItem.getDescription(), orderItem.getQuantity());
            if (orderItem.getStatus().equals("To buy")) {
                itemsToBuy.add(item);
            } else if (orderItem.getStatus().equals("To return")) {
                itemsToReturn.add(item);
            }
            iterator.remove();
        }
        var sellers = keeperServer.getSellers();
        if(sellers.isEmpty()) {
            throw new JavaFXException("No sellers available at the moment :(");
        }
        sellers.get(0).acceptOrder(customerClient, itemsToBuy, itemsToReturn);
        offersTabClicked(null);
    }

    @FXML private void buyProductButtonClick(ActionEvent event) {
        var orderItem = orderProductsTable.getSelectionModel().getSelectedItem();
        if(orderItem == null) {
            return;
        }
        orderItem.setStatus("To buy");
        offersTabClicked(null);
    }

    @FXML private void returnProductButtonClick(ActionEvent event) {
        var orderItem = orderProductsTable.getSelectionModel().getSelectedItem();
        if(orderItem == null) {
            return;
        }
        orderItem.setStatus("To return");
        offersTabClicked(null);
    }

    private void setupTables() {
        mainTabPane.setVisible(false);

        //PRODUCTS TABLE
        var itemDescriptionColumn = new TableColumn<Item, String>("Description");
        itemDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        var itemQuantityColumn = new TableColumn<Item, String>("Quantity");
        itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productTable.getColumns().clear();
        productTable.getColumns().addAll(itemQuantityColumn, itemDescriptionColumn);

//        //ORDER PRODUCTS TABLE
        var orderProductsDescriptionColumn = new TableColumn<OrderItem, String>("Description");
        orderProductsDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        var orderProductsQuantityColumn = new TableColumn<OrderItem, String>("Quantity");
        orderProductsQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        var orderProductsStatusColumn = new TableColumn<OrderItem, String>("Status");
        orderProductsStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderProductsTable.getColumns().clear();
        orderProductsTable.getColumns().addAll(orderProductsQuantityColumn, orderProductsDescriptionColumn, orderProductsStatusColumn);
//
        productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @Data
    public static class OrderItem {
        private String description;
        private Integer quantity;
        private String status;

        public OrderItem(String description, Integer quantity, String status) {
            this.description = description;
            this.quantity = quantity;
            this.status = status;
        }
    }
}