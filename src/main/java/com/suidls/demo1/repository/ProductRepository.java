package com.suidls.demo1.repository;

import com.suidls.demo1.repository.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query(value = "select max(productId) from Product")
    int generateProductId();

}
