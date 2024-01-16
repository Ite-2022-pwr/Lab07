package pl.pwr.ite.model.remote;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import pl.pwr.ite.model.User;

@Data
public class Payload {
    @JsonIgnore private User user;
    private RemoteMethod method;
}
