package com.haliri.israj.javaspringbootrabbitmq.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Order implements Serializable {
    private String orderNumber;
    private String productId;
    private double amount;
}
