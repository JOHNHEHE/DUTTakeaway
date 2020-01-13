package entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Dish extends BmobObject {

    private String dishName;//菜名

    private String storeName;//所属店家名字

    private Double price;//价格

    private BmobFile dishPhoto;//菜图片

    private Integer salesCount;//销量

    private Integer number;//份数（默认为0）

    public Dish (){

    }

    public Dish(String dishName,String storeName, Double price, BmobFile dishPhoto, Integer salesCount, Integer number) {
        this.dishName = dishName;
        this.storeName = storeName;
        this.price = price;
        this.dishPhoto = dishPhoto;
        this.salesCount = salesCount;
        this.number = number;
    }

    public Dish(String tableName, String dishName, String storeName, Double price, BmobFile dishPhoto, Integer salesCount, Integer number) {
        super(tableName);
        this.dishName = dishName;
        this.storeName = storeName;
        this.price = price;
        this.dishPhoto = dishPhoto;
        this.salesCount = salesCount;
        this.number = number;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public BmobFile getDishPhoto() {
        return dishPhoto;
    }

    public void setDishPhoto(BmobFile dishPhoto) {
        this.dishPhoto = dishPhoto;
    }

    public Integer getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(Integer salesCount) {
        this.salesCount = salesCount;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}
