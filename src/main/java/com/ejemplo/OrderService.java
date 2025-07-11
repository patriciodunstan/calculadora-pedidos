package com.ejemplo;

public class OrderService {
    private DiscountRepository repository;
    public OrderService(DiscountRepository repository){
        this.repository = repository;
    }

    public double getTotal(double subtotal, String discount_code, boolean has_express_shipment){
        if(subtotal < 0){
            throw new IllegalArgumentException("Subtotal cannot be negative");
        }

        double discount = repository.getRate(discount_code);
        double shipment = has_express_shipment ? 20.0 : 10.0;
        return (subtotal * (1 - discount)) + shipment;
    }
}