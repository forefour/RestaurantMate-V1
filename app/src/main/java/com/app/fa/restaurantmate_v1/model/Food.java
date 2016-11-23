package com.app.fa.restaurantmate_v1.model;

/**
 * Created by Foremost on 23/11/2559.
 */

public class Food {

    String name;
    int price;

    public Food() {
    }

    public Food(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
