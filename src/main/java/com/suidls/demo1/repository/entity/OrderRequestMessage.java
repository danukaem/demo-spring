package com.suidls.demo1.repository.entity;


import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestMessage {

    public int productId;
    public SelectionQuantityType selectionQuantityType;
    public int quantity;


    public enum SelectionQuantityType{
        UNIT,CARTON
    }
}
