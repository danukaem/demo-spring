package com.suidls.demo1.service;

import com.suidls.demo1.repository.ProductRepository;
import com.suidls.demo1.repository.entity.OrderRequestMessage;
import com.suidls.demo1.repository.entity.PriceWithQuantity;
import com.suidls.demo1.repository.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreService {
    private ProductRepository productRepository;

    @Autowired
    public StoreService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    @org.springframework.cache.annotation.Cacheable("getAllProducts")
    public Iterable<Product> getProduct() {

        return productRepository.findAll();
    }


    public Product saveProduct(Product product) {

        return productRepository.save(product);
    }


    public Product getProductById(int id) {

        if (productRepository.findById(id).isPresent()) {
            return productRepository.findById(id).get();

        } else {
            return null;

        }
    }

    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    public double getPrice(OrderRequestMessage orderRequestMessage) {
        double totalPrice = 0;
        if (productRepository.findById(orderRequestMessage.productId).isPresent()) {
            Product product = productRepository.findById(orderRequestMessage.productId).get();
            switch (orderRequestMessage.getSelectionQuantityType()) {
                case UNIT: {
                    int noOfCarton = orderRequestMessage.getQuantity() / product.getNoOfUnitsPerCarton();
                    int remainedUnits = orderRequestMessage.getQuantity() % product.getNoOfUnitsPerCarton();
                    if (noOfCarton >= 3) {
                        totalPrice = (noOfCarton * product.getPriceOfCarton() * 0.9) + (remainedUnits * product.getPriceOfCarton() / product.getNoOfUnitsPerCarton() * 1.3);
                    } else {
                        totalPrice = (noOfCarton * product.getPriceOfCarton()) + (remainedUnits * product.getPriceOfCarton() / product.getNoOfUnitsPerCarton() * 1.3);
                    }
                    break;
                }
                case CARTON: {
                    if (orderRequestMessage.getQuantity() >= 3) {
                        totalPrice = (orderRequestMessage.getQuantity() * product.getPriceOfCarton() * 0.9);
                    } else {
                        totalPrice = (orderRequestMessage.getQuantity() * product.getPriceOfCarton());
                    }
                }
                break;
            }
        } else {
            totalPrice = 0;
        }
        return totalPrice;
    }

    public List<PriceWithQuantity> getPriceList(int productId, int minValue, int maxValue) {
        if (productRepository.findById(productId).isPresent()) {
            Product product = productRepository.findById(productId).get();
            Map<Integer, Double> hashMap = new HashMap<>();
            List<PriceWithQuantity> priceWithQuantities = new ArrayList<>();
            for (int i = minValue; i <= maxValue; i++) {
                double totalPrice;
                int noOfCarton = i / product.getNoOfUnitsPerCarton();
                int remainedUnits = i % product.getNoOfUnitsPerCarton();
                if (noOfCarton >= 3) {
                    totalPrice = (noOfCarton * product.getPriceOfCarton() * 0.9) + (remainedUnits * product.getPriceOfCarton() / product.getNoOfUnitsPerCarton() * 1.3);

                } else {
                    totalPrice = (noOfCarton * product.getPriceOfCarton()) + (remainedUnits * product.getPriceOfCarton() / product.getNoOfUnitsPerCarton() * 1.3);

                }
                hashMap.put(i, totalPrice);
            }
            hashMap.forEach((k, v) -> {
                PriceWithQuantity priceWithQuantity = new PriceWithQuantity();
                priceWithQuantity.setQuantity(k);
                priceWithQuantity.setPrice(v);
                priceWithQuantities.add(priceWithQuantity);
            });
            return priceWithQuantities;
        } else {
            return null;
        }


    }


    public int getGeneratedProductId() {
        long count = productRepository.count();
        if (count > 0) {
            return productRepository.generateProductId() + 1;
        } else {
            return 1;
        }
    }

}
