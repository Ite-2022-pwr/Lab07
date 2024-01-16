package pl.pwr.ite.service.rmi;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.ite.shop.api.ICallback;
import pl.pwr.ite.shop.api.Item;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class RmiCallback implements ICallback, Serializable {

}
