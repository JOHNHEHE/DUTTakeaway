package entity;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class ShoppingCart extends BmobObject {

    private String username;//购物车所属用户

    private String storeName;//购物车所属店家

    private List<Dish> dishes;//购物车所含菜品

    public ShoppingCart(){

    }

    public ShoppingCart(String storeName, String username, List<Dish> dishes) {
        this.storeName = storeName;
        this.username = username;
        this.dishes = dishes;
    }

    public ShoppingCart(String tableName, String storeName, String username, List<Dish> dishes) {
        super(tableName);
        this.storeName = storeName;
        this.username = username;
        this.dishes = dishes;
    }

    public String getName() {return username;}

    public void setName(String name) {this.username = name;}

    public String getStoreName() {return storeName;}

    public void setStoreName(String storeName) {this.storeName = storeName;}


    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
