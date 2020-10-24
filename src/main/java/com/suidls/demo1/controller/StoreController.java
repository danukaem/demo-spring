package com.suidls.demo1.controller;

import com.suidls.demo1.repository.entity.OrderRequestMessage;
import com.suidls.demo1.repository.entity.PriceWithQuantity;
import com.suidls.demo1.repository.entity.Product;
import com.suidls.demo1.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class StoreController {


    private StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    //    @Cacheable("findProducts")
    @org.springframework.cache.annotation.Cacheable("findAllProducts")
    @GetMapping("/getProducts")
    public ResponseEntity<Iterable<Product>> getProducts() throws InterruptedException {
        Thread.sleep(5000);
        return ResponseEntity.ok(storeService.getProduct());
    }

    @org.springframework.cache.annotation.Cacheable("findProductById")
    @GetMapping("/getProduct/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId) throws InterruptedException {
        Thread.sleep(5000);
        return ResponseEntity.ok(storeService.getProductById(productId));
    }

    //    @Scheduled(fixedRate = 100)
    @Caching(
            evict = {
                    @CacheEvict(value = "findAllProducts", allEntries = true),
                    @CacheEvict(value = "findProductById", allEntries = true)
            }
    )
    @PostMapping("/saveProduct")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        return ResponseEntity.ok(storeService.saveProduct(product));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "findAllProducts", allEntries = true),
                    @CacheEvict(value = "findProductById", allEntries = true)
            }
    )
    @PutMapping("/updateProduct")
    public ResponseEntity<Iterable<Product>> updateProduct(@RequestBody Product product) {
        storeService.updateProduct(product);
        return ResponseEntity.ok(storeService.getProduct());
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "findAllProducts", allEntries = true),
                    @CacheEvict(value = "findProductById", allEntries = true)
            }
    )
    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<Iterable<Product>> deleteProduct(@PathVariable("productId") int productId) {
        storeService.deleteProductById(productId);
        return ResponseEntity.ok(storeService.getProduct());
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "findAllProducts", allEntries = true),
                    @CacheEvict(value = "findProductById", allEntries = true)
            }
    )
    @DeleteMapping("/deleteAllProducts")
    public ResponseEntity<Iterable<Product>> deleteAllProduct() {
        storeService.deleteAllProducts();
        return ResponseEntity.ok(storeService.getProduct());
    }


    @PostMapping("/getPrice")
    public ResponseEntity<Double> getPrice(@RequestBody OrderRequestMessage orderRequestMessage) {
        return ResponseEntity.ok(storeService.getPrice(orderRequestMessage));
    }

    @GetMapping("getPriceList/{productId}/{minValue}/{maxValue}")
    public ResponseEntity<List<PriceWithQuantity>> getPriceList(@PathVariable("productId") int productId, @PathVariable("minValue") int minValue, @PathVariable("maxValue") int maxValue) {

        return ResponseEntity.ok(storeService.getPriceList(productId, minValue, maxValue));
    }

    @GetMapping("/getGeneratedProductId")
    public ResponseEntity<Integer> getGeneratedProductId() {
        return ResponseEntity.ok(storeService.getGeneratedProductId());
    }


}
