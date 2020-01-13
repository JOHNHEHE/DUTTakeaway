package com.dut.duttake_away;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import utils.NetworkUtil;

/**
 * Created by 张浩天
 */

public class NetworkActivity extends AppCompatActivity {

    Button btnisConnected;
    boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        btnisConnected=findViewById(R.id.btnisConnected);
        btnisConnected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtil networkUtil=new NetworkUtil();
                isConnected=networkUtil.isConnectedNetwork();
                if(isConnected){
                    Intent intent = new Intent(NetworkActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(NetworkActivity.this, "你还没有网络哦", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
