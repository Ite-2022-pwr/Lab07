package pl.pwr.ite.warehouse.service;

import pl.pwr.ite.model.User;
import pl.pwr.ite.model.enums.UserRole;
import pl.pwr.ite.service.RepositoryBase;

public class UserRepository extends RepositoryBase<User> {
    private static UserRepository INSTANCE = null;

    public static UserRepository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }
}
