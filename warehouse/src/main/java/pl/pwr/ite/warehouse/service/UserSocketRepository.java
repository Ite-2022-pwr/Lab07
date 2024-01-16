package pl.pwr.ite.warehouse.service;

import pl.pwr.ite.model.UserSocket;
import pl.pwr.ite.model.enums.UserRole;
import pl.pwr.ite.service.RepositoryBase;

import java.util.UUID;

public class UserSocketRepository extends RepositoryBase<UserSocket> {
    private static UserSocketRepository INSTANCE = null;

    public UserSocket findByUserId(UUID userId) {
        return entities.stream()
                .filter(e -> e.getUser().getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public UserSocket findByHostAndPort(String host, Integer port) {
        return entities.stream()
                .filter(e ->
                        e.getHost().equals(host)
                        && e.getPort().equals(port)
                ).findFirst().orElse(null);
    }

    public UserSocket findFirstByRole(UserRole role) {
        return entities.stream().filter(e -> e.getUser().getRole().equals(role)).findFirst().orElse(null);
    }

    public static UserSocketRepository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UserSocketRepository();
        }
        return INSTANCE;
    }
}
