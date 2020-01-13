package com.dut.duttake_away;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.Bmob;
import db.tools.SearchStore;
import db.tools.Tool;
import entity.Store;

/**
 * Created by 陈贤鹏
 */

public class StoreActivity extends AppCompatActivity {

    private Tool tool = new Tool();

    private SearchStore searchStore = new SearchStore();

    private RecyclerView recyclerView;

    private SearchView searchView;

    private TextView textView;

    private List<Store> storeList =  new ArrayList<Store>();

    private List<String> storeNamelist = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        Bmob.initialize(this, "");  //输入application key


        Button order=findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StoreActivity.this,OrderActivity.class);
                startActivity(i);
                overridePendingTransition(0, android.R.anim.fade_out);
                finish();
            }
        });
        Button home=findViewById(R.id.me);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StoreActivity.this,PersonalInfoActivity.class);
                startActivity(i);
                overridePendingTransition(0, android.R.anim.fade_out);
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_store);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        searchStore.SearchAllStore(recyclerView);


        // 设置搜索文本监听
        searchView = (SearchView) findViewById(R.id.search);
        textView = (TextView) findViewById(R.id.text_hide);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
            public boolean onQueryTextSubmit(String query) {//搜索时触发事件
                return false;
             }
             @Override
             public boolean onQueryTextChange(String newText) {//搜索时根据文本框动态改变搜索内容
                 if (!TextUtils.isEmpty(newText)){
                     searchStore.SearchStore(recyclerView, textView, newText);
                 }else{
                     textView.setVisibility(View.GONE);
                     searchStore.SearchAllStore(recyclerView);
                 }
                  return false;
             }
         });

    }

}
