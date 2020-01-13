package proxy;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;
import entity.User;
import utils.LogUtil;

/**
 * Created by 张浩天
 */


public class UserProxy {

    public static final String TAG = "UserProxy";

    //登录
    public void login(String username,String password) {
        BmobUser.loginByAccount(username, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    LogUtil.i(TAG,"登录成功啦"+user.getName());
                    return;
                } else {
                    LogUtil.i(TAG,"登录失败啦");
                }
            }
        });
    }



    //获取系统当前登录用户
    public User getCurrentUser(){
        User user = BmobUser.getCurrentUser(User.class);
        if(user != null){
            LogUtil.i(TAG,"本地用户信息" + user.getUsername() + "-");
            return user;
        }else{
            LogUtil.i(TAG,"本地用户为null,请登录。");
        }
        return null;
    }

    public boolean isLogin(){
        if(this.getCurrentUser()!=null){
            return true;
        }
        else{
            return false;
        }
    }

    public void updateUser(User user) {
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    LogUtil.i(TAG,"更新本地用户信息成功");
                } else {
                    LogUtil.i(TAG,"更新本地用户信息失败");
                }
            }
        });
    }

    public void logout(){
        BmobUser.logOut();
    }

}
