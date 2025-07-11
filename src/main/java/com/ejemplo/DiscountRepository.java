package com.ejemplo;

public class DiscountRepository {
 public double getRate(String code){
    if(code.equals("SALES10")) return 0.1;
    return 0.0;
 }   
}