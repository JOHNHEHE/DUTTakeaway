package entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Store extends BmobObject {

    private String storeName;//店名

    private String phoneNumber;//联系电话

    private Integer deliveryTime;//配送时间（单位：分钟）

    private Integer salesCount;//销量

    private BmobFile headPhoto;//头像

    public Store(){

    }

    public Store(String storeName, String phoneNumber, Integer deliveryTime, Integer salesCount) {
        this.storeName = storeName;
        this.phoneNumber = phoneNumber;
        this.deliveryTime = deliveryTime;
        this.salesCount = salesCount;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(Integer salesCount) {
        this.salesCount = salesCount;
    }

    public BmobFile getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(BmobFile headPhoto) {
        this.headPhoto = headPhoto;
    }
}
