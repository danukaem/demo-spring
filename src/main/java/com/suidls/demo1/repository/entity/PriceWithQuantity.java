package com.suidls.demo1.repository.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PriceWithQuantity {

    int quantity;
    double price;
}
