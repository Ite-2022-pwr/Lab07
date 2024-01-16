package pl.pwr.ite.model.remote;

import lombok.Data;

@Data
public class MethodArgument {
    private Class<?> type;
    private String value;
}
