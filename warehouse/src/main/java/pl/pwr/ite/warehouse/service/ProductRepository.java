package pl.pwr.ite.warehouse.service;

import pl.pwr.ite.model.Order;
import pl.pwr.ite.model.Product;
import pl.pwr.ite.service.RepositoryBase;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductRepository extends RepositoryBase<Product> {
    private static ProductRepository INSTANCE = null;

    public ProductRepository() {
        for(int i = 0; i < 5; i++) {
            var product = new Product();
            product.setName("P" + i);
            add(product);
        }
    }

    public List<Product> getAllByProductIds(List<UUID> productIds) {
        return entities.stream()
                .filter(e -> !e.isOrdered() && productIds.contains(e.getId().toString()))
                .collect(Collectors.toList());
    }

    public static ProductRepository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ProductRepository();
        }
        return INSTANCE;
    }
}
