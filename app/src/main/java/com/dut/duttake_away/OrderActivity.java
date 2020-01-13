package com.dut.duttake_away;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import db.tools.SearchUserOrder;
import proxy.UserProxy;
import utils.LogUtil;


/**
 * Created by 张浩天
 */

public class OrderActivity extends AppCompatActivity {

    final String TAG = "OrderInformation";
    RecyclerView recyclerView;
    SearchUserOrder searchAllOrder = new SearchUserOrder();
    UserProxy userProxy = new UserProxy();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        Button me = findViewById(R.id.me);
        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i(TAG, "前往我的页面");
                Intent i = new Intent(OrderActivity.this, PersonalInfoActivity.class);
                startActivity(i);
                overridePendingTransition(0, android.R.anim.fade_out);
                finish();
            }
        });

        Button home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i(TAG, "前往主页面");
                Intent i=new Intent(OrderActivity.this,StoreActivity.class);
                startActivity(i);
                overridePendingTransition(0, android.R.anim.fade_out);
                finish();
            }
        });

        Button comment = findViewById(R.id.btnComment);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderActivity.this, CommentActivity.class);
                startActivity(i);
            }
        });

        recyclerView = findViewById(R.id.recycler_order);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setReverseLayout(true);//布局反向
        recyclerView.setLayoutManager(layoutManager);
        searchAllOrder.setRecyclerView(recyclerView);
        searchAllOrder.search(userProxy.getCurrentUser().getUsername());




        /*Order o=new Order();
        o.setObjectId("XjET888O");
        List<Dish> d=new ArrayList<>();
        d.add(new Dish("火鸡饭","金饭村", 18.0, null, null, 2));
        //d.add(new Dish("龙虾炒饭","西爷龙虾", 18.0, null, null, 1));
        o.setDishes(d);
        o.update(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    LogUtil.i(TAG,"更新成功");
                }else{
                    LogUtil.i(TAG,"更新失败："+e.getMessage());
                }
            }

        });*/
    }




}
