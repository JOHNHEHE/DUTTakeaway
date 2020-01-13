package entity;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject {


    private String username;//评论的用户的学号


    private String name;//评论的用户姓名

    private String storeName;//用户评论的店家

    private String message;//评论内容

    public Comment(){

    }

    public Comment(String username, String storeName, String message,String name) {
        this.username = username;
        this.storeName = storeName;
        this.message = message;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
