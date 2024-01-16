package pl.pwr.ite.service;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.ite.model.User;
import pl.pwr.ite.model.UserSocket;

@Getter
@Setter
public class ApplicationContext {

    private UserSocket registeredSocket;

    private User registeredUser;
}
