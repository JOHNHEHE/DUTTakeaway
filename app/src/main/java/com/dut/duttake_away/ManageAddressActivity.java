package com.dut.duttake_away;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import entity.User;
import proxy.UserProxy;
import utils.LogUtil;

/**
 * Created by 张浩天
 */

public class ManageAddressActivity extends AppCompatActivity {

    final  String TAG="ManageAddress";
    User currentuser=this.getUser();
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);

        Button chooseDorm =  findViewById(R.id.bnChooseDorm);    //选择宿舍
        chooseDorm.setText(currentuser.getAddress().substring(0,2));
        chooseDorm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               selectDormAlertDialog();
            }
        });

        Button chooseRoom =  findViewById(R.id.bnChooseRoom);    //选择宿舍
        chooseRoom.setText(currentuser.getAddress().substring(2,5));
        chooseRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRoomAlertDialog();
            }
        });

        Button updateAdd =  findViewById(R.id.bnUpdateAdd);
        updateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将地址更新
                updateAdd();
                LogUtil.i(TAG,"用户确认修改地址");
                Intent i=new Intent(ManageAddressActivity.this,PersonalInfoActivity.class);
                i.putExtra("address",address);  //后台更新有延迟，直接将地址传回页面
                startActivity(i);
            }
        });

    }

    public void selectDormAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManageAddressActivity.this,android.R.style.Theme_Holo_Light_Dialog);
        //builder.setIcon(R.drawable);
        builder.setTitle("选择一个宿舍");
        //    指定下拉列表的显示数据
        final String[] dorms = {"一舍", "二舍", "三舍","四舍"};
        //    设置一个下拉的列表选择项
        builder.setItems(dorms, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(ManageAddressActivity.this, "选择的宿舍为：" + dorms[which], Toast.LENGTH_SHORT).show();
                Button c =  findViewById(R.id.bnChooseDorm);
                c.setText(dorms[which]);
            }
        });
        builder.show();
    }

    public void selectRoomAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManageAddressActivity.this,android.R.style.Theme_Holo_Light_Dialog);
        //builder.setIcon(R.drawable);
        builder.setTitle("选择一个房间");
        //指定下拉列表的显示数据
        final String[] rooms = {"100", "101", "102","103","104","105","106","107","108","109","110"
                                ,"201","202","203","204","205","206","207","208","209","210"
                                ,"301","302","303","304","305","306","307","308","309","310"
                                ,"401","402","403","404","405","406","407","408","409","410"};
        //设置一个下拉的列表选择项
        builder.setItems(rooms, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(ManageAddressActivity.this, "选择的房间为：" + rooms[which], Toast.LENGTH_SHORT).show();
                Button c =  findViewById(R.id.bnChooseRoom);
                c.setText(rooms[which]);
            }
        });
        builder.show();
    }

    public void updateAdd(){
        Button dorm =  findViewById(R.id.bnChooseDorm);
        String dormString=dorm.getText().toString();
        Button room =  findViewById(R.id.bnChooseRoom);
        String roommString=room.getText().toString();
        address=dormString+roommString;
        currentuser.setAddress(address);
        UserProxy userProxy=new UserProxy();
        userProxy.updateUser(currentuser);
    }

    protected User getUser(){
        UserProxy userProxy=new UserProxy();
        return userProxy.getCurrentUser();
    }
}
