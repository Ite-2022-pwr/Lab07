package pl.pwr.ite.service;

import pl.pwr.ite.model.EntityBase;
import pl.pwr.ite.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class RepositoryBase<E extends EntityBase> {

    protected final List<E> entities = new ArrayList<>();

    public E findById(UUID id) {
        return entities.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public E add(E entity) {
        if(entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        entities.add(entity);
        return entity;
    }

    public void remove(E entity) {
        entities.remove(entity);
    }

    public void remove(UUID id) {
        remove(findById(id));
    }

    public List<E> findAll() {
        return entities;
    }
}
