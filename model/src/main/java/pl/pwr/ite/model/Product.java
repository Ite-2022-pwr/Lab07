package pl.pwr.ite.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.pwr.ite.model.enums.ProductStatus;

import java.util.UUID;

@Getter
@Setter
public class Product extends EntityBase {

    private String name;

    private boolean ordered = false;

    private ProductStatus status = ProductStatus.Other;
}
