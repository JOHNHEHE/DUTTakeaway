package com.dut.duttake_away;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.List;

import db.tools.SearchAllComment;
import entity.Comment;
import utils.LogUtil;

/**
 * Created by 张浩天
 */

public class CommentActivity extends AppCompatActivity {

    final String TAG="Comment";
    RecyclerView recyclerView;
    SearchAllComment searchAllComment=new SearchAllComment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        recyclerView = findViewById(R.id.recycler_comment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setReverseLayout(true);//布局反向
        recyclerView.setLayoutManager(layoutManager);
        searchAllComment.search(recyclerView);
    }
}
