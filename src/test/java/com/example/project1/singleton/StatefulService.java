package com.example.project1.singleton;

public class StatefulService {

    private int price; // 상태를 유지하는 필드 : 10000 -> 20000

    /*public void order(String name, int price){
        System.out.println("name + \", price = \" + price = " + name + ", price = " + price);
        this.price = price; //여기가 문제!
    }*/

    public int order(String name, int price){
        System.out.println("name + \", price = \" + price = " + name + ", price = " + price);
        return price;
    }

    public int getPrice() {
        return price;
    }
}
