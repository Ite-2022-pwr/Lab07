package pl.pwr.ite.model.remote;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RemoteMethod {
    private String methodName;
    private final List<MethodArgument> arguments = new ArrayList<>();
}
