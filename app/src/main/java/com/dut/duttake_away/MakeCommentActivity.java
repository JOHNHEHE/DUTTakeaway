package com.dut.duttake_away;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import db.tools.Tool;
import entity.Comment;
import proxy.UserProxy;

/**
 * Created by 张浩天
 */

public class MakeCommentActivity extends AppCompatActivity {

    TextView storename;
    Button makeComment;
    EditText commentContent;
    String content;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_comment);
        storename=findViewById(R.id.commenttoStore);
        name=getIntent().getStringExtra("storename");
        storename.setText(name);
        makeComment=findViewById(R.id.bnMakeComment);
        makeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentContent=findViewById(R.id.commentText);
                content=commentContent.getText().toString();
                if(content.equals("")||content.trim().isEmpty()){
                    Toast.makeText(MakeCommentActivity.this, "要有评价内容哦", Toast.LENGTH_SHORT).show();
                }
                else{
                    Tool tool=new Tool();
                    UserProxy userProxy=new UserProxy();
                    tool.add(new Comment(userProxy.getCurrentUser().getUsername(),name,content,userProxy.getCurrentUser().getName()));
                    finish();
                }
            }
        });
    }
}
