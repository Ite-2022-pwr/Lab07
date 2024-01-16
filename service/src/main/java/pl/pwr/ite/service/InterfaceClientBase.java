package pl.pwr.ite.service;

import pl.pwr.ite.model.remote.MethodArgument;
import pl.pwr.ite.model.remote.Payload;
import pl.pwr.ite.model.remote.RemoteMethod;
import pl.pwr.ite.service.remote.CommunicationInterface;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public abstract class InterfaceClientBase<I extends CommunicationInterface> extends ClientBase {

    private final Class<I> communicationInterfaceType;
    private final List<Method> interfaceMethods = new ArrayList<>();

    protected InterfaceClientBase(String host, int port, Class<I> communicationInterfaceType) {
        super(host, port);
        this.communicationInterfaceType = communicationInterfaceType;
        getAllMethods();
    }

    protected Object send(String methodName, Object... arguments) {
        var remoteMethod = new RemoteMethod();
        var method = findMethodByName(methodName);
        remoteMethod.setMethodName(method.getName());
        if(arguments != null) {
            for(var argument : arguments) {
                var methodArgument = new MethodArgument();
                methodArgument.setType(argument.getClass());
                methodArgument.setValue(dataParser.serialize(argument));
                remoteMethod.getArguments().add(methodArgument);
            }
        }
        var payload = new Payload();
        payload.setMethod(remoteMethod);
        var serializedData = dataParser.serialize(payload);
        var responseRaw = send(serializedData.getBytes());
        var returnType = method.getReturnType();
        if(returnType == Void.TYPE) {
            return null;
        }
        if(returnType.equals(String.class)) {
            return responseRaw;
        }
        return dataParser.deserialize(responseRaw, returnType);
    }

    private void getAllMethods() {
        var canonicalName = communicationInterfaceType.getCanonicalName();
        try {
            interfaceMethods.addAll(List.of(Class.forName(canonicalName).getDeclaredMethods()));
        } catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException(String.format("No class with name '%s' found.", canonicalName));
        }
    }

    private Method findMethodByName(String methodName) {
        var methods = interfaceMethods.stream().filter(m -> m.getName().equals(methodName)).toList();
        if(methods.size() != 1) {
            throw new IllegalArgumentException(String.format("No unique method with name '%s' found", methodName));
        }
        return methods.get(0);
    }
}
