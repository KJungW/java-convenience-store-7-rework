package store.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.constant.exception_message.RepositoryExceptionMessage;
import store.domain.Product;

public class ProductRepository {

    private Map<String, Product> products = new HashMap<>();

    public void add(Product product) {
        validateDuplicate(product.getName());
        products.put(product.getName(), product);
    }

    public Product find(String productName) {
        validateExistence(productName);
        return products.get(productName).copy();
    }

    public List<Product> findAll() {
        return products.keySet().stream()
                .map(key -> products.get(key))
                .map(Product::copy)
                .toList();
    }

    public void update(Product product) {
        validateExistence(product.getName());
        products.put(product.getName(), product);
    }

    public boolean checkExistence(String productName) {
        return products.containsKey(productName);
    }

    private void validateDuplicate(String productName) {
        if (checkExistence(productName)) {
            throw new IllegalArgumentException(RepositoryExceptionMessage.DATA_ALREADY_EXIST.getMessage());
        }
    }

    private void validateExistence(String productName) {
        if (!checkExistence(productName)) {
            throw new IllegalArgumentException(RepositoryExceptionMessage.DATA_NOT_EXIST.getMessage());
        }
    }
}
