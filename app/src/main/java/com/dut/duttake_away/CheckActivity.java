package com.dut.duttake_away;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import db.tools.SearchShoppingCart;
import entity.Store;
import entity.User;
import proxy.UserProxy;

/**
 * Created by 陈贤鹏
 */

public class CheckActivity extends AppCompatActivity {

    private Store store = new Store();

    private TextView address_view;

    private TextView name_view;

    private TextView phone_view;

    private TextView reach_time_view;

    private TextView store_name;

    private SearchShoppingCart searchShoppingCart = new SearchShoppingCart();

    private TextView submit_order;

    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_layout);

        //接受页面跳转的数据
        final Intent intent = getIntent();
        String JsonData = intent.getStringExtra("store");
        store = new Gson().fromJson(JsonData, Store.class);

        UserProxy userProxy=new UserProxy();
        //设置地址
        address_view = (TextView) findViewById(R.id.address_view);
        address_view.setText("地址:"+userProxy.getCurrentUser().getAddress());

        //设置姓名
        name_view = (TextView) findViewById(R.id.name_view);
        name_view.setText(userProxy.getCurrentUser().getName());

        //设置电话
        phone_view = (TextView) findViewById(R.id.phone_view);
        phone_view.setText(userProxy.getCurrentUser().getMobilePhoneNumber());

        //设置送达时间
        SimpleDateFormat   formatter   =   new SimpleDateFormat("HH:mm");
        Date curDate =  new Date(System.currentTimeMillis()+store.getDeliveryTime()*60*1000);
        String   str   =   formatter.format(curDate);
        reach_time_view = (TextView) findViewById(R.id.reach_time_view);
        reach_time_view.setText("大约"+str+"送达");

        //设置店名
        store_name = (TextView) findViewById(R.id.store_name);
        store_name.setText(store.getStoreName());

        //显示所点菜品
        searchShoppingCart.SearchShopingCart(getWindow().getDecorView(),store,userProxy.getCurrentUser());//获得当前用户

        //提交订单
        submit_order = (TextView) findViewById(R.id.submit_order);
        submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(CheckActivity.this);
                dialog.setMessage("确认下单？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UserProxy userp=new UserProxy();
                        searchShoppingCart.SearchShoppingCartToOrder(store,userp.getCurrentUser());
                        Intent intent1 = new Intent(CheckActivity.this, StoreActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的 activity
                        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳转的界面
                        startActivity(intent1);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
    }
}
