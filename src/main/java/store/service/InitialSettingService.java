package store.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import store.constant.exception_message.ServiceExceptionMessage;
import store.constant.input.InputSeparator;
import store.domain.Product;
import store.domain.Promotion;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.utility.FileUtility;

public class InitialSettingService {

    private static final int DEFAULT_TIME_VALUE = 0;
    private static final String NULL_PROMOTION_NAME_IN_FILE = "null";

    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public InitialSettingService(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public void initialize(String productsFileName, String promotionFileName) {
        Set<Promotion> initialPromotions = getInitialPromotionByFile(promotionFileName);
        Set<Product> initialProducts = getInitialProductByFile(productsFileName);
        doPromotionsInProductExist(initialProducts, initialPromotions);

        initialPromotions.forEach(promotionRepository::add);
        initialProducts.forEach(productRepository::add);
    }

    private Set<Promotion> getInitialPromotionByFile(String fileName) {
        List<String> rawPromotions = FileUtility.readFileBySpace(fileName);
        rawPromotions.removeFirst();
        return rawPromotions.stream()
                .map(this::parsePromotion)
                .collect(Collectors.toSet());
    }

    private Promotion parsePromotion(String rawPromotion) {
        List<String> promotionParts = List.of(rawPromotion.split(InputSeparator.COMMA.getContent()));
        String name = promotionParts.get(0);
        int purchaseCount = Integer.parseInt(promotionParts.get(1));
        int giftCount = Integer.parseInt(promotionParts.get(2));
        LocalDateTime startDate = parseDate(promotionParts.get(3));
        LocalDateTime endDate = parseDate(promotionParts.get(4));
        return new Promotion(name, purchaseCount, giftCount, startDate, endDate);
    }

    private LocalDateTime parseDate(String rawDate) {
        List<String> dateParts = List.of(rawDate.split(InputSeparator.HYPHEN.getContent()));
        int year = Integer.parseInt(dateParts.get(0));
        int month = Integer.parseInt(dateParts.get(1));
        int day = Integer.parseInt(dateParts.get(2));
        return LocalDateTime.of(year, month, day, DEFAULT_TIME_VALUE, DEFAULT_TIME_VALUE, DEFAULT_TIME_VALUE);
    }

    private Set<Product> getInitialProductByFile(String fileName) {
        List<String> rawProducts = FileUtility.readFileBySpace(fileName);
        rawProducts.removeFirst();
        return parseProduct(rawProducts);
    }

    private Set<Product> parseProduct(List<String> rawProducts) {
        Map<String, Product> productResult = new HashMap<>();
        for (String rawProduct : rawProducts) {
            Product parsedProduct = parseProduct(rawProduct);
            if (productResult.containsKey(parsedProduct.getName())) {
                compositeProduct(productResult.get(parsedProduct.getName()), parsedProduct);
                continue;
            }
            productResult.put(parsedProduct.getName(), parsedProduct);
        }
        return new HashSet<>(productResult.values());
    }

    private Product parseProduct(String rawProduct) {
        List<String> promotionParts = List.of(rawProduct.split(InputSeparator.COMMA.getContent()));
        String name = promotionParts.get(0);
        int price = Integer.parseInt(promotionParts.get(1));
        int quantity = Integer.parseInt(promotionParts.get(2));
        String promotionName = promotionParts.get(3);
        if (promotionName.equals(NULL_PROMOTION_NAME_IN_FILE)) {
            return new Product(name, price, quantity);
        }
        return new Product(name, price, quantity, promotionName);
    }

    private Product compositeProduct(Product originProduct, Product newProduct) {
        if (!originProduct.getName().equals(newProduct.getName())) {
            throw new IllegalArgumentException(ServiceExceptionMessage.NOT_SAME_PRODUCT.getMessage());
        }
        if (newProduct.checkIsPromoted()) {
            newProduct.addCommonQuantity(originProduct.getCommonQuantity());
            return newProduct;
        }
        originProduct.addCommonQuantity(newProduct.getCommonQuantity());
        return originProduct;
    }

    private void doPromotionsInProductExist(Set<Product> products, Set<Promotion> promotions) {
        List<String> promotionNamesInProduct = products.stream()
                .map(Product::getPromotionName)
                .filter(productName -> !productName.isEmpty())
                .toList();
        Set<String> promotionNames = promotions.stream()
                .map(Promotion::getName)
                .collect(Collectors.toSet());
        if (!promotionNames.containsAll(promotionNamesInProduct)) {
            throw new IllegalArgumentException(ServiceExceptionMessage.INVALID_PROMOTIONS_IN_PRODUCT.getMessage());
        }
    }
}
