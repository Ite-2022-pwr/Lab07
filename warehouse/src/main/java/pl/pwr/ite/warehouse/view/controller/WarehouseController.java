package pl.pwr.ite.warehouse.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.SneakyThrows;
import pl.pwr.ite.service.rmi.RmiKeeper;
import pl.pwr.ite.shop.api.IKeeper;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class WarehouseController {

    @FXML private Button startButton;
    private IKeeper server;

    @FXML @SneakyThrows
    protected void onHelloButtonClick() {
        IKeeper server = new RmiKeeper();
        this.server = (IKeeper) UnicastRemoteObject.exportObject(server, 0);
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("KeeperServer", this.server);
        startButton.setDisable(true);
    }

    @SneakyThrows
    public void stop() {
        UnicastRemoteObject.unexportObject(server, true);
    }
}