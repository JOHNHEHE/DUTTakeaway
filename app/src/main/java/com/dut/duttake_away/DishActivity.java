package com.dut.duttake_away;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import adapter.DishAdapter;
import db.tools.SearchDish;
import entity.Store;
import entity.User;
import proxy.UserProxy;

/**
 * Created by 陈贤鹏
 */


public class DishActivity extends AppCompatActivity {

    private  RecyclerView recyclerView;

    private TextView storeName;

    private SearchDish searchDish = new SearchDish();

    private DishAdapter dishAdapter;

    private TextView go_to_check;

    private  Store store = new Store();//菜品所属店家

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dish_layout);

        //接受页面跳转的数据
        Intent intent = getIntent();
        String JsonData = intent.getStringExtra("store");
        store = new Gson().fromJson(JsonData,Store.class);

        //设置菜品页面商家名字
        storeName = (TextView) findViewById(R.id.text_store_name);
        storeName.setText(store.getStoreName());

        //查询该商家所有菜品并显示出来
        User user = new User();
        UserProxy userProxy=new UserProxy();
        user.setUsername(userProxy.getCurrentUser().getUsername());
        searchDish.SearchAllDish(getWindow().getDecorView(), store, userProxy.getCurrentUser());//加载当前用户

        //查询购物车状态并显示出来

        //结算监听
        go_to_check = (TextView) findViewById(R.id.goTOCheckOut);
        go_to_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DishActivity.this, CheckActivity.class);
                intent.putExtra("store",new Gson().toJson(store));
                startActivity(intent);

            }
        });


    }
}
