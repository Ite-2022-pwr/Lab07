package pl.pwr.ite.service.javafx;

import lombok.Getter;
import pl.pwr.ite.model.UserSocket;
import pl.pwr.ite.model.enums.UserRole;
import pl.pwr.ite.service.ApplicationContext;
import pl.pwr.ite.service.InterfaceServerBase;
import pl.pwr.ite.service.remote.CommunicationInterface;
import pl.pwr.ite.service.remote.client.WarehouseClient;

public abstract class CommunicationController<I extends CommunicationInterface, S extends InterfaceServerBase<I>> {

    @Getter protected final ApplicationContext applicationContext = new ApplicationContext();
    @Getter protected final WarehouseClient warehouseClient = new WarehouseClient();
    protected final S server;

    public CommunicationController(S server) {
        this.server = server;
    }

    protected void register(UserRole role, String host, Integer port) {
        var userSocket = warehouseClient.register(role, host, port);
        if(userSocket == null) {
            throw new IllegalArgumentException(String.format("Address %s:%s has been already registered.", host, port));
        }
        server.start(userSocket.getHost(), userSocket.getPort());
        applicationContext.setRegisteredSocket(userSocket);
        applicationContext.setRegisteredUser(userSocket.getUser());
    }

    protected void unregister() {
        warehouseClient.unregister(applicationContext.getRegisteredUser().getId());
        applicationContext.setRegisteredUser(null);
        applicationContext.setRegisteredSocket(null);
    }

    public void stop() {
        getWarehouseClient().unregister(getApplicationContext().getRegisteredUser().getId());
        getWarehouseClient().disconnect();
        server.stop();
    }
}
