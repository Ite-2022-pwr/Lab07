package pl.pwr.ite.service;

import pl.pwr.ite.model.Order;
import pl.pwr.ite.model.remote.Payload;
import pl.pwr.ite.service.remote.CommunicationInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;

public abstract class InterfaceServerBase<I extends CommunicationInterface> extends ServerBase {

    private final Object _instance;

    protected InterfaceServerBase(Class<I> communicationInterfaceType) {
        try {
            _instance = Class.forName(communicationInterfaceType.getName()).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Object process(String data) {
        var payload = dataParser.deserialize(data, Payload.class);
        var methodName = payload.getMethod().getMethodName();
        var methodArguments = payload.getMethod().getArguments();

        var argumentTypes = new ArrayList<Class<?>>();
        var arguments = new ArrayList<>();
        for(var methodArgument : methodArguments) {
            var type = methodArgument.getType();
            var value = dataParser.deserialize(methodArgument.getValue(), methodArgument.getType());
            argumentTypes.add(type);
            arguments.add(value);
        }
        try {
            var method = _instance.getClass().getDeclaredMethod(methodName, argumentTypes.toArray(new Class<?>[0]));
            if(!Void.TYPE.equals(method.getReturnType())) {
                return method.invoke(_instance, arguments.toArray());
            }
            method.invoke(_instance, arguments.toArray());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
}