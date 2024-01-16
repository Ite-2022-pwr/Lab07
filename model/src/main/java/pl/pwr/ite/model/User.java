package pl.pwr.ite.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.pwr.ite.model.enums.UserRole;

import java.util.UUID;

@Getter
@Setter
public class User extends EntityBase {

    private UserRole role;
}
