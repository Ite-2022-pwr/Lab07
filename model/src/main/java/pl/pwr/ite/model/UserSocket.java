package pl.pwr.ite.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserSocket extends EntityBase {

    private User user;

    private String host;

    private Integer port;
}

