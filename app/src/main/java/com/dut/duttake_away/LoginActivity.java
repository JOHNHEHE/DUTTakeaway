package com.dut.duttake_away;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import proxy.UserProxy;

/**
 * Created by 张浩天
 */

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "");  //输入application key
        initial();
    }

    public void initial() {

        UserProxy userProxy=new UserProxy();
        if(userProxy.isLogin()){
            Intent i=new Intent(LoginActivity.this,StoreActivity.class);
            startActivity(i);
        }
        else{
            Button login =  findViewById(R.id.bnLogin);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText username = findViewById(R.id.userNameText);
                    EditText password = findViewById(R.id.passwdText);
                    UserProxy userProxy=new UserProxy();
                    userProxy.login(username.getText().toString(),password.getText().toString());
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                Thread.sleep(1000);//休眠1秒，等待登录查询结果的更新
                                UserProxy userProxy=new UserProxy();
                                if(userProxy.getCurrentUser()!=null){
                                    Intent i=new Intent(LoginActivity.this,StoreActivity.class);
                                    startActivity(i);
                                }
                                else{
                                    Looper.prepare();
                                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                }
            });
        }




        /*BmobQuery<Store> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.findObjects(new FindListener<Store>() {
            @Override
            public void done(List<Store> object, BmobException e) {
                if (e == null) {
                    Toast.makeText(LoginActivity.this, String.valueOf(object.size()), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "查询失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/




    }

    //登录成功后销毁活动
    @Override
    protected  void onPause() {

        super.onPause();
        this.finish();
    }
}
