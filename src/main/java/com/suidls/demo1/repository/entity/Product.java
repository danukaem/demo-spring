package com.suidls.demo1.repository.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    @Id
    int productId;
    String productName;
    int noOfUnitsPerCarton;
    double priceOfCarton;


}
