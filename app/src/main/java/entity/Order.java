package entity;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class Order extends BmobObject {

    private String orderNumber;//订单号

    private List<Dish> dishes;//订单所含菜品


    private String username;//下单的客户

    private String state;//订单状态

    public Order(){

    }

    public Order(String orderNumber, List<Dish> dishes, String username, String state) {
        this.orderNumber = orderNumber;
        this.dishes = dishes;
        this.username = username;
        this.state = state;
    }

    public Order(String tableName, String orderNumber, List<Dish> dishes, String username, String state) {
        super(tableName);
        this.orderNumber = orderNumber;
        this.dishes = dishes;
        this.username = username;
        this.state = state;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public String getName() {
        return username;}

    public void setName(String name) {this.username = name;}

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
